-- 消息内容表
create table chat_message_content
(
    id varchar(100) not null comment '消息的Id',
    content varchar(500) null comment '消息内容',
    type int null comment '消息类比(文本、图片、网址、视频等等)',
    create_time datetime null comment '消息产生时间',
    constraint chat_content_pk
        primary key (id)
);

-- 消息索引表
create table chat_message_index
(
    sender_id bigint unsigned null comment '发送者的id',
    receiver_id bigint unsigned null comment '接受者的id',
    chat_message_id varchar(100) not null comment '聊天信息id',
    constraint chat_message_index_pk
        primary key (chat_message_id)
);

-- 为朋友关系添加字段,最后一条消息
alter table friend_info
    add last_message varchar(500) null comment '最后一条消息';