-- 用户信息
create table user_info
(
    id bigint unsigned auto_increment primary key,
    username varchar(20) null comment '用户名，长度最多为20',
    password varchar(40) null comment '密码，长度最长为40',
    avatar_url varchar(100) null comment '头像的url地址',
    mail_address varchar(40) null comment '邮箱地址，长度最长为40'
);

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