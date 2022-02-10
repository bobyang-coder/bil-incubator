package com.bil.account.model.chart;

import com.bil.account.utils.AccountNo;
import com.google.common.collect.Lists;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

import static com.bil.account.utils.AmountUtils.fen2Yuan;

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


    public static List<PieChart> toPieChart(List<AccountDcData> list) {
        return CollectionUtils.emptyIfNull(list).stream()
                .map(e -> PieChart.builder()
                        .titleName(AccountNo.parseAccountNo(e.getAccountNo()).getAccountType().getName())
                        .dataList(
                                Lists.newArrayList(
                                        PieChartData.builder().name("借").value(fen2Yuan(e.getDAmount())).build(),
                                        PieChartData.builder().name("贷").value(fen2Yuan(e.getCAmount())).build()
                                )
                        ).build()
                ).collect(Collectors.toList());
    }
}
