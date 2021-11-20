package com.bil.account.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 账户转账请求
 *
 * @author haibo.yang   <bobyang_coder@163.com>
 * @since 2021/08/23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("账户转账请求")
public class AccountTransferReq {

    @ApiModelProperty(name = "tradeNo", value = "交易流水号", required = true)
    private String tradeNo;

    @ApiModelProperty(name = "commandType", value = "记账指令类型", required = true)
    private String commandType;

    @ApiModelProperty(name = "transferType", value = "转账类型", required = true)
    private String transferType;

    @ApiModelProperty(name = "tradeTime", value = "交易时间", required = true)
    private Date tradeTime;

    @ApiModelProperty(name = "tradeType", value = "交易类型", required = true)
    private String tradeType;

    @ApiModelProperty(name = "fromAccountNo", value = "转出账户号", required = true)
    private String fromAccountNo;

    @ApiModelProperty(name = "toAccountNo", value = "转入账户号", required = true)
    private String toAccountNo;

    @ApiModelProperty(name = "amount", value = "交易金额", required = true)
    private Long amount;

    @ApiModelProperty(name = "note", value = "备注")
    private String note;
}
