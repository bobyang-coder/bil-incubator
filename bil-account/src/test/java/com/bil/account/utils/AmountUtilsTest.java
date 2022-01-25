package com.bil.account.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * AmountUtils Test
 *
 * @author haibo.yang   <bobyang_coder@163.com>
 * @since 2022/01/23
 */
class AmountUtilsTest {

    @Test
    void fen2Yuan() {
        System.out.println(AmountUtils.fen2Yuan(101L));
    }
}