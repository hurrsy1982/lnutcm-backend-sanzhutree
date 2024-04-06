package com.sanzhu.mybatis.generator.plugins.extend;


import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.XmlConstants;
import org.mybatis.generator.config.PropertyRegistry;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：springboot 后端脚手架<br>
 * 程序名称：lnutcm-backend-sanzhu-mybatis-generator-plugins<br>
 * 日期：2024-04-01<br>
 * 作者：willa<br>
 * 模块：Mybatis Generator 逆向工程-RenameJavaMapperPlugin<br>
 * 描述：1.编写目的:自定义项目中逆向工程中生成Mapper SqlMapper<br>
 * 2.实现需求:生成Mapper SqlMapper<br>
 * <p>
 * 备注：禁止商用<br>
 * ------------------------------------------------------------<br>
 * 修改历史
 * 序号               日期              修改人       修改原因
 * 修改备注：
 */
public class MapperExtendPlugin extends PluginAdapter {


    /***/
    private String endString;

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
        return false;
    }

    /**
     * This method can be used to generate additional Java files needed by your
     * implementation that might be related to a specific table. This method is
     * called once for every table in the configuration.
     *
     * @param introspectedTable The class containing information about the table as
     *                          introspected from the database
     * @return a List of GeneratedJavaFiles - these files will be saved
     * with the other files from this run.
     */
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        var generatedJavaFileList = new ArrayList<GeneratedJavaFile>();
        String projectType = System.getProperty("user.dir");//获取项目根目录
        String targetProjectType =
                context.getJavaClientGeneratorConfiguration().getTargetProject();
        String targetPackage = context.getJavaClientGeneratorConfiguration().getTargetPackage();//获取package文件位置
        String domainObjectName = introspectedTable.getTableConfiguration().getDomainObjectName();
        String addtionalType = targetPackage + "." + domainObjectName + endString;
        String fullJavaMapperType =
                projectType.replaceAll("\\\\", "/") + File.separatorChar
                +targetProjectType.replaceAll("\\.", "/") + File.separatorChar
                +targetPackage.replaceAll("\\.", "/")
                        + domainObjectName + endString
                +".java";
        File fullJavaMapperFile=new File(fullJavaMapperType);
        //设置Mapper扩展类
        if(!fullJavaMapperFile.exists()){
            String superType = introspectedTable.getMyBatis3JavaMapperType();
            Interface interfaze = new Interface(addtionalType);
            interfaze.setVisibility(JavaVisibility.PUBLIC);//设置类访问修饰符
            interfaze.addImportedType(new FullyQualifiedJavaType(superType));//设置需要引入的类
            interfaze.addSuperInterface(new FullyQualifiedJavaType(superType));//设置超类

            interfaze.addImportedType(
                    new FullyQualifiedJavaType("org.apache.ibatis.annotations.Mapper")); //$NON-NLS-1$
            interfaze.addAnnotation("@Mapper"); //$NON-NLS-1$
            GeneratedJavaFile generatedJavaFile = new GeneratedJavaFile(interfaze, targetPackage
                    ,  context.getProperty
                    (PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),context.getJavaFormatter());
            generatedJavaFileList.add(generatedJavaFile);
        }


        return generatedJavaFileList;
    }

    /**
     * This method can be used to generate additional XML files needed by your
     * implementation that might be related to a specific table. This method is
     * called once for every table in the configuration.
     *
     * @param introspectedTable The class containing information about the table as
     *                          introspected from the database
     * @return a List of GeneratedXmlFiles - these files will be saved
     * with the other files from this run.
     */
    @Override
    public List<GeneratedXmlFile> contextGenerateAdditionalXmlFiles(IntrospectedTable introspectedTable) {
        List<GeneratedXmlFile> answer = new ArrayList<>(1);

        String projectType = System.getProperty("user.dir");//获取项目根目录
        String targetProjectType =
                context.getJavaClientGeneratorConfiguration().getTargetProject();
        String targetPackage = context.getJavaClientGeneratorConfiguration().getTargetPackage();//获取package文件位置
        String domainObjectName = introspectedTable.getTableConfiguration().getDomainObjectName();
        String addtionalType = targetPackage + "." + domainObjectName + endString;

        String fullXMLMapperType =
                projectType.replaceAll("\\\\", "/") + File.separatorChar
                        +targetProjectType.replaceAll("\\.", "/") + File.separatorChar
                        +targetPackage.replaceAll("\\.", "/")
                        + domainObjectName + endString
                        +".xml";
        File fullJavaMapperFile=new File(fullXMLMapperType);//得到欲生成的xml
        if(!fullJavaMapperFile.exists()){
            Document document = new Document(
                    XmlConstants.MYBATIS3_MAPPER_CONFIG_PUBLIC_ID,
                    XmlConstants.MYBATIS3_MAPPER_CONFIG_SYSTEM_ID);
            XmlElement root = new XmlElement("mapper"); //$NON-NLS-1$
            document.setRootElement(root);
            root.addAttribute(new Attribute("namespace",
                    addtionalType)); //$NON-NLS-1$
            String fileNameExt =domainObjectName + endString
                    +".xml";
            GeneratedXmlFile gxf = new GeneratedXmlFile(document, fileNameExt,
                    targetPackage, targetProjectType,
                    false, context.getXmlFormatter());


            answer.add(gxf);

        }

        return answer;
    }



}
