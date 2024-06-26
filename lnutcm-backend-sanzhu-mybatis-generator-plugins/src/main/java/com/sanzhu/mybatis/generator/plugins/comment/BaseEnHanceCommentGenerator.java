package com.sanzhu.mybatis.generator.plugins.comment;

import com.lnutcm.sanzhu.utils.date.DateUtils;
import com.sanzhu.mybatis.generator.plugins.utils.CommentUtils;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.kotlin.KotlinFile;
import org.mybatis.generator.api.dom.kotlin.KotlinFunction;
import org.mybatis.generator.api.dom.kotlin.KotlinProperty;
import org.mybatis.generator.api.dom.kotlin.KotlinType;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.internal.DefaultCommentGenerator;
import org.mybatis.generator.internal.util.StringUtility;


import java.util.Properties;
import java.util.Set;

import static org.mybatis.generator.internal.util.StringUtility.isTrue;

/**
 * 项目名称：springboot 后端脚手架<br>
 * 程序名称：lnutcm-backend-sanzhu-mybatis-generator-plugins<br>
 * 日期：2024-03-23<br>
 * 作者：胡蓉蓉<br>
 * 模块：Mybatis Generator 逆向工程<br>
 * 描述：1.编写目的:自定义项目中逆向工程后的注释<br>
 * 2.功能分类:增强属性注释、类级别注释、方法注释 <br>
 * <p>
 * 备注：禁止商用<br>
 * ------------------------------------------------------------<br>
 * 修改历史
 * 序号               日期              修改人       修改原因
 * 修改备注：
 */

public class BaseEnHanceCommentGenerator extends DefaultCommentGenerator {
    private final Properties properties = new Properties();

    /**
     * 抑制日期  默认false：
     */
    private boolean suppressDate;

    /**
     * 抑制所有注释 默认false：
     */
    private boolean suppressAllComments;

    /**
     * 使用自定义模块 默认false;
     */
    private boolean addRemarkComments;

    public BaseEnHanceCommentGenerator() {
        suppressDate = false;
        suppressAllComments = false;
        addRemarkComments = false;
    }

    /**
     * Adds properties for this instance from any properties configured in the
     * CommentGenerator configuration.
     *
     * <p>This method will be called before any of the other methods.
     *
     * @param properties All properties from the configuration
     */
    @Override
    public void addConfigurationProperties(Properties properties) {
        this.properties.putAll(properties);
        this.suppressDate = StringUtility.isTrue
                (properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_DATE));
        this.suppressAllComments = StringUtility.isTrue
                (properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_ALL_COMMENTS));
        this.addRemarkComments = StringUtility.isTrue(properties.getProperty
                (PropertyRegistry.COMMENT_GENERATOR_ADD_REMARK_COMMENTS));
    }

    /**
     * Function name:    addFieldComment<br>
     * Params:<br>
     *
     * @param field              the field<br>
     * @param introspectedTable  the introspected table<br>
     * @param introspectedColumn the introspected column<br>
     *                           Inside the function:<br>
     *                           方法为指定的字段添加一个 Javadoc 注释。该字段与指定的表相关，并用于保存指定列的值。
     */

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable,
                                IntrospectedColumn introspectedColumn) {
        // 添加字段注释
        String fieldRemarks = introspectedColumn.getRemarks();
        if (fieldRemarks != null) {
            if (this.suppressAllComments || !addRemarkComments) {
                return;
            }
            field.addJavaDocLine("/**"); //$NON-NLS-1$
            String remarks = CommentUtils.
                    formatRemarks(this.addRemarkComments, introspectedColumn.getRemarks());
            //取出对应字段注释 ,格式化字段对应列的注释
            field.addJavaDocLine(" *  <p> 字段备注:" + remarks + "</p>");
            //添加数据库对应字段与表信息
            field.addJavaDocLine(" *  <p> 表:" + introspectedTable.getFullyQualifiedTable() + "</p>");
            field.addJavaDocLine(" *  <p> 列:" + introspectedColumn.getActualColumnName());
            if (!suppressDate) {
                //添加对应时间
                field.addJavaDocLine(" *  <p>时间:" + DateUtils.getStringCurrentDate() + "</p>");
            }

            field.addJavaDocLine(" */"); //$NON-NLS-1$
        }
        //super.addFieldComment(field, introspectedTable, introspectedColumn);
    }


    /**
     * Function name:    addFieldComment<br>
     * Params:<br>
     *
     * @param field             the field<br>
     * @param introspectedTable the introspected table<br>
     *                          Inside the function:<br>
     *                          方法为指定的静态字段添加一个 Javadoc 注释。该字段与指定的表相关，并用于保存指定列的值。
     */
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
        if (this.suppressAllComments || !addRemarkComments) {
            return;
        }
        field.addJavaDocLine("/**"); //
        //添加数据库对应字段与表信息
        field.addJavaDocLine(" *  <p> 表:" + introspectedTable.getFullyQualifiedTable() + "</p>");
        if (!suppressDate) {
            //添加对应时间
            field.addJavaDocLine(" *  <p> 时间:" + DateUtils.getStringCurrentDate() + "</p>");
        }

        field.addJavaDocLine(" */"); //$NON-NLS-1$

        //super.addFieldComment(field, introspectedTable);
    }

    /**
     * Function name:    addModelClassComment<br>
     * Params:<br>
     *
     * @param topLevelClass     the the top level class<br>
     * @param introspectedTable the introspected table<br>
     *                          Inside the function:<br>
     *                          自定义类注释。
     */
    @Override
    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        if (suppressAllComments || !addRemarkComments) {
            return;
        }
        topLevelClass.addJavaDocLine("/**"); //$NON-NLS-1$
        String remarks = CommentUtils.
                formatRemarks(this.addRemarkComments, introspectedTable.getRemarks());
        String s = "* 项目名称:<br> "
                + "* 程序名称:"+"<br>"
                + "* 日期:" + DateUtils.getStringCurrentDate() + "<br>"
                + "* 作者:" + System.getProperty("user.name") + "<br>"
                + "* 模块:" + introspectedTable.getFullyQualifiedTable() + "<br>"
                + "* 描述:" + remarks + "<br>";

        topLevelClass.addJavaDocLine(s);

        topLevelClass.addJavaDocLine(" */"); //
        // super.addModelClassComment(topLevelClass, introspectedTable);

    }

    /**
     * Adds a comment for a model class.
     *
     * @param modelClass        the generated KotlinType for the model
     * @param introspectedTable the introspected table
     */
    @Override
    public void addModelClassComment(KotlinType modelClass, IntrospectedTable introspectedTable) {
        super.addModelClassComment(modelClass, introspectedTable);
    }

    /**
     * Adds the inner class comment.
     *
     * @param innerClass        the inner class
     * @param introspectedTable the introspected table
     */
    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {
        if (suppressAllComments || !addRemarkComments) {
            return;
        }
        super.addClassComment(innerClass, introspectedTable);
    }

    /**
     * Adds the inner class comment.
     *
     * @param innerClass        the inner class
     * @param introspectedTable the introspected table
     * @param markAsDoNotDelete the mark as do not delete
     */
    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {
        super.addClassComment(innerClass, introspectedTable, markAsDoNotDelete);
    }

    /**
     * Adds the enum comment.
     *
     * @param innerEnum         the inner enum
     * @param introspectedTable the introspected table
     */
    @Override
    public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable) {
        super.addEnumComment(innerEnum, introspectedTable);
    }


    /**
     * Function name:    addGetterComment<br>
     * Inside the function:<br>
     * getter注释<br>
     * Params:<br>
     *
     * @param method             the method<br>
     * @param introspectedTable  the introspected table<br>
     * @param introspectedColumn the introspected column<br>
     */
    @Override
    public void addGetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }

        super.addGetterComment(method, introspectedTable, introspectedColumn);
    }

    /**
     * Function name:    addSetterComment<br>
     * Inside the function:<br>
     * setter  注释<br>
     * Params:<br>
     *
     * @param method             the method<br>
     * @param introspectedTable  the introspected table<br>
     * @param introspectedColumn the introspected column<br>
     */
    @Override
    public void addSetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }

        super.addSetterComment(method, introspectedTable, introspectedColumn);
    }

    /**
     * Function name:addGeneralMethodComment<br>
     * Params:<br>
     * param  method            the method<br>
     * param  introspectedTable the introspected table<br>
     * Inside the function:<br>
     * 为生成的Mapper的方法添加注释<br>
     */
    @Override
    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
        if (suppressAllComments || !addRemarkComments) {
            return;
        }
        String remarks = introspectedTable.getRemarks();
        if (addRemarkComments && StringUtility.stringHasValue(remarks)) {
            method.addJavaDocLine("/**"); //$NON-NLS-1$
            if ("insert".equalsIgnoreCase(method.getName())) {
                method.addJavaDocLine(" * " + "<p>新增对象-" + introspectedTable.getRemarks() + "</p>");
            }
            if ("insertSelective".equalsIgnoreCase(method.getName())) {
                method.addJavaDocLine(" * " + "<p>选择性新增对象-" + introspectedTable.getRemarks() + "</p>");

            }
            if ("selectByPrimaryKey".equalsIgnoreCase(method.getName())) {
                method.addJavaDocLine(" * " + "<p>根据主键查询 - " + introspectedTable.getRemarks() + "</p>");

            }
            if ("selectByExample".equalsIgnoreCase(method.getName())) {
                method.addJavaDocLine(" * " + "<p>根据条件查询 - " + introspectedTable.getRemarks() + "</p>");

            }
            if ("updateByPrimaryKeySelective".equalsIgnoreCase(method.getName())) {

                method.addJavaDocLine(" * " + "<p>根据主键选择性更新 - " + introspectedTable.getRemarks() + "</p>");

            }
            if ("updateByExampleSelective".equalsIgnoreCase(method.getName())) {
                method.addJavaDocLine(" * " + "<p>根据条件选择性更新 - " + introspectedTable.getRemarks() + "</p>");
            }

            if ("updateByExampleSelective".equalsIgnoreCase(method.getName())) {
                method.addJavaDocLine(" * " + "<p>根据条件选择性更新 - " + introspectedTable.getRemarks() + "</p>");
            }

            if ("updateByExample".equalsIgnoreCase(method.getName())) {
                method.addJavaDocLine(" * " + "<p>根据条件更新 - " + introspectedTable.getRemarks() + "</p>");
            }
            if ("deleteByExample".equalsIgnoreCase(method.getName())) {
                method.addJavaDocLine(" * " + "根据条件删除 - " + introspectedTable.getRemarks());
            }
            if ("deleteByPrimaryKey".equalsIgnoreCase(method.getName())) {
                method.addJavaDocLine(" * " + "根据主键删除 - " + introspectedTable.getRemarks());
            }

            if (method.getName().equalsIgnoreCase("countByExample")) {
                method.addJavaDocLine(" * " + "根据条件查询数量 - " + introspectedTable.getRemarks());
            }

            if (!suppressDate) {//添加对应时间
                method.addJavaDocLine(" *  <p>时间:" + DateUtils.getStringCurrentDate() + "</p>");
            }
            method.addJavaDocLine(" * " + "<p>表:" + introspectedTable.getFullyQualifiedTable() + "</p>");
            method.addJavaDocLine(" */"); //$NON-NLS-1$
            // super.addGeneralMethodComment(method, introspectedTable);
        }

    }
    /**
     * This method is called to add a file level comment to a generated java file. This method
     * could be used to add a general file comment (such as a copyright notice). However, note
     * that the Java file merge function in Eclipse does not deal with this comment. If you run
     * the generator repeatedly, you will only retain the comment from the initial run.
     *
     * <p>The default implementation does nothing.
     *
     * @param compilationUnit the compilation unit
     */
    @Override
    public void addJavaFileComment(CompilationUnit compilationUnit) {
        super.addJavaFileComment(compilationUnit);
    }

    /**
     * This method should add a suitable comment as a child element of the specified xmlElement to warn users that the
     * element was generated and is subject to regeneration.
     *
     * @param xmlElement the xml element
     */
    @Override
    public void addComment(XmlElement xmlElement) {
        super.addComment(xmlElement);
    }

    /**
     * This method is called to add a comment as the first child of the root element. This method
     * could be used to add a general file comment (such as a copyright notice). However, note
     * that the XML file merge function does not deal with this comment. If you run the generator
     * repeatedly, you will only retain the comment from the initial run.
     *
     * <p>The default implementation does nothing.
     *
     * @param rootElement the root element
     */
    @Override
    public void addRootComment(XmlElement rootElement) {
        super.addRootComment(rootElement);
    }

    /**
     * Adds a @Generated annotation to a method.
     *
     * @param method            the method
     * @param introspectedTable the introspected table
     * @param imports           the comment generator may add a required imported type to this list
     * @since 1.3.6
     */
    @Override
    public void addGeneralMethodAnnotation(Method method, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> imports) {
        super.addGeneralMethodAnnotation(method, introspectedTable, imports);
    }

    /**
     * Adds a @Generated annotation to a method.
     *
     * @param method             the method
     * @param introspectedTable  the introspected table
     * @param introspectedColumn thr introspected column
     * @param imports            the comment generator may add a required imported type to this list
     * @since 1.3.6
     */
    @Override
    public void addGeneralMethodAnnotation(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn, Set<FullyQualifiedJavaType> imports) {
        super.addGeneralMethodAnnotation(method, introspectedTable, introspectedColumn, imports);
    }

    /**
     * Adds a @Generated annotation to a field.
     *
     * @param field             the field
     * @param introspectedTable the introspected table
     * @param imports           the comment generator may add a required imported type to this list
     * @since 1.3.6
     */
    @Override
    public void addFieldAnnotation(Field field, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> imports) {
        super.addFieldAnnotation(field, introspectedTable, imports);
    }

    /**
     * Adds a @Generated annotation to a field.
     *
     * @param field              the field
     * @param introspectedTable  the introspected table
     * @param introspectedColumn the introspected column
     * @param imports            the comment generator may add a required imported type to this list
     * @since 1.3.6
     */
    @Override
    public void addFieldAnnotation(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn, Set<FullyQualifiedJavaType> imports) {
        super.addFieldAnnotation(field, introspectedTable, introspectedColumn, imports);
    }

    /**
     * Adds a @Generated annotation to a class.
     *
     * @param innerClass        the class
     * @param introspectedTable the introspected table
     * @param imports           the comment generator may add a required imported type to this list
     * @since 1.3.6
     */
    @Override
    public void addClassAnnotation(InnerClass innerClass, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> imports) {
        super.addClassAnnotation(innerClass, introspectedTable, imports);
    }

    /**
     * This method is called to add a file level comment to a generated Kotlin file. This method
     * could be used to add a general file comment (such as a copyright notice).
     *
     * <p>The default implementation does nothing.
     *
     * @param kotlinFile the Kotlin file
     */
    @Override
    public void addFileComment(KotlinFile kotlinFile) {
        super.addFileComment(kotlinFile);
    }

    @Override
    public void addGeneralFunctionComment(KotlinFunction kf, IntrospectedTable introspectedTable, Set<String> imports) {
        super.addGeneralFunctionComment(kf, introspectedTable, imports);
    }

    @Override
    public void addGeneralPropertyComment(KotlinProperty property, IntrospectedTable introspectedTable, Set<String> imports) {
        super.addGeneralPropertyComment(property, introspectedTable, imports);
    }
}
