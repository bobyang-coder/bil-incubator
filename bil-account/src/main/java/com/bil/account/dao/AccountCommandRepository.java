package com.bil.account.dao;

import com.bil.account.model.entity.AccountCommand;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 记账指令表
 *
 * @author haibo.yang
 * @since 2021/11/21
 */
public interface AccountCommandRepository extends JpaRepository<AccountCommand, Long> {

    /**
     * 根据唯一键查询
     *
     * @param commandTradeNo
     * @param commandType
     * @return
     */
    AccountCommand findAccountCommandByCommandTradeNoAndCommandType(String commandTradeNo, String commandType);
}
