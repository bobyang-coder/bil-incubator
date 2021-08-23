package com.bil.account.model.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 转账请求
 *
 * @author haibo.yang   <bobyang_coder@163.com>
 * @since 2021/08/23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferAccountReq {

    /**
     * 转出账户号
     */
    private String fromAccountNo;

    /**
     * 转入账户号
     */
    private String toAccountNo;

    /**
     * 交易金额
     */
    private Long amount;


    /**
     * 转账类型
     */
    private String transferType;

    /**
     * 交易流水号
     */
    private String tradeNo;

    /**
     * 交易时间
     */
    private Date tradeTime;

    /**
     * 交易类型
     */
    private String tradeType;

}
