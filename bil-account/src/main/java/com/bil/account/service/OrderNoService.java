package com.bil.account.service;

import com.bil.account.contants.VoucherType;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 单号生成器
 *
 * @author haibo.yang   <bobyang_coder@163.com>
 * @since 2021/08/23
 */
@Component
public class OrderNoService {


    public String generateOrderNo(VoucherType voucherType) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
        return voucherType.getCode() + timestamp;
    }
}
