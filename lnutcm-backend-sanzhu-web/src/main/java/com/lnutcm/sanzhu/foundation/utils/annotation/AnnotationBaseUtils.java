package com.lnutcm.sanzhu.foundation.utils.annotation;

import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class AnnotationBaseUtils {



    public static boolean hasMethodAnnotation
            (Method  handlerMethod,Class<? extends Annotation> annotationType){
        return AnnotatedElementUtils.hasAnnotation(handlerMethod, annotationType);
    }
}
