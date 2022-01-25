package com.bil.account.utils;

import com.bil.account.contants.VoucherType;
import org.junit.jupiter.api.Test;

/**
 * UidTest
 *
 * @author haibo.yang   <bobyang_coder@163.com>
 * @since 2021/11/21
 */
class UidTest {

    @Test
    void create() {
        String uid = Uid.create(VoucherType.ACCOUNT_VOUCHER);
        System.out.println(uid);
        System.out.println(Uid.parse(uid));
    }
}