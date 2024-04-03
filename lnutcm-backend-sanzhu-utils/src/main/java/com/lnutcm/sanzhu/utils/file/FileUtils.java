package com.lnutcm.sanzhu.utils.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 项目名称：springboot 后端脚手架<br>
 * 程序名称：lutcm-backend-sanzhu-utils<br>
 * 日期：2024-03-23<br>
 * 作者：胡蓉蓉<br>
 * 模块：文件处理模块<br>
 * 描述：1.编写目的:抽离业务逻辑中关于文件处理的部分，避免业务逻辑代码被污染<br>
 *      2.函数分类:文件处理函数<br>
 *
 * 备注：禁止商用<br>
 * ------------------------------------------------------------<br>
 * 修改历史
 * 序号               日期              修改人       修改原因
 * 修改备注：
 * version
 */

public class FileUtils {
    private final static Logger logger = LoggerFactory.getLogger(FileUtils.class);
    /**
     * 在指定目录中创建一个新的空文件，使用给定的前缀和后缀字符串生成其名称。 参数:fileTempPath 图片完整路径,prex 前缀 suff 后缀
     */
    public static File getEmptyFile(String fileTempPath, String prex, String suff) throws IOException {
        File dir = new File(fileTempPath);//
        if (!dir.exists())
        {  boolean result=   dir.mkdir();}
        File downloadFile = null;
        downloadFile = File.createTempFile(prex, suff, dir);// 按照给定目录,创建了一个新文件
        // download 前缀 .zip 后缀 dir path
        return downloadFile;
    }

    public static File getZeroLengthFile(String fileDirPath, String fileName) {
        File dir = new File(fileDirPath);//
        if (!dir.exists()) {
            dir.mkdir();
        }
        File zeroLengthFile = new File(fileDirPath+File.separatorChar+fileName);
        logger.info(zeroLengthFile.getPath() + "文件路径");
        try {
            if (!zeroLengthFile.exists()) {
                logger.info(zeroLengthFile.getPath() + "开始建立文件----");
                boolean crsuc;
                crsuc = zeroLengthFile.createNewFile();
                logger.info(zeroLengthFile.getPath() + "建立文件----" + crsuc);
            }else {

            }
        } catch (IOException e) {
            try {
                FileOutputStream out=	new java.io.FileOutputStream(fileDirPath+File.separatorChar+fileName);
                out.close();
            } catch (FileNotFoundException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            e.printStackTrace();
        }

        return zeroLengthFile;

    }

    /** 截取文件后缀 */
    public static String getFileExt(String filePath) {

        int index = filePath.lastIndexOf(".");
        if (index < 0 || index + 1 == filePath.length()) {
            return "";
        } else {
            return filePath.substring(index + 1);
        }

    }

    /** 截取文件的保存路径 ,参数:路径+文件名(文件的完整路径) */

    public static String getFilePath(String fileFullPath) {
        int index = fileFullPath.lastIndexOf("/");
        if (index < 0) {
            return "";
        } else {
            String fileDir = fileFullPath.substring(0, index);
            return fileDir;
        }

    }


}
