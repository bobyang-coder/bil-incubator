package com.bil.account.model.chart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 折线图数据
 *
 * @author haibo.yang   <bobyang_coder@163.com>
 * @since 2022/01/23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LineChartData {
    private String accountTypeName;
    private List<Integer> dateList;
    private List<String> amountList;
    private List<String> balanceList;
}
