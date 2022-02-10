create table if not exists `blood_relationship`
(
    `id`                bigint auto_increment               not null comment '主键'
        primary key,
    `node`              varchar(100)                        not null comment '节点',
    `node_type`         varchar(100)                        not null comment '节点类型',
    `category`          varchar(100)                        not null comment '分类',
    `tag`               varchar(100)                        not null comment '标签',
    `source`            varchar(100)                        not null comment '来源',
    `relationship_desc` varchar(100)                        not null comment '关系描述',
    `create_time`       timestamp default CURRENT_TIMESTAMP not null comment '创建时间',
    `update_time`       timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
    `version`           varchar(10)                         not null comment '版本号',
    index `idx_node` (`node`)
)
    comment '血缘关系表';