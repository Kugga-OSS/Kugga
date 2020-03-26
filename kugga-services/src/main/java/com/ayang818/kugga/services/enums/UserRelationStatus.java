package com.ayang818.kugga.services.enums;

/**
 * @author 杨丰畅
 * @description 这里的状态机是如下（下文请求方称为owner，被请求方成为other）
 * 1. owner请求添加好友时候，表中添加两条记录，owner的status均为 success，other的status位 waiting
 * 2. other同意时将自己作为owner那条记录的status设置为success，此时两条记录的status都为success，双方好友关系建立
 * 3. other拒绝时将自己作为owner那条记录的status设置为fail，此时两条记录的status一条为success，一条为fail，双方好友关系建立失败
 * 4. 好友关系建立失败后，并不删除relation表中的数据，因为请求方需要知道自己请求失败。下一次添加好友的时候先检查，有没有存在申请记录，
 * 有的话则更新，没有的话则插入。
 * 5. owner发出好友申请后，处理完业务逻辑后尝试通过redis发布这条消息，若对方在线，直接推送过去这个请求，在界面上显示
 * 若不在线，则在对方下一次上线时主动拉去未处理的消息并提示
 * @date 2020/3/25 21:37
 **/
public interface UserRelationStatus {
    /**
     * 添加失败
     */
    Byte FAIL = 0;

    /**
     * 等待同意
     */
    Byte WAITING = 1;

    /**
     * 双方都同意成为好友
     */
    Byte SUCCESS = 2;
}
