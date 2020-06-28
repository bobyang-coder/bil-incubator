package com.bil.shorturl.model;

import com.bil.shorturl.util.QRCodeUtil;
import com.google.zxing.WriterException;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

/**
 * 短链
 *
 * @author haibo.yang   <bobyang_coder@163.com>
 * @since 2020/06/07
 */
@Data
@Builder
public class ShortUrl implements Serializable {

    /**
     * 短链网址
     */
    private String shortWebsite;

    /**
     * 短码
     */
    private String shortCode;

    /**
     * 原始 url
     */
    private String originUrl;

    /**
     * 原始 url 的 hash 值，用于 db 搜索使用
     * <p>
     * 为什么要用 hash 值搜索呢？
     * - 因为原始 url 可能太长
     */
    private String originHash;

    /**
     * 关闭访问
     */
    private boolean closeAccess;

    /**
     * 扫码访问
     */
    private boolean scanQrAccess;

    /**
     * 访问密码
     */
    private String accessPwd;

    /**
     * 是否开启统计
     */
    private boolean openStatistics;

    /**
     * 生效时间
     */
    private Date startEffectiveTime;

    /**
     * 结束时间
     */
    private Date endEffectiveTime;

    /**
     * 有效时段
     */
    private String effectivePeriod;

    /**
     * IP黑名单
     */
    private String[] ipBlacklist;

    /**
     * 禁止来源
     */
    private String[] sourceBlacklist;

    /**
     * 获取短链
     *
     * @return
     */
    public String getShortUrl() {
        return shortWebsite + "/" + shortCode;
    }

    public ResponseEntity buildResponseEntity(HttpServletResponse response) {
        if (scanQrAccess) {
            //返回二维码
            return buildQRImageResponse();
        }
        try {
            //重定向
            sendRedirect(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void sendRedirect(HttpServletResponse response) throws IOException {
        response.addHeader("shortUrl", getShortUrl());
        response.addHeader("originUrl", originUrl);
        response.setStatus(HttpStatus.FOUND.value());
        response.sendRedirect(originUrl);
    }

    /**
     * 构建二维码图片响应
     *
     * @return
     */
    public ResponseEntity<byte[]> buildQRImageResponse() {
        //二维码内的信息
        String info = getShortUrl();
        byte[] qrCode = null;
        try {
            qrCode = QRCodeUtil.getQRCodeImage(info, 360, 360);
        } catch (WriterException e) {
            System.out.println("Could not generate QR Code, WriterException :: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Could not generate QR Code, IOException :: " + e.getMessage());
        }
        // Set headers
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(qrCode, headers, HttpStatus.CREATED);
    }
}
