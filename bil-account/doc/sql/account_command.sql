create table if not exists `account_command`
(
    `id`               bigint auto_increment               not null comment '主键'
        primary key,
    `command_no`       varchar(50)                         not null comment '记账指令号',
    `command_trade_no` varchar(50)                         not null comment '记账交易号',
    `trade_time`       datetime                            not null comment '交易时间',
    `command_type`     varchar(10)                         not null comment '记账指令类型',
    `command_note`     varchar(100)                        not null comment '记账指令备注',
    `update_time`      timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
    `create_time`      timestamp default CURRENT_TIMESTAMP not null comment '创建时间',
    `version`          varchar(10)                         not null comment '版本号',
    unique key `uniq_command_trade_no_command_type` (`command_trade_no`, `command_type`)
) comment '记账指令表';