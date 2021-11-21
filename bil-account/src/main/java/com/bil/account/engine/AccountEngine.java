package com.bil.account.engine;

import com.bil.account.contants.AccountConstants;
import com.bil.account.contants.VoucherType;
import com.bil.account.dao.AccountCommandRepository;
import com.bil.account.dao.AccountFlowRepository;
import com.bil.account.dao.AccountRepository;
import com.bil.account.dao.AccountVoucherRepository;
import com.bil.account.model.entity.Account;
import com.bil.account.model.entity.AccountCommand;
import com.bil.account.model.entity.AccountFlow;
import com.bil.account.model.entity.AccountVoucher;
import com.bil.account.model.param.AccountTransferReq;
import com.bil.account.service.UidService;
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

    private static final String VERSION = "1";

    @Resource
    private AccountRepository accountRepository;
    @Resource
    private AccountFlowRepository accountFlowRepository;
    @Resource
    private AccountVoucherRepository accountVoucherRepository;
    @Resource
    private AccountCommandRepository accountCommandRepository;

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
        String accountNo = UidService.$.generateAccountNo(objectNo, accountType);
        Account account = Account.builder()
                .accountNo(accountNo)
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
        return accountRepository.save(account);
    }

    /**
     * 转账
     *
     * @param req
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public String transfer(AccountTransferReq req) {
        //1. 参数校验
        String fromAccountNo = req.getFromAccountNo();
        String toAccountNo = req.getToAccountNo();
        AccountConstants.AccountType fromAccountType = AccountUtil.extractAccountType(fromAccountNo);
        Assert.notNull(fromAccountType, "转出账号类型类型不支持");
        AccountConstants.AccountType toAccountType = AccountUtil.extractAccountType(toAccountNo);
        Assert.notNull(toAccountType, "转入账号类型类型不支持");
        AccountConstants.TransferType transferType = AccountConstants.TransferType.findByCode(req.getTransferType());
        Assert.notNull(transferType, "转账类型不支持");

        //2. 查询转账指令表
        AccountCommand command = accountCommandRepository
                .findAccountCommandByCommandTradeNoAndCommandType(req.getTradeNo(), req.getCommandType());
        if (null != command) {
            return command.getCommandNo();
        }

        //3. 构建记账凭证
        String commandNo = UidService.$.generateOrderNo(VoucherType.ACCOUNT_VOUCHER);
        command = AccountCommand.builder()
                .commandNo(commandNo)
                .commandType(req.getCommandType())
                .commandTradeNo(req.getTradeNo())
                .commandNote(req.getNote())
                .version(VERSION)
                .build();

        String accountingCode = StringUtils.join(fromAccountType.getCode(), toAccountType.getCode());
        AccountVoucher voucher = AccountVoucher.builder()
                .voucherNo(commandNo)
                .fromAccountNo(fromAccountNo)
                .toAccountNo(toAccountNo)
                .amount(req.getAmount())
                .currency(AccountConstants.Currency.CNY.getCode())
                .transferType(transferType.getCode())
                .tradeNo(req.getTradeNo())
                .tradeTime(req.getTradeTime())
                .tradeType(req.getTradeType())
                .accountingCode(accountingCode)
                .resultCode("0000")
                .resultNote("记账成功")
                .version(VERSION)
                .build();
        //4. 更新余额
        Long fromAmount = calcTransferAmount(req.getAmount(), fromAccountType, true);
        Long toAmount = calcTransferAmount(req.getAmount(), toAccountType, false);
        updateBalance(fromAmount, fromAccountNo);
        updateBalance(toAmount, toAccountNo);
        //5. 记账务流水
        AccountFlow fromAccountFlow = buildAccountFlow(fromAccountNo, toAccountNo, true, fromAmount, commandNo);
        AccountFlow toAccountFlow = buildAccountFlow(toAccountNo, fromAccountNo, false, toAmount, commandNo);
        accountFlowRepository.saveAll(Lists.newArrayList(fromAccountFlow, toAccountFlow));
        //6. 记账务凭证
        AccountVoucher saveVoucher = accountVoucherRepository.save(voucher);
        //7. 记记账指令
        accountCommandRepository.save(command);
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
