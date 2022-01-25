package com.bil.account.utils;

import com.bil.account.contants.AccountConstants;
import com.bil.account.contants.AccountConstants.AccountType;
import com.bil.account.model.param.AccountTransferReq;
import com.bil.account.service.UidService;
import com.google.common.collect.Maps;
import org.joda.time.LocalDateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 账本工具类
 *
 * @author haibo.yang   <bobyang_coder@163.com>
 * @since 2022/01/13
 */
public class BookkeepingUtils {

    public static final String OBJECT_NO = "bozige";


    /**
     * 交易时间序号
     */
    private static final Map<String, AtomicInteger> TRADE_DATE_SEQ = Maps.newHashMap();


    public static AccountTransferReq build(String tradeDate, AccountType fromType, AccountType toType, String yuan, String note) {
        AtomicInteger seq = TRADE_DATE_SEQ.computeIfAbsent(tradeDate, s -> new AtomicInteger(0));
        String tradeNo = "bozige-pbook-" + tradeDate + seq.addAndGet(1);
        Date tradeTime = LocalDateTime.parse(tradeDate, ISODateTimeFormat.yearMonthDay()).toDate();
        return AccountTransferReq.builder()
                .tradeNo(tradeNo)
                .commandType("bozige-pbook")
                .transferType(AccountConstants.TransferType.bill.getCode())
                .tradeType("bookkeeping")
                .tradeTime(tradeTime)
                .fromObjectNo(OBJECT_NO)
                .fromAccountType(String.valueOf(fromType.getCode()))
                .toObjectNo(OBJECT_NO)
                .toAccountType(String.valueOf(toType.getCode()))
                .amount(AmountUtils.yuan2Fen(yuan))
                .note(note)
                //自动开户
                .autoOpenAcc(true)
                //可为负数
                .canNegative(true)
                .build();
    }

    public static String buildAccountNo(String accountType) {
        AccountType type = AccountType.findByCode(accountType);
        Assert.notNull(type, "账户类型非法");
        return UidService.$.generateAccountNo(OBJECT_NO, type);
    }

}
