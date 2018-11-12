package com.bjxg.tools.codegenerator.projectitem.tools.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bjxg.tools.codegenerator.projectitem.tools.bean.ClellBean;

public class AutoUtils {
	/*** 将首字母变小写 **/
	public static String getLowerCase(String name) {
		if (name != null && name.trim().length() > 0) {
			return name.substring(0, 1).toLowerCase() + name.substring(1);
		}
		return name;
	}

	/*** 将首字母变大写 **/
	public static String getUpperCase(String name) {
		if (name != null && name.trim().length() > 0) {
			return name.substring(0, 1).toUpperCase() + name.substring(1);
		}
		return name;
	}

	/*** 获取现在时间 以传入的格式为准 ***/
	public static String getNowDate(String dateFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(new Date());
	}

	/*****
	 * 获取数据库显示名称
	 * 
	 * @return
	 */
	public static String getTableShowName(String name) {
		ArrayList<String> files = splitByUpperCase(name);
		if (files != null && files.size() > 0) {
			StringBuffer str = new StringBuffer();
			for (int i = 0; i < files.size(); i++) {
				if (i != 0) {
					str.append("_");
				}
				str.append(files.get(i).toLowerCase());
			}
			return str.toString();
		}
		return name;
	}

	/*****
	 * 传入一个字符串 将它分割成大写字符为首的字符串数组
	 */
	public static ArrayList<String> splitByUpperCase(String str) {
		ArrayList<String> rs = new ArrayList<String>();
		int index = 0;
		int len = str.length();
		for (int i = 1; i < len; i++) {
			if (Character.isUpperCase(str.charAt(i))) {
				rs.add(str.substring(index, i));
				index = i;
			}
		}
		rs.add(str.substring(index, len));
		return rs;
	}

	/*****
	 * 获取主键字段
	 */
	public static ClellBean getPrimarykey(List<ClellBean> clellBeanLs, String primaryName) {
		if (clellBeanLs != null && clellBeanLs.size() > 0) {
			if (primaryName == null || primaryName.trim().length() == 0) {
				primaryName = "主键";
			}
			for (ClellBean clellBean : clellBeanLs) {
				if (primaryName.equals(clellBean.describe)) {
					return clellBean;
				}
			}
		}
		return new ClellBean();
	}
}
