package com.bil.account.contants;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * 账户常量
 *
 * @author haibo.yang   <bobyang_coder@163.com>
 * @since 2021/08/23
 */
public interface AccountConstants {


    /**
     * 账户类型
     */
    @Getter
    enum AccountType {

        /**
         * ......
         */
        CASH(110001, "现金户", Direction.DEBIT.getCode()),
        FUND(210001, "基金户", Direction.DEBIT.getCode()),
        DEPOSIT(310001, "存款户", Direction.DEBIT.getCode()),
        SALARY(410001, "工资户", Direction.CREDIT.getCode()),

        ;

        private int code;

        private String name;

        private String direction;


        AccountType(Integer code, String name, String direction) {
            this.code = code;
            this.name = name;
            this.direction = direction;
        }

        public static AccountType findByCode(Integer code) {
            return Arrays.stream(values())
                    .filter(e -> Objects.equals(code, e.getCode()))
                    .findFirst()
                    .orElse(null);
        }
    }


    /**
     * 账户状态
     */
    @Getter
    enum AccountStatus {

        /**
         * 账户状态: normal - 正常， freeze - 冻结 , closed - 关闭
         * ......
         */
        NORMAL("normal", "正常"),
        FREEZE("freeze", "冻结"),
        CLOSED("closed", "关闭"),

        ;

        private String code;

        private String name;


        AccountStatus(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public static AccountStatus findByCode(String code) {
            return Arrays.stream(values())
                    .filter(e -> Objects.equals(code, e.getCode()))
                    .findFirst()
                    .orElse(null);
        }
    }

    /**
     * 余额方向
     */
    @Getter
    enum Direction {

        /**
         * D:debit - 借记账户， C:credit - 贷记账户
         */
        DEBIT("D", "借记账户"),
        CREDIT("C", "贷记账户"),
        ;

        private String code;

        private String name;


        Direction(String code, String name) {
            this.code = code;
            this.name = name;
        }
    }

    /**
     * 币种
     */
    @Getter
    enum Currency {

        /**
         * 1、人民币 、积分 3、代金券 4、美元 5、台币
         * ......
         */
        CNY("1", "人民币"),
        POINTS("2", "积分"),
        VOUCHER("3", "代金券"),
        USD("4", "美元"),
        ;

        private String code;

        private String name;


        Currency(String code, String name) {
            this.code = code;
            this.name = name;
        }
    }


    /**
     * 转账类型
     */
    @Getter
    enum TransferType {

        /**
         * ......
         */
        Recharge("1", "充值"),
        Withdraw("2", "提现"),
        Transfer("3", "转账"),
        Withhold("4", "代扣"),
        Wages("5", "工资"),
        ;

        private String code;

        private String name;


        TransferType(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public static TransferType findByCode(String code) {
            return Arrays.stream(values())
                    .filter(e -> Objects.equals(code, e.getCode()))
                    .findFirst()
                    .orElse(null);
        }
    }
}
