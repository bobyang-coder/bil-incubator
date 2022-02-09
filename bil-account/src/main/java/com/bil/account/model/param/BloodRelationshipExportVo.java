package com.bil.account.model.param;

import com.alibaba.excel.annotation.ExcelProperty;
import com.bil.account.model.entity.BloodRelationship;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 血缘关系表
 *
 * @author haibo.yang   <bobyang_coder@163.com>
 * @since 2022/02/09
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BloodRelationshipExportVo {
    /**
     * 节点
     */
    @ExcelProperty(index = 0)
    private String node;

    /**
     * 节点类
     */
    @ExcelProperty(index = 1)
    private String nodeType;

    /**
     * 分类
     */
    @ExcelProperty(index = 2)
    private String category;

    /**
     * 标签
     */
    @ExcelProperty(index = 3)
    private String tag;

    /**
     * 来源
     */
    @ExcelProperty(index = 4)
    private String source;

    /**
     * 关系表
     */
    @ExcelProperty(index = 5)
    private String relationshipDesc;


    public BloodRelationship to() {
        return BloodRelationship.builder()
                .node(node)
                .nodeType(nodeType)
                .category(category)
                .tag(tag)
                .source(source)
                .relationshipDesc(relationshipDesc)
                .createTime(new Date())
                .updateTime(new Date())
                .version("1")
                .build();
    }

}
