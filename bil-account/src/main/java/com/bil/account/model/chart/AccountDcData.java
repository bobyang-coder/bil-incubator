package com.bil.account.model.chart;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 账户借贷数据
 *
 * @author haibo.yang   <bobyang_coder@163.com>
 * @since 2022/01/26
 */
@Data
@Builder
@NoArgsConstructor
public class AccountDcData {
    private String accountNo;
    private Long dAmount;
    private Long cAmount;

    public AccountDcData(String accountNo, Long dAmount, Long cAmount) {
        this.accountNo = accountNo;
        this.dAmount = dAmount;
        this.cAmount = cAmount;
    }
}
