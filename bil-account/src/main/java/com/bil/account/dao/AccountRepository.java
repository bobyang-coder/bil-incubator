package com.bil.account.dao;

import com.bil.account.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 账户表
 *
 * @author haibo.yang   <bobyang_coder@163.com>
 * @since 2021/08/23
 */
public interface AccountRepository extends JpaRepository<Account, Long> {

    /**
     * 根据账号查询
     *
     * @param accountNo
     * @return
     */
    Account findByAccountNo(String accountNo);

    /**
     * 根据对象号查询
     *
     * @param objectNo
     * @return
     */
    List<Account> queryByObjectNo(String objectNo);

    /**
     * 根据对象号删除
     *
     * @param objectNo
     * @return
     */
    int deleteAccountsByObjectNo(String objectNo);

    /**
     * 查询余额
     *
     * @param accountNo
     * @return
     */
    @Query("select balance from  Account  where accountNo = :accountNo")
    Long queryBalanceByAccountNo(String accountNo);


    @Modifying
    @Query("update Account set balance = balance + :amount  where accountNo = :accountNo")
    int addAmount(@Param("amount") Long amount, @Param("accountNo") String accountNo);


    @Modifying
    @Query("update Account set balance = balance - :amount  where accountNo = :accountNo and balance >= :amount")
    int subAmount(@Param("amount") Long amount, @Param("accountNo") String accountNo);

    /**
     * 减金额-支持负数
     *
     * @param amount
     * @param accountNo
     * @return
     */
    @Modifying
    @Query("update Account set balance = balance - :amount  where accountNo = :accountNo")
    int subAmountSupportNegative(@Param("amount") Long amount, @Param("accountNo") String accountNo);

}
