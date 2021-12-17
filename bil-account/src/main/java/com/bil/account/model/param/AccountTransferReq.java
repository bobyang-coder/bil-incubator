package com.bil.account.model.param;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date tradeTime;

    @ApiModelProperty(name = "tradeType", value = "交易类型", required = true)
    private String tradeType;

    @ApiModelProperty(name = "fromObjectNo", value = "转出账户号", required = true)
    private String fromObjectNo;

    @ApiModelProperty(name = "fromAccountType", value = "转出账户类型", required = true)
    private String fromAccountType;

    @ApiModelProperty(name = "toObjectNo", value = "转入账户号", required = true)
    private String toObjectNo;

    @ApiModelProperty(name = "toAccountType", value = "转入账户类型", required = true)
    private String toAccountType;

    @ApiModelProperty(name = "amount", value = "交易金额", required = true)
    private Long amount;

    @ApiModelProperty(name = "note", value = "备注")
    private String note;

    @ApiModelProperty(name = "autoOpenAcc", value = "是否自动开户")
    private Boolean autoOpenAcc;
}
