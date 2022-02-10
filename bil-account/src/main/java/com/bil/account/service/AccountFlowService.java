package com.bil.account.service;

import com.bil.account.dao.AccountFlowRepository;
import com.bil.account.model.base.MaxMinId;
import com.bil.account.model.chart.AccountDcData;
import com.bil.account.model.entity.Account;
import com.bil.account.model.entity.AccountFlow;
import org.apache.commons.collections4.CollectionUtils;
import org.joda.time.LocalDateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 账务流水表
 *
 * @author haibo.yang   <bobyang_coder@163.com>
 * @since 2021/11/21
 */
@Service
public class AccountFlowService {

    @Resource
    private AccountFlowRepository accountFlowRepository;
    @Resource
    private AccountService accountService;

    /**
     * @param accountNo
     * @return
     */
    public List<AccountFlow> queryAccountFlow(String accountNo) {
        return accountFlowRepository.queryByAccountNoOrderByIdDesc(accountNo);
    }

    /**
     * 查询账户借贷金额
     *
     * @return
     */
    public List<AccountDcData> queryAccountDcData() {
        return accountFlowRepository.queryAccountDcData();
    }

    public List<AccountDcData> sumAccountDcData(String objectNo, String fromYear, String toYear) {
        List<Account> accountList = accountService.queryByObjectNo(objectNo);
        if (CollectionUtils.isEmpty(accountList)) {
            return Collections.emptyList();
        }
        Date fromDateTime = LocalDateTime.parse(fromYear, ISODateTimeFormat.year()).toDate();
        Date toDateTime = LocalDateTime.parse(toYear, ISODateTimeFormat.year()).plusYears(1).toDate();
        return accountList.stream()
                .map(account -> {
                    MaxMinId maxMinId = accountFlowRepository.queryMaxMinId(account.getAccountNo(), fromDateTime, toDateTime);
                    if (null == maxMinId) {
                        return null;
                    }
                    return accountFlowRepository.queryAccountDcDataByMaxMinId(
                            account.getAccountNo(), maxMinId.getMinId(), maxMinId.getMaxId());
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
