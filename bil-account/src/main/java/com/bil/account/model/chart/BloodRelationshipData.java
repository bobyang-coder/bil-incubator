package com.bil.account.model.chart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 血缘关系数据
 *
 * @author haibo.yang   <bobyang_coder@163.com>
 * @since 2022/02/09
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BloodRelationshipData {

    private List<Node> nodes;
    private List<Link> links;


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Node {
        private String name;

        //节点所在的层，取值从 0 开始。
        private int depth;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Link {

        //边的源节点名称
        private String source;

        //边的目标节点名称
        private String target;

        //边的数值，决定边的宽度。
        private float value;
    }
}
