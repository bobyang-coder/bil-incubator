# 清空数据
delete FROM ACCOUNT ;
delete FROM ACCOUNT_COMMAND ;
delete FROM ACCOUNT_FLOW  ;
delete FROM ACCOUNT_VOUCHER  ;

# 流水试算平衡
SELECT sum(credit_amount),sum(debit_amount) FROM ACCOUNT_FLOW ;

# 统计借贷账户余额
SELECT direction,sum(balance) FROM ACCOUNT group by direction;

# 查看流水
SELECT f.debit_amount, f.credit_amount, f.prefix_balance, f.balance, t.trade_time, t.abstract_content
FROM ACCOUNT_FLOW f
         inner join ACCOUNT_VOUCHER t on f.voucher_no = t.voucher_no
where f.account_no = '100001000000bozige110003';


SELECT f.debit_amount, f.credit_amount, f.prefix_balance, f.balance, t.trade_time, t.abstract_content
FROM ACCOUNT_FLOW f
         inner join ACCOUNT_VOUCHER t on f.voucher_no = t.voucher_no
where f.account_no = '100001000000bozige410004';

# 借款
SELECT f.debit_amount, f.credit_amount, f.prefix_balance, f.balance, t.trade_time, t.abstract_content
FROM ACCOUNT_FLOW f
         inner join ACCOUNT_VOUCHER t on f.voucher_no = t.voucher_no
where f.account_no = '100001000000bozige210002';


# 查询资产和负债
select * FROM ACCOUNT where account_type like '1%' or account_type like '2%';


# 统计时间段内借贷金额
select account_no as accountNo,sum(credit_amount) as cAmount,sum(debit_amount) as dAmount FROM ACCOUNT_FLOW where trade_time >='2021-01-01 00:00:00' and trade_time <'2022-01-01 00:00:00' group by account_no;