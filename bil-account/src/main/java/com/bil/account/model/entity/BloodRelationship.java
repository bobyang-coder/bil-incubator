package com.bil.account.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
@Entity
@Table(name = "blood_relationship")
public class BloodRelationship {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 节点
     */
    @Column(name = "node")
    private String node;

    /**
     * 节点类
     */
    @Column(name = "node_type")
    private String nodeType;

    /**
     * 分类
     */
    @Column(name = "category")
    private String category;

    /**
     * 标签
     */
    @Column(name = "tag")
    private String tag;

    /**
     * 来源
     */
    @Column(name = "source")
    private String source;

    /**
     * 关系描述
     */
    @Column(name = "relationship_desc")
    private String relationshipDesc;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 版本号
     */
    @Column(name = "version")
    private String version;
}
