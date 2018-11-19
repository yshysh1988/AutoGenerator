package com.bjxg.tools.codegenerator.projectitem.tools.utils.nck;

import com.bjxg.tools.codegenerator.projectitem.tools.bean.ClellBean;
import com.bjxg.tools.codegenerator.projectitem.tools.constants.RowConstants;
import com.bjxg.tools.codegenerator.projectitem.tools.utils.AutoUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;

public class NckListFileUtils {

    /**
     * 读取文件，并且写入新文件中
     *
     * @param fileName   读取文件
     * @param writeFile  写入文件
     * @param tableName  类生产的表明
     * @param classsName 生产的类名
     * @param proName    属性名
     * @return
     */
    public static String readToFileList(File fileName, File writeFile, String tableName, String classsName,
                                        String proName, boolean isEmpty, String path, List<ClellBean> clLs, String basePackage) throws Exception {
        ClellBean primarykey = AutoUtils.getPrimarykey(clLs, "主键");
        String result = "";
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
            fileReader = new FileReader(fileName);
            bufferedReader = new BufferedReader(fileReader);
            String read = "";
            HashMap<String, String> chenkMap = new HashMap<String, String>();
            chenkMap.put("formParas", "");
            chenkMap.put("regArray", "");
            chenkMap.put("errorMsgArray", "");
            String clzss = AutoUtils.getLowerCase(classsName);
            while ((read = bufferedReader.readLine()) != null) {
                if (read != null && read.trim().length() > 0) {
                    read = read.replace("@tabelName", tableName);
                    read = read.replace("@ClassName", classsName);
                    read = read.replace("@propertyName", proName);
                    read = read.replace("@idName", tableName + "Id");
                    read = read.replace("@IdName", classsName + "Id");
                    read = read.replace("@primarykey", AutoUtils.getLowerCase(primarykey.name));
                    read = read.replace("@showName", proName.replace("表", ""));
                    read = read.replace("@package@", basePackage);
                    if (classsName != null && classsName.trim().length() > 0) {
                        read = read.replace("@class", clzss);
                    }
                    if ("@listQuery@".equals(read)) {
                        read = setListTableTrtd(new File(path + "listShowThFragment.txt"), writeFile, clLs, path,
                                chenkMap, "vague", clzss);
                    } else if ("@listShowTh@".equals(read)) {
                        read = setListTableTrtd(new File(path + "listShowTableThFragment.txt"), writeFile, clLs,
                                path, null, "listShowTh", clzss);
                    } else if ("@listShowVal@".equals(read)) {
                        read = setListTableTrtd(new File(path + "listShowTableTdFragment.txt"), writeFile, clLs,
                                path, null, "listShowVal", clzss);
                    }
                    read = read.replace("@formParas", chenkMap.get("formParas"));
                    read = read.replace("@regArray", chenkMap.get("regArray"));
                    read = read.replace("@errorMsgArray", chenkMap.get("errorMsgArray"));
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
     * list.jsp 页面
     ***/
    private static String setListTableTrtd(File fileName, File writeFile, List<ClellBean> clLs, String path,
                                           HashMap<String, String> chenkMap, String type, String clzss) throws Exception {
        if (clLs == null || clLs.size() == 0) {
            return null;
        }
        String result = "";
        String formParas = "";
        String regArray = "";
        String errorMsgArray = "";
        for (ClellBean clell : clLs) {
            if (RowConstants.ROW_ZERO.equals(clell.row) || clell.type == null || clell.type.trim().length() == 0
                    || "主键".equals(clell.describe)) {
                continue;
            }
            if ("vague".equals(type)) {
                if (("y".equals(clell.vague) || "Y".equals(clell.vague))) {
                    if (formParas != null && formParas.trim().length() > 0) {
                        formParas = formParas + ",'" + AutoUtils.getLowerCase(clell.name) + "'";
                    } else {
                        formParas = "'" + AutoUtils.getLowerCase(clell.name) + "'";
                    }
                    if (regArray != null && regArray.trim().length() > 0) {
                        regArray = regArray + ",null";
                    } else {
                        regArray = "new Array()";
                    }
                    if (errorMsgArray != null && errorMsgArray.trim().length() > 0) {
                        errorMsgArray = errorMsgArray + ",null";
                    } else {
                        errorMsgArray = "new Array()";
                    }
                    result = result + readToFileList(fileName, clell, path, clzss);
                }
            } else if ("listShowTh".equals(type)) {
                fileName = new File(path + "listShowTableThFragment.txt");
                result = result + readToFileList(fileName, clell, path, clzss);
            } else if ("listShowVal".equals(type)) {
                if ("Date".equals(clell.type)) {
                    fileName = new File(path + "listShowDateFragment.txt");
                } else {
                    fileName = new File(path + "listShowStringFragment.txt");
                }
                if (("y".equals(clell.isSelect) || "Y".equals(clell.isSelect))
                        && clell.relevanceField.trim().length() > 0) {
                    continue;
                }
                result = result + readToFileList(fileName, clell, path, clzss);
            } else {
                if (("y".equals(clell.isSelect) || "Y".equals(clell.isSelect))
                        && clell.relevanceField.trim().length() > 0) {
                    continue;
                }
                result = result + readToFileList(fileName, clell, path, clzss);
            }
        }
        if (chenkMap != null) {
            chenkMap.put("formParas", formParas);
            chenkMap.put("regArray", regArray);
            chenkMap.put("errorMsgArray", errorMsgArray);
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
    private static String readToFileList(File fileName, ClellBean clell, String path, String clzss) throws Exception {
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
                        String img = "";
                        if ("y".equals(clell.isImg) || "Y".equals(clell.isImg)) {
                            img = "type='img' imgWidth='60px'  imgHeight='60px'";
                        }
                        String date = "";
                        if ("Date".equals(clell.type)) {
                            date = "type='date'";
                        }
                        read = read.replace("@propertyName", AutoUtils.getLowerCase(clell.name));
                        read = read.replace("@IMg@", img);
                        read = read.replace("@DATE@", date);
                        read = read.replace("@listdescribe", clell.describe);
                        read = read.replace("@class", clzss);
                        String maxlength = "";
                        String showWidth = "";
                        if ("@describeVal".equals(read)) {
                            if ("Date".equals(clell.type)) {
                                read = readToFileListDescribe(new File(path + "listTrTdFragmentDate.txt"), clell);
                            } else if ("Y".equals(clell.isSelect) || "y".equals(clell.isSelect)) {
                                read = readToFileListDescribe(new File(path + "listTrTdFragmentSelect.txt"), clell);
                            } else {
                                read = readToFileListDescribe(new File(path + "listTrTdFragmentString.txt"), clell);
                            }
                        }
                        if ("String".equals(clell.type)) {
                            if (clell.length != null && clell.length.trim().length() > 0) {
                                maxlength = "maxlength='" + clell.length + "'";
                            }
                        }
                        read = read.replace("@maxlength", maxlength);
                        if (clell.width != null && clell.width.trim().length() > 0) {
                            showWidth = "width='" + clell.width + "'";
                        }
                        read = read.replace("@showWidth", showWidth);
                        String showImg = "";
                        if ("Y".equals(clell.isImg) || "y".equals(clell.isImg)) {
                            showImg = readToFileListShow(new File(path + "listShowTableImgFragment.txt"), clell, path);
                        }
                        read = read.replace("@showImg", showImg);
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

    /**
     * 读取文件，并且写入新文件中
     *
     * @param fileName 读取文件
     * @param clell    属性名
     * @return
     */
    public static String readToFileListDescribe(File fileName, ClellBean clell) throws Exception {
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
                        read = read.replace("@fileType", clell.fileType);

                        read = read.replace("@package@", clell.basePackage);
                        read = read.replace("@entityClass", clell.entityClass);
                        read = read.replace("@dictionaryVal", clell.disVal);
                        read = read.replace("@dictionaryName", clell.disDesc);
                        read = read.replace("@filterVal", clell.filterVal);
                        read = read.replace("@relevanceField", AutoUtils.getLowerCase(clell.relevanceField));

                        String maxlength = "";
                        if ("String".equals(clell.type)) {
                            if (clell.length != null && clell.length.trim().length() > 0) {
                                maxlength = "maxlength='" + clell.length + "'";
                            }
                        }
                        read = read.replace("@maxlength", maxlength);
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

    /**
     * 读取文件，并且写入新文件中
     *
     * @param fileName 读取文件
     * @param clell    属性名
     * @return
     */
    private static String readToFileListShow(File fileName, ClellBean clell, String path) throws Exception {
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
                        read = read.replace("@fileType", clell.fileType);
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
