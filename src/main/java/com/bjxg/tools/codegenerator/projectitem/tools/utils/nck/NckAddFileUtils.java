package com.bjxg.tools.codegenerator.projectitem.tools.utils.nck;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;

import com.bjxg.tools.codegenerator.projectitem.tools.bean.ClellBean;
import com.bjxg.tools.codegenerator.projectitem.tools.constants.DateConstants;
import com.bjxg.tools.codegenerator.projectitem.tools.constants.RowConstants;
import com.bjxg.tools.codegenerator.projectitem.tools.utils.AutoUtils;

public class NckAddFileUtils {

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
	 * @param primarykey
	 *            主键字段
	 * @return
	 */
	public static String readNckToFile(File fileName, File writeFile, String tableName, String classsName,
			String proName, ClellBean primarykey, boolean isEmpty, String path, String packagestr, List<ClellBean> clLs)
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
						read = read.replace("@tabelName", tableName);
						read = read.replace("@ClassName", classsName);
						read = read.replace("@className", tableName);
						read = read.replace("@package@", packagestr);
						read = read.replace("@primarykey", AutoUtils.getLowerCase(primarykey.name));
						read = read.replace("@showName", proName.replace("表", ""));
						read = read.replace("@showDate", AutoUtils.getNowDate(DateConstants.DATE_FORMAT1));
						read = read.replace("@class", clzss);
						if ("@divFiled@".equals(read)) {
							read = setAddTableTrtd(new File(path + "addFragmentTrtd.txt"), writeFile, clLs, path, null,
									clzss);
						} 
						else if ("@@reqDate@@".equals(read)) {
							read = setAddReqDate(clLs, clzss,"reqDate");
						}
						else if ("@funImg@".equals(read)) {
							read = readToFileAdd(new File(path + "addFragmentImgEdit.txt"), primarykey, path, clzss);
						}
						else if ("@@saveReqDate@@".equals(read)) {
							read = setAddReqDate(clLs, clzss,"saveReqDate");
						}
						else if ("@saveImg@".equals(read)) {
							read = setSaveImg(clLs, path ,clzss);
						}
						else if("@reqSave@".equals(read)) {
							read = setReqSave(clLs, path, clzss);
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

	/** add.jsp 页面 ***/
	public static String setAddTableTrtd(File fileName, File writeFile, List<ClellBean> clLs, String path,
			HashMap<String, String> chenkMap, String clzss) throws Exception {
		if (clLs == null || clLs.size() == 0) {
			return null;
		}
		String result = "";
		String formParas = "";
		String regArray = "";
		String errorMsgArray = "";
		int index = 1;
		boolean isOne = true;
		for (ClellBean clell : clLs) {
			if (RowConstants.ROW_ZERO.equals(clell.row) || clell.type == null || clell.type.trim().length() == 0
					|| "主键".equals(clell.describe)) {
				continue;
			}
			
			if("Y".equals( clell.isImg) || "y".equals( clell.isImg)) {
				fileName = new File(path + "addFragmentImg.txt");
				if (!isOne) {
					result = result + "			</div>\r\n			<div class='layui-form-item'>\r\n";
				}
				result = result + readToFileAdd(fileName, clell, path, clzss);
				index = 1;
			}else if (clell.getLength() > 100) {
				fileName = new File(path + "addFragmentText.txt");
				if (!isOne) {
					result = result + "			</div>\r\n			<div class='layui-form-item'>\r\n";
				}
				result = result + readToFileAdd(fileName, clell, path, clzss);
				index = 1;
			} else {
				if ("Date".equals(clell.type)) {
					fileName = new File(path + "addFragmentDate.txt");
				} else {
					fileName = new File(path + "addFragmentString.txt");
				}
				if (index == 1) {
					if (isOne) {
						result = result + "			<div class='layui-form-item'>\r\n";
					} else {
						result = result + "			</div>\r\n			<div class='layui-form-item'>\r\n";
					}
					index++;
				} else {
					index = 1;
				}
				result = result + readToFileAdd(fileName, clell, path, clzss);
			}
			isOne = false;
			
			
			String error = "";
			String form = "";
			if ("y".equals(clell.isVereist) || "Y".equals(clell.isVereist)) {
				error = "'不能为空'";
				form = "emptyReg";
			}
			if (formParas != null && formParas.trim().length() > 0) {
				formParas = formParas + ",'" + AutoUtils.getLowerCase(clell.name) + "'";
			} else {
				formParas = "'" + AutoUtils.getLowerCase(clell.name) + "'";
			}
			if (regArray != null && regArray.trim().length() > 0) {
				regArray = regArray + ",new Array(" + form + ")";
			} else {
				regArray = "new Array(" + form + ")";
			}
			if (errorMsgArray != null && errorMsgArray.trim().length() > 0) {
				errorMsgArray = errorMsgArray + ",new Array(" + error + ")";
			} else {
				errorMsgArray = "new Array(" + error + ")";
			}
		}
		result = result + "	   </div>\r\n";
		if (chenkMap != null) {
			chenkMap.put("formParas", formParas);
			chenkMap.put("regArray", regArray);
			chenkMap.put("errorMsgArray", errorMsgArray);
		}
		return result;
	}
	
	public static String setReqSave( List<ClellBean> clLs, String path, String clzss) throws Exception {
		File fileName = null;
		if (clLs == null || clLs.size() == 0) {
			return null;
		}
		String result = "";
		for (ClellBean clell : clLs) {
			if (RowConstants.ROW_ZERO.equals(clell.row) || clell.type == null 
					|| clell.type.trim().length() == 0) {
				continue;
			}
			if("y".equals(clell.isVereist) || "Y".equals(clell.isVereist)) {
				if("y".equals(clell.isImg) || "Y".equals(clell.isImg)) {					
					fileName = new File(path + "addFragmentSaveImgDatabt.txt");
				}else {					
					fileName = new File(path + "addFragmentSaveDatabt.txt");
				}
			}else {
				if("y".equals(clell.isImg) || "Y".equals(clell.isImg)) {					
					fileName = new File(path + "addFragmentSaveImgData.txt");
				}else {					
					fileName = new File(path + "addFragmentSaveData.txt");
				}
			}
			result = result + readToFileAdd(fileName, clell, path, clzss);
		}

		return result;
	}
	
	public static String setSaveImg( List<ClellBean> clLs, String path, String clzss) throws Exception {
		File fileName = new File(path + "addFragmentImgUpload.txt");
		if (clLs == null || clLs.size() == 0) {
			return null;
		}
		String result = "";
		for (ClellBean clell : clLs) {
			if (RowConstants.ROW_ZERO.equals(clell.row) || clell.type == null || clell.type.trim().length() == 0
					|| "主键".equals(clell.describe)) {
				continue;
			}
			if("y".equals(clell.isImg) || "Y".equals(clell.isImg)) {
				result = result + readToFileAdd(fileName, clell, path, clzss);
			}
			
		}
		
		return result;
	}

	private static String setAddReqDate(List<ClellBean> clLs, String clzss,String type) throws Exception {
		if (clLs == null || clLs.size() == 0) {
			return null;
		}
		String result = "";
		int index = 0;
		for (ClellBean clell : clLs) {
			if (RowConstants.ROW_ZERO.equals(clell.row)) {
				continue;
			}
			
			if(index > 0) {
				result = result + "\r\n";
			}
			if("reqDate".equals(type)) {
				if ("Date".equals(clell.type)) {
					result = result + "   						  \"" + clell.name + "Str\":data.field." + clell.name+"Str";
				}else {
					if("y".equals(clell.isImg) || "Y".equals(clell.isImg)) {					
						result = result + "   						  \"" + clell.name + "\":$('#" + clell.name+"Img').attr('src')";
					}else {					
						result = result + "   						  \"" + clell.name + "\":data.field." + clell.name;
					}
				}
			}else if("saveReqDate".equals(type)) {
				if ("Date".equals(clell.type)) {
					result = result + "            \"" + clell.name + "Str\":" + clell.name;
				}else {					
					result = result + "            \"" + clell.name + "\":" + clell.name;
				}
			}

			
			if (index < clLs.size() - 2) {
				result = result + ",";
			}
			index++;
		}
		return result;
	}

	/**
	 * 读取文件，并且写入新文件中
	 * 
	 * @param fileName
	 *            读取文件
	 * @param clell
	 *            属性名
	 * @return
	 */
	public static String readToFileAdd(File fileName, ClellBean clell, String path, String clzss) throws Exception {
		String result = "";
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		try {
			fileReader = new FileReader(fileName);
			bufferedReader = new BufferedReader(fileReader);
			try {
				String read = "";
				String isVereist = "";
				if ("y".equals(clell.isVereist) || "Y".equals(clell.isVereist)) {
					isVereist = "lay-verify=\"required\"";
				}
				while ((read = bufferedReader.readLine()) != null) {
					if (read != null && read.trim().length() > 0) {
						read = read.replace("@describe@", clell.describe);
						read = read.replace("@propertyName", AutoUtils.getLowerCase(clell.name));
						read = read.replace("@class", clzss);
						read = read.replace("@isVereist@", isVereist);
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
	 * @param fileName
	 *            读取文件
	 * @param clell
	 *            属性名
	 * @return
	 */
	public static String readToFileAddDescribe(File fileName, ClellBean clell, String type) throws Exception {
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
		if ("describe".equals(type)) {
			result = result + clell.describe;
		}
		return result;
	}

}
