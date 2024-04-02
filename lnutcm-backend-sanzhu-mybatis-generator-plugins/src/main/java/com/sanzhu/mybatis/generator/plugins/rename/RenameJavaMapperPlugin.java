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
 * 描述：1.编写目的:自定义项目中逆向工程中Mapper的生成位置<br>
 *      2.实现需求:修改Mapper的生成位置<br>
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
    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        //首先获取Mapper文件全路径

        String oldType = introspectedTable.getMyBatis3JavaMapperType();//获得*Mapper文件全路径
        Matcher matcher = pattern.matcher(oldType);
        oldType = matcher.replaceAll(replaceString);
        introspectedTable.setMyBatis3JavaMapperType(oldType);

        //获取xml 中
        introspectedTable.getMyBatis3FallbackSqlMapNamespace();



    }
}
