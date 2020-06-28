package com.bil.shorturl.controller;

import com.bil.shorturl.model.RequestData;
import com.bil.shorturl.model.ShortUrl;
import com.bil.shorturl.service.ShortUrlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 根控制器
 *
 * @author haibo.yang   <bobyang_coder@163.com>
 * @since 2020/6/6
 */
@RestController
@RequestMapping("/")
@Slf4j
public class RootController {

    /**
     * 短码与请求数据统计的缓存
     * todo 切换成分布式缓存
     */
    private static final Map<String, RequestData> SURL_STATISTICS = new ConcurrentHashMap<>();

    @Resource
    private ShortUrlService shortUrlService;

    /**
     * 路由
     *
     * @param shortCode
     * @param response
     */
    @GetMapping("{shortCode}")
    public ResponseEntity route(@PathVariable("shortCode") String shortCode,
                                HttpServletRequest request,
                                HttpServletResponse response) {
        ShortUrl shortUrlInfo = shortUrlService.findShortUrl(shortCode);
        SURL_STATISTICS.computeIfAbsent(shortCode, s -> RequestData.from(shortUrlInfo.getShortUrl(), request))
                .incAccessCount();
        return shortUrlInfo.buildResponseEntity(response);
    }

    /**
     * 扫码路由
     */
    @GetMapping("qr/{shortCode}")
    public void scanQrRoute(@PathVariable("shortCode") String shortCode,
                            HttpServletRequest request,
                            HttpServletResponse response) throws IOException {
        ShortUrl shortUrlInfo = shortUrlService.findShortUrl(shortCode);
        SURL_STATISTICS.computeIfAbsent(shortCode, s -> RequestData.from(shortUrlInfo.getShortUrl(), request))
                .incAccessCount();
        shortUrlInfo.sendRedirect(response);
    }

    /**
     * 生成短链
     *
     * @param url
     * @return
     */
    @GetMapping("surl/gen/v1")
    @ResponseBody
    public String shortUrl(@RequestParam("url") String url,
                           @RequestParam("scanQrAccess") boolean scanQrAccess) {
        return shortUrlService.genShortUrl(url, scanQrAccess).getShortUrl();
    }


    /**
     * 替换原始url
     *
     * @param surl             短链接
     * @param originReplaceUrl 替换原始的链接
     * @return
     */
    @GetMapping("surl/replace/v1")
    @ResponseBody
    public boolean replaceOriginUrl(@RequestParam("surl") String surl,
                                    @RequestParam("originReplaceUrl") String originReplaceUrl) {
        return shortUrlService.replaceOriginUrl(surl, originReplaceUrl);
    }

}
