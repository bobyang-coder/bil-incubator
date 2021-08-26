package com.bil.uid.api;


import java.time.LocalDate;

/**
 * Created by bob on 2020/3/29.
 *
 * @author haibo.yang   <bobyang_coder@163.com>
 * @version v1.0.0
 * @since 2020/3/29
 */
public interface Uid {
    static Uid of(long value) {
        //TODO
        return null;
    }

    Integer getOffset();

    Long getSequence();

    LocalDate getTimeDate();

    Integer getTimeDays();

    Integer getVersion();

    /**
     * 业务编码
     *
     * @return
     */
    Integer getBizCode();

}
