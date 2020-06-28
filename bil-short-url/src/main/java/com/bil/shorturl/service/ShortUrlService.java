package com.bil.shorturl.service;

import com.bil.shorturl.model.ShortUrl;

/**
 * 短链服务
 *
 * @author haibo.yang   <bobyang_coder@163.com>
 * @since 2020/06/07
 */
public interface ShortUrlService {

    /**
     * 生成短链
     *
     * @param originUrl    原始url
     * @param scanQrAccess 是否开启扫码访问
     * @return
     */
    ShortUrl genShortUrl(String originUrl, boolean scanQrAccess);

    /**
     * 查询短链信息
     *
     * @param shortCode 短码
     * @return
     */
    ShortUrl findShortUrl(String shortCode);

    /**
     * 替换原始url
     *
     * @param shortCode        短码
     * @param originReplaceUrl 原始url
     * @return
     */
    boolean replaceOriginUrl(String shortCode, String originReplaceUrl);
}
