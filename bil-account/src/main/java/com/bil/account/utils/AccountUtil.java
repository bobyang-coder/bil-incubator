package com.bil.account.utils;

import com.bil.account.contants.AccountConstants;

/**
 * Account Util
 *
 * @author haibo.yang   <bobyang_coder@163.com>
 * @since 2021/08/23
 */
public class AccountUtil {


    /**
     * 从账号中提取账号类型
     *
     * @param accountNo
     * @return
     */
    public static AccountConstants.AccountType extractAccountType(String accountNo) {
        int startIndex = accountNo.length() - 6;
        Integer accountType = Integer.valueOf(accountNo.substring(startIndex));
        return AccountConstants.AccountType.findByCode(accountType);
    }

}
