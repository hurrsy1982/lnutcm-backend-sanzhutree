package com.sanzhu.mybatis.generator.plugins.target;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;

import java.util.List;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

/**
 * 项目名称：springboot 后端脚手架<br>
 * 程序名称：lnutcm-backend-sanzhu-mybatis-generator-plugins<br>
 * 日期：2024-03-31<br>
 * 作者：胡蓉蓉<br>
 * 模块：Mybatis Generator 逆向工程<br>
 * 描述：1.编写目的:自定义项目中逆向工程的Example类的生成位置<br>
 * 2.实现需求:生成的Example类不和model类放置在一个文件夹<br>
 * <p>
 * 备注：禁止商用<br>
 * ------------------------------------------------------------<br>
 * 修改历史
 * 序号               日期              修改人       修改原因
 * 修改备注：
 */

public class ExampleTargetPlugin extends PluginAdapter {

    private String targetPackage;

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
        this.targetPackage = properties.getProperty("targetPackage"); //Example生成的目标文件夹
        boolean valid = stringHasValue(targetPackage);//判断配置的字符串是否为空
        if (valid) {
            warnings.add(getString("ValidationError.18", //$NON-NLS-1$
                    "ExampleTargetPlugin", //$NON-NLS-1$
                    "searchString")); //$NON-NLS-1$
        }
        return valid;
    }

    /**
     * Function name:initialized<br>

     * Inside the function:<br>
     *                          方法在对内省表调用getGeneratedXXXFiles方法之前调用的。<br>
     *                          插件可以实现此方法来覆盖任何默认属性，或更改数据库的结果<br>
     *                          内省，在任何代码生成活动发生之前。<br>
     *Params:<br>
     * @param introspectedTable the introspected table<br>

     */
    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        //首先获取Example文件全路径
        //用参数targetPackage替换包名
        //设置新的Example 文件路径
        String oldExampleType = introspectedTable.getExampleType();//获得*Example文件全路径
        String oldTargetPackage = context.getJavaModelGeneratorConfiguration().getTargetPackage();//获取pojo路径
        String exampleType = oldExampleType.replace(oldTargetPackage, targetPackage);
        introspectedTable.setExampleType(exampleType);

    }
}
