package com.bjxg.tools.codegenerator.projectitem.tools.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.Clob;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>
 * 字符串操作辅助性工具类。
 * </p>
 * 
 * @author 文件创建者姓名:张建辉 zjianhui@linewell.com
 * @version 1.0.0 date:
 *          <p>
 *          Copyright (c) Jul 10, 2011 Linewell.com
 *          </p>
 */
@SuppressWarnings("unchecked")
public class StrUtil {

	/**
	 * Logger for this class
	 */
	private static final Log logger = LogFactory.getLog(StrUtil.class);

	/**
	 * 功能：格式化空字符串
	 * 
	 * @param str
	 * @return String
	 */
	public static String formatNull(String str) {
		return null == str || "null".equals(str) ? "" : str;
	}

	/**
	 * 功能：格式化空字符串,为空时返回默认字符串
	 * 
	 * @param str
	 *            原字符串
	 * @param defaultStr
	 *            默认字符串
	 * @return String
	 */
	public static String formatNull(String str, String defaultStr) {
		return StrUtil.isNull(str) ? defaultStr : str;
	}

	/**
	 * 将一维数组转化成字符
	 * 
	 * @param ret
	 *            -String []
	 * @return
	 */
	public static String toString(String[] ret) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < ret.length; i++) {
			if (i == ret.length - 1) {
				sb.append(ret[i]);
			} else {
				sb.append(ret[i] + ",");
			}
		}
		return sb.toString();
	}

	/**
	 * 将jdbc查询出来的结果转化成string
	 * 
	 * @param ret
	 * @return
	 */
	public static String toString(String[][] ret) {
		StringBuffer sb = new StringBuffer();
		for (int i = 1; i < ret.length; i++) {
			if (i == ret.length - 1) {
				sb.append(toString(ret[i]));
			} else {
				sb.append(toString(ret[i]) + ",");
			}
		}
		return sb.toString();
	}

	/**
	 * 功能：判断字符串是否为空
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isNull(String str) {
		return null == str || "".equals(str) || "null".equals(str) || "undefined".equals(str);
	}

	/**
	 * 将String转换成byte数组
	 * 
	 * @param in
	 * @return String
	 */
	public static byte[] stringToByte(String str, String encoding) {
		byte[] result = null;
		try {
			encoding = StrUtil.isNull(encoding) ? "GBK" : encoding;
			result = str.getBytes(encoding);
		} catch (UnsupportedEncodingException e) {
			logger.error(e);
		}
		return result;
	}

	/**
	 * 将byte数组转换成String
	 * 
	 * @param in
	 * @return String
	 */
	public static String byteToString(byte[] bytes, String encoding) {
		String result = null;
		try {
			encoding = StrUtil.isNull(encoding) ? "GBK" : encoding;
			result = new String(bytes, encoding);
		} catch (UnsupportedEncodingException e) {
			logger.error(e);
		}
		return result;
	}

	/**
	 * 把一维数组转换成以split为分隔的字符串
	 * 
	 * @param arrays
	 * @param split
	 * @return String
	 */
	public static String arrayToString(String[] arrays, String split) {
		StringBuffer sb = new StringBuffer();
		if (null != arrays && arrays.length > 0) {
			for (int i = 0; i < arrays.length; i++) {
				sb.append((i == 0 ? "" : split) + arrays[i]);
			}
		}
		return sb.toString();
	}

	/**
	 * 把以split为分隔的字符串转换成一维数组
	 * 
	 * @param arrays
	 * @param split
	 * @return String
	 */
	public static String arrayToString(String[][] arrays, String split) {
		String result = "";
		if (null != arrays && arrays.length > 0) {
			for (int i = 1; i < arrays.length; i++) {
				for (int m = 0; m < arrays[i].length; m++) {
					result += (StrUtil.isNull(result) ? "" : split) + arrays[i][m];
				}
			}
		}
		return result;
	}

	/**
	 * 如果文本中有xml的实体字符，则要采用CDATA的方式输出
	 * 
	 * @param in
	 *            传入的文本
	 * @return 输出的处理结果文本
	 */
	public static String formatXMLValue(String in) {
		if (null == in || in.equals("")) {
			return in;
		}
		if (in.indexOf('>') >= 0 || in.indexOf('<') >= 0 || in.indexOf('&') >= 0) {
			char[] chars1 = new char[1];
			chars1[0] = 91;
			char[] chars2 = new char[1];
			chars2[0] = 93;
			return "<!" + new String(chars1) + "CDATA" + new String(chars1) + " " + in + " " + new String(chars2)
					+ new String(chars2) + ">";
		} else {
			return in;
		}
	}

	/**
	 * 把字符串里出现的", <,>,&等特殊字符转换成标准可识别的字符 如字符串中包括 <,会转换成 &lt;
	 */
	public static String formatXMLAttribute(String str) {
		if (null == str || str.equals("")) {
			return str;
		}
		String[] src = { "<", ">", "\"", "'", "&" };
		String[] des = { "&lt;", "&gt;", "&quot", "&apos", "&amp;" };
		String result = str;
		for (int i = 0; i < src.length; i++) {
			result = result.replaceAll(src[i], des[i]);
		}
		return result;
	}

	/**
	 * 将分隔符的字符串转化为List对象
	 * 
	 * @param str
	 *            String-传入的分隔符字符串
	 * @param split
	 *            String-分隔符
	 * @return List对象
	 */
	public static List<String> stringToList(String str, String split) {
		List<String> list = new ArrayList<String>();
		if (!StrUtil.isNull(str)) {
			String args[] = str.split(split);
			list = Arrays.asList(args);
		}
		return list;
	}

	/**
	 * 将分隔符的字符串转化为String一维数组对象
	 * 
	 * @param str
	 *            String-传入的分隔符字符串
	 * @param split
	 *            String-分隔符
	 * @return String一维数组对象
	 */
	public static String[] stringToArray(String str, String split) {
		String array[] = new String[0];
		if (!StrUtil.isNull(str)) {
			array = str.split(split);
		}
		return array;
	}

	/**
	 * 将列表转换成以指定分隔符的字符串
	 * 
	 * @param list
	 *            : List<String> - 含有字符串元素的列表
	 * @param split
	 *            : String - 分隔符
	 * @return String - 以指定分隔符的字符串
	 */
	public static String listToString(final List list, final String split) {
		String str = "";
		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				str += (0 == i ? "" : split) + list.get(i).toString();
			}
		}
		return str;
	}

	/**
	 * 判断一个字符串是否是正整数
	 * 
	 * @return true or false
	 */
	public static boolean isPositiveInteger(String num) {
		Pattern pattern = Pattern.compile("^[0-9]*[1-9][0-9]*$");
		Matcher isNum = pattern.matcher(num);
		if (isNum.matches()) {
			return true;
		}
		return false;
	}

	/**
	 * 判断一个字符串是否是负整数
	 * 
	 * @return true or false
	 */
	public static boolean isNegativeInteger(String num) {
		Pattern pattern = Pattern.compile("^-[0-9]*[1-9][0-9]*$");
		Matcher isNum = pattern.matcher(num);
		if (isNum.matches()) {
			return true;
		}
		return false;
	}

	/**
	 * 判断一个字符串是不是由整数组成的
	 * 
	 * @return true or false
	 */
	public static boolean isNumber(String num) {
		Pattern pattern = Pattern.compile("^[0-9]*$");
		Matcher isNum = pattern.matcher(num);
		if (isNum.matches()) {
			return true;
		}
		return false;
	}

	/**
	 * 将字符串中的中文数字转换成阿拉伯数字
	 * 
	 * @param s
	 *            中文数字
	 * @return String
	 */
	public static String chineseToNumber(String s) {
		char[] chars = s.toCharArray();
		char[] chineseNum = new char[] { '零', '一', '二', '三', '四', '五', '六', '七', '八', '九' };
		for (int i = 0; i < chars.length; i++) {
			for (int j = 0; j < chineseNum.length; j++) {
				if (chars[i] == chineseNum[j]) {
					String temp = String.valueOf(j);
					chars[i] = temp.charAt(0);
					break;
				}
			}
		}
		String str = new String(chars);
		return str;
	}

	/**
	 * 为字符串后面不足补0
	 * 
	 * @param str
	 *            需要处理的字符串
	 * @param length
	 *            补零后的总长度
	 * @param type
	 *            类型：1.往前补0 2.往后补0
	 * @return 长度为length的补零后的字符串
	 */
	public static String fillZero(String str, int length, int type) {
		String code = "";
		for (int i = 0; i < length; i++) {
			code = code + "0";
		}

		if (type == 1) {
			code = code.substring(str.length()) + str;
		} else {
			code = str + code.substring(str.length());
		}
		return code;
	}

	/**
	 * 编码
	 * 
	 * @param bstr
	 * @return String
	 */
	public static String encode(byte[] bstr) {
		return new sun.misc.BASE64Encoder().encode(bstr);
	}

	/**
	 * 解码
	 * 
	 * @param str
	 * @return string
	 */
	public static byte[] decode(String str) {
		byte[] bt = null;
		try {
			sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
			bt = decoder.decodeBuffer(str);
		} catch (IOException e) {
			logger.error(e);
		}
		return bt;
	}

	/**
	 * 功能说明:将1,2,3字符串转换成'1','2','3'
	 * 
	 * @param id
	 *            -源字符串
	 * @param split-字符串分割符
	 * @return String
	 * @author chh
	 * @Jun 28, 2012
	 */
	public static String toSqlIds(String id, String split) {
		if (StrUtil.isNull(id)) {
			return "''";
		}
		String[] ids = id.split(split);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < ids.length; i++) {
			sb.append((i == 0 ? "" : ",") + ("'" + ids[i] + "'"));
		}
		return sb.toString();
	}

	/**
	 * 功能说明：将Object对象转化成String对象
	 * 
	 * @param value
	 * @return String
	 * @author chh
	 * @Sep 27, 2012
	 */
	public static String objectToString(Object value) {
		String strValue;
		if (value == null) {
			strValue = null;
		} else if (value instanceof String) {
			strValue = (String) value;
		} else if (value instanceof Integer) {
			strValue = ((Integer) value).toString();
		} else if (value instanceof Long) {
			strValue = ((Long) value).toString();
		} else if (value instanceof Float) {
			strValue = ((Float) value).toString();
		} else if (value instanceof Double) {
			strValue = ((Double) value).toString();
		} else if (value instanceof Boolean) {
			strValue = ((Boolean) value).toString();
		} else if (value instanceof Date) {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 时间格式
			format.setTimeZone(TimeZone.getTimeZone("GMT+8"));// 时区格式
			strValue = format.format((Date) value);
		}
		// else if (value instanceof Clob) {
		// strValue = ClobUtil.clobToStr((Clob)value);
		// }
		// else if (value instanceof Blob) {
		// strValue = BlobUtil.blobToString((Blob)value);
		// }
		else {
			strValue = value.toString();
		}
		return strValue;
	}

	/**
	 * 判断数组中是否包含指定字符串
	 * 
	 * @param array
	 *            字符串数据
	 * @param value
	 *            指定的字符串
	 * @return 包含则返回true,不包含返回false
	 */
	public static boolean isArrayHasValue(String[] array, String value) {
		for (int i = 0; i < array.length; i++) {
			if (array[i].equals(value)) {				
				return true;
			}
		}
		return false;
	}

	/**
	 * 从表达式中提取参数
	 * 
	 * @param expression
	 *            表达式
	 * @param regExp
	 *            参数的匹配格式(正则表达式)
	 * @return 匹配的参数名称(多个参数用","分格)
	 */
	public static String getPramsFromExpression(String expression, String regExp) {
		String result = "";
		if (!StrUtil.isNull(expression)) {
			Pattern p = Pattern.compile(regExp);
			Matcher m = p.matcher(expression);
			while (m.find()) {
				String param = m.group(1);
				if (!StrUtil.isNull(param) && result.indexOf(param) < 0) {// 过滤重复的参数
					result += (StrUtil.isNull(result) ? "" : ",") + param;
				}
			}
		}
		return result;
	}

	/**
	 * 去除字符串中的指定字符串
	 * 
	 * @param parent
	 *            父字符串
	 * @param son
	 *            子字符串
	 * @param split
	 *            字符串分隔符
	 * @return String 去除指定字符串的新字符串
	 */
	public static String getRemovedStr(String parent, String son, String split) {
		String result = "";
		if (!StrUtil.isNull(parent) && !("".equals(son) || null == son)) {
			String[] strArr = parent.split(split);
			for (String string : strArr) {
				if (!(son.equals(string))) {
					result += (string + split);
				}
			}
			if (!StrUtil.isNull(result)) {
				result = result.substring(0, result.length() - 1);
			}
		}
		return result;
	}

	/**
	 * 得到随机数
	 * 
	 * @param lengths
	 * @return
	 */
	public static String getRandom() {
		Random random1 = new Random();
		return String.valueOf(random1.nextInt());
	}

	/**
	 * 得到LEN长度的随机数
	 * 
	 * @param len
	 * @return
	 */
	public static String getRandom(int len) {
		String result = "";
		Random random = new Random();
		for (int i = 0; i < len; i++) {
			result += (Math.abs(random.nextInt()) % 10);
		}
		return result;
	}

	public static void main(String[] args) {

		long begin = System.currentTimeMillis();
		String str = "createdEndTime";
		StringBuffer resultBuff = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			Character c = str.charAt(i);
			if (Character.isUpperCase(c)) {
				resultBuff.append("_");
			}
			resultBuff.append(Character.toLowerCase(c));
		}
		long end = System.currentTimeMillis();
		System.out.println("结果:" + resultBuff.toString() + "用时：" + (end - begin) + "毫秒");
	}

	public static Map<String, String> getRandomColor(Set<String> colorEles) {
		Random random = new Random();
		Map<String, String> colorMap = new HashMap<String, String>();
		for (String ele : colorEles) {
			int first = random.nextInt(255);
			int center = random.nextInt(255);
			int last = random.nextInt(255);
			if (first != 0) {
				if (first > 85) {
					center = random.nextInt(85);
				}
			} else {
				if (center > 85) {
					last = random.nextInt(85);
				}
			}
			String colorValue = Integer.toHexString(first) + Integer.toHexString(center) + Integer.toHexString(last);
			if (colorValue.length() < 6) {
				colorValue += random.nextInt(9);
			}
			colorMap.put(ele, colorValue);
		}
		return colorMap;
	}

	/**
	 * 向父字符串中添加指定分隔符的子字符串
	 * 
	 * @param parent
	 *            父字符串
	 * @param son
	 *            子字符串
	 * @param split
	 *            字符串分隔符
	 * @return String 增加指定子字符串的新字符串
	 */
	public static String getNewStrByAdd(String parent, String son, String split) {
		String result = "";
		if (StrUtil.isNull(son)) {
			return parent;
		}
		if (StrUtil.isNull(parent)) {
			return son;
		}
		result = parent + split + son;
		return result;
	}

	/**
	 * 获取指定格式的日期字符串
	 * 
	 * @param dateStr
	 *            日期字符串（其中年月日的格式为：yyyy-MM-dd） return String 返回格式字符串（月/日如：20/2）
	 */
	public static String doFormatDate(String dateStr) {
		String formatStr = "";
		if (!StrUtil.isNull(dateStr)) {
			String month = dateStr.substring(5, 7);
			String day = dateStr.substring(8, 10);
			if (month.indexOf("0") == 0) {
				month = month.substring(1);
			}
			if (day.indexOf("0") == 0) {
				day = day.substring(1);
			}
			formatStr = day + "/" + month;
		}
		return formatStr;
	}

	public static String rtrim(String str, String replaceStr) {
		int num = str.length();
		String strResult = "";
		for (int i = num - 1; i > -1; i--) {
			if (!(str.substring(i, i + 1).equals(replaceStr))) {
				strResult = str.substring(0, i + 1);
				break;
			}
		}
		return strResult;
	}

	public static boolean isMobileNO(String mobiles) {
		boolean resultFlag = false;
		if (!isNull(mobiles)) {
			Pattern p = Pattern.compile("^((\\(\\d{3}\\))|(\\d{3}\\-))?1\\d{10}$");
			Matcher m = p.matcher(mobiles);
			resultFlag = m.matches();
		}
		return resultFlag;
	}
}