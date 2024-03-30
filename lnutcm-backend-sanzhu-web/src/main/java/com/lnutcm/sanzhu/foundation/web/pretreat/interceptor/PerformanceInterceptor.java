package com.lnutcm.sanzhu.foundation.web.pretreat.interceptor;

import com.lnutcm.sanzhu.foundation.utils.annotation.AnnotationBaseUtils;
import com.lnutcm.sanzhu.foundation.log.LoggerDuration;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.Method;
/**
 *  2.应用场景:<br>
 *   日志记录，可以记录请求信息的日志，以便进行信息监控、信息统计、计算PV（Page View）等等。<br>
 *      *
 *      * 权限检查：如登陆检测，进入处理器检测是否登陆，如果没有直接返回到登陆页面。<br>
 *      *
 *      * 性能监控：有时候系统在某段时间莫名其妙的慢，可以通过拦截器在进入处理器之前记录开始时间，在处理完后记录结束时间，从而得到该请求的处理时间（如果有反向代理，如apache可以自动记录）；
 *      *
 *      * 通用行为：读取cookie得到用户信息并将用户对象放入请求，从而方便后续流程使用，还有如提取Locale、Theme信息等，只要是多个处理器都需要的即可使用拦截器实现。
 *      *
 * */
@Component
public class PerformanceInterceptor implements HandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     *定义ThreadLocal变量，在多线程环境下记录请求处理的开始时间。
     ThreadLocal为每个线程存储一个独立的变量副本。
     因此，每个线程都可以独立地访问和修改该变量的值，而不会影响其他线程。
     在多线程环境下，使用ThreadLocal可以避免线程安全问题，从而提高程序的并发性能。
     */

    private static final ThreadLocal<Long> START_TIME = new ThreadLocal<>();

    /**
     * 描述:此方法的作用是在请求进入到Controller进行拦截，有返回值。<br>
     * (返回true则将请求放行进入Controller控制层，false则请求结束返回错误信息)<br>
     * */
    @Override
 public   boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
      if(handler instanceof HandlerMethod handlerMethod){
         //handler:HandlerMethod类型对象,里面的属性有请求所在的类信息、
          //请求方法、请求参数等内容。
          //所以从这里可以认为handler相当于是平常业务代码中每个请求对应的的controller类以及方法信息
          Method method=handlerMethod.getMethod();
          //1.实现框架的记录请求时间功能,通过注解功能完成
          //业务说明:判断请求方法上是否带有@LoggerDuration注解，如果有：开始记录
          if(AnnotationBaseUtils.hasMethodAnnotation(method,LoggerDuration.class)){
              logger.info("-----------------记录请求时间开始---------------------");
              logger.info("记录请求耗时---1.请求线程名称:"+Thread.currentThread().getName());
              START_TIME.set(System.currentTimeMillis());//记录请求起始时间

          }



      }

        return true;
    }

    /**在业务处理器处理请求执行完成后，生成视图之前执行 */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                    @Nullable ModelAndView modelAndView) throws Exception {



    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                         @Nullable Exception ex) throws Exception {
        if(handler instanceof HandlerMethod handlerMethod){
            //handler:HandlerMethod类型对象,里面的属性有请求所在的类信息、
            //请求方法、请求参数等内容。
            //所以从这里可以认为handler相当于是平常业务代码中每个请求对应的的controller类以及方法信息
            Method method=handlerMethod.getMethod();
            if(AnnotationBaseUtils.hasMethodAnnotation(method,LoggerDuration.class)
            && START_TIME.get()!=null){
                long duration=  System.currentTimeMillis()-START_TIME.get();//完整请求所用时间
                String addr=request.getRemoteAddr();   //获取发送请求的客户端ip地址
                String  methodName=handlerMethod.getMethod().getName();//请求方法名
                logger.info("记录请求耗时---2.该请求耗时:"+duration+"毫秒");
                logger.info("记录请求耗时---3.发送请求的客户端ip地址:"+addr);
                logger.info("记录请求耗时---4.请求方法名:"+methodName);
                logger.info("-----------------记录请求时间结束---------------------");
                START_TIME.remove();
            }

        }
    }
}
