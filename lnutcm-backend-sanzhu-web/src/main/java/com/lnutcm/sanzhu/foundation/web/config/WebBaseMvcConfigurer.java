package com.lnutcm.sanzhu.foundation.web.config;

import com.lnutcm.sanzhu.foundation.web.pretreat.interceptor.PerformanceInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan("com.lnutcm.sanzhu.foundation.web")
public class WebBaseMvcConfigurer implements WebMvcConfigurer {
    @Autowired
private PerformanceInterceptor performanceInterceptor;


    /** */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //1.加入的顺序就是拦截器执行的顺序，
        //2.按顺序执行所有拦截器的preHandle
        //3.所有的preHandle 执行完再执行全部postHandle 最后是postHandle
        registry.addInterceptor(performanceInterceptor);
    }
}
