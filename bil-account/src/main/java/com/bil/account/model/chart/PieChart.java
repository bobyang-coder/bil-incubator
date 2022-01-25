package com.bil.account.model.chart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 饼图
 *
 * @author haibo.yang   <bobyang_coder@163.com>
 * @since 2022/01/26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PieChart {

    /**
     * 饼图标题
     */
    private String titleName;

    /**
     * 饼图数据
     */
    private List<PieChartData> dataList;
}
