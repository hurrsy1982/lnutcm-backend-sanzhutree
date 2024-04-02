package com.sanzhu.mybatis.generator.plugins.rename;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

public class RenameMapperXmlPlugin extends PluginAdapter {
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
                        "RenameMapperXmlPlugin", //$NON-NLS-1$
                        "searchString")); //$NON-NLS-1$
            }
            if (!stringHasValue(replaceString)) {
                warnings.add(getString("ValidationError.18", //$NON-NLS-1$
                        "RenameMapperXmlPlugin", //$NON-NLS-1$
                        "replaceString")); //$NON-NLS-1$
            }
        }

        return valid;
    }


    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        //首先获取Mapper文件全路径

        String oldType = introspectedTable.getMyBatis3FallbackSqlMapNamespace();//获得*Mapper文件全路径
        Matcher matcher = pattern.matcher(oldType);
        oldType = matcher.replaceAll(replaceString);
        introspectedTable.setMyBatis3JavaMapperType(oldType);

    }
}
