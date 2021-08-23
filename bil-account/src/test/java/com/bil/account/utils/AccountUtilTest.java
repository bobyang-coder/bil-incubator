package com.bil.account.utils;

import com.bil.account.contants.AccountConstants;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

/**
 * AccountUtil Test
 *
 * @author haibo.yang   <bobyang_coder@163.com>
 * @since 2021/08/23
 */
class AccountUtilTest {

    @Test
    void extractAccountType() {
        AccountConstants.AccountType accountType = AccountUtil.extractAccountType("120110001");
        Assert.assertEquals(AccountConstants.AccountType.CASH, accountType);
    }
}