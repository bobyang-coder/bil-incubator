package com.bil.shorturl.service.impl;

import com.bil.shorturl.model.ShortUrl;
import com.bil.shorturl.service.ShortUrlService;
import com.bil.shorturl.util.ShortUrlUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 短链服务
 *
 * @author haibo.yang   <bobyang_coder@163.com>
 * @since 2020/06/07
 */
@Service
public class ShortUrlServiceImpl implements ShortUrlService {

    /**
     * 短码与短链信息的缓存
     * todo 将其切换成分布式缓存
     */
    private static final Map<String, ShortUrl> SHORT_URL_CACHE = new ConcurrentHashMap<>();

    /**
     * 原始链接与短码的缓存
     * todo 将其切换成分布式缓存
     */
    private static final Map<String, String> ORIGIN_URL_SHORT_CODE_CACHE = new ConcurrentHashMap<>();


    @Override
    public ShortUrl genShortUrl(String originUrl, boolean scanQrAccess) {
        ShortUrl shortUrl = null;
        String shortCode = ORIGIN_URL_SHORT_CODE_CACHE.get(originUrl);
        if (StringUtils.isNoneBlank(shortCode)) {
            shortUrl = SHORT_URL_CACHE.get(shortCode);
        } else {
            shortCode = ShortUrlUtils.getShortCodeByMd5(originUrl);
        }
        if (null == shortUrl) {
            shortUrl = ShortUrl.builder()
                    .shortWebsite("http://127.0.0.1:8080")
                    .shortCode(shortCode)
                    .originUrl(originUrl)
                    .scanQrAccess(scanQrAccess)
                    .build();
            //缓存
            SHORT_URL_CACHE.put(shortCode, shortUrl);
            ORIGIN_URL_SHORT_CODE_CACHE.put(originUrl, shortCode);
        }
        shortUrl.setScanQrAccess(scanQrAccess);
        return shortUrl;
    }

    @Override
    public ShortUrl findShortUrl(String shortCode) {
        ShortUrl shortUrl = SHORT_URL_CACHE.get(shortCode);
        if (null == shortUrl) {
            throw new IllegalArgumentException("短链不存在");
        }
        return shortUrl;
    }

    @Override
    public boolean replaceOriginUrl(String shortCode, String originReplaceUrl) {
        ShortUrl url = findShortUrl(shortCode);
        url.setOriginUrl(originReplaceUrl);
        return true;
    }
}
