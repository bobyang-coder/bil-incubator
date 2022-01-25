create table if not exists `account`
(
    `id`                  bigint auto_increment not null comment '主键'
        primary key,
    `account_no`          varchar(30)                    default '' not null comment '账户号',
    `account_name`        varchar(255)          not null comment '账户名称',
    `account_type`        varchar(8)            not null comment '账户类型',
    `balance`             bigint                not null default 0 comment '余额',
    `currency`            char                  not null default '1' comment '币种(1:人民币,2:积分,3:代金券,4:美元,5:台币)',
    `overdraft`           char                  not null default '1' comment '是否可透支，1 可透支，0 不可透支',
    `direction`           char                  not null comment '借贷方向(D:debit - 借记账户， C:credit - 贷记账户)',
    `accounting_subjects` varchar(10)           not null comment '会计科目',
    `account_level`       char                  not null comment '账户级别',
    `status`              varchar(10)           not null comment '账户状态: normal - 正常， freeze - 冻结 , closed - 关闭',
    `object_no`           varchar(25)           not null comment '对象号',
    `update_time`         timestamp                      default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
    `create_time`         timestamp                      default CURRENT_TIMESTAMP not null comment '创建时间',
    `version`             varchar(10)           not null comment '版本号',
    unique key `uniq_account_no` (`account_no`),
    index `idx_object_no` (`object_no`)
)
    comment '账户表';




