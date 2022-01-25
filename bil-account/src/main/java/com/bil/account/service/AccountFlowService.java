package com.bil.account.service;

import com.bil.account.dao.AccountFlowRepository;
import com.bil.account.model.chart.AccountDcData;
import com.bil.account.model.entity.AccountFlow;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
}
