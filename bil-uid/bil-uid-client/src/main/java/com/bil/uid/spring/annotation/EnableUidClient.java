package com.bil.uid.spring.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Created by bob on 2020/3/29.
 *
 * @author haibo.yang   <bobyang_coder@163.com>
 * @version v1.0.0
 * @since 2020/3/29
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(UidClientConfigurationSelector.class)
public @interface EnableUidClient {

    String order() default "";
}
