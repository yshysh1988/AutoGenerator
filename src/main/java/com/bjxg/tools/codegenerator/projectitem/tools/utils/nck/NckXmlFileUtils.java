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

public class NckXmlFileUtils {

    /**
     * 读取文件，并且写入新文件中
     *
     * @param fileName   读取文件
     * @param writeFile  写入文件
     * @param tableName  类生产的表明
     * @param classsName 生产的类名
     * @param proName    属性名
     * @param primarykey 主键字段
     * @return
     */
    public static String readNckToFile(File fileName, File writeFile, String tableName, String classsName,
                                       String proName, ClellBean primarykey, boolean isEmpty, String path, String packagestr, List<ClellBean> clLs, Project project)
            throws Exception {
        String result = "";
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
            fileReader = new FileReader(fileName);
            bufferedReader = new BufferedReader(fileReader);
            try {
                String read = "";
                String clzss = AutoUtils.getLowerCase(classsName);
                while ((read = bufferedReader.readLine()) != null) {
                    if (read != null && read.trim().length() > 0) {
                        read = read.replace("@tabelName", AutoUtils.getTableShowName(classsName));
                        read = read.replace("@ClassName", classsName);
                        read = read.replace("@className", tableName);
                        read = read.replace("@package@", packagestr);
                        read = read.replace("@pramkeType", primarykey.type);
                        read = read.replace("@primarykey", AutoUtils.getLowerCase(primarykey.name));
                        read = read.replace("@primary_key", primarykey.dbname);
                        read = read.replace("@showName", proName.replace("表", ""));
                        read = read.replace("@showDate", AutoUtils.getNowDate(DateConstants.DATE_FORMAT1));

                        read = read.replace("@packageDao@", project.getDaoPackage());
                        read = read.replace("@packageBean@", project.getBeanPackage());
                        read = read.replace("@primaryType@", primarykey.type);

                        if (classsName != null && classsName.trim().length() > 0) {
                            read = read.replace("@class", clzss);
                        }
                        if ("@@Result@@".equals(read)) {
                            read = setResult(clLs, path, clzss);
                        } else if ("@@AllListValue@@".equals(read)) {
                            read = setSelect(writeFile, clLs, path, clzss);
                        } else if ("@@PageCount@@".equals(read)) {
                            read = setPageCount(writeFile, clLs, path, clzss);
                        } else if ("@@insertFile@@".equals(read)) {
                            read = setInsert(writeFile, clLs, path, clzss, "field");
                        } else if ("@@insertValue@@".equals(read)) {
                            read = setInsert(writeFile, clLs, path, clzss, "Value");
                        } else if ("@@update@@".equals(read)) {
                            read = setUpdate(writeFile, clLs, path, clzss);
                        } else if ("@@InfoResult@@".equals(read)) {
                            read = setInfoResult(clLs, path, clzss);
                        } else if ("@@selectDate@@".equals(read)) {
                            read = setSelectDate(clLs);
                        }
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
        NckFileUtils.writeToFile(result, writeFile, isEmpty);
        return result;
    }

    private static String setResult(List<ClellBean> clLs, String path, String clzss) throws Exception {
        if (clLs == null || clLs.size() == 0) {
            return null;
        }
        File fileName;
        String result = "";
        for (ClellBean clell : clLs) {
            if (RowConstants.ROW_ZERO.equals(clell.row) || clell.type == null || clell.type.trim().length() == 0) {
                continue;
            }
            if ("Date".equals(clell.type)) {
                fileName = new File(path + "xmlResultDateFragment.txt");
            } else {
                fileName = new File(path + "xmlResultFragment.txt");
            }
            result = result + readToFileNck(fileName, clell, path, clzss);
        }
        return result;
    }

    private static String setInfoResult(List<ClellBean> clLs, String path, String clzss) throws Exception {
        File fileName;
        if (clLs == null || clLs.size() == 0) {
            return null;
        }
        String result = "";
        for (ClellBean clell : clLs) {
            if (RowConstants.ROW_ZERO.equals(clell.row) || clell.type == null || clell.type.trim().length() == 0) {
                continue;
            }
            if ("Date".equals(clell.type)) {
                fileName = new File(path + "xmlInfoResultDateFragment.txt");
            } else {
                fileName = new File(path + "xmlInfoResultFragment.txt");
            }
            result = result + readToFileNck(fileName, clell, path, clzss);
        }
        return result;
    }

    private static String setSelectDate(List<ClellBean> clLs) throws Exception {
        if (clLs == null || clLs.size() == 0) {
            return null;
        }
        String result = "";
        int index = 0;
        for (ClellBean clell : clLs) {
            if (RowConstants.ROW_ZERO.equals(clell.row) || clell.type == null || clell.type.trim().length() == 0) {
                continue;
            }
            if ("Date".equals(clell.type)) {
                if (index > 0) {
                    result = result + "\r\n";
                }
                result = result + "           ,date_format(" + clell.dbname + ",'%Y-%m-%d %H:%i:%s') as " + clell.name
                        + " ";
                index++;
            }

        }
        return result;
    }

    /**
     * nck xml
     ***/
    private static String setSelect(File writeFile, List<ClellBean> clLs, String path, String clzss) throws Exception {
        if (clLs == null || clLs.size() == 0) {
            return null;
        }
        File fileName = null;
        String result = "";
        for (ClellBean clell : clLs) {
            if (!RowConstants.ROW_ZERO.equals(clell.row) && clell.type != null && clell.type.trim().length() > 0) {
                if ("Date".equals(clell.type)) {
                    fileName = new File(path + "xmlSelectWhereEqDateFragment.txt");
                } else {
                    if ("Y".equals(clell.vague) || "y".equals(clell.vague)) {
                        fileName = new File(path + "xmlSelectWhereLikeFragment.txt");
                    } else {
                        fileName = new File(path + "xmlSelectWhereEqStringFragment.txt");
                    }
                }

                result = result + readToFileNck(fileName, clell, path, clzss);
            }
        }
        return result;
    }

    private static String setPageCount(File writeFile, List<ClellBean> clLs, String path, String clzss)
            throws Exception {
        if (clLs == null || clLs.size() == 0) {
            return null;
        }
        File file = null;
        String result = "";
        for (ClellBean clell : clLs) {
            if (!RowConstants.ROW_ZERO.equals(clell.row) && clell.type != null && clell.type.trim().length() > 0) {
                if ("Date".equals(clell.type)) {
                    file = new File(path + "xmlSelectWhereEqDateFragment.txt");
                } else {
                    if ("Y".equals(clell.vague) || "y".equals(clell.vague)) {
                        file = new File(path + "xmlSelectWhereLikeFragment.txt");
                    } else {
                        file = new File(path + "xmlSelectWhereEqStringFragment.txt");
                    }
                }

                result = result + readToFileNck(file, clell, path, clzss);
            }
        }
        return result;
    }

    private static String setInsert(File writeFile, List<ClellBean> clLs, String path, String clzss, String type)
            throws Exception {
        if (clLs == null || clLs.size() == 0) {
            return null;
        }
        File file = null;
        String result = "";
        for (ClellBean clell : clLs) {
            if (RowConstants.ROW_ZERO.equals(clell.row) || clell.type == null || clell.type.trim().length() == 0) {
                continue;
            }
            if ("主键".equals(clell.describe) && !"String".equals(clell.type)) {
                continue;
            }

            if ("field".equals(type)) {
                file = new File(path + "xmlInsertFileFragment.txt");
            } else {
                if ("Date".equals(clell.type)) {
                    file = new File(path + "xmlInsertValueDateFragment.txt");
                } else {
                    file = new File(path + "xmlInsertValueStringFragment.txt");
                }
            }
            result = result + readToFileNck(file, clell, path, clzss);

        }
        return result;
    }

    private static String setUpdate(File writeFile, List<ClellBean> clLs, String path, String clzss) throws Exception {
        if (clLs == null || clLs.size() == 0) {
            return null;
        }
        File file = null;
        String result = "";
        for (ClellBean clell : clLs) {
            if (RowConstants.ROW_ZERO.equals(clell.row) || clell.type == null
                    || clell.type.trim().length() == 0 || "主键".equals(clell.describe)) {
                continue;
            }

            if ("Date".equals(clell.type)) {
                file = new File(path + "xmlUpdateValueDateFragment.txt");
            } else {
                file = new File(path + "xmlUpdateValueStringFragment.txt");
            }
            result = result + readToFileNck(file, clell, path, clzss);
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
    public static String readToFileNck(File fileName, ClellBean clell, String path, String clzss) throws Exception {
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
                        read = read.replace("@@tableFile@@", clell.dbname);
                        read = read.replace("@class", clzss);
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
