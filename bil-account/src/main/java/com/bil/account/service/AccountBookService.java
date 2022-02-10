package com.bil.account.service;

import com.bil.account.dao.AccountFlowRepository;
import com.bil.account.dao.AccountRepository;
import com.bil.account.dao.AccountVoucherRepository;
import com.bil.account.utils.BookkeepingUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 账本服务
 *
 * @author haibo.yang
 * @since 2022/1/28
 */
@Service
@Slf4j
public class AccountBookService {
    @Resource
    private AccountFlowRepository accountFlowRepository;
    @Resource
    private AccountRepository accountRepository;
    @Resource
    private AccountVoucherRepository accountVoucherRepository;

    /**
     * 清理账本
     */
    @Transactional(rollbackFor = Exception.class)
    public void clearBookkeeping() {
        int voucherCount = accountVoucherRepository.deleteAccountVouchersByFromObjectNo(BookkeepingUtils.OBJECT_NO);
        int flowCount = accountFlowRepository.deleteAccountFlowsByAccountNoIsLike(BookkeepingUtils.OBJECT_NO);
        int accountCount = accountRepository.deleteAccountsByObjectNo(BookkeepingUtils.OBJECT_NO);
        log.info("清理账本-voucherCount:{},flowCount:{},accountCount:{}", voucherCount, flowCount, accountCount);
    }
}
