package com.bil.account.utils.des;

import java.lang.annotation.*;

/**
 * 解密
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Decryptable {
}
