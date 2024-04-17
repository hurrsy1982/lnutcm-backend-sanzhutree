package com.sanzhu.mybatis.generator.plugins.utils;

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

    public static void buildUpdateByExampleElement
            (String statementId, IntrospectedTable introspectedTable, List<IntrospectedColumn> columns,
             Context context, String columnName
                    , String defaultValue, XmlElement element) {
        // XmlElement answer = new XmlElement("update"); //$NON-NLS-1$
        //  answer.addAttribute(new Attribute("id", statementId)); //$NON-NLS-1$
        // answer.addAttribute(new Attribute("parameterType", "map")); //$NON-NLS-1$ //$NON-NLS-2$
        // context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();
        sb.append("update "); //$NON-NLS-1$
        sb.append(introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime());
        element.addElement(new TextElement(sb.toString()));

        // set up for first column
        sb.setLength(0);
        sb.append("set "); //$NON-NLS-1$

        Iterator<IntrospectedColumn> iter = ListUtilities
                .removeGeneratedAlwaysColumns(columns).iterator();
        while (iter.hasNext()) {
            IntrospectedColumn introspectedColumn = iter.next();

            sb.append(MyBatis3FormattingUtilities.getAliasedEscapedColumnName(introspectedColumn));
            sb.append(" = "); //$NON-NLS-1$
            if (introspectedColumn.getActualColumnName().equalsIgnoreCase(columnName)) {
                sb.append(defaultValue);
            } else {
                sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn, "row.")); //$NON-NLS-1$
            }
            if (iter.hasNext()) {
                sb.append(',');
            }

            element.addElement(new TextElement(sb.toString()));

            // set up for the next column
            if (iter.hasNext()) {
                sb.setLength(0);
                OutputUtilities.xmlIndent(sb, 1);
            }
        }

        element.addElement(getUpdateByExampleIncludeElement(introspectedTable));
        //return element;
    }

    protected static XmlElement getUpdateByExampleIncludeElement(IntrospectedTable introspectedTable) {
        XmlElement ifElement = new XmlElement("if"); //$NON-NLS-1$
        ifElement.addAttribute(new Attribute("test", "example != null")); //$NON-NLS-1$ //$NON-NLS-2$

        XmlElement includeElement = new XmlElement("include"); //$NON-NLS-1$
        includeElement.addAttribute(new Attribute("refid", //$NON-NLS-1$
                introspectedTable.getMyBatis3UpdateByExampleWhereClauseId()));
        ifElement.addElement(includeElement);

        return ifElement;
    }

    public static void buildUpdateByPrimaryKeyElement
            (String statementId, String parameterType,Context context,
             IntrospectedTable introspectedTable, List<IntrospectedColumn> columns,XmlElement element) {
        //XmlElement answer = new XmlElement("update"); //$NON-NLS-1$

        //answer.addAttribute(new Attribute("id", statementId)); //$NON-NLS-1$
       // answer.addAttribute(new Attribute("parameterType", parameterType)); //$NON-NLS-1$

       // context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();
        sb.append("update "); //$NON-NLS-1$
        sb.append(introspectedTable.getFullyQualifiedTableNameAtRuntime());
        element.addElement(new TextElement(sb.toString()));

        // set up for first column
        sb.setLength(0);
        sb.append("set "); //$NON-NLS-1$

        Iterator<IntrospectedColumn> iter = ListUtilities.removeGeneratedAlwaysColumns(columns).iterator();
        while (iter.hasNext()) {
            IntrospectedColumn introspectedColumn = iter.next();

            sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
            sb.append(" = "); //$NON-NLS-1$
            sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));

            if (iter.hasNext()) {
                sb.append(',');
            }

            element.addElement(new TextElement(sb.toString()));

            // set up for the next column
            if (iter.hasNext()) {
                sb.setLength(0);
                OutputUtilities.xmlIndent(sb, 1);
            }
        }
        buildPrimaryKeyWhereClause(introspectedTable).forEach(element::addElement);

        //return answer;
    }

    public static List<TextElement> buildPrimaryKeyWhereClause(IntrospectedTable introspectedTable) {
        List<TextElement> answer = new ArrayList<>();
        boolean first = true;
        for (IntrospectedColumn introspectedColumn : introspectedTable.getPrimaryKeyColumns()) {
            String line = "";
            if (first) {
                line = "where "; //$NON-NLS-1$
                first = false;
            } else {
                line = "  and "; //$NON-NLS-1$
            }

            line += MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn);
            line += " = "; //$NON-NLS-1$
            line += MyBatis3FormattingUtilities.getParameterClause(introspectedColumn);
            answer.add(new TextElement(line));
        }

        return answer;
    }

}
