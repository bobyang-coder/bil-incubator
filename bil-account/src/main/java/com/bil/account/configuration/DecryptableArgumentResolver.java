package com.bil.account.configuration;

import com.alibaba.fastjson.JSON;
import com.bil.account.model.base.EncryptReq;
import com.bil.account.utils.des.Decryptable;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.util.Assert;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;
import org.springframework.web.servlet.mvc.method.annotation.ServletModelAttributeMethodProcessor;

import javax.servlet.ServletRequest;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 参数解密
 */
@Slf4j
public class DecryptableArgumentResolver implements HandlerMethodArgumentResolver {
    private static final String SIGN = "bozige";
    ServletModelAttributeMethodProcessor formResolver = new ServletModelAttributeMethodProcessor(true);
    RequestResponseBodyMethodProcessor jsonResolver = new RequestResponseBodyMethodProcessor(Lists.newArrayList(
            new GsonHttpMessageConverter()
    ));


    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        Decryptable decrypt = methodParameter.getParameterAnnotation(Decryptable.class);
        Class<?> parameterType = methodParameter.getParameterType();
        return null != decrypt && (parameterType.equals(EncryptReq.class));
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) throws Exception {
        ServletRequest servletRequest = nativeWebRequest.getNativeRequest(ServletRequest.class);
        String contentType = servletRequest.getContentType();
        if (contentType == null) {
            throw new IllegalArgumentException("不支持contentType");
        }
        Object obj;
        if (contentType.contains(MediaType.APPLICATION_JSON_VALUE)) {
            obj = jsonResolver.resolveArgument(methodParameter, modelAndViewContainer, nativeWebRequest, webDataBinderFactory);
        } else if (contentType.contains(MediaType.APPLICATION_FORM_URLENCODED_VALUE)) {
            obj = formResolver.resolveArgument(methodParameter, modelAndViewContainer, nativeWebRequest, webDataBinderFactory);
        } else if (contentType.contains("multipart")) {
            obj = formResolver.resolveArgument(methodParameter, modelAndViewContainer, nativeWebRequest, webDataBinderFactory);
        } else {
            throw new IllegalArgumentException("不支持contentType");
        }
        Assert.notNull(obj, "请求参数不能为空");
        EncryptReq req = (EncryptReq) obj;
        //验签
        String sign = req.getSign();
        Assert.notNull(sign, "签名不能为空");
        Assert.isTrue(SIGN.equals(sign), "签名错误");
        //数据解密 todo
        String encryptData = req.getEncryptData();
        Class<?> aClass = getGenericClass(methodParameter.getGenericParameterType());
        Object o = JSON.parseObject(encryptData, aClass);
        req.setData(o);
        log.info(JSON.toJSONString(req));
        return req;
    }

    private Class<?> getGenericClass(Type type) {
        ParameterizedType parameterizedType = ((ParameterizedType) type);
        Object genericClass = parameterizedType.getActualTypeArguments()[0];
        if (genericClass instanceof ParameterizedType) { // 处理多级泛型
            return (Class<?>) ((ParameterizedType) genericClass).getRawType();
        } else if (genericClass instanceof GenericArrayType) { // 处理数组泛型
            return (Class<?>) ((GenericArrayType) genericClass).getGenericComponentType();
        } else {
            return (Class<?>) genericClass;
        }
    }
}
