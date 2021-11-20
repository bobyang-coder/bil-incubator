package com.bil.account.engine;

import com.bil.account.ApplicationTests;
import com.bil.account.contants.AccountConstants;
import com.bil.account.model.entity.Account;
import com.bil.account.model.param.AccountTransferReq;
import org.junit.Assert;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;
import java.util.Date;

/**
 * AccountEngine Test
 *
 * @author haibo.yang   <bobyang_coder@163.com>
 * @since 2021/08/23
 */
class AccountEngineTest extends ApplicationTests {

    @Resource
    private AccountEngine accountEngine;

    @Test
    @Order(1)
    void openAccount() {
        Account account1 = accountEngine.openAccount("110", AccountConstants.AccountType.SALARY.getCode());
        Assert.assertNotNull(account1);
        Account account2 = accountEngine.openAccount("110", AccountConstants.AccountType.CASH.getCode());
        Assert.assertNotNull(account2);
        Account account3 = accountEngine.openAccount("120", AccountConstants.AccountType.CASH.getCode());
        Assert.assertNotNull(account3);
        Account account4 = accountEngine.openAccount("120", AccountConstants.AccountType.SALARY.getCode());
        Assert.assertNotNull(account4);
    }


    @Test
    @Order(2)
    void findAccount() {
        Account account = accountEngine.findAccount("110410001");
        Assert.assertNotNull(account);
    }

    @Test
    @Order(3)
    public void transferSalary() {
        AccountTransferReq req = AccountTransferReq.builder()
                .fromAccountNo("110410001")
                .toAccountNo("110110001")
                .amount(88L)
                .transferType(AccountConstants.TransferType.Transfer.getCode())
                .tradeNo("bob-test-01")
                .tradeTime(new Date())
                .tradeType("发工资")
                .build();
        String voucherNo = accountEngine.transfer(req);
        Assert.assertNotNull(voucherNo);

        AccountTransferReq req2 = AccountTransferReq.builder()
                .fromAccountNo("120410001")
                .toAccountNo("120110001")
                .amount(88L)
                .transferType(AccountConstants.TransferType.Transfer.getCode())
                .tradeNo("bob-test-01")
                .tradeTime(new Date())
                .tradeType("发工资")
                .build();
        String voucherNo2 = accountEngine.transfer(req2);
        Assert.assertNotNull(voucherNo2);
    }

    @Test
    @Order(4)
    public void transfer() {
        AccountTransferReq req = AccountTransferReq.builder()
                .fromAccountNo("110110001")
                .toAccountNo("120110001")
                .amount(10L)
                .transferType(AccountConstants.TransferType.Transfer.getCode())
                .tradeNo("bob-test-01")
                .tradeTime(new Date())
                .tradeType("转账")
                .build();
        String voucherNo = accountEngine.transfer(req);
        Assert.assertNotNull(voucherNo);
    }


}