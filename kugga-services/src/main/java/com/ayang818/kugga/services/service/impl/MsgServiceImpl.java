package com.ayang818.kugga.services.service.impl;

import com.ayang818.kugga.services.enums.MsgType;
import com.ayang818.kugga.services.mapper.*;
import com.ayang818.kugga.services.pojo.MsgDto;
import com.ayang818.kugga.services.pojo.model.*;
import com.ayang818.kugga.services.pojo.vo.MsgListVo;
import com.ayang818.kugga.services.pojo.vo.MsgTmp;
import com.ayang818.kugga.utils.JsonUtil;
import com.ayang818.kugga.utils.enums.ContentType;
import com.ayang818.kugga.services.pojo.vo.MsgVo;
import com.ayang818.kugga.services.service.MsgService;
import com.ayang818.kugga.utils.enums.RedisKeyPartConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author 杨丰畅
 * @description TODO
 * @date 2020/1/23 15:20
 **/
@Component
public class MsgServiceImpl implements MsgService {

    public static final Logger logger = LoggerFactory.getLogger(MsgServiceImpl.class);

    @Autowired
    MessageMapper messageMapper;

    @Autowired
    MsgExtMapper msgExtMapper;

    @Autowired
    MessageRelationMapper messageRelationMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserRelationMapper userRelationMapper;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Override
    public MsgVo sendMsg(MsgDto msgDto) {
        Long sUid = msgDto.getSenderUid();
        Long rUid = msgDto.getReceiverUid();
        // 暂时全部为文本
        Integer contentType = msgDto.getContentType();
        String msgContent = msgDto.getContent();
        Date currentTime = new Date(System.currentTimeMillis());

        /* 存消息内容 */
        Message message = new Message();
        message.setSenderId(sUid);
        message.setReceiverId(rUid);
        message.setContent(msgContent);
        message.setMsgType(ContentType.TEXT);
        message.setCreateTime(currentTime);
        messageMapper.insert(message);
        Long mid = message.getMid();

        /* 存消息关系1,s-r */
        MessageRelation messageRelationS2R = new MessageRelation();
        messageRelationS2R.setOwnerUid(sUid);
        messageRelationS2R.setOtherUid(rUid);
        messageRelationS2R.setIsSender(true);
        messageRelationS2R.setMid(mid);
        messageRelationS2R.setCreateTime(currentTime);
        messageRelationMapper.insert(messageRelationS2R);

        /* 存消息关系2,r-s */
        MessageRelation messageRelationR2S = new MessageRelation();
        messageRelationR2S.setOwnerUid(rUid);
        messageRelationR2S.setOtherUid(sUid);
        messageRelationR2S.setIsSender(false);
        messageRelationR2S.setMid(mid);
        messageRelationR2S.setCreateTime(currentTime);
        messageRelationMapper.insert(messageRelationR2S);

        /* 存sUid-rUid关系, 更新最后的mid */
        UserRelationExample userRelationExample1 = new UserRelationExample();
        userRelationExample1.createCriteria().andOwnerUidEqualTo(sUid).andOtherUidEqualTo(rUid);
        List<UserRelation> userRelations1 = userRelationMapper.selectByExample(userRelationExample1);
        if (userRelations1 != null && !userRelations1.isEmpty()) {
            UserRelation userRelation = userRelations1.get(0);
            userRelation.setLastMid(mid);
            userRelationMapper.updateByExample(userRelation, userRelationExample1);
        }

        /* 存rUid-sUid关系, 更新最后的mid */
        UserRelationExample userRelationExample2 = new UserRelationExample();
        userRelationExample2.createCriteria().andOwnerUidEqualTo(rUid).andOtherUidEqualTo(sUid);
        List<UserRelation> userRelations2 = userRelationMapper.selectByExample(userRelationExample2);
        if (userRelations2 != null && !userRelations2.isEmpty()) {
            UserRelation userRelation = userRelations2.get(0);
            userRelation.setLastMid(mid);
            userRelationMapper.updateByExample(userRelation, userRelationExample2);
        }

        /* 更新未读消息*/
        String totalKey = rUid.toString() + RedisKeyPartConstants.TOTAL_UNRAED;
        String personalKey = rUid.toString() + RedisKeyPartConstants.PERSONAL_UNREAD;
        // 变更总未读消息
        redisTemplate.opsForValue().increment(totalKey, 1);
        // logger.info("总未读消息变更为{}", redisTemplate.opsForValue().get(totalKey));
        // 变更会话未读消息
        redisTemplate.opsForHash().increment(personalKey, sUid.toString(), 1);
        // logger.info("与用户{}未读消息变更为{}", sUid.toString(), redisTemplate.opsForHash().get(personalKey, sUid.toString()));

        User sender = userMapper.selectByPrimaryKey(sUid);
        User receiver = userMapper.selectByPrimaryKey(rUid);
        MsgVo msgVo = MsgVo.builder()
                .mid(mid)
                .content(msgContent)
                .senderName(sender.getDisplayName())
                .receiverName(receiver.getDisplayName())
                .senderUid(sUid)
                .receiverUid(rUid)
                .createTime(currentTime)
                .type(contentType)
                .msgType(MsgType.NEWMSG)
                .build();

        /* 将消息发布到Redis中 */
        redisTemplate.convertAndSend(RedisKeyPartConstants.WBE_SOCKET_TOPIC, JsonUtil.toJson(msgVo));

        return msgVo;
    }

    @Override
    public MsgListVo fetchMsg(Long ownerUid, Long otherUid) {
        /*
        * 这里需要的是senderUid，receiverUid，mid，content， createTime这几项最基本的信息
        */
        List<MsgTmp> msgTmpList = msgExtMapper.fetchMsg(ownerUid, otherUid);
        if (msgTmpList == null || msgTmpList.size() == 0) {
            return MsgListVo.builder()
                    .state(1)
                    .msgList(new ArrayList<>())
                    .build();
        }
        List<MsgVo> msgVoList = new LinkedList<>();
        for (MsgTmp msgTmp : msgTmpList) {
            MsgVo msgVo = MsgVo.builder()
                    .createTime(msgTmp.getCreateTime())
                    .mid(msgTmp.getMid())
                    .content(msgTmp.getContent())
                    // 要是这条消息关系的拥有者是发送方，那么这条关系的Owner是sender，Other是receiver
                    .receiverUid(msgTmp.getSender() == 1 ? msgTmp.getOtherUid() :
                            msgTmp.getOwnerUid())
                    .senderUid(msgTmp.getSender() == 1 ? msgTmp.getOwnerUid() :
                            msgTmp.getOtherUid())
                    .build();
            msgVoList.add(msgVo);
        }
        return MsgListVo.builder()
                .msgList(msgVoList)
                .state(1)
                .build();
    }

}
