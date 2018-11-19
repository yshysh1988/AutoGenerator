package com.bjxg.tools.codegenerator.projectitem.tools.utils.nck;

import com.bjxg.tools.codegenerator.projectitem.Project;
import com.bjxg.tools.codegenerator.projectitem.tools.bean.ClellBean;
import com.bjxg.tools.codegenerator.projectitem.tools.constants.DateConstants;
import com.bjxg.tools.codegenerator.projectitem.tools.constants.RowConstants;
import com.bjxg.tools.codegenerator.projectitem.tools.utils.AutoUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

public class NckVoFileUtils {

    /**
     * 读取文件，并且写入新文件中
     *
     * @param fileName   读取文件
     * @param writeFile  写入文件
     * @param tableName  类生产的表明
     * @param classsName 生产的类名
     * @param proName    属性名
     * @param methodName get/set方法名
     * @param typeName   属性的类型
     * @param lengthName 字段的长度
     * @return
     */
    public static String readNckToFile(File fileName, File writeFile, String tableName, String classsName,
                                       String proName, String methodName, String typeName, String lengthName, boolean isEmpty, String path,
                                       String packagestr, List<ClellBean> clLs, Project project) throws Exception {
        String result = "";
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
            fileReader = new FileReader(fileName);
            bufferedReader = new BufferedReader(fileReader);
            String read = "";
            while ((read = bufferedReader.readLine()) != null) {
                if (read != null && read.trim().length() > 0) {
                    read = read.replace("@tabelName", tableName);
                    read = read.replace("@ClassName", classsName);
                    read = read.replace("@className", tableName);
                    read = read.replace("@package@", packagestr);
                    read = read.replace("@showName", proName.replace("表", ""));
                    read = read.replace("@showDate", AutoUtils.getNowDate(DateConstants.DATE_FORMAT1));
                    read = read.replace("@packageVo@", project.getVoPackage());
                    if (classsName != null && classsName.trim().length() > 0) {
                        read = read.replace("@class", AutoUtils.getLowerCase(classsName));
                    }
                    if ("@property".equals(read)) {
                        read = setNckClass(new File(path + "propertyFragment.txt"), writeFile, clLs, path,
                                "bean");
                    } else if (read.equals("@method")) {
                        read = setNckClass(new File(path + "methodFragment.txt"), writeFile, clLs, path,
                                "bean");
                    }
                }
                result = result + read + "\r\n";
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (fileReader != null) {
                fileReader.close();
            }
        }
        NckFileUtils.writeToFile(result, writeFile, isEmpty);
        return result;
    }

    /**
     * nck bean 类
     ***/
    public static String setNckClass(File fileName, File writeFile, List<ClellBean> clLs, String path, String type) throws Exception {
        if (clLs == null || clLs.size() == 0) {
            return null;
        }
        String result = "";
        for (ClellBean clell : clLs) {
            if (!RowConstants.ROW_ZERO.equals(clell.row) && clell.type != null && clell.type.trim().length() > 0) {
                result = result + readToFileNck(fileName, clell, path);
            }
        }
        return result;
    }

    /**
     * 读取文件，并且写入新文件中
     *
     * @param fileName 读取文件
     * @param clell    属性名
     * @return
     */
    public static String readToFileNck(File fileName, ClellBean clell, String path) throws Exception {
        String result = "";
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
            fileReader = new FileReader(fileName);
            bufferedReader = new BufferedReader(fileReader);
            try {
                String read = "";
                while ((read = bufferedReader.readLine()) != null) {
                    if (read != null && read.trim().length() > 0) {
                        read = read.replace("@propertyName", AutoUtils.getLowerCase(clell.name));
                        read = read.replace("@type", clell.type);
                        read = read.replace("@methodName", AutoUtils.getUpperCase(clell.name));
                        read = read.replace("@describe@", clell.describe);
                    }
                    result = result + read + "\r\n";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (fileReader != null) {
                fileReader.close();
            }
        }
        return result;
    }


}
