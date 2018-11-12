package com.bjxg.tools.codegenerator.projectitem.tools.utils.nck;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

import com.bjxg.tools.codegenerator.projectitem.tools.bean.ClellBean;
import com.bjxg.tools.codegenerator.projectitem.tools.constants.DateConstants;
import com.bjxg.tools.codegenerator.projectitem.tools.utils.AutoUtils;

public class NckControllerFileUtils {

	/**
	 * 读取文件，并且写入新文件中
	 * 
	 * @param fileName
	 *            读取文件
	 * @param writeFile
	 *            写入文件
	 * @param tableName
	 *            类生产的表明
	 * @param classsName
	 *            生产的类名
	 * @param proName
	 *            属性名
	 * @param methodName
	 *            get/set方法名
	 * @param primarykey
	 *            主键字段
	 * @return
	 */
	public static String readNckToFile(File fileName, File writeFile, String tableName, String classsName,
			String proName, ClellBean primarykey, boolean isEmpty, String path, String packagestr,
			List<ClellBean> clLs) throws Exception {
		String result = "";
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		try {
			fileReader = new FileReader(fileName);
			bufferedReader = new BufferedReader(fileReader);
			try {
				String read = "";
				String clzss = classsName.toLowerCase();
				while ((read = bufferedReader.readLine()) != null) {
					if (read != null && read.trim().length() > 0) {
						read = read.replace("@tabelName", tableName);
						read = read.replace("@ClassName", classsName);
						read = read.replace("@className", tableName);
						read = read.replace("@package@", packagestr);
						read = read.replace("@pramkeType", primarykey.type);
						read = read.replace("@primarykey", primarykey.name);
						read = read.replace("@Primarykey", AutoUtils.getUpperCase(primarykey.name));
						read = read.replace("@showName", proName.replace("表", ""));
						read = read.replace("@showDate", AutoUtils.getNowDate(DateConstants.DATE_FORMAT1));
						if (classsName != null && classsName.trim().length() > 0) {
							read = read.replace("@@clzss", clzss);
							read = read.replace("@class", AutoUtils.getLowerCase(classsName));
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

}
