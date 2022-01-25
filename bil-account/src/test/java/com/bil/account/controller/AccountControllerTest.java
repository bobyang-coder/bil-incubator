package com.bil.account.controller;

import com.bil.account.ApplicationTests;
import com.bil.account.contants.AccountConstants;
import com.bil.account.contants.AccountConstants.AccountType;
import com.bil.account.controller.api.AccountController;
import com.bil.account.model.param.AccountTransferReq;
import com.bil.account.utils.BookkeepingUtils;
import com.google.common.collect.Maps;
import org.joda.time.LocalDateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * AccountController Test
 *
 * @author haibo.yang   <bobyang_coder@163.com>
 * @since 2021/12/16
 */
class AccountControllerTest extends ApplicationTests {

    private static final String OBJECT_NO = "bob";

    private static final Map<String, AtomicInteger> tradeDateSeq = Maps.newHashMap();

    @Resource
    private AccountController accountController;



    @Test
    void transfer() {
        accountController.transfer(BookkeepingUtils.build("2021-12-10", AccountType.DEPOSIT, AccountType.SALARY, "1", "剩余工资"));
        accountController.transfer(BookkeepingUtils.build("2021-12-10", AccountType.DEPOSIT, AccountType.SOCIAL_SECURITY, "2", "社保提现"));
        accountController.transfer(BookkeepingUtils.build("2021-12-10", AccountType.DEPOSIT, AccountType.SALARY, "3", "2021-11薪资"));
        accountController.transfer(BookkeepingUtils.build("2021-12-15", AccountType.DEPOSIT, AccountType.BANK_LOAD, "4", "招商闪电贷"));
        accountController.transfer(BookkeepingUtils.build("2021-12-18", AccountType.HOUSE_LOAN, AccountType.DEPOSIT, "5.67", "小额贷12月份"));
        accountController.transfer(BookkeepingUtils.build("2021-12-20", AccountType.DEPOSIT, AccountType.BANK_LOAD, "6", "招商闪电贷"));
        accountController.transfer(BookkeepingUtils.build("2021-12-20", AccountType.CREDIT_CONSUMPTION, AccountType.DEPOSIT, "7", "还招商信用卡"));
        accountController.transfer(BookkeepingUtils.build("2021-12-25", AccountType.GIFT_GIVING, AccountType.DEPOSIT, "8", "大师兄结婚"));
    }

    public static AccountTransferReq build(String tradeDate, AccountType fromType, AccountType toType, String yuan, String note) {
        AtomicInteger seq = tradeDateSeq.computeIfAbsent(tradeDate, s -> new AtomicInteger(0));
        String tradeNo = "bob-bill-" + tradeDate + seq.addAndGet(1);
        Date tradeTime = LocalDateTime.parse(tradeDate, ISODateTimeFormat.yearMonthDay()).toDate();
        return AccountTransferReq.builder()
                .tradeNo(tradeNo)
                .commandType("bob-bill")
                .transferType(AccountConstants.TransferType.bill.getCode())
                .tradeType("bill")
                .tradeTime(tradeTime)
                .fromObjectNo(OBJECT_NO)
                .fromAccountType(String.valueOf(fromType.getCode()))
                .toObjectNo(OBJECT_NO)
                .toAccountType(String.valueOf(toType.getCode()))
                .amount(new BigDecimal(yuan).multiply(new BigDecimal(100)).longValue())
                .note(note)
                .autoOpenAcc(true)
                .build();
    }
}