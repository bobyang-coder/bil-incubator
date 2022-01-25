package com.bil.account.model.param;

import com.alibaba.excel.annotation.ExcelProperty;
import com.bil.account.contants.BookConstants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 个人记账请求
 *
 * @author haibo.yang   <bobyang_coder@163.com>
 * @since 2022/01/13
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("个人记账请求")
public class PersonalBookkeepingReq {

    @ApiModelProperty(name = "tradeDate", value = "交易日期", required = true)
    @ExcelProperty(index = 0)
    private String tradeDate;

    /**
     * @see BookConstants.BookkeepingType#getCode()
     */
    @ApiModelProperty(name = "bookkeepingType", value = "记账类型", required = true)
    @ExcelProperty(index = 1)
    private String bookkeepingType;

    @ApiModelProperty(name = "amount", value = "金额", required = true)
    @ExcelProperty(index = 2)
    private String amount;

    @ApiModelProperty(name = "note", value = "交易备注", required = true)
    @ExcelProperty(index = 3)
    private String note;

}
