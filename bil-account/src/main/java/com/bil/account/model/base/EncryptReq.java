package com.bil.account.model.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 加密请求
 *
 * @param <T>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EncryptReq<T> {

    /**
     * 签名
     */
    private String sign;

    /**
     * 加密key
     */
    private String encryptKey;

    /**
     * 加密数据
     */
    private String encryptData;

    /**
     * 请求时间戳
     */
    private Long timestamp;

    /**
     * 解密后数据数据
     */
    private T data;
}
