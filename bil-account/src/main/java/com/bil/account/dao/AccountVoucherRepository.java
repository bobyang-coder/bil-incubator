package com.bil.account.dao;

import com.bil.account.model.entity.AccountVoucher;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 记账凭证表
 *
 * @author haibo.yang   <bobyang_coder@163.com>
 * @since 2021/08/23
 */
public interface AccountVoucherRepository extends JpaRepository<AccountVoucher, Long> {

}
