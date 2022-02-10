package com.bil.account.utils;

import com.alibaba.fastjson.JSON;
import com.bil.account.contants.AccountConstants;
import org.junit.jupiter.api.Test;

/**
 * AccountNo Test
 *
 * @author haibo.yang
 * @since 2022/1/28
 */
class AccountNoTest {

    @Test
    void generateAccountNo() {
        String accountNo = AccountNo.generateAccountNo("bozige", AccountConstants.AccountType.CASH);
        System.out.println(accountNo);
        AccountNo no = AccountNo.parseAccountNo(accountNo);
        System.out.println(JSON.toJSONString(no));
    }
}