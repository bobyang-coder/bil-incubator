package com.bil.shorturl.model;

import com.bil.shorturl.util.UrlUtils;
import lombok.Builder;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * Created by bob on 2020/6/6.
 *
 * @author haibo.yang   <bobyang_coder@163.com>
 * @version v1.0.0
 * @since 2020/6/6
 */
@Data
@Builder
public class RequestData implements Serializable {

    /**
     * 短链
     */
    private String shortUrl;

    /**
     * 来源
     */
    private String origin;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 真实ip地址
     */
    private String realIpAddr;

    /**
     * 访问次数
     */
    private int accessCount;

    public static RequestData from(String shortUrl, HttpServletRequest request) {
        return RequestData.builder()
                .shortUrl(shortUrl)
                .realIpAddr(UrlUtils.getRealIpAddress(request))
                .build();
    }

    /**
     * 增加访问次数
     */
    public void incAccessCount() {
        accessCount++;
    }
}
