package com.bil.account.dao;

import com.bil.account.model.base.MaxMinId;
import com.bil.account.model.chart.AccountDcData;
import com.bil.account.model.entity.AccountFlow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * 账户流水表
 *
 * @author haibo.yang   <bobyang_coder@163.com>
 * @since 2021/08/23
 */
public interface AccountFlowRepository extends JpaRepository<AccountFlow, Long> {

    /**
     * 根据账号查询流水
     *
     * @param accountNo
     * @return
     */
    List<AccountFlow> queryByAccountNoOrderByIdDesc(String accountNo);

    int deleteAccountFlowsByAccountNoIsLike(String accountNo);


    @Query("select new com.bil.account.model.chart.AccountDcData(accountNo,sum(debitAmount) ,sum(creditAmount)) FROM AccountFlow group by accountNo")
//    @Query("select new com.bil.account.model.chart.AccountDcData(accountNo,sum(debitAmount) ,sum(creditAmount)) FROM AccountFlow" +
//            " where tradeTime >='2021-01-01 00:00:00' and tradeTime <'2022-01-01 00:00:00' group by accountNo")
    List<AccountDcData> queryAccountDcData();

    @Query("select new com.bil.account.model.chart.AccountDcData(accountNo,sum(debitAmount) ,sum(creditAmount)) FROM AccountFlow " +
            "where accountNo =:accountNo and id >= :minId and id<= :maxId")
    AccountDcData queryAccountDcDataByMaxMinId(String accountNo, Long minId, Long maxId);


    @Query("select new com.bil.account.model.base.MaxMinId(min(id),max(id)) from AccountFlow where accountNo = :accountNo and tradeTime >= :tradeTimeStart and tradeTime < :tradeTimeEnd")
    MaxMinId queryMaxMinId(String accountNo, Date tradeTimeStart, Date tradeTimeEnd);

}
