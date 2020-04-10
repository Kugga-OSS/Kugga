package com.ayang818.kugga.services.service;

import com.ayang818.kugga.services.pojo.MsgDto;
import com.ayang818.kugga.services.pojo.vo.MsgListVo;
import com.ayang818.kugga.services.pojo.vo.MsgVo;

/**
 * @author 杨丰畅
 * @description 消息对象的操作
 * @date 2020/1/23 15:19
 **/
public interface MsgService {

    /**
     * 发送消息
     */
    MsgVo sendMsg(MsgDto msgDto);


    /**
     * @description 拉取某两人之间的消息
     * @param ownerUid
     * @param otherUid
     * @return
     */
    MsgListVo fetchMsg(Long ownerUid, Long otherUid);
}
