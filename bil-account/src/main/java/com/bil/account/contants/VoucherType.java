package com.bil.account.contants;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * 凭证类型
 *
 * @author haibo.yang   <bobyang_coder@163.com>
 * @since 2021/08/23
 */
@Getter
public enum VoucherType {

    /**
     * ......
     */
    ACCOUNT_NO("100001", "账户号"),
    ACCOUNT_VOUCHER("100002", "账务凭证"),
    ;

    private String code;

    private String name;


    VoucherType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static VoucherType findByCode(String code) {
        return Arrays.stream(values())
                .filter(e -> Objects.equals(code, e.getCode()))
                .findFirst()
                .orElse(null);
    }
}
