package com.bil.account.model.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 通用响应
 *
 * @author haibo.yang
 * @since 2021/11/20
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse<T> {

    private static final String SUCCESS = "0";
    private static final String ERROR = "-1";

    private String code;
    private String msg;
    private T data;


    public static <D> AccountResponse<D> success(D data) {
        return AccountResponse.<D>builder()
                .code(SUCCESS)
                .data(data)
                .build();
    }

    public static AccountResponse<?> fail(String msg) {
        return AccountResponse.builder()
                .code(ERROR)
                .msg(msg)
                .build();
    }
}
