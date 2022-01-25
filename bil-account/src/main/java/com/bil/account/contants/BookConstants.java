package com.bil.account.contants;

import com.bil.account.contants.AccountConstants.AccountType;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * 账本常量
 *
 * @author haibo.yang   <bobyang_coder@163.com>
 * @since 2022/01/13
 */
public interface BookConstants {


    /**
     * 记账类型
     */
    @Getter
    enum BookkeepingType {

        /**
         * 资产来源
         */
        SALARY("101", "发工资", AccountType.DEPOSIT, AccountType.SALARY),
        WITHDRAW_SOCIAL_SECURITY("102", "社保提现", AccountType.DEPOSIT, AccountType.SOCIAL_SECURITY),
        CMB_BANK_LOAD("103", "招商闪电贷", AccountType.DEPOSIT, AccountType.CMB_BANK_LOAD),
        PERSONAL_LOAD("104", "个人借款", AccountType.DEPOSIT, AccountType.PERSONAL_LOAD),
        CMB_CREDIT_PAYMENT_RETURN("105", "招商信用卡代付归还", AccountType.DEPOSIT, AccountType.CMB_CREDIT_CONSUMPTION),

        /**
         * 资产消耗
         */
        HOUSE_LOAN("201", "还房贷", AccountType.HOUSE_LOAN, AccountType.DEPOSIT),
        CMB_CREDIT_CONSUMPTION("202", "招商信用卡还款", AccountType.CMB_CREDIT_CONSUMPTION, AccountType.DEPOSIT),
        GIFT_GIVING("203", "送礼", AccountType.GIFT_GIVING, AccountType.DEPOSIT),
        ROOM_CHARGE("204", "房租", AccountType.ROOM_CHARGE, AccountType.DEPOSIT),
        CMB_BANK_LOAD_REPAYMENT("205", "招商闪电贷还款", AccountType.CMB_BANK_LOAD, AccountType.DEPOSIT),
        DAILY_EXPENSES("206", "日常消费", AccountType.DAILY_EXPENSES, AccountType.DEPOSIT),
        PERSONAL_LOAD_RETURN("207", "个人借款还款", AccountType.PERSONAL_LOAD, AccountType.DEPOSIT),
        ;

        private String code;

        private String name;

        /**
         * 借
         */
        private AccountType d;

        /**
         * 贷
         */
        private AccountType c;

        BookkeepingType(String code, String name, AccountType d, AccountType c) {
            this.code = code;
            this.name = name;
            this.d = d;
            this.c = c;
        }

        public static BookkeepingType findByCode(String code) {
            return Arrays.stream(values())
                    .filter(e -> Objects.equals(code, e.getCode()))
                    .findFirst()
                    .orElse(null);
        }
    }
}
