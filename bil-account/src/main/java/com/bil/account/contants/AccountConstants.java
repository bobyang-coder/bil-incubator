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
         * 资产类账户 : 资产账户的余额=借方期初余额+借方本期发生额-贷方本期发生额。
         */
        CASH(110001, "现金户", AccountingSubject.PERSONAL_CASH),
        FUND(110002, "基金户", AccountingSubject.LICAI_FUND),
        DEPOSIT(110003, "存款户", AccountingSubject.BANK_DEPOSIT),


        /**
         * 负债类账户 : 贷方期末余额=贷方期初余额+贷方本期发生额-借方本期发生额。
         */
        BANK_LOAD(210001, "银行贷款户", AccountingSubject.BANK_LOAN),
        PERSONAL_LOAD(210002, "个人借款户", AccountingSubject.PERSONAL_LOAD),

        /**
         * 所有者权益类账户
         */
        SALARY(310001, "工资户", AccountingSubject.SALARY),
        SOCIAL_SECURITY(310002, "社保户", AccountingSubject.SOCIAL_SECURITY),

        /**
         * 损益类账户
         */
        HOUSE_LOAN(410001, "房贷户", AccountingSubject.HOUSE_LOAN),
        CREDIT_CONSUMPTION(410002, "信用卡消费户", AccountingSubject.CREDIT_CONSUMPTION),
        GIFT_GIVING(410003, "送礼户", AccountingSubject.GIFT_GIVING),

        /**
         * 成本类账户
         */
        ;

        private int code;

        private String name;

        /**
         * 会计科目
         */
        private AccountingSubject accountingSubject;


        AccountType(Integer code, String name, AccountingSubject accountingSubject) {
            this.code = code;
            this.name = name;
            this.accountingSubject = accountingSubject;
        }

        public static AccountType findByCode(String code) {
            return findByCode(Integer.valueOf(code));
        }

        public static AccountType findByCode(Integer code) {
            return Arrays.stream(values())
                    .filter(e -> Objects.equals(code, e.getCode()))
                    .findFirst()
                    .orElse(null);
        }
    }

    /**
     * 会计科目
     * <p>
     * “资产=负债+所有者权益”
     * “收入-费用=利润”
     * “资产+费用=负债+所有者权益+收入”
     * <p>
     * 科目编号格式：x(一级科目)xxx(二级科目)xxx(三级科目)
     */
    @Getter
    enum AccountingSubject {
        /**
         * 一级科目
         */
        ASSET("1", "资产", AccountingLevel.L1, null, Direction.N),
        LIABILITY("2", "负债", AccountingLevel.L1, null, Direction.N),
        EQUITY("3", "所有者权益", AccountingLevel.L1, null, Direction.N),
        PROFIT_LOSS("4", "损益", AccountingLevel.L1, null, Direction.N),
        PROFIT("5", "利润", AccountingLevel.L1, null, Direction.N),

        /**
         * 二级科目-损益类
         */
        CASH("1001", "现金", AccountingLevel.L2, ASSET, Direction.N),
        DEPOSIT("1002", "存款", AccountingLevel.L2, ASSET, Direction.N),
        LICAI("1003", "理财", AccountingLevel.L2, ASSET, Direction.N),
        FIXED_ASSET("1004", "固定资产", AccountingLevel.L2, ASSET, Direction.N),
        LOAN("2001", "借款", AccountingLevel.L2, LIABILITY, Direction.N),
        INCOME("3001", "收入", AccountingLevel.L2, EQUITY, Direction.N),
        EXPENDITURE("4001", "支出", AccountingLevel.L2, PROFIT_LOSS, Direction.N),


        /**
         * 三级科目
         */
        PERSONAL_CASH("1001001", "个人现金", AccountingLevel.L3, LOAN, Direction.D),
        BANK_DEPOSIT("1002001", "银行存款", AccountingLevel.L3, LOAN, Direction.D),
        LICAI_FUND("1003001", "基金理财", AccountingLevel.L3, LOAN, Direction.D),
        FIXED_ASSET_HOUSE("1003001", "房屋", AccountingLevel.L3, LOAN, Direction.D),
        BANK_LOAN("2001001", "借款-银行贷款", AccountingLevel.L3, LOAN, Direction.C),
        PERSONAL_LOAD("2001002", "借款-向个人借款", AccountingLevel.L3, LOAN, Direction.C),
        SALARY("3001001", "收入-工资", AccountingLevel.L3, INCOME, Direction.C),
        SOCIAL_SECURITY("3001002", "收入-社保", AccountingLevel.L3, INCOME, Direction.C),
        HOUSE_LOAN("4001001", "支出-房贷", AccountingLevel.L3, EXPENDITURE, Direction.D),
        CREDIT_CONSUMPTION("4001002", "支出-信用卡消费", AccountingLevel.L3, EXPENDITURE, Direction.D),
        GIFT_GIVING("4001003", "支出-送礼", AccountingLevel.L3, EXPENDITURE, Direction.D),
        HOUSE_RENT("4001004", "支出-房租", AccountingLevel.L3, EXPENDITURE, Direction.D),

        ;


        /**
         * 科目编码
         */
        private String code;

        /**
         * 科目名称
         */
        private String name;

        /**
         * 科目等级
         */
        private AccountingLevel level;

        /**
         * 父级科目
         */
        private AccountingSubject parent;

        /**
         * 余额方向
         */
        private Direction direction;


        AccountingSubject(String code, String name, AccountingLevel level, AccountingSubject parent, Direction direction) {
            this.code = code;
            this.name = name;
            this.level = level;
            this.parent = parent;
            this.direction = direction;
        }
    }


    /**
     * 会计科目等级
     */
    @Getter
    enum AccountingLevel {

        /**
         * 科目等级
         */
        L1(1, "一级科目"),
        L2(2, "二级科目"),
        L3(3, "二级科目"),
        ;

        /**
         * 等级
         */
        private int level;

        /**
         * 等级描述
         */
        private String desc;

        AccountingLevel(int level, String desc) {
            this.level = level;
            this.desc = desc;
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
        INIT("init", "未开账"),
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
     * <p>
     * 会计记帐的标识符号，没有实际含义．
     * 一般用于两种情况：
     * 1. 描述科目的余额方向，借方余额或者贷方余额，在会计平衡体系中，借方余额总和=贷方余额总和
     * 2. 描述记账的方向，相对于余额方向，同向的记账使余额增加，反向的记账使余额减少。
     * 如：对借方余额的科目进牙剥昔方记账，则借方余额增加；对借方余额的科目进行贷方记账，则借方余额减少，反之亦然
     */
    @Getter
    enum Direction {

        /**
         * D:debit - 借记账户， C:credit - 贷记账户 , N:none - 无方向 只有三级科目才有方向
         */
        N("N", "NONE", "无方向"),
        D("D", "DEBIT", "借"),
        C("C", "CREDIT", "贷"),
        ;

        private String code;
        private String fullCode;

        private String name;


        Direction(String code, String fullCode, String name) {
            this.code = code;
            this.fullCode = fullCode;
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
        begin_input("0", "期初录入"),
        recharge("1", "充值"),
        withdraw("2", "提现"),
        transfer("3", "转账"),
        withhold("4", "代扣"),
        wages("5", "工资"),
        loan("6", "贷款"),
        bill("7", "记账"),
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

    @Getter
    enum YesNo {
        /**
         * ...
         */
        YES("1", 1, true),
        NO("0", 0, false),
        ;

        private String charValue;
        private int intValue;
        private boolean booleanValue;

        YesNo(String charValue, int intValue, boolean booleanValue) {
            this.charValue = charValue;
            this.intValue = intValue;
            this.booleanValue = booleanValue;
        }
    }
}
