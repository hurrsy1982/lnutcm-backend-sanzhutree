package com.sanzhu.mybatis.generator.plugins.parameter;


import com.alibaba.fastjson2.JSONObject;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.VisitableElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.internal.util.JavaBeansUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.sanzhu.mybatis.generator.plugins.utils.XmlElementTools;
import org.mybatis.generator.internal.util.StringUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;


/**
 * 项目名称：springboot 后端脚手架<br>
 * 程序名称：lnutcm-backend-sanzhu-mybatis-generator-plugins<br>
 * 日期：2024-04-18<br>
 * 作者：胡蓉蓉<br>
 * 模块：Mybatis Generator 逆向工程<br>
 * 描述：1.编写目的:在日期等列上使用默认值，避免程序手工赋值，减少工作量<br>
 * 2.功能分类:插入与更新时均使用默认值<br>
 * <p>
 * 备注：禁止商用<br>
 * ------------------------------------------------------------<br>
 * 修改历史
 * 序号               日期              修改人       修改原因
 * 修改备注：
 */

public class UpdateOperatorInfoUseDefaultValuePlugin extends PluginAdapter {
    /**
     * 列名称
     */
    private String columnName = "";
    /**
     * 默认值
     */
    private String defaultValue = "";

    /**
     * 修改模式
     */
    private String updateMode = "";//


    private boolean isSimple = true;

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

    private static final String MAT_UPDATE_CONDITION = "(^set\\s)?\\S+\\s?=.*";
    private static final String FMT_CONTENT_OLD = "(row\\.)?%s";

    /**
     * 更新时删除某列
     */
    private static final String CUSTOM_MODE_REMOVED = "REMOVE";
    /**
     * 更新时删除某列
     */
    private static final String CUSTOM_MODE_REPLACE = "REPLACE";

    private static final Logger logger = LoggerFactory.getLogger(UpdateOperatorInfoUseDefaultValuePlugin.class);

    /**
     * Function name:validate<br>
     * Inside the function:<br>
     * 方法在对内省表调用setXXX方法之后调用的。<br>
     * 判断该插件是否能够生效。<br>
     * return:boolean<br>
     * Params:<br>
     *
     * @param warnings List<String> (向列表中添加字符串以指定警告。例如，如果<br>
     *                 插件无效，您应该指定原因。警告为在运行完成后报告给用户)<br>
     */
    @Override
    public boolean validate(List<String> warnings) {
        this.columnName = properties.getProperty("columnName"); //需要引入的类全名
        this.defaultValue = properties.getProperty("defaultValue"); //替换为
        this.updateMode = properties.getProperty("updateMode");//更新模式   删除 或者 使用默认值
        isSimple = Boolean.parseBoolean(properties.getProperty("isSimple"));
        boolean valid = stringHasValue(columnName)
                && stringHasValue(updateMode)
                && (stringHasValue(defaultValue)
                && CUSTOM_MODE_REPLACE.equalsIgnoreCase(updateMode));

        //判断配置的字符串是否为空
        logger.info("更新与插入时使用默认值组件:validate-columnName--{}", this.columnName);
        logger.info("更新与插入时使用默认值组件:validate-valid--{}", this.defaultValue);
        logger.info("更新与插入时使用默认值组件:validate-isSimple--{}", isSimple);
        if (!valid) {
            if (!stringHasValue(columnName)) {
                warnings.add(getString("ValidationError.18", //$NON-NLS-1$
                        "UpdateOperateInfoPlugin", //$NON-NLS-1$
                        "columnName")); //$NON-NLS-1$
            }
            if (!stringHasValue(defaultValue)
                    && CUSTOM_MODE_REPLACE.equalsIgnoreCase(updateMode)) {
                warnings.add(getString("ValidationError.18", //$NON-NLS-1$
                        "UpdateOperateInfoPlugin", //$NON-NLS-1$
                        "defaultValue")); //$NON-NLS-1$
            }

            if (!stringHasValue(updateMode)) {
                warnings.add(getString("ValidationError.18", //$NON-NLS-1$
                        "UpdateOperateInfoPlugin", //$NON-NLS-1$
                        "updateMode")); //$NON-NLS-1$
            }
        }

        return valid;
    }

    /**
     * Function name:sqlMapUpdateByExampleSelectiveElementGenerated<br>
     * Inside the function:<br>
     * 查询出需要设置默认值的条件，查询后，该节点使用默认值来替换<br>
     * 当updateByExampleSelective节点产生时调用     <br>
     * Params:<br>
     *
     * @param introspectedTable the introspected table<br>
     * @param element           XmlElement     the generated &lt;update&gt; element<br>
     */
    @Override
    public boolean sqlMapUpdateByExampleSelectiveElementGenerated
    (XmlElement element, IntrospectedTable introspectedTable) {
        String node = defaultValue + ",";
        TextElement ele = new TextElement(node);
        logger.info("更新时插入默认值组件:updateByExampleSelective产生--原始节点--{}", JSONObject.toJSONString(element));
        replaceSelectiveMethodElement(element, ele, FMT_NOT_NULL_SELECTIVE_ROW_CONDITION, this.columnName.toLowerCase());
        logger.info("更新时插入默认值组件:updateByExampleSelective产生--替换后节点--{}", JSONObject.toJSONString(element));
        return super.sqlMapUpdateByExampleSelectiveElementGenerated(element, introspectedTable);
    }

    /**
     * Function name:sqlMapUpdateByExampleWithBLOBsElementGenerated<br>
     * Inside the function:<br>
     * 在生成UpdateByExampleWithBLOBs节点时调用，完成某列使用默认值或者去掉某个节点的功能 <br>
     * Params:<br>
     *
     * @param introspectedTable the introspected table<br>
     * @param element           XmlElement     the generated &lt;update&gt; element<br>
     * @Return boolean
     */
    @Override
    public boolean sqlMapUpdateByExampleWithBLOBsElementGenerated
    (XmlElement element, IntrospectedTable introspectedTable) {
        replaceNoSelectiveMethodTextElement(element);
        return super.sqlMapUpdateByExampleWithBLOBsElementGenerated(element, introspectedTable);
    }

    /**
     * Function name:sqlMapUpdateByExampleWithBLOBsElementGenerated<br>
     * Inside the function:<br>
     * 在生成 updateByExampleWithourBLOBs 节点时调用 ;完成某列使用默认值或者去掉某个节点的功能 <br>
     * Params:<br>
     *
     * @param introspectedTable the introspected table<br>
     * @param element           XmlElement     the generated &lt;update&gt; element<br>
     *                          Return boolean
     */
    @Override
    public boolean sqlMapUpdateByExampleWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        replaceNoSelectiveMethodTextElement(element);
        return super.sqlMapUpdateByExampleWithoutBLOBsElementGenerated(element, introspectedTable);
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
        //处理 节点中的sql
        replaceNoSelectiveMethodTextElement(element);

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
        replaceNoSelectiveMethodTextElement(element);
        return super.sqlMapUpdateByPrimaryKeyWithoutBLOBsElementGenerated(element, introspectedTable);
    }

    private void replaceNoSelectiveMethodTextElement(XmlElement element) {
        //思路:
        // 在节点中筛选出  WHERE  条件之前的 所有 TextElements
        // 在已经筛选的条件中找到要替换或者删除的TextElements
        // 查找到节点以后，进行节点替换或者删除操作
        //替换操作比较简单，使用默认值进行替换
        //删除操作需要判断该节点是否是最后一个节点，然后进行删除，如果被删除的是最后一个节点，需要去掉前一个节点最后的逗号
        //如果被删除的节点含有set,下一个节点需要补上set 关键字

        var textElements = new ArrayList<TextElement>();
        var results = XmlElementTools.getSetTextElements(element);//获取 update语句中 Set 中的内容
        AtomicInteger index = new AtomicInteger();
        results.forEach(e -> {
            //"(^set\\s)?\\S+\\s?=.*"
            if (((TextElement) e).getContent() != null &&//找到text节点且格式为 set xx = xx 或者 xx = xx
                    ((TextElement) e).getContent().trim().matches(MAT_UPDATE_CONDITION)
                    && ((TextElement) e).getContent().trim().toLowerCase().contains(columnName.toLowerCase())) {
                String value = ((TextElement) e).getContent().replace("set", "");
                //此处查询生成节点的源代码，找到生成规律，每一列设置内容都单独放到一个节点内部
                //在 x=x 中查找匹配的列
                ArrayList<String> strings = XmlElementTools.splitSetTextElementContents(e);
                String condition = strings.get(0);
                if (condition.equalsIgnoreCase(columnName)) {//该列是要匹配的
                    textElements.add((TextElement) e);
                }
                index.getAndIncrement();
            }

        });
        if (!textElements.isEmpty()) {
            TextElement textElement = textElements.get(0);
            var textElementIndex = element.getElements().indexOf(textElement);//在列表中的位置

            if (CUSTOM_MODE_REPLACE.equalsIgnoreCase(this.updateMode)) {//替换
                ArrayList<String> strings = XmlElementTools.
                        splitSetTextElementContents(textElement);//按等号分割节点内容
                if (textElement.getContent().endsWith(",")) {
                    element.getElements().set(textElementIndex,new TextElement(
                            strings.get(0)+" = "+defaultValue+","
                    ));
                }else{
                    element.getElements().set(textElementIndex,new TextElement(
                            strings.get(0)+" = "+defaultValue
                    ));
                }

            }
            if (CUSTOM_MODE_REMOVED.equalsIgnoreCase(this.updateMode)) {//更新时去掉某节点

                element.getElements().remove(textElementIndex);

            }

        }
    }

    private boolean needRemovePreTextElementComma(){
        return  true;
    }



    /**
     * Function name:sqlMapUpdateByPrimaryKeySelectiveElementGenerated<br>
     * Inside the function:<br>
     * 查询出需要设置默认值的条件，查询后，该节点使用默认值来替换<br>
     * 当updateByPrimaryKeySelective节点产生时调用     <br>
     * Params:<br>
     *
     * @param introspectedTable the introspected table<br>
     * @param element           XmlElement     the generated &lt;update&gt; element<br>
     */
    @Override
    public boolean sqlMapUpdateByPrimaryKeySelectiveElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        String node = defaultValue + ",";
        TextElement ele = new TextElement(node);
        replaceSelectiveMethodElement(element, ele, FMT_NOT_NULL_SELECTIVE_CONDITION, this.columnName.toLowerCase());
        return super.sqlMapUpdateByPrimaryKeySelectiveElementGenerated(element, introspectedTable);
    }

    /**
     * Function name:replaceElement<br>
     * Inside the function:<br>
     * 查询出set节点，在set节点中查询出需要替换的if节点，用默认值TextElement<br>
     * 当updateByExampleSelective节点产生时调用     <br>
     * Params:<br>
     *
     * @param element XmlElement     the generated &lt;update&gt; element<br>
     */
    private static void replaceSelectiveMethodElement(XmlElement element, TextElement ele, String fmt, String columnName) {
        if (element.getElements() != null && !element.getElements().isEmpty()) {
            List<VisitableElement> xmlElementSetList = (List<VisitableElement>)
                    element.getElements().stream().filter(e -> {
                        boolean fitResult = XmlElementTools.hasXmlElementName(e, "set");
                        if (fitResult) {
                            logger.info("更新时插入默认值组件:replaceElement--是否存在set节点--{}", true);
                        }
                        return fitResult;
                    }).collect(Collectors.toList());//获取set 节点
            logger.info("更新时插入默认值组件:replaceElement--筛选出的set节点结果--{}", JSONObject.toJSONString(xmlElementSetList));
            if (!xmlElementSetList.isEmpty()) {
                XmlElement set = (XmlElement) xmlElementSetList.get(0);
                logger.info("更新时插入默认值组件:replaceElement--set节点--{}", JSONObject.toJSONString(set));
                List<VisitableElement> srcElementList = XmlElementTools.filterElementByNameAndAttrInfo(set, "if"
                        , "test", String.format(
                                JavaBeansUtil.getCamelCaseString
                                        (columnName, false),
                                fmt)
                );//在set节点中查找要修改的特定的if节点,该节点value 中内容对应配置文件中columnName
                logger.info("更新时插入默认值组件:replaceElement调用--查询出的欲替换的节点--{}", JSONObject.toJSONString(srcElementList));
                if (srcElementList != null && !srcElementList.isEmpty()) {//该节点存在
                    XmlElement ifElement = (XmlElement) srcElementList.get(0);
                    logger.info("更新时插入默认值组件:replaceElement调用--节点内容--{}", JSONObject.toJSONString(ifElement));
                    int index = set.getElements().indexOf(ifElement);
                    if (index == set.getElements().size() - 1) {//最后一项，去掉逗号
                        ele = new TextElement(ele.getContent().replace(",", ""));
                    }
                    logger.info("更新时插入默认值组件:replaceElement调用--替换节点--{}", JSONObject.toJSONString(ele));
                    var result = Collections.replaceAll(set.getElements(), ifElement, ele);//使用默认值替换原有if条件标签
                    logger.info("更新时插入默认值组件:replaceElement调用--替换结果--{}", JSONObject.toJSONString(result));
                }
            }

        }
    }




}
