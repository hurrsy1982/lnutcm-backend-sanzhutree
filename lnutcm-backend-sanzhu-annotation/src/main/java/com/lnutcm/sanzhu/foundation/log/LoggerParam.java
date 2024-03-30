package com.lnutcm.sanzhu.foundation.log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 项目名称：springboot 后端脚手架<br>
 * 程序名称：lutcm-backend-sanzhu-annotation<br>
 * 日期：2024-03-25<br>
 * 作者：胡蓉蓉<br>
 * 模块：注解模块<br>
 * 描述：1.编写目的:该注解是方法级注解<br>
 *      2.使用方法，在Controller层某方法加上该注解，打印入参
 * 备注：禁止商用<br>
 * ------------------------------------------------------------<br>
 * 修改历史
 * 序号               日期              修改人       修改原因
 *
 * 修改备注：
 * @version 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoggerParam {
}
