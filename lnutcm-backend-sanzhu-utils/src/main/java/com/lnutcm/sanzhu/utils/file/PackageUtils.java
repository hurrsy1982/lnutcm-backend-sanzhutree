package com.lnutcm.sanzhu.utils.file;

import com.google.common.base.Strings;

/**
 * 项目名称：springboot 后端脚手架<br>
 * 程序名称：lnutcm-backend-sanzhu-utils<br>
 * 日期：2024-03-23<br>
 * 作者：胡蓉蓉<br>
 * 模块：工具类<br>
 * 描述：1.编写目的:java包相关功能<br>
 *      2.功能分类:处理java包相关 如获取名称等 <br>
 * <p>
 * 备注：禁止商用<br>
 * ------------------------------------------------------------<br>
 * 修改历史
 * 序号               日期              修改人       修改原因
 * 修改备注：
 */
public class PackageUtils {

    /**
     * Function name:getClassName<br>
     * Params:<br>
     * param  fullName String<br>
     * Inside the function:<br>
     * 方法从包名全路径中截取ClassName
     */
    public static String getClassName(String fullName) {
        fullName= Strings.nullToEmpty(fullName);
        int index = fullName.lastIndexOf(".");
        if (index < 0 || index + 1 == fullName.length() ) {
            return "";
        } else {
            return fullName.substring(index + 1);
        }
    }

    /**
     * Function name:getClassName<br>
     * Params:<br>
     * param  fullName String<br>
     * Inside the function:<br>
     * 方法从包名全路径中截取PackageName
     */
    public String getPackageName(String fullName) {
        fullName= Strings.nullToEmpty(fullName);
        int dotIndex = fullName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fullName.substring(0, dotIndex);
    }
}
