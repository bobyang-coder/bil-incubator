package com.bil.uid.spring.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 客户端标识
 *
 * @author haibo.yang   <bobyang_coder@163.com>
 * @version v1.0.0
 * @since 2020/3/29
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface UidClient {

    /**
     * 业务标识
     *
     * @return
     */
    String bizTag();

    /**
     * 超时时间
     *
     * @return
     */
    int timeout() default 0;
}
