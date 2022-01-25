package com.bil.account.model.chart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 饼图数据
 *
 * @author haibo.yang   <bobyang_coder@163.com>
 * @since 2022/01/23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PieChartData {
    private String name;
    private String value;
}
