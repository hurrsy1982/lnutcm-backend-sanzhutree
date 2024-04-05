package com.sanzhu.mybatis.generator.plugins.rename;


import com.sanzhu.mybatis.generator.plugins.utils.CommentUtils;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.internal.util.StringUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;



/**
 * 项目名称：springboot 后端脚手架<br>
 * 程序名称：lnutcm-backend-sanzhu-mybatis-generator-plugins<br>
 * 日期：2024-04-01<br>
 * 作者：胡蓉蓉<br>
 * 模块：Mybatis Generator 逆向工程-RenameJavaMapperPlugin<br>
 * 描述：1.编写目的:自定义项目中逆向工程中Mapper的生成位置<br>
 * 2.实现需求:修改Mapper的生成位置<br>
 * <p>
 * 备注：禁止商用<br>
 * ------------------------------------------------------------<br>
 * 修改历史
 * 序号               日期              修改人       修改原因
 * 修改备注：
 */
public class GenerateSpringDocPlugin extends PluginAdapter {

    private final static Logger logger
            = LoggerFactory.getLogger(GenerateSpringDocPlugin.class);

    /**
     * 抑制所有注解 默认false：
     */
    private boolean suppressSpringDocAnnotation;


    /***/
    private static final String API_MODEL_SCHEMA_FULL_CLASS_NAME
            = "io.swagger.v3.oas.annotations.media.Schema";

    private static final String API_PROPERTIES_SCHEMA_FULL_CLASS_NAME
            = "io.swagger.v3.oas.annotations.media.Schema";


    public GenerateSpringDocPlugin() {
        this.suppressSpringDocAnnotation = false;

    }


    /**
     * This method is called after all the setXXX methods are called, but before
     * any other method is called. This allows the plugin to determine whether
     * it can run or not. For example, if the plugin requires certain properties
     * to be set, and the properties are not set, then the plugin is invalid and
     * will not run.
     *
     * @param warnings add strings to this list to specify warnings. For example, if
     *                 the plugin is invalid, you should specify why. Warnings are
     *                 reported to users after the completion of the run.
     * @return true if the plugin is in a valid state. Invalid plugins will not
     * be called
     */

    @Override
    public boolean validate(List<String> warnings) {

        this.suppressSpringDocAnnotation = StringUtility.isTrue
                (properties.getProperty("suppressAnnotation")); //是否抑制Swagger注解
        boolean valid = !suppressSpringDocAnnotation;//判断是否抑制注解
        return valid;
    }

    /**
     * This method is called after the field is generated for a specific column
     * in a table.
     *
     * @param field              the field generated for the specified column
     * @param topLevelClass      the partially implemented model class. You can add additional
     *                           imported classes to the implementation class if necessary.
     * @param introspectedColumn The class containing information about the column related
     *                           to this field as introspected from the database
     * @param introspectedTable  The class containing information about the table as
     *                           introspected from the database
     * @param modelClassType     the type of class that the field is generated for
     * @return true if the field should be generated, false if the generated
     * field should be ignored. In the case of multiple plugins, the
     * first plugin returning false will disable the calling of further
     * plugins.
     */
    @Override
    public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass,
                                       IntrospectedColumn introspectedColumn,
                                       IntrospectedTable introspectedTable,
                                       ModelClassType modelClassType) {
       var annotation="";

        if (introspectedColumn.getRemarks() != null) {
            var remarks = CommentUtils.
                    formatRemarks(true, introspectedColumn.getRemarks());//格式化注释
         annotation="@Schema(name=\"" + field.getName()+
                "\"" + " " + "description=" + "\""+remarks
                + "\""+ " "
                + ")";
         if(!introspectedColumn.isNullable()){
             annotation="@Schema(name=\"" + field.getName()+
                     "\"" + " " + "description=" + "\""+remarks
                     + "\""+ " requiredMode= Schema.RequiredMode.REQUIRED"
                     + ")";

         }
        }
        field.addAnnotation(annotation);
        //topLevelClass.addImportedType
         //       (new FullyQualifiedJavaType(API_MODEL_SCHEMA_FULL_CLASS_NAME));
        //introspectedColumn.isNullable()
        // @Schema(name="employeeId" ,description="雇员ID" ,type="Long", requiredMode= Schema.RequiredMode.REQUIRED)
        return super.modelFieldGenerated(field, topLevelClass, introspectedColumn, introspectedTable, modelClassType);
    }

    /**
     * This method is called after the base record class is generated by the
     * JavaModelGenerator. This method will only be called if
     * the table rules call for generation of a base record class.
     * <br><br>
     * This method is only guaranteed to be called by the default Java
     * model generators. Other user supplied generators may, or may not, call
     * this method.
     *
     * @param topLevelClass     the generated base record class
     * @param introspectedTable The class containing information about the table as
     *                          introspected from the database
     * @return true if the class should be generated, false if the generated
     * class should be ignored. In the case of multiple plugins, the
     * first plugin returning false will disable the calling of further
     * plugins.
     */
    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        var remarks = CommentUtils.
                formatRemarks(true, introspectedTable.getRemarks());//格式化注释
        topLevelClass.
                addAnnotation("@Schema(name=\"" + topLevelClass.getClass().getName() +
                        "\"" + " " + "description=" + "\""+remarks
                        + "\"");//在类上加上注解@Schema
        topLevelClass.addImportedType
                (new FullyQualifiedJavaType(API_MODEL_SCHEMA_FULL_CLASS_NAME));
        return super.modelBaseRecordClassGenerated(topLevelClass, introspectedTable);
    }

    /**
     * This method is called after the setter, or mutator, method is generated
     * for a specific column in a table.
     *
     * @param method             the setter, or mutator, method generated for the specified
     *                           column
     * @param topLevelClass      the partially implemented model class. You can add additional
     *                           imported classes to the implementation class if necessary.
     * @param introspectedColumn The class containing information about the column related
     *                           to this field as introspected from the database
     * @param introspectedTable  The class containing information about the table as
     *                           introspected from the database
     * @param modelClassType     the type of class that the field is generated for
     * @return true if the method should be generated, false if the generated
     * method should be ignored. In the case of multiple plugins, the
     * first plugin returning false will disable the calling of further
     * plugins.
     */
    @Override
    public boolean modelSetterMethodGenerated(Method method,
                                              TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
                                              IntrospectedTable introspectedTable, ModelClassType modelClassType) {

        return true;
    }
}
