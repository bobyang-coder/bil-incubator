package com.bil.shorturl.util;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

/**
 * ShortUrlUtils Test
 *
 * @author haibo.yang   <bobyang_coder@163.com>
 * @version v1.0.0
 * @since 2020/6/6
 */
class ShortUrlUtilsTest {

    @Test
    void shortUrlByMd5() {
        String[] shortUrl = ShortUrlUtils.shortCodeByMd5("www.baidu.com");
        Assert.assertNotNull(shortUrl);
        for (int i = 0; i < shortUrl.length; i++) {
            System.out.printf("%s:%s\n", i, shortUrl[i]);
        }
    }

    @Test
    void shortUrlBySeqNo() {
        String url = ShortUrlUtils.shortCodeBySeqNo(Long.MAX_VALUE);
        Assert.assertNotNull(url);
        Assert.assertEquals(6, url.length());
        System.out.println(url);
    }
}