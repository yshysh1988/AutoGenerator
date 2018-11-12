package com.bjxg.tools.codegenerator.projectitem.tools.utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.bjxg.tools.codegenerator.projectitem.Project;
import com.bjxg.tools.codegenerator.projectitem.tools.bean.ClellBean;
import com.bjxg.tools.codegenerator.projectitem.tools.constants.RowConstants;

public class LoadExclUtils {
	/**
	 * @param fileName
	 *            加载文件的路径
	 * @param project
	 */

	public static void createDocList(Project project) {
		String fileName = project.getDocPath();
		boolean isE2007 = false;
		if (fileName.endsWith("xlsx")) {
			isE2007 = true;
		}
		try {
			InputStream input = new FileInputStream(fileName);
			Workbook wb = null;
			if (isE2007) {
				wb = new XSSFWorkbook(input);
			} else {
				wb = new HSSFWorkbook(input);
			}
			List<List<ClellBean>> docList = new ArrayList<List<ClellBean>>();
			ClellBean fileId = null;
			for (int i = 0; i < wb.getNumberOfSheets(); i++) {
				Sheet sheet = wb.getSheetAt(i); // 获取每个表单的内容
				Iterator<Row> rows = sheet.rowIterator(); // 迭代表中的每一行
				List<ClellBean> sheetList = new ArrayList<ClellBean>();
				while (rows.hasNext()) {
					Row row = rows.next(); // 获取每行的内容
					Iterator<Cell> cells = row.cellIterator(); // 迭代每一列
					fileId = new ClellBean();
					fileId.basePackage = project.getBasePackage();
					fileId.row = row.getRowNum() + "";
					boolean isAdd = true;
					while (cells.hasNext()) {
						Cell cell = cells.next();
						switch (cell.getCellType()) {
						case HSSFCell.CELL_TYPE_NUMERIC:
							setFileId(cell.getColumnIndex(), cell.getNumericCellValue() + "", fileId);
							break;
						case HSSFCell.CELL_TYPE_STRING:
							setFileId(cell.getColumnIndex(), cell.getStringCellValue() + "", fileId);

							// if ("通用字段".equals(cell.getStringCellValue())) {
							// isAdd = false;
							// continue;
							// }
							if (cell.getColumnIndex() == 0 && isContainChinese(cell.getStringCellValue())) {
								isAdd = false;
								continue;
							}
							if ("END".equals(cell.getStringCellValue())) {
								isAdd = false;
								continue;
							}
							break;
						case HSSFCell.CELL_TYPE_BOOLEAN:
							setFileId(cell.getColumnIndex(), cell.getBooleanCellValue() + "", fileId);
							break;
						case HSSFCell.CELL_TYPE_FORMULA:
							setFileId(cell.getColumnIndex(), cell.getCellFormula() + "", fileId);
							break;
						default:
							setFileId(cell.getColumnIndex(), "", fileId);
							break;
						}
					}
					if (isAdd) {
						sheetList.add(fileId);
					}
				}
				docList.add(sheetList);
			}
			project.setDocList(docList);
			setIsNulls(project);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private static void setIsNulls(Project project) {
		List<List<ClellBean>> docList = project.getDocList();
		if (docList == null || docList.size() == 0) {
			return;
		}
		List<List<ClellBean>> isNulls = new ArrayList<List<ClellBean>>();
		for (List<ClellBean> list : docList) {
			List<ClellBean> nullList = new ArrayList<ClellBean>();
			for (ClellBean fileId : list) {
				if (RowConstants.ROW_ZERO.equals(fileId.row)) {
					nullList.add(fileId);
				} else if ("N".equals(fileId.isNull) || "n".equals(fileId.isNull)) {
					nullList.add(fileId);
				}
			}
			isNulls.add(nullList);
		}
		project.setIsNulls(isNulls);
	}

	private static void setFileId(int cellNum, String value, ClellBean fileId) {
		if (RowConstants.ROW_ZERO.equals(fileId.row)) {
			if (cellNum == 1) {
				fileId.describe = value;
				return;
			} else if (cellNum == 3) {
				return;
			}
		}
		switch (cellNum) {
		case 0:
			if (value != null && value.trim().length() > 0) {
				value = value.trim();
			}
			fileId.name = value;
			fileId.dbname = AutoUtils.getTableShowName(value);
			break;
		case 1:
			fileId.type = value;
			setDbType(fileId, value);
			break;
		case 2:
			if (value != null && value.trim().length() > 0) {
				if (!LoadExclUtils.isContainChinese(value)) {
					value = value.substring(0, value.length() - 2);
				}
			}
			fileId.length = value;
			break;
		case 3:
			fileId.describe = value;
			break;
		case 4:
			fileId.annotate = value;
			break;
		case 5:
			fileId.vague = value;
			break;
		case 6:
			fileId.isNull = value;
			break;
		case 7:
			fileId.isList = value;
			break;
		case 8:
			if (value == null || value.trim().length() == 0) {
				value = "120px";
			}
			fileId.width = value;
			break;
		case 9:
			if("String".equals(fileId.name)) {
				fileId.isImg = value;
			}
			
			break;
		case 10:
			if (value != null && value.trim().length() > 0) {
				if (!LoadExclUtils.isContainChinese(value)) {
					value = value.substring(0, value.length() - 2);
				} else {
					break;
				}
			} else {
				break;
			}
			fileId.fileType = value;
			break;
		case 11:
			fileId.isVereist = value;
			break;
		case 12:
			fileId.isSelect = value;
		case 13:
			fileId.entityClass = value;
		case 14:
			fileId.disVal = value;
		case 15:
			fileId.disDesc = value;
			break;
		case 16:
			fileId.filterVal = value;
			break;
		case 17:
			fileId.isHidden = value;
			break;
		default:
			fileId.relevanceField = value;
			break;
		}

	}

	private static void setDbType(ClellBean fileId, String value) {
		if (value == null || value.trim().length() == 0) {
			return;
		}
		value = value.trim();
		switch (value) {
		case "String":
			fileId.dbtype = "VARCHAR";
			fileId.dblength = fileId.length;
			break;
		case "byte[]":
			fileId.dbtype = "BLOB";
			fileId.dblength = fileId.length;
			break;
		case "Long":
			fileId.dbtype = "INTEGER";
			fileId.dblength = fileId.length;
			break;
		case "Integer":
			fileId.dbtype = "SMALLINT";
			fileId.dblength = fileId.length;
			break;
		case "Boolean":
			fileId.dbtype = "BIT";
			fileId.dblength = fileId.length;
			break;
		case "BigInteger":
			fileId.dbtype = "BIGINT";
			fileId.dblength = fileId.length;
			break;
		case "Float":
			fileId.dbtype = "FLOAT";
			fileId.dblength = fileId.length;
			break;
		case "Double":
			fileId.dbtype = "DOUBLE";
			fileId.dblength = fileId.length;
			break;
		case "BigDecimal":
			fileId.dbtype = "DECIMAL";
			fileId.dblength = fileId.length;
			break;
		case "Date":
			fileId.dbtype = "DATETIME";
			fileId.dblength = fileId.length;
			break;
		default:
			break;
		}

	}

	public static boolean isContainChinese(String str) {
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher m = p.matcher(str);
		if (m.find()) {
			return true;
		}
		return false;
	}
}
