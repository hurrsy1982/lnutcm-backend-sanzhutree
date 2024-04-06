package com.sanzhu.mybatis.generator.plugins.rename;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;
/**
 * 项目名称：springboot 后端脚手架<br>
 * 程序名称：lnutcm-backend-sanzhu-mybatis-generator-plugins<br>
 * 日期：2024-04-01<br>
 * 作者：胡蓉蓉<br>
 * 模块：Mybatis Generator 逆向工程-RenameJavaMapperPlugin<br>
 * 描述：1.编写目的:自定义项目中逆向工程中Mapper的结尾<br>
 *      2.实现需求:修改JAVA Mapper的文件名结尾 例如:默认以Mapper结尾，修改成以Dao 结尾<br>
 * <p>
 * 备注：禁止商用<br>
 * ------------------------------------------------------------<br>
 * 修改历史
 * 序号               日期              修改人       修改原因
 * 修改备注：
 */
public class RenameJavaMapperPlugin extends PluginAdapter {
    private String replaceString;
    private Pattern pattern;

    @Override
    public boolean validate(List<String> warnings)  {

        String searchString = properties.getProperty("searchString"); //被替换的字符串
        this.replaceString = properties.getProperty("replaceString"); //替换为
        boolean valid = stringHasValue(searchString)
                && stringHasValue(replaceString);//判断配置的字符串是否为空
        if (valid) {
            pattern = Pattern.compile(searchString);
        } else {
            if (!stringHasValue(searchString)) {
                warnings.add(getString("ValidationError.18", //$NON-NLS-1$
                        "RenameJavaMapperPlugin", //$NON-NLS-1$
                        "searchString")); //$NON-NLS-1$
            }
            if (!stringHasValue(replaceString)) {
                warnings.add(getString("ValidationError.18", //$NON-NLS-1$
                        "RenameJavaMapperPlugin", //$NON-NLS-1$
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
    public void initialized(IntrospectedTable introspectedTable) {
        //首先获取Mapper文件全路径
        //进行正则匹配，然后替换Mapper名称
        //重新设置Mapper名称
        String oldType = introspectedTable.getMyBatis3JavaMapperType();//获得*Mapper文件全路径
        Matcher matcher = pattern.matcher(oldType);
        oldType = matcher.replaceAll(replaceString);
        introspectedTable.setMyBatis3JavaMapperType(oldType);

        //获取命名空间
        //进行正则匹配，然后替换Xml名称
        //重新设置Xml名称
        String oldSqlXmlType =  introspectedTable.getMyBatis3FallbackSqlMapNamespace();
        Matcher matcherXml = pattern.matcher(oldSqlXmlType);
        oldSqlXmlType = matcherXml.replaceAll(replaceString);
        introspectedTable.setMyBatis3FallbackSqlMapNamespace(oldSqlXmlType);

    }
}
