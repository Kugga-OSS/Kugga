-- 用户表
create table kugga_user
(
    uid bigint unsigned auto_increment comment '用户uid',
    username varchar(200) not null comment '用户名',
    display_name varchar(200) not null comment '显示的用户名',
    password varchar(200) not null comment '密码',
    avatar varchar(500) not null default 'https://kugga-storage.oss-cn-hangzhou.aliyuncs.com/avatar/default.png' comment '头像url',
    email varchar(200) not null comment '邮件地址',
    is_blocked tinyint(1) null comment '账号是否未被激活',
    constraint kugga_user_pk
        primary key (uid)
);

create index idx_username_password_is_blocked
    on kugga_user (username, password, is_blocked);

-- 消息内容表
create table kugga_message_content
(
    mid bigint unsigned auto_increment comment '消息id',
    content varchar(500) not null comment '消息内容',
    sender_id bigint unsigned not null comment '发送人uid(冗余)',
    receiver_id bigint unsigned not null comment '接受者uid(冗余)',
    msg_type int not null comment '消息类型',
    create_time timestamp not null,
    constraint kugga_message_content_pk
        primary key (mid)
);

-- 消息索引表
create table kugga_message_relation
(
    owner_uid bigint unsigned not null comment '消息拥有者',
    other_uid bigint unsigned not null comment '关系第二方',
    mid bigint unsigned not null comment '消息mid',
    is_sender tinyint(1) not null comment '是发件人还是收件人',
    create_time timestamp not null,
    constraint kugga_message_relation_pk
        primary key (owner_uid, mid)
);

-- 用户关系表
create table kugga_user_relation
(
    owner_uid bigint unsigned not null comment '关系拥有者',
    other_uid bigint unsigned not null comment '关系第二方',
    last_mid bigint unsigned null comment '最后一条消息',
    is_pass tinyint(2) not null comment '0表示未通过,1表示等待中,2表示通过',
    create_time timestamp not null,
    constraint kugga_user_relation_pk
        unique (owner_uid, other_uid)
);