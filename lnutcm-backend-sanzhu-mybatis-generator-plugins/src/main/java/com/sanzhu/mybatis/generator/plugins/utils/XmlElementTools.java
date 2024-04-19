package com.sanzhu.mybatis.generator.plugins.utils;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.OutputUtilities;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.VisitableElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.ListUtilities;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.config.Context;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class XmlElementTools {

    public static List<VisitableElement> filterElementByNameAndAttrInfo
            (XmlElement element, String name, String attrName, String attrValue) {
        return element.getElements()
                .stream().filter(
                        childElement ->
                                XmlElementTools.hasXmlElement(childElement,
                                        name, attrName, attrValue
                                ))
                .collect(Collectors.toList());
    }

    public static boolean hasXmlElement(VisitableElement childElement, String name,

                                        String attrName, String attrValue) {
        if (!(childElement instanceof XmlElement)) {
            return false;
        }
        return hasXmlElementName(childElement, name)
                && hasXmlElementAttr(childElement, attrName, attrValue)
                ;
    }

    /**
     *
     */
    public static boolean hasXmlElementName(VisitableElement childElement, String name) {
        if (!(childElement instanceof XmlElement)) {
            return false;
        }
        boolean result = ((XmlElement) childElement).getName().equalsIgnoreCase(name);
        return result;
    }

    public static boolean hasXmlElementAttr(
            VisitableElement childElement, String
            attrName, String attrValue) {

        boolean result = ((XmlElement) childElement).getAttributes().stream()
                .anyMatch(attribute -> {
                    //判断 条件是否是test &&  test里的值 ( e.g  row.bigscreenInfoId != null）
                    return attribute.getName().equalsIgnoreCase(attrName)
                            && attribute.getValue().equalsIgnoreCase(attrValue);
                });
        return result;
    }


    /**
     * Function name:getSetTextElements<br>
     * Inside the function:<br>
     *                    方法获取 update 语句中 所有 set 节点之后 where 节点之前的 TextElement <br>
     *                     遍历所有TextElement 对象, 把所有符合条件的TextElement对象放入列表中，返回列表。<br>
     * return:ArrayList<TextElement><br>
     * Params:<br>
     *
     * @param   element  XmlElement
     */

    public static ArrayList<TextElement> getSetTextElements(XmlElement element) {
        var results=new ArrayList<TextElement>();
        for(VisitableElement visitableElement: element.getElements()){//寻找where 条件的位置

            if(visitableElement instanceof TextElement textElement){
                if(textElement.getContent().contains("update")
                        || textElement.getContent().contains("UPDATE")){continue;}
                if(textElement.getContent().contains("where")
                        || textElement.getContent().contains("WHERE")  ){
                    break;//where 以后的节点不在匹配范围内
                }
                results.add(textElement) ;
            }
        }
        return results;
    }

    /**
     * Function name:splitTextElementContents<br>
     * Inside the function:<br>
     *                    方法分割Set 条件部分的textElement 中内容，按=分割  <br>
     *                     (sql 语句  update  aa <br>
     *                                       set xx=yy,
     *                                       zz=dd)<br>
     * return:ArrayList<String><br>
     * Params:<br>
     *
     * @param   textElement  TextElement
     */

    public static ArrayList<String> splitSetTextElementContents(TextElement textElement) {
        Iterable<String> split = Splitter.on('=')
                .trimResults()//移除结果字符串的前导空白和尾部空白
                .omitEmptyStrings()//从结果中自动忽略空字符串
                .split(textElement.getContent());//分割条件字符串
        return Lists.newArrayList(split);
    }


}
