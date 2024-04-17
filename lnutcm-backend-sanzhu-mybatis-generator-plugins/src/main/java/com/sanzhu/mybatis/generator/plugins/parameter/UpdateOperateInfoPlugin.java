package com.sanzhu.mybatis.generator.plugins.parameter;


import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.VisitableElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.internal.util.JavaBeansUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.sanzhu.mybatis.generator.plugins.utils.XmlElementTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

public class UpdateOperateInfoPlugin extends PluginAdapter {

    private String columnName = "";

    private String defaultValue = "";

    private boolean isSimple=true;

    /**
     * if  条件为NULL
     */
    private static final String FMT_NULL_SELECTIVE_CONDITION = "%s == null";
    /**
     * if  条件不为NULL
     */
    private static final String FMT_NOT_NULL_SELECTIVE_CONDITION = "%s != null";
    /**
     * selective if  条件不为NULL
     */
    private static final String FMT_NOT_NULL_SELECTIVE_ROW_CONDITION = "row.%s != null";

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Function name:validate<br>
     * Inside the function:<br>
     *                 方法在对内省表调用setXXX方法之后调用的。<br>
     *                 判断该插件是否能够生效。<br>
     * return:boolean<br>
     * Params:<br>
     * @param warnings List<String> (向列表中添加字符串以指定警告。例如，如果<br>
     *                 插件无效，您应该指定原因。警告为在运行完成后报告给用户)<br>

     */
    @Override
    public boolean validate(List<String> warnings) {
        this.columnName = properties.getProperty("columnName"); //需要引入的类全名
        this.defaultValue = properties.getProperty("defaultValue"); //替换为
        boolean valid = stringHasValue(columnName)
                && stringHasValue(defaultValue);//判断配置的字符串是否为空
        if (!valid) {
            if (!stringHasValue(columnName)) {
                warnings.add(getString("ValidationError.18", //$NON-NLS-1$
                        "UpdateOperateInfoPlugin", //$NON-NLS-1$
                        "columnName")); //$NON-NLS-1$
            }
            if (!stringHasValue(defaultValue)) {
                warnings.add(getString("ValidationError.18", //$NON-NLS-1$
                        "UpdateOperateInfoPlugin", //$NON-NLS-1$
                        "defaultValue")); //$NON-NLS-1$
            }
        }

        return valid;
    }

    /**
     * Function name:sqlMapUpdateByExampleSelectiveElementGenerated<br>
     * Inside the function:<br>
     *                          查询出需要设置默认值的条件，查询后，该节点使用默认值来替换<br>
     *                          当updateByExampleSelective节点产生时调用     <br>
     * Params:<br>

     * @param introspectedTable the introspected table<br>
     * @param element           XmlElement     the generated &lt;update&gt; element<br>
     *

     */
    @Override
    public boolean sqlMapUpdateByExampleSelectiveElementGenerated
    (XmlElement element, IntrospectedTable introspectedTable) {
        String node = defaultValue + ",";
        TextElement ele = new TextElement(node);
        replaceElement(element, ele, FMT_NOT_NULL_SELECTIVE_ROW_CONDITION, this.columnName.toLowerCase());
        return super.sqlMapUpdateByExampleSelectiveElementGenerated(element, introspectedTable);
    }

    /**
     * Function name:sqlMapUpdateByExampleWithBLOBsElementGenerated<br>
     * Inside the function:<br>
     *        重新生成xml内容 <br>
     * Params:<br>
     *
     * @param introspectedTable the introspected table<br>
     * @param element           XmlElement     the generated &lt;update&gt; element<br>
     * @Return boolean
     */
    @Override
    public boolean sqlMapUpdateByExampleWithBLOBsElementGenerated
    (XmlElement element, IntrospectedTable introspectedTable) {
        element.getElements().clear();
        XmlElementTools.buildUpdateByExampleElement
                (introspectedTable.getUpdateByExampleWithBLOBsStatementId()
                        , introspectedTable, introspectedTable.getAllColumns(),
                        context, this.columnName, this.defaultValue, element);
        return super.sqlMapUpdateByExampleWithBLOBsElementGenerated(element, introspectedTable);
    }



    /**
     * Function name:sqlMapUpdateByExampleWithBLOBsElementGenerated<br>
     * Inside the function:<br>
     *        在生成 updateByExampleWithourBLOBs 节点时调用 重新生成xml内容 <br>
     * Params:<br>
     *
     * @param introspectedTable the introspected table<br>
     * @param element           XmlElement     the generated &lt;update&gt; element<br>
     * Return boolean
     */
    @Override
    public boolean sqlMapUpdateByExampleWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        element.getElements().clear();
        XmlElementTools.buildUpdateByExampleElement
                (introspectedTable.getUpdateByExampleStatementId()
                        , introspectedTable, introspectedTable.getNonBLOBColumns(),
                        context, this.columnName, this.defaultValue, element);
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
        String node = defaultValue + ",";
        TextElement ele = new TextElement(node);
        replaceElement(element, ele, FMT_NOT_NULL_SELECTIVE_CONDITION, this.columnName.toLowerCase());
        return super.sqlMapUpdateByPrimaryKeySelectiveElementGenerated(element, introspectedTable);
    }


    private static void replaceElement(XmlElement element, TextElement ele, String fmt, String columnName) {
        if (element.getElements() != null && !element.getElements().isEmpty()) {
            XmlElement set = (XmlElement) element.getElements().stream().filter(e -> {
                return XmlElementTools.hasXmlElementName(e, "set");
            });//获取set 节点
            List<VisitableElement> srcElementList = XmlElementTools.filterElementByNameAndAttrInfo(set, "if"
                    , "test", String.format(
                            JavaBeansUtil.getCamelCaseString
                                    (columnName, false),
                            fmt)
            );//在set节点中查找要修改的特定的if节点,该节点value 中内容对应配置文件中columnName
            if (srcElementList != null && !srcElementList.isEmpty()) {
                XmlElement ifElement = (XmlElement) srcElementList.get(0);
                var result = Collections.replaceAll(set.getElements(), ifElement, ele);//使用默认值替换原有if条件标签
            }
        }
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
        XmlElementTools.buildUpdateByPrimaryKeyElement
                ( introspectedTable.getUpdateByPrimaryKeyWithBLOBsStatementId(),
                        introspectedTable.getBaseRecordType(),  context,
                         introspectedTable,  introspectedTable.getNonPrimaryKeyColumns(),element);
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
        List<IntrospectedColumn> columns=new ArrayList<IntrospectedColumn>();
        element.getElements().clear();
        if (isSimple) {
            columns = introspectedTable.getNonPrimaryKeyColumns();
        } else {
            columns = introspectedTable.getBaseColumns();
        }
        return super.sqlMapUpdateByPrimaryKeyWithoutBLOBsElementGenerated(element, introspectedTable);
    }
}
