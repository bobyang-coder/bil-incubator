package com.bil.account.dao;

import com.bil.account.model.entity.BloodRelationship;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 血缘关系表
 *
 * @author haibo.yang   <bobyang_coder@163.com>
 * @since 2022/02/09
 */
public interface BloodRelationshipRepository extends JpaRepository<BloodRelationship, Long> {
}
