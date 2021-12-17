package com.bil.account.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * 记账凭证表
 *
 * @author bob
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account_voucher")
public class AccountVoucher {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 记账凭证号
     */
    @Column(name = "voucher_no")
    private String voucherNo;

    /**
     * 借记对象号
     */
    @Column(name = "from_object_no")
    private String fromObjectNo;

    /**
     * 借记账户号
     */
    @Column(name = "from_account_no")
    private String fromAccountNo;

    /**
     * 贷记账户号
     */
    @Column(name = "to_object_no")
    private String toObjectNo;

    /**
     * 贷记账户号
     */
    @Column(name = "to_account_no")
    private String toAccountNo;

    /**
     * 交易金额
     */
    @Column(name = "amount")
    private Long amount;

    /**
     * 币种
     */
    @Column(name = "currency")
    private String currency;

    /**
     * 转账类型
     */
    @Column(name = "transfer_type")
    private String transferType;

    /**
     * 交易流水号
     */
    @Column(name = "trade_no")
    private String tradeNo;

    /**
     * 交易时间
     */
    @Column(name = "trade_time")
    private Date tradeTime;

    /**
     * 交易类型
     */
    @Column(name = "trade_type")
    private String tradeType;

    /**
     * 记账码
     */
    @Column(name = "accounting_code")
    private String accountingCode;

    /**
     * 结果码
     */
    @Column(name = "result_code")
    private String resultCode;

    /**
     * 结果备注
     */
    @Column(name = "result_note")
    private String resultNote;

    /**
     * 摘要内容
     */
    @Column(name = "abstract_content")
    private String abstractContent;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 版本号
     */
    @Column(name = "version")
    private String version;

}
