package com.sanzhu.mybatis.generator.plugins.annotation;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;

import java.util.List;


import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;
/**
 * 项目名称：springboot 后端脚手架<br>
 * 程序名称：lnutcm-backend-sanzhu-mybatis-generator-plugins<br>
 * 日期：2024-04-07<br>
 * 作者：胡蓉蓉<br>
 * 模块：Mybatis Generator 逆向工程<br>
 * 描述：1.编写目的:为项目中的model添加注解<br>
 * 2.功能分类:增强类级别注解<br>
 * <p>
 * 备注：禁止商用<br>
 * ------------------------------------------------------------<br>
 * 修改历史
 * 序号               日期              修改人       修改原因
 * 修改备注：
 */

public class AddClassAnnotationsPlugin  extends PluginAdapter {

    public static final String ANNOTATION_CLASS = "annotationClass";
    public static final String ANNOTATION_STRING = "annotationString";

    private String annotationClass="";
    private String annotationString="";

    @Override
    public boolean validate(List<String> warnings){

        this.annotationClass = properties.getProperty("annotationClass"); //需要引入的类全名
        this.annotationString = properties.getProperty("annotationString"); //替换为
        boolean valid = stringHasValue(annotationClass)
                && stringHasValue(annotationString);//判断配置的字符串是否为空
        if (!valid)  {
            if (!stringHasValue(annotationClass)) {
                warnings.add(getString("ValidationError.18", //$NON-NLS-1$
                        "AddClassAnnotationsPlugin", //$NON-NLS-1$
                        "annotationClass")); //$NON-NLS-1$
            }
            if (!stringHasValue(annotationString)) {
                warnings.add(getString("ValidationError.18", //$NON-NLS-1$
                        "AddClassAnnotationsPlugin", //$NON-NLS-1$
                        "annotationString")); //$NON-NLS-1$
            }
        }

        return valid;
    }
    /**
     * Function name:clientGenerated<br>
     * Params:<br>
     * param  introspectedTable the introspected table<br>
     * param  interfaze Interface<br>
     * Inside the function:<br>
     * 当生成整个客户端时，会调用此方法。实现此方法可将其他方法或字段添加到生成的客户端接口或实现中。<br>
     */
    @Override
    public boolean clientGenerated(Interface interfaze, IntrospectedTable introspectedTable) {
            interfaze.addImportedType(
                    new FullyQualifiedJavaType(annotationClass)); //$NON-NLS-1$
            interfaze.addAnnotation(annotationString); //$NON-NLS-1$

        return true;
    }
}
