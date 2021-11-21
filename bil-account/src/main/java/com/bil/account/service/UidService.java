package com.bil.account.service;

import com.bil.account.contants.AccountConstants;
import com.bil.account.contants.VoucherType;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 单号生成器
 *
 * @author haibo.yang   <bobyang_coder@163.com>
 * @since 2021/08/23
 */
@Component
public class UidService {

    public static UidService $;

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

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
        String timestamp = LocalDateTime.now().format(FORMATTER);
        return voucherType.getCode() + timestamp;
    }

    /**
     * 生成账户号
     *
     * @param objectNo
     * @param accountType
     * @return
     */
    public String generateAccountNo(String objectNo, AccountConstants.AccountType accountType) {
        return VoucherType.ACCOUNT_NO + objectNo + accountType.getCode();
    }
}
