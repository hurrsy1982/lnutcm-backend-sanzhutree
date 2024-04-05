package com.sanzhu.mybatis.generator.plugins.utils;


import org.mybatis.generator.internal.util.StringUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * 项目名称：springboot 后端脚手架<br>
 * 程序名称：lnutcm-backend-sanzhu-mybatis-generator-plugins<br>
 * 日期：2024-03-23<br>
 * 作者：胡蓉蓉<br>
 * 模块：逆向工程 Mybatis Generator 功能增强<br>
 * 描述：1.编写目的:抽离业务逻辑中关于时间处理的部分，避免业务逻辑代码被污染<br>
 *      2.函数功能:格式化指定日期为特定格式的函数<br>
 *
 * 备注：禁止商用<br>
 * ------------------------------------------------------------<br>
 * 修改历史
 * 序号               日期              修改人       修改原因
 * 修改备注：
 * version
 */
public class CommentUtils {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public static String  formatRemarks(boolean  addRemarkComments,String remarks) {
        var result ="";
        if (addRemarkComments && StringUtility.stringHasValue(remarks)) {
            String[] remarkLines = remarks.split(System.getProperty("line.separator")); //$NON-NLS-1$
            result = Arrays.stream(remarkLines).
                    reduce("", (partialString, element) -> partialString + "," + element);
            result.replace("\\"," ");

        }
        return result;
    }

}
