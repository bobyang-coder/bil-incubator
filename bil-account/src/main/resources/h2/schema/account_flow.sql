create table if not exists `account_flow`
(
    `id`                  bigint auto_increment               not null comment '主键'
        primary key,
    `voucher_no`          varchar(255)                        not null comment '凭证号',
    `account_no`          varchar(30)                         not null comment '账户号',
    `balance`             bigint                              not null comment '本期余额',
    `prefix_balance`      varchar(20)                         not null comment '期初余额',
    `credit_amount`       bigint                              not null comment '贷记金额',
    `debit_amount`        bigint                              not null comment '借记金额',
    `opposite_account_no` varchar(30)                         not null comment '对端账户号',
    `create_time`         timestamp default CURRENT_TIMESTAMP not null comment '创建时间',
    `version`             varchar(10)                         not null comment '版本号',
    unique key `uniq_voucher_no_account_no` (`voucher_no`, `account_no`)
)
    comment '账务流水表';