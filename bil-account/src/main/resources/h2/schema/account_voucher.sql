create table if not exists `account_voucher`
(
    `id`              bigint auto_increment not null comment '主键'
        primary key,
    `voucher_no`      varchar(50)           not null comment '记账凭证号',
    `from_account_no` varchar(25)           not null comment '转出账户号',
    `to_account_no`   varchar(25)           not null comment '转入账户号',
    `amount`          bigint                not null comment '交易金额',
    `currency`        varchar(8)            not null comment '币种',
    `transfer_type`   varchar(10)           not null comment '转账类型',
    `trade_no`        varchar(50)           not null comment '交易流水号',
    `trade_time`      timestamp             not null default CURRENT_TIMESTAMP comment '交易时间',
    `trade_type`      varchar(50)           not null comment '交易类型',
    `accounting_code` varchar(20)           not null default '' comment '记账码',
    `result_code`     varchar(10)           not null default '' comment '结果码',
    `result_note`     varchar(10)           not null default '' comment '结果备注',
    `create_time`     timestamp                      default CURRENT_TIMESTAMP not null comment '创建时间',
    `version`         varchar(10)           not null comment '版本号',
    unique key `uniq_voucher_no` (`voucher_no`)
)
    comment '记账凭证表';