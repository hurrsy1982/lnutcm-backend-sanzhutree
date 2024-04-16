package com.sanzhu.mybatis.generator.plugins.parameter;


import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.VisitableElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.internal.util.JavaBeansUtil;

import java.util.Collections;
import java.util.List;

import com.sanzhu.mybatis.generator.plugins.utils.XmlElementTools;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

public class UpdateOperateInfoPlugin extends PluginAdapter {

    private String columnName = "";

    private String defaultValue = "";

    /**
     * if  条件为NULL
     */
    private static final String FMT_NULL_SELECTIVE_CONDITION = "%s == null";
    private static final String FMT_NOT_NULL_SELECTIVE_CONDITION = "%s != null";

    private static final String FMT_NOT_NULL_SELECTIVE_ROW_CONDITION = "row.%s != null";

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
        this.columnName = properties.getProperty("columnName"); //需要引入的类全名
        this.defaultValue = properties.getProperty("defaultValue"); //替换为
        boolean valid = stringHasValue(columnName)
                && stringHasValue(defaultValue);//判断配置的字符串是否为空
        if (!valid)  {
            if (!stringHasValue(columnName)) {
                warnings.add(getString("ValidationError.18", //$NON-NLS-1$
                        "UpdateOperateInfoPlugin", //$NON-NLS-1$
                        "columnName")); //$NON-NLS-1$
            }
            if (!stringHasValue(defaultValue)) {
                warnings.add(getString("ValidationError.18", //$NON-NLS-1$
                        "UpdateOperateInfoPlugin", //$NON-NLS-1$
                        "replaceString")); //$NON-NLS-1$
            }
        }

        return valid;
    }

    /**
     * Function name:sqlMapUpdateByExampleSelectiveElementGenerated<br>
     * Params:<br>
     * param  introspectedTable the introspected table<br>
     * param  element      XmlElement     the generated &lt;update&gt; element<br>
     * Inside the function:<br>
     * 查询出需要设置默认值的条件，查询后，该节点使用默认值来替换
     * <br>
     */
    @Override
    public boolean sqlMapUpdateByExampleSelectiveElementGenerated
    (XmlElement element, IntrospectedTable introspectedTable) {
        String node = defaultValue + ",";
        TextElement ele = new TextElement(node);
        if (element.getElements() != null && element.getElements().size() > 0) {
            XmlElement set = (XmlElement) element.getElements().get(0);//获取set 节点
            List<VisitableElement> srcElementList = XmlElementTools.getElementByNameAndAttrInfo(set,"if"
            ,"test",String.format(
                            JavaBeansUtil.getCamelCaseString
                                    (this.columnName.toLowerCase(), false),
                            FMT_NOT_NULL_SELECTIVE_ROW_CONDITION)
            )
                    ;//在set节点中查找要修改的特定的if节点,该节点对应配置文件中columnName
            if (srcElementList != null && srcElementList.size() > 0) {
                XmlElement ifElement=(XmlElement)srcElementList.get(0);
                Collections.replaceAll(set.getElements(),ifElement,ele);//使用默认值替换原有if条件标签
            }

        }


        //

        return super.sqlMapUpdateByExampleSelectiveElementGenerated(element, introspectedTable);
    }




    /**
     * This method is called when the updateByExampleWithBLOBs element is
     * generated.
     *
     * @param element           the generated &lt;update&gt; element
     * @param introspectedTable The class containing information about the table as
     *                          introspected from the database
     * @return true if the element should be generated, false if the generated
     * element should be ignored. In the case of multiple plugins, the
     * first plugin returning false will disable the calling of further
     * plugins.
     */
    @Override
    public boolean sqlMapUpdateByExampleWithBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        element.getElements().clear();
        XmlElementTools.buildUpdateByExampleElement
                (introspectedTable.getUpdateByExampleWithBLOBsStatementId()
        ,introspectedTable,context,this.columnName,this.defaultValue);
        return super.sqlMapUpdateByExampleWithBLOBsElementGenerated(element, introspectedTable);
    }



    /**
     * This method is called when the updateByExampleWithourBLOBs element is
     * generated.
     *
     * @param element           the generated &lt;update&gt; element
     * @param introspectedTable The class containing information about the table as
     *                          introspected from the database
     * @return true if the element should be generated, false if the generated
     * element should be ignored. In the case of multiple plugins, the
     * first plugin returning false will disable the calling of further
     * plugins.
     */
    @Override
    public boolean sqlMapUpdateByExampleWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        element.getElements().clear();
        return super.sqlMapUpdateByExampleWithoutBLOBsElementGenerated(element, introspectedTable);
    }

    /**
     * This method is called when the updateByPrimaryKeySelective element is
     * generated.
     *
     * @param element           the generated &lt;update&gt; element
     * @param introspectedTable The class containing information about the table as
     *                          introspected from the database
     * @return true if the element should be generated, false if the generated
     * element should be ignored. In the case of multiple plugins, the
     * first plugin returning false will disable the calling of further
     * plugins.
     */
    @Override
    public boolean sqlMapUpdateByPrimaryKeySelectiveElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {

        return super.sqlMapUpdateByPrimaryKeySelectiveElementGenerated(element, introspectedTable);
    }

    /**
     * This method is called when the updateByPrimaryKeyWithBLOBs element is
     * generated.
     *
     * @param element           the generated &lt;update&gt; element
     * @param introspectedTable The class containing information about the table as
     *                          introspected from the database
     * @return true if the element should be generated, false if the generated
     * element should be ignored. In the case of multiple plugins, the
     * first plugin returning false will disable the calling of further
     * plugins.
     */
    @Override
    public boolean sqlMapUpdateByPrimaryKeyWithBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        element.getElements().clear();
        return super.sqlMapUpdateByPrimaryKeyWithBLOBsElementGenerated(element, introspectedTable);
    }

    /**
     * This method is called when the updateByPrimaryKeyWithoutBLOBs element is
     * generated.
     *
     * @param element           the generated &lt;update&gt; element
     * @param introspectedTable The class containing information about the table as
     *                          introspected from the database
     * @return true if the element should be generated, false if the generated
     * element should be ignored. In the case of multiple plugins, the
     * first plugin returning false will disable the calling of further
     * plugins.
     */
    @Override
    public boolean sqlMapUpdateByPrimaryKeyWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        element.getElements().clear();

        return super.sqlMapUpdateByPrimaryKeyWithoutBLOBsElementGenerated(element, introspectedTable);
    }
}
