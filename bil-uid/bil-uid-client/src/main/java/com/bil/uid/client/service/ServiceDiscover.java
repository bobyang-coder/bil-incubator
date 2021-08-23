package com.bil.uid.client.service;

/**
 * 服务自动发现
 *
 * @author haibo.yang   <bobyang_coder@163.com>
 * @version v1.0.0
 * @since 2020/3/29
 */
public interface ServiceDiscover<T> {

    T get();
}
