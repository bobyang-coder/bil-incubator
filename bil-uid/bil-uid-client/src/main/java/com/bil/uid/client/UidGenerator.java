package com.bil.uid.client;

import java.util.List;

/**
 * Created by bob on 2020/3/29.
 *
 * @author haibo.yang   <bobyang_coder@163.com>
 * @version v1.0.0
 * @since 2020/3/29
 */
public interface UidGenerator {


    /**
     * 获取下一个值
     *
     * @return
     */
    long next();

    /**
     * 获取一批值
     *
     * @param batchSize 批次大小
     * @return
     */
    List<Long> batchNext(int batchSize);

}
