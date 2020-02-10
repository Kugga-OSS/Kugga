package com.ayang818.kugga.services.pojo

import lombok.Builder

@Builder
data class MsgDto(var senderUid : Long,
                  var receiverUid : Long,
                  var content : String,
                  /**
                   * 消息内容类型 : 文本、文件、图片等
                   */
                  var contentType : Int,
                  /**
                   * 消息类型 : ACK、发送新消息、心跳包、注册连接等
                   */
                  var msgType : Int)