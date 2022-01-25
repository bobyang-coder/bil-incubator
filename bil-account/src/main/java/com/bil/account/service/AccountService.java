package com.bil.account.service;

import com.bil.account.dao.AccountRepository;
import com.bil.account.model.entity.Account;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 账户
 *
 * @author haibo.yang   <bobyang_coder@163.com>
 * @since 2022/01/23
 */
@Service
public class AccountService {

    @Resource
    private AccountRepository accountRepository;

    public List<Account> queryByObjectNo(String objectNo) {
        return accountRepository.queryByObjectNo(objectNo);
    }

    public Account findByAccountNo(String accountNo) {
        return accountRepository.findByAccountNo(accountNo);
    }
}
