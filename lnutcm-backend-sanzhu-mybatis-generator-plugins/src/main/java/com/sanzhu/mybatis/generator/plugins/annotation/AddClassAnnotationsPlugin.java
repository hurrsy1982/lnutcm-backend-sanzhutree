package com.sanzhu.mybatis.generator.plugins.annotation;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;

import java.util.List;


import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

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
                        "searchString")); //$NON-NLS-1$
            }
            if (!stringHasValue(annotationString)) {
                warnings.add(getString("ValidationError.18", //$NON-NLS-1$
                        "AddClassAnnotationsPlugin", //$NON-NLS-1$
                        "replaceString")); //$NON-NLS-1$
            }
        }

        return valid;
    }
    /**
     * Function name:initialized<br>
     * Params:<br>
     * param  introspectedTable the introspected table<br>
     * Inside the function:<br>
     * 方法在对内省表调用getGeneratedXXXFiles方法之前调用的。插件
     * 可以实现此方法来覆盖任何默认属性，或更改数据库的结果内省，在任何代码生成活动发生之前。<br>
     */
    @Override
    public boolean clientGenerated(Interface interfaze, IntrospectedTable introspectedTable) {

            interfaze.addImportedType(
                    new FullyQualifiedJavaType(annotationClass)); //$NON-NLS-1$
            interfaze.addAnnotation("@Mapper"); //$NON-NLS-1$

        return true;
    }
}
