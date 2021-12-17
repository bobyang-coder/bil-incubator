package com.bil.account.service;

import com.bil.account.contants.AccountConstants;
import com.bil.account.contants.VoucherType;
import com.bil.account.utils.Uid;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Resource;

/**
 * 单号生成器
 *
 * @author haibo.yang   <bobyang_coder@163.com>
 * @since 2021/08/23
 */
@Component
public class UidService {

    public static UidService $;

    @Resource
    public void inject(UidService uidService) {
        $ = uidService;
    }

    /**
     * 生成单号
     *
     * @param voucherType
     * @return
     */
    public String generateOrderNo(VoucherType voucherType) {
        return Uid.create(voucherType);
    }

    /**
     * 生成账户号
     *
     * @param objectNo
     * @param accountType
     * @return
     */
    public String generateAccountNo(String objectNo, AccountConstants.AccountType accountType) {
        Assert.isTrue(objectNo.length() <= 12, "对象号大于12位");
        objectNo = Uid.padLeft(objectNo, 12, "0");
        return VoucherType.ACCOUNT_NO.getCode() + objectNo + accountType.getCode();
    }

    public static void main(String[] args) {
        System.out.println(Math.floorDiv(1000,2));
    }
}
