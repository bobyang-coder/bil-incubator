package com.bil.account.utils;

import com.bil.account.contants.AccountConstants;
import com.bil.account.contants.AccountConstants.Direction;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 金额工具类
 *
 * @author haibo.yang   <bobyang_coder@163.com>
 * @since 2022/01/13
 */
public class AmountUtils {

    private static final BigDecimal B_100 = new BigDecimal(100);

    public static Long yuan2Fen(String yuan) {
        return new BigDecimal(yuan).multiply(B_100).longValue();
    }

    public static Long yuan2Fen(Long yuan) {
        return new BigDecimal(yuan).multiply(B_100).longValue();
    }

    /**
     * 分转元
     *
     * @param yuan
     * @return
     */
    public static String fen2Yuan(Long yuan) {
        if (null == yuan) {
            return "0";
        }
        return new BigDecimal(yuan).divide(B_100, 2, RoundingMode.UNNECESSARY).toString();
    }

    /**
     * 判断交易金额
     *
     * @param accountType
     * @param creditAmount
     * @param debitAmount
     * @return
     */
    public static Long judgeTradeAmount(AccountConstants.AccountType accountType, Long creditAmount, Long debitAmount) {
        Direction direction = accountType.getAccountingSubject().getDirection();
        long amount = 0;
        //同增异减
        if (creditAmount != 0) {
            amount = Direction.C.equals(direction) ? creditAmount : -creditAmount;
        } else if (debitAmount != 0) {
            amount = Direction.D.equals(direction) ? debitAmount : -debitAmount;
        }
        return amount;
    }
}
