create table short_url
(
    `id`           bigint auto_increment not null
        primary key,
    `short_url`    varchar(20)           not null default '' comment '短连接',
    `origin_url`   text                  not null default '' comment '原始连接',
    `origin_hash`  varchar(32)           not null default '' comment '原始连接hash值',
    `close_access` tinyint(1)            not null default 0 comment '关闭访问',
    `update_time`  timestamp             not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
    `create_time`  timestamp             not null default CURRENT_TIMESTAMP comment '创建时间',
    index 'idx_origin_hash' (`origin_hash`)
) engine innodb comment '短链表';