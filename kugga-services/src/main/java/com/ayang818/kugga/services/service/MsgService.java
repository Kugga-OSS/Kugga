package com.ayang818.kugga.services.service;

import com.ayang818.kugga.services.pojo.MsgDto;
import com.ayang818.kugga.services.pojo.vo.MsgVo;

import java.util.List;

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
     * 获取消息
     */
    List<MsgVo> fetchMsg();
}
