package com.sanzhu.mybatis.generator.plugs.comment;

import com.sanzhu.mybatis.generator.plugs.utils.CommentUtils;
import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.kotlin.KotlinFile;
import org.mybatis.generator.api.dom.kotlin.KotlinFunction;
import org.mybatis.generator.api.dom.kotlin.KotlinProperty;
import org.mybatis.generator.api.dom.kotlin.KotlinType;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.internal.DefaultCommentGenerator;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.Arrays;
import java.util.Properties;
import java.util.Set;

/**
 * 项目名称：springboot 后端脚手架<br>
 * 程序名称：lnutcm-backend-sanzhu-mybatis-generator-plugins<br>
 * 日期：2024-03-23<br>
 * 作者：胡蓉蓉<br>
 * 模块：Mybatis Generator 逆向工程<br>
 * 描述：1.编写目的:自定义项目中逆向工程后的注释<br>
 *      2.功能分类:增强行级别注释、类级别注释、方法注释 <br>
 * <p>
 * 备注：禁止商用<br>
 * ------------------------------------------------------------<br>
 * 修改历史
 * 序号               日期              修改人       修改原因
 * 修改备注：
 */

public class BaseEnHanceCommentGenerator extends DefaultCommentGenerator {

    /**
     * 抑制日期  默认false：不抑制
     */
    private boolean suppressDate;

    private boolean suppressAllComments;

    /**
     * If suppressAllComments is true, this option is ignored.
     */
    private boolean addRemarkComments;


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

    }

    /**
     * This method should add a Javadoc comment to the specified field. The field is related to the
     * specified table and is used to hold the value of the specified column.
     * 方法应该为指定的字段添加一个 Javadoc 注释。该字段与指定的表相关，并用于保存指定列的值。
     * <p><b>Important:</b> This method should add a the nonstandard JavaDoc tag "@mbg.generated" to
     * the comment. Without this tag, the Eclipse based Java merge feature will fail.
     *
     * @param field              the field
     * @param introspectedTable  the introspected table
     * @param introspectedColumn the introspected column
     */

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable,
                                IntrospectedColumn introspectedColumn) {
        // 添加字段注释

        String fieldRemarks = introspectedColumn.getRemarks();
        if (fieldRemarks != null) {
            if (this.suppressAllComments) {
                return;
            }
            field.addJavaDocLine("/**"); //$NON-NLS-1$
          String remarks=  CommentUtils.formatRemarks(this.addRemarkComments,introspectedColumn.getRemarks());

        }
        //super.addFieldComment(field, introspectedTable, introspectedColumn);
    }



    /**
     * Adds the field comment.
     *
     * @param field             the field
     * @param introspectedTable the introspected table
     */
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
        super.addFieldComment(field, introspectedTable);
    }

    /**
     * Adds a comment for a model class.  The Java code merger should
     * be notified not to delete the entire class in case any manual
     * changes have been made.  So this method will always use the
     * "do not delete" annotation.
     *
     * <p>Because of difficulties with the Java file merger, the default implementation
     * of this method should NOT add comments.  Comments should only be added if
     * specifically requested by the user (for example, by enabling table remark comments).
     *
     * @param topLevelClass     the top level class
     * @param introspectedTable the introspected table
     */
    @Override
    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        super.addModelClassComment(topLevelClass, introspectedTable);
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
     * Adds the getter comment.
     *
     * @param method             the method
     * @param introspectedTable  the introspected table
     * @param introspectedColumn the introspected column
     */
    @Override
    public void addGetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        super.addGetterComment(method, introspectedTable, introspectedColumn);
    }

    /**
     * Adds the setter comment.
     *
     * @param method             the method
     * @param introspectedTable  the introspected table
     * @param introspectedColumn the introspected column
     */
    @Override
    public void addSetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        super.addSetterComment(method, introspectedTable, introspectedColumn);
    }

    /**
     * Adds the general method comment.
     *
     * @param method            the method
     * @param introspectedTable the introspected table
     */
    @Override
    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
        super.addGeneralMethodComment(method, introspectedTable);
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
