package com.lnutcm.sanzhu.foundation.web.enhance.request.handler;


import com.alibaba.fastjson2.JSON;
import com.lnutcm.sanzhu.foundation.log.LoggerParam;
import com.lnutcm.sanzhu.foundation.utils.annotation.AnnotationBaseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * RequestBodyAdvice：<br>
 * Spring框架中用于处理请求接口，它可以在请求体被读取之前和响应体被写入之前进行一些处理。<br>
 *
 * 使用场景：<br>
 *
 * 日志记录：记录请求和响应的详细信息。<br>
 *
 * 参数校验：在请求体被读取之前进行校验。<br>
 *
 * 参数转换：在请求体被读取之前进行参数转换。<br>
 *
 * 响应数据处理：在响应体被写入之前进行数据处理。<br>
 *
 * */
@ControllerAdvice
public class RequestBodyBaseAdvice implements RequestBodyAdvice {


    private final Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * Invoked first to determine if this interceptor applies.
     *
     * @param methodParameter the method parameter  方法的参数对象<br>
     * @param targetType      the target type, not necessarily the same as the method  type方法的参数类型
     *                        parameter type, e.g. for {@code HttpEntity<String>}.<br>
     * @param converterType   the selected converter type<br>
     * @return whether this interceptor should be invoked or not<br>
     */
    @Override
    public boolean supports(MethodParameter methodParameter,
                            Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        if(AnnotationBaseUtils.
                hasMethodAnnotation(methodParameter.getMethod(), LoggerParam.class)){
            return true;
        }
        return false;
    }

    /**
     * Invoked second before the request body is read and converted.
     *
     * @param inputMessage  the request
     * @param parameter     the target method parameter
     * @param targetType    the target type, not necessarily the same as the method
     *                      parameter type, e.g. for {@code HttpEntity<String>}.
     * @param converterType the converter used to deserialize the body
     * @return the input request or a new instance (never {@code null})
     */
    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {


        return inputMessage;
    }

    /**
     * Invoked third (and last) after the request body is converted to an Object.
     *
     * @param body          set to the converter Object before the first advice is called
     * @param inputMessage  the request
     * @param parameter     the target method parameter
     * @param targetType    the target type, not necessarily the same as the method
     *                      parameter type, e.g. for {@code HttpEntity<String>}.
     * @param converterType the converter used to deserialize the body
     * @return the same body or a new instance
     */
    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter,
                                Type targetType,
                                Class<? extends HttpMessageConverter<?>> converterType) {
        String  strBody=JSON.toJSONString(body);
        logger.info("1.打印请求参数---------------请求方法："+parameter.getMethod().getName());
        logger.info("1.打印请求参数---------------参数列表："+strBody);
        return body;
    }

    /**
     * Invoked second (and last) if the body is empty.
     *
     * @param body          usually set to {@code null} before the first advice is called
     * @param inputMessage  the request
     * @param parameter     the method parameter
     * @param targetType    the target type, not necessarily the same as the method
     *                      parameter type, e.g. for {@code HttpEntity<String>}.
     * @param converterType the selected converter type
     * @return the value to use, or {@code null} which may then raise an
     * {@code HttpMessageNotReadableException} if the argument is required
     */
    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {

        return body;
    }
}
