package com.bil.account.configuration;

import com.bil.account.model.base.Response;
import com.fasterxml.jackson.databind.JsonMappingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 全局异常处理器
 *
 * @author haibo.yang
 * @since 2021/11/20
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Response<?> defaultHandler(HttpServletRequest request, Exception exception) {
        StringBuffer url = request.getRequestURL();
        log.error("请求url异常-url:{}", url, exception);
        if (exception instanceof MethodArgumentNotValidException
                || exception instanceof JsonMappingException
                || exception instanceof HttpMessageNotReadableException
                || exception instanceof HttpRequestMethodNotSupportedException
                || exception instanceof ServletRequestBindingException
                || exception instanceof HttpMessageNotWritableException) {
            return Response.fail(getParamErrorDetails(exception));
        } else {
            exception.printStackTrace();
            return Response.fail(exception.getMessage());
        }
    }

    private String getParamErrorDetails(Exception e) {
        StringBuilder sb = new StringBuilder();
        if (e instanceof MethodArgumentNotValidException) {
            for (ObjectError error : ((MethodArgumentNotValidException) e).getBindingResult().getAllErrors()) {
                DefaultMessageSourceResolvable messageSourceResolvable = (DefaultMessageSourceResolvable) Objects.requireNonNull(error.getArguments())[0];
                sb.append("[key=").append(messageSourceResolvable.getCode()).append(",");
                sb.append(error.getDefaultMessage()).append("] ");
            }
        }
        if (e instanceof MissingServletRequestParameterException) {
            sb.append("[key=");
            sb.append(((MissingServletRequestParameterException) e).getParameterName());
            sb.append("],");
            sb.append(e.getMessage());
        }
        if (e instanceof HttpMessageNotReadableException) {
            sb.append(e.getLocalizedMessage());
        }
        if (StringUtils.isEmpty(sb.toString())) {
            sb.append(e.getMessage());
        }
        return sb.toString();
    }
}
