create table if not exists `account_voucher`
(
    `id`               bigint auto_increment not null comment '主键'
        primary key,
    `voucher_no`       varchar(50)           not null comment '记账凭证号',
    `from_object_no`   varchar(25)           not null comment '借记账户号',
    `from_account_no`  varchar(25)           not null comment '借记账户号',
    `to_object_no`     varchar(25)           not null comment '贷记账户号',
    `to_account_no`    varchar(25)           not null comment '贷记账户号',
    `amount`           bigint                not null comment '交易金额',
    `currency`         varchar(8)            not null comment '币种',
    `transfer_type`    varchar(10)           not null comment '转账类型',
    `trade_no`         varchar(50)           not null comment '交易流水号',
    `trade_time`       timestamp             not null default CURRENT_TIMESTAMP comment '交易时间',
    `trade_type`       varchar(50)           not null comment '交易类型',
    `note`             varchar(100)          not null comment '记账备注',
    `accounting_code`  varchar(20)           not null default '' comment '记账码',
    `result_code`      varchar(10)           not null default '' comment '结果码',
    `result_note`      varchar(10)           not null default '' comment '结果备注',
    `abstract_content` varchar(100)          not null comment '摘要',
    `create_time`      timestamp                      default CURRENT_TIMESTAMP not null comment '创建时间',
    `update_time`      timestamp                      default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
    `version`          varchar(10)           not null comment '版本号',
    unique key `uniq_voucher_no` (`voucher_no`),
    index `idx_trade_no` (`trade_no`),
    index `idx_trade_time` (`trade_time`)
)
    comment '记账凭证表';