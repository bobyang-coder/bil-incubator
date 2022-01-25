package com.bil.account.contants;

import org.junit.jupiter.api.Test;

/**
 * AccountConstantsTest
 *
 * @author haibo.yang   <bobyang_coder@163.com>
 * @since 2022/01/23
 */
public class AccountConstantsTest {


    @Test
    public void demo() {
        System.out.println(AccountConstants.AccountType.DEPOSIT.getAccountingSubject().getRootAccountingSubject());
    }

}