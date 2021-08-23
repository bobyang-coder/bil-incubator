package com.bil.account.engine;

import com.bil.account.contants.AccountConstants;
import com.bil.account.contants.VoucherType;
import com.bil.account.dao.AccountFlowRepository;
import com.bil.account.dao.AccountRepository;
import com.bil.account.dao.AccountVoucherRepository;
import com.bil.account.model.entity.Account;
import com.bil.account.model.entity.AccountFlow;
import com.bil.account.model.entity.AccountVoucher;
import com.bil.account.model.param.TransferAccountReq;
import com.bil.account.service.OrderNoService;
import com.bil.account.utils.AccountUtil;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;

/**
 * 账务引擎
 *
 * @author haibo.yang   <bobyang_coder@163.com>
 * @since 2021/08/23
 */
@Component
public class AccountEngine {

    @Resource
    private AccountRepository accountRepository;
    @Resource
    private AccountFlowRepository accountFlowRepository;
    @Resource
    private AccountVoucherRepository accountVoucherRepository;
    @Resource
    private OrderNoService orderNoService;

    /**
     * 查询账户
     *
     * @param accountNo
     * @return
     */
    public Account findAccount(String accountNo) {
        return accountRepository.findByAccountNo(accountNo);
    }


    /**
     * 开户
     */
    public Account openAccount(String objectNo, int accountTypeCode) {
        AccountConstants.AccountType accountType = AccountConstants.AccountType.findByCode(accountTypeCode);
        Assert.notNull(accountType, "账户类型不支持:" + accountTypeCode);
        Account account = Account.builder()
                .accountNo(objectNo + accountType.getCode())
                .accountName(accountType.getName())
                .accountType(String.valueOf(accountType.getCode()))
                .balance(0L)
                .currency(AccountConstants.Currency.CNY.getCode())
                .overdraft("1")
                .direction(accountType.getDirection())
                .accountingSubjects("1")
                .accountLevel("1")
                .status(AccountConstants.AccountStatus.NORMAL.getCode())
                .objectNo(objectNo)
                .version("1")
                .build();
        Account save = accountRepository.save(account);
        return save;
    }

    /**
     * 转账
     *
     * @param transferReq
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public String transfer(TransferAccountReq transferReq) {
        String voucherNo = orderNoService.generateOrderNo(VoucherType.ACCOUNT_VOUCHER);
        String fromAccountNo = transferReq.getFromAccountNo();
        String toAccountNo = transferReq.getToAccountNo();

        AccountConstants.AccountType fromAccountType = AccountUtil.extractAccountType(fromAccountNo);
        Assert.notNull(fromAccountType, "转出账号类型类型不支持");
        AccountConstants.AccountType toAccountType = AccountUtil.extractAccountType(toAccountNo);
        Assert.notNull(toAccountType, "转入账号类型类型不支持");

        String accountingCode = StringUtils.join(fromAccountType.getCode(), toAccountType.getCode());
        AccountConstants.TransferType transferType = AccountConstants.TransferType.findByCode(transferReq.getTransferType());
        Assert.notNull(transferType, "转账类型不支持");

        AccountVoucher voucher = AccountVoucher.builder()
                .voucherNo(voucherNo)
                .fromAccountNo(fromAccountNo)
                .toAccountNo(toAccountNo)
                .amount(transferReq.getAmount())
                .currency(AccountConstants.Currency.CNY.getCode())
                .transferType(transferType.getCode())
                .tradeNo(transferReq.getTradeNo())
                .tradeTime(transferReq.getTradeTime())
                .tradeType(transferReq.getTradeType())
                .accountingCode(accountingCode)
                .resultCode("")
                .resultNote("")
                .version("1")
                .build();
        //1. 更新余额
        Long fromAmount = calcTransferAmount(transferReq.getAmount(), fromAccountType, true);
        Long toAmount = calcTransferAmount(transferReq.getAmount(), toAccountType, false);
        updateBalance(fromAmount, fromAccountNo);
        updateBalance(toAmount, toAccountNo);
        //2. 构建账务流水
        AccountFlow fromAccountFlow = buildAccountFlow(fromAccountNo, toAccountNo, true, fromAmount, voucherNo);
        AccountFlow toAccountFlow = buildAccountFlow(toAccountNo, fromAccountNo, false, toAmount, voucherNo);
        accountFlowRepository.saveAll(Lists.newArrayList(fromAccountFlow, toAccountFlow));
        //3. 保存账务凭证
        AccountVoucher saveVoucher = accountVoucherRepository.save(voucher);
        return saveVoucher.getVoucherNo();

    }

    /**
     * 计算账号转账金额
     *
     * @param amount
     * @param accountType
     * @param isCredit    是否是贷记
     * @return
     */
    private Long calcTransferAmount(Long amount, AccountConstants.AccountType accountType, boolean isCredit) {
        String direction = accountType.getDirection();
        //同增异减
        boolean add = (direction.equals(AccountConstants.Direction.CREDIT.getCode()) && isCredit)
                || (direction.equals(AccountConstants.Direction.DEBIT.getCode()) && !isCredit);
        return add ? amount : -amount;
    }


    private void updateBalance(Long amount, String accountNo) {
        int updateCount = amount > 0
                ? accountRepository.addAmount(amount, accountNo)
                : accountRepository.subAmount(-amount, accountNo);
        Assert.isTrue(updateCount <= 1, "账号重复:" + accountNo);
        Assert.isTrue(updateCount == 1, "账号余额不足:" + accountNo);
    }

    private AccountFlow buildAccountFlow(String accountNo,
                                         String oppositeAccountNo,
                                         boolean isCredit,
                                         Long amount,
                                         String voucherNo) {
        //期末余额
        Long balance = accountRepository.queryBalanceByAccountNo(accountNo);
        //期初余额
        Long prefixBalance = balance - amount;
        //贷记金额
        long absAmount = Math.abs(amount);
        Long creditAmount = isCredit ? absAmount : 0;
        //借记金额
        Long debitAmount = isCredit ? 0 : absAmount;
        return AccountFlow.builder()
                .voucherNo(voucherNo)
                .accountNo(accountNo)
                .balance(balance)
                .prefixBalance(prefixBalance)
                .creditAmount(creditAmount)
                .debitAmount(debitAmount)
                .oppositeAccountNo(oppositeAccountNo)
                .version("1")
                .build();
    }
}
