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
public class Response<T> {

    private static final String SUCCESS = "0";
    private static final String ERROR = "-1";

    private String code;
    private String msg;
    private T data;

    public static <D> Response<D> OK() {
        return success(null);
    }

    public static <D> Response<D> success(D data) {
        return Response.<D>builder()
                .code(SUCCESS)
                .data(data)
                .build();
    }

    public static Response<?> fail(String msg) {
        return Response.builder()
                .code(ERROR)
                .msg(msg)
                .build();
    }
}
