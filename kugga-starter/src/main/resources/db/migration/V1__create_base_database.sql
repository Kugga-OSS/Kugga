-- 用户信息
create table user_info
(
    id bigint unsigned auto_increment primary key,
    username varchar(20) null comment '用户名，长度最多为20',
    password varchar(40) null comment '密码，长度最长为40',
    avatar_url varchar(100) null comment '头像的url地址',
    mail_address varchar(40) null comment '邮箱地址，长度最长为40'
);

-- 聊天记录
create table chat_info
(
    id varchar(100) primary key comment '使用snowflake流水号生成的全局唯一ID',
    sender_id bigint unsigned null comment '发送者的ID',
    receiver_id bigint unsigned null comment '签收者的ID',
    message_content varchar(400) null comment '消息长度最多为400',
    is_signed boolean null comment '消息是否已被签收，true为已签收，false为未签收'
);

create index idx_receiverId_isSigned_messageContent_senderId
    on chat_info (receiver_id, is_signed, message_content, sender_id);

-- 朋友关系
create table friend_info
(
    id bigint unsigned auto_increment primary key ,
    owner_id bigint unsigned null comment '发送者的ID',
    friend_id bigint unsigned null comment '签收者的ID',
    pending_status tinyint null comment '发出好友请求的状态，1为request，2为success，若不同意或忽略，则删除。(注意这里的关系时双向的，增加和删除时都是两条的操作)'
);

create index idx_ownerId_friendId_pendingStatus
    on friend_info (owner_id, pending_status, friend_id);