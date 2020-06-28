package com.bil.shorturl.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 短链工具类
 *
 * @author haibo.yang   <bobyang_coder@163.com>
 * @version v1.0.0
 * @since 2020/6/5
 */
public class ShortUrlUtils {

    /**
     * 分组数量
     */
    private static final int GROUP_COUNT = 4;

    /**
     * 短链长度
     */
    private static final int SHORT_URL_LENGTH = 6;

    /**
     * 可以自定义生成MD5加密字符传前的混合KEY
     */
    private static final String SALT = "bobyang";

    /**
     * 要使用生成URL的字符
     */
    private static final String[] CHARS = new String[]{
            "a", "b", "c", "d", "e", "f", "g", "h",
            "i", "j", "k", "l", "m", "n", "o", "p",
            "q", "r", "s", "t", "u", "v", "w", "x",
            "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D",
            "E", "F", "G", "H", "I", "J", "K", "L",
            "M", "N", "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z"
    };

    public static String getShortCodeByMd5(String url) {
        return shortCodeByMd5(url)[0];
    }

    /**
     * 通过对url md5加密后获取短链
     *
     * @param url
     * @return
     */
    public static String[] shortCodeByMd5(String url) {
        // 对传入网址进行 MD5 加密
        String hex = getMd5(SALT + url);
        String[] resUrl = new String[GROUP_COUNT];
        for (int i = 0; i < GROUP_COUNT; i++) {
            // 把加密字符按照 8 位一组 16 进制与 0x3FFFFFFF 进行位与运算
            String tempSubStr = hex.substring(i * 8, i * 8 + 8);
            // 这里需要使用 long 型来转换，因为 Integer .parseInt() 只能处理 31 位 , 首位为符号位 , 如果不用 long ，则会越界
            long value = Long.parseLong(tempSubStr, 16);
            // 把字符串存入对应索引的输出数组
            resUrl[i] = get62Radix(value);
        }
        return resUrl;
    }

    /**
     * 根据自增序号获取短码
     *
     * @param seqNo
     * @return
     */
    public static String shortCodeBySeqNo(Long seqNo) {
        return get62Radix(seqNo);
    }


    /**
     * 十进制 -> 62进制
     *
     * @param value
     * @return
     */
    private static String get62Radix(Long value) {
        // Long 二进制有效长度为32位， 需要将前面两位去掉，留下30位 ，可以 & 0x3fffffff 进行位与运算得到想要的结果。
        // （30位才能转换62进制，否则超出）
        long lHexLong = 0x3FFFFFFF & value;
        StringBuilder builder = new StringBuilder();
        for (int j = 0; j < SHORT_URL_LENGTH; j++) {
            // 把得到的值与 0x0000003D 进行位与运算，取得字符数组 chars 索引
            long index = 0x0000003D & lHexLong;
            // 把取得的字符相连
            builder.append(CHARS[(int) index]);
            // 每次循环按位右移 5 位
            lHexLong = lHexLong >> 5;
        }
        return builder.toString();
    }


    /**
     * 获取md5值
     *
     * @param value
     * @return
     */
    private static String getMd5(String value) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new IllegalStateException("获取md5加密字符串失败");
        }
        digest.update(value.getBytes());
        return new BigInteger(1, digest.digest()).toString(16);
    }


}
