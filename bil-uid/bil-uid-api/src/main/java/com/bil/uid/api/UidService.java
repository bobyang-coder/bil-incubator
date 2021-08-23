package com.bil.uid.api;

/**
 * Created by bob on 2020/3/29.
 *
 * @author haibo.yang   <bobyang_coder@163.com>
 * @version v1.0.0
 * @since 2020/3/29
 */
public interface UidService {

    /**
     * 获取下一个区间
     *
     * @param bizTag
     * @return
     */
    UidIncrementRange nextRange(String bizTag);

    /**
     * 获取下一个区间
     *
     * @param bizTag
     * @param batchSize
     * @return
     */
    UidIncrementRange nextRange(String bizTag, int batchSize);
}
