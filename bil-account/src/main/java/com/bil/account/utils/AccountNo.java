package com.bil.account.utils;

import com.bil.account.contants.AccountConstants.AccountType;
import com.bil.account.contants.VoucherType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

/**
 * 账户号
 *
 * @author haibo.yang
 * @since 2022/1/28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountNo {

    private String objectNo;

    private VoucherType voucherType;

    private AccountType accountType;


    public static final int VOUCHER_TYPE_LENGTH = 6;
    public static final int OBJECT_NO_LENGTH = 12;
    public static final int ACCOUNT_TYPE_LENGTH = 6;

    public static final int TOTAL_LENGTH = VOUCHER_TYPE_LENGTH + OBJECT_NO_LENGTH + ACCOUNT_TYPE_LENGTH;


    public static String generateAccountNo(String objectNo, AccountType accountType) {
        Assert.isTrue(objectNo.length() <= OBJECT_NO_LENGTH, "对象号大于12位");
        objectNo = Uid.padLeft(objectNo, OBJECT_NO_LENGTH, "0");
        return VoucherType.ACCOUNT_NO.getCode() + objectNo + accountType.getCode();
    }

    public static AccountNo parseAccountNo(String accountNo) {
        Assert.isTrue(accountNo.length() == TOTAL_LENGTH, "账户号非法:长度应为" + TOTAL_LENGTH);
        VoucherType voucherType = VoucherType.findByCode(accountNo.substring(0, VOUCHER_TYPE_LENGTH));
        String objectNo = Uid.removeLeft(accountNo.substring(VOUCHER_TYPE_LENGTH, VOUCHER_TYPE_LENGTH + OBJECT_NO_LENGTH), "0");
        AccountType accountType = AccountType.findByCode(accountNo.substring(VOUCHER_TYPE_LENGTH + OBJECT_NO_LENGTH, TOTAL_LENGTH));
        return AccountNo.builder()
                .voucherType(voucherType)
                .objectNo(objectNo)
                .accountType(accountType)
                .build();
    }

}
