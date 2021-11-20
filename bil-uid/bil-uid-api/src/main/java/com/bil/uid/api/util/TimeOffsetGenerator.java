package com.bil.uid.api.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 基于时间偏移量生成
 *
 * @author haibo.yang
 * @since 2021/8/26
 */
public class TimeOffsetGenerator {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    public static final Long START = 20200101000000L;

    /**
     * 业务编码
     */
    private Integer bizCode;

    public TimeOffsetGenerator(Integer bizCode) {
        this.bizCode = bizCode;
    }


    public String next() {
        Long now = Long.valueOf(LocalDateTime.now().format(DATE_TIME_FORMATTER));
        long offset = now - START;
        System.out.println(offset);
        //22位：6位业务前缀+11位时间差+5位序号  10w/s
        return String.format("%s%s%s", bizCode, offset, "11111");
    }

    public LocalDateTime getLocalDate(String uid) {
        long offset = Long.parseLong(uid.substring(bizCode.toString().length(), uid.length() - 5));
        System.out.println(offset);
        long target = offset + START;
        return LocalDateTime.parse(Long.toString(target), DATE_TIME_FORMATTER);
    }


    public static void main(String[] args) {
        TimeOffsetGenerator generator = new TimeOffsetGenerator(100001);
        System.out.println(generator.next());
        System.out.println(generator.getLocalDate(generator.next()));
        System.out.println(Long.MAX_VALUE);
        //9223372036854775807
        //1000011072519412511111
    }

}
