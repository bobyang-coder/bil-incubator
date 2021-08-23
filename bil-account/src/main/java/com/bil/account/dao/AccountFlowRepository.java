package com.bil.account.dao;

import com.bil.account.model.entity.AccountFlow;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 账户流水表
 *
 * @author haibo.yang   <bobyang_coder@163.com>
 * @since 2021/08/23
 */
public interface AccountFlowRepository extends JpaRepository<AccountFlow, Long> {

}
