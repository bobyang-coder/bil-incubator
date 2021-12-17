package com.bil.account.utils;

import com.bil.account.contants.VoucherType;
import lombok.*;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * uid
 *
 * @author haibo.yang   <bobyang_coder@163.com>
 * @since 2021/11/21
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Uid {
    public static final String DATE_TIME_FORMAT = "yyyyMMddHHmmss";
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);

    private VoucherType voucherType;

    private LocalDateTime dateTime;

    private String randomNum;

    private String uid;


    public static String create(VoucherType voucherType) {
        //凭证类型+时间戳+随机数
        String timestamp = LocalDateTime.now().format(FORMATTER);
        Random random = new SecureRandom();
        int randomNum = random.nextInt(99999);
        String randomStr = padLeft(String.valueOf(randomNum), 5, "0");
        return voucherType.getCode() + timestamp + randomStr;
    }


    public static Uid parse(String uid) {
        VoucherType voucherType = VoucherType.findByCode(uid.substring(0, 6));
        String dateTimeStr = uid.substring(6, 6 + DATE_TIME_FORMAT.length());
        String randomNum = uid.substring(6 + DATE_TIME_FORMAT.length());
        return Uid.builder()
                .voucherType(voucherType)
                .dateTime(LocalDateTime.parse(dateTimeStr, FORMATTER))
                .randomNum(randomNum)
                .uid(uid)
                .build();
    }

    /**
     * 左填充
     *
     * @param expectLength
     * @param str
     * @param fillStr
     * @return
     */
    public static String padLeft(String str, int expectLength, String fillStr) {
        int length = str.length();
        if (length < expectLength) {
            while (length < expectLength) {
                StringBuilder sb = new StringBuilder();
                sb.append(fillStr).append(str);
                length = sb.toString().length();
                str = sb.toString();
            }
        }
        return str;
    }
}
