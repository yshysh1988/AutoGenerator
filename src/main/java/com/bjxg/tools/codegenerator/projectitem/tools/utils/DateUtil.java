package com.bjxg.tools.codegenerator.projectitem.tools.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DateUtil {
	/**
	 * Logger for this class
	 */
	private static final Log logger = LogFactory.getLog(DateTime.class);
	private String year;
	private String month;
	private String day;
	private static final DateFormat STANDARD_TIME_PATTERN = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final DateFormat STANDARD_DATE_PATTERN = new SimpleDateFormat("yyyy-MM-dd");
	private static final DateFormat STANDARD_CHINESE_DATE_PATTERN = new SimpleDateFormat("yyyy年MM月dd日");

	/**
	 * 月份字符串最小值
	 */
	public static final String MONTH_MIN = "01";
	/**
	 * 月份字符串最大值
	 */
	public static final String MONTH_MAX = "12";

	/**
	 * 天数字符串最小值
	 */
	public static final String DAY_MIN = "01";
	/**
	 * 天数字符串最大值
	 */
	public static final String DAY_MAX = "31";

	public DateUtil() {
		String dateTime = DateTime.getNowDateTime();
		this.year = dateTime.substring(0, 4);
		this.month = dateTime.substring(5, 7);
		this.day = dateTime.substring(8, 10);
	}

	public int getCurYear() {
		return Integer.parseInt(this.year);
	}

	public int getCurYearMin() {
		return Integer.parseInt(this.year + MONTH_MIN + DAY_MIN);
	}

	public int getCurYearMax() {
		return Integer.parseInt(this.year + MONTH_MAX + DAY_MAX);
	}

	public int getCurMonthMin() {
		return Integer.parseInt(this.year + this.month + DAY_MIN);
	}

	public int getCurMonthMax() {
		return Integer.parseInt(this.year + this.month + DAY_MAX);
	}

	public int getCurDay() {
		return Integer.parseInt(this.year + this.month + this.day);
	}

	/**
	 * 获取本年到本月的所有月份
	 * 
	 * @return new String[]{"2010-01", "2010-02", "2010-03"}
	 */
	public String[] months() {
		int m = Integer.parseInt(month);
		String res[] = new String[m];
		for (int i = 1; i <= m; i++) {
			if (i < 10) {
				res[i - 1] = year + "-0" + i;
			} else {
				res[i - 1] = year + "-" + i;
			}
		}
		return res;

	}

	/**
	 * 12个月
	 * 
	 * @param year
	 * @return if null return 最近12个月, else return 年12个月
	 */
	public String[] months(String year) {
		if (null == year) {
			return months12();
		}
		return months12(Integer.parseInt(year));

	}

	public String[] months(String begin, String end) {
		DateFormat df = new SimpleDateFormat("yyyy-MM");
		ArrayList l = new ArrayList();
		try {
			Date db = df.parse(begin);
			Calendar cb = new GregorianCalendar();
			cb.setTime(db);
			Date de = df.parse(end);
			Calendar ce = new GregorianCalendar();
			ce.setTime(de);
			l.add(df.format(cb.getTime()));
			while (cb.before(ce)) {
				cb.add(Calendar.MONTH, 1);
				l.add(df.format(cb.getTime()));
			}
		} catch (ParseException e) {
			logger.error(e);
		}
		int m = l.size();
		String res[] = new String[m];
		for (int i = 0; i < m; i++) {
			res[i] = (String) l.get(i);
		}
		return res;

	}

	/**
	 * 获取当前月的开始时间
	 * 
	 * @return 2011-01-01 00:00:00
	 */
	public String getCurrentMonthBegin() {
		return date(null, 0, 0, 0, 0, 0, 0);
	}

	/**
	 * 获取当前月的结束时间
	 * 
	 * @return 2011-01-31 23:59:59
	 */
	public String getCurrentMonthEnd() {
		return date(null, 0, 1, 0, 0, 0, -1);
	}

	/**
	 * 获取前一个月的开始时间
	 * 
	 * @return 2010-12-01 00:00:00
	 */
	public String getPreviousMonthBegin() {
		return date(null, 0, -1, 0, 0, 0, 0);
	}

	/**
	 * 获取前一个月的结束时间
	 * 
	 * @return 2010-12-31 23:59:59
	 */
	public String getPreviousMonthEnd() {
		return date(null, 0, 0, 0, 0, 0, -1);
	}

	/**
	 * 获取date的前一个月的开始时间
	 * 
	 * @return 2010-12-01 00:00:00
	 */
	public String getZDPreviousMonthBegin(Date date) {
		return date(date, 0, -1, 0, 0, 0, 0);
	}

	/**
	 * 获取date的前一个月的结束时间
	 * 
	 * @return 2010-12-31 23:59:59
	 */
	public String getZDPreviousMonthEnd(Date date) {
		return date(date, 0, 0, 0, 0, 0, -1);
	}

	/**
	 * 获取当前季度的开始时间
	 * 
	 * @return 2011-01-01 00:00:00
	 */
	public String getCurrentSeasonBegin() {
		return getSeason(0);
	}

	/**
	 * 获取当前季度的结束时间
	 * 
	 * @return 2011-03-31 23:59:59
	 */
	public String getCurrentSeasonEnd() {
		return getSeason(1);
	}

	/**
	 * 当前季度的时间
	 * 
	 * @param m
	 *            0：当前季度的开始时间 1：当前季度的结束时间
	 * @return
	 */
	private String getSeason(int m) {
		Date d = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		int month = ((c.get(Calendar.MONTH) / 3) + m) * 3;
		c.set(c.get(Calendar.YEAR), month, 1, 0, 0, -m);
		return STANDARD_TIME_PATTERN.format(c.getTime());
	}

	/**
	 * 获取当前年的开始时间
	 * 
	 * @return 2011-01-01 00:00:00
	 */
	public String getCurrentYearBegin() {
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(c.get(Calendar.YEAR), 0, 1, 0, 0, 0);
		return STANDARD_TIME_PATTERN.format(c.getTime());
	}

	/**
	 * 获取当前时间
	 * 
	 * @return 2011-01-05 13:49:59
	 */
	public String getCurrentYearEnd() {
		return DateTime.getNowDateTime();
	}

	/**
	 * 昨天的开始时间
	 * 
	 * @return 2010-12-01 00:00:00
	 */
	public String getYesterdayBegin() {
		return dateday(null, 0, 0, -1, 0, 0, 0);
	}

	/**
	 * 昨天的结束时间
	 * 
	 * @return 2010-12-31 23:59:59
	 */
	public String getYesterdayEnd() {
		return dateday(null, 0, 0, 0, 0, 0, -1);
	}

	/**
	 * 今天的开始时间
	 * 
	 * @return 2010-12-01 00:00:00
	 */
	public String getTodayBegin() {
		return dateday(null, 0, 0, 0, 0, 0, 0);
	}

	/**
	 * 今天的结束时间
	 * 
	 * @return 2010-12-31 23:59:59
	 */
	public String getTodayEnd() {
		return dateday(null, 0, 0, 1, 0, 0, -1);
	}

	/**
	 * 本周的开始时间
	 * 
	 * @return 2010-12-01 00:00:00
	 */
	public String getWeekBegin() {
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		if (c.get(Calendar.DAY_OF_WEEK) == 1) {
			c.add(Calendar.DATE, -7);
		}
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		// c.add(Calendar.DAY_OF_MONTH, 1 - c.get(Calendar.DAY_OF_WEEK));
		return STANDARD_TIME_PATTERN.format(c.getTime());

	}

	/**
	 * 本周结束时间
	 * 
	 * @return 2010-12-31 23:59:59
	 */
	public String getWeekEnd() {
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		if (c.get(Calendar.DAY_OF_WEEK) == 1) {
			c.add(Calendar.DATE, -1);
		}
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		// c.add(Calendar.DAY_OF_MONTH, 1 - c.get(Calendar.DAY_OF_WEEK));
		c.add(Calendar.WEEK_OF_MONTH, 1);
		c.add(Calendar.SECOND, -1);
		return STANDARD_TIME_PATTERN.format(c.getTime());
	}

	/**
	 * 某个时间的变化
	 * 
	 * @param date
	 *            日期时间
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @param day
	 *            天
	 * @param hour
	 *            时
	 * @param minute
	 *            分
	 * @param second
	 *            秒
	 * @return 2011-01-01 00:00:00 or 2011-01-31 23:59:59
	 */
	public String date(Date date, int year, int month, int day, int hour, int minute, int second) {
		// DateFormat df = new SimpleDateFormat("yyyyMMdd");
		if (date == null) {
			date = new Date();
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), 1, 0, 0, 0);
		c.add(Calendar.YEAR, year);
		c.add(Calendar.MONTH, month);
		c.add(Calendar.DAY_OF_MONTH, day);
		c.add(Calendar.HOUR_OF_DAY, hour);
		c.add(Calendar.MINUTE, minute);
		c.add(Calendar.SECOND, second);
		return STANDARD_TIME_PATTERN.format(c.getTime());
	}

	/**
	 * 某个时间的变化
	 * 
	 * @param date
	 *            日期时间
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @param day
	 *            天
	 * @param hour
	 *            时
	 * @param minute
	 *            分
	 * @param second
	 *            秒
	 * @return 2011-01-01 00:00:00 or 2011-01-31 23:59:59
	 */
	private String dateday(Date date, int year, int month, int day, int hour, int minute, int second) {
		// DateFormat df = new SimpleDateFormat("yyyyMMdd");
		if (date == null) {
			date = new Date();
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		c.add(Calendar.YEAR, year);
		c.add(Calendar.MONTH, month);
		c.add(Calendar.DAY_OF_MONTH, day);
		c.add(Calendar.HOUR_OF_DAY, hour);
		c.add(Calendar.MINUTE, minute);
		c.add(Calendar.SECOND, second);
		return STANDARD_TIME_PATTERN.format(c.getTime());
	}

	/**
	 * 两个日期之间的全部日期数值数组
	 * 
	 * @param begin
	 * @param end
	 * 			@return{2010-10-10, 2010-10-11, 2010-10-12, ,...,2011-01-11}
	 */
	public String[] dates(String begin, String end) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String[] arr = new String[] { "" };
		try {
			Date db = df.parse(begin);
			Calendar cb = Calendar.getInstance();
			cb.setTime(db);
			Date de = df.parse(end);
			Calendar ce = Calendar.getInstance();
			ce.setTime(de);
			long lday = (ce.getTimeInMillis() - cb.getTimeInMillis()) / (1000 * 60 * 60 * 24);
			int lg = Long.valueOf(lday).intValue();
			arr = new String[lg + 1];
			arr[0] = df.format(cb.getTime());
			for (int i = 0; i < lg; i++) {
				cb.add(Calendar.DAY_OF_YEAR, 1);
				arr[i + 1] = df.format(cb.getTime());
			}
		} catch (ParseException e) {
			logger.error(e);
		}
		return arr;

	}

	/**
	 * 最近12个月
	 * 
	 * @param begin
	 * @param end
	 * @return {2010-02, 2010-03, 2010-04,..,2011-01}
	 */
	public String[] months12() {
		DateFormat df = new SimpleDateFormat("yyyy-MM");
		String[] arr = new String[12];
		Date d = new Date();
		Calendar cb = Calendar.getInstance();
		cb.setTime(d);
		cb.add(Calendar.MONTH, -12);
		for (int i = 0; i < 12; i++) {
			cb.add(Calendar.MONTH, 1);
			arr[i] = df.format(cb.getTime());
		}
		return arr;
	}

	/**
	 * 某年的12个月
	 * 
	 * @param begin
	 * @param end
	 * @return {2010-01, 2010-02, 2010-03, 2010-04,..,2010-12}
	 */
	public String[] months12(int year) {
		DateFormat df = new SimpleDateFormat("yyyy-MM");
		String[] arr = new String[12];
		Calendar cb = Calendar.getInstance();
		if (year == cb.get(Calendar.YEAR)) {
			return months();
		}
		cb.set(year, -1, 1);
		for (int i = 0; i < 12; i++) {
			cb.add(Calendar.MONTH, 1);
			arr[i] = df.format(cb.getTime());
		}
		return arr;
	}

	/**
	 * 从当前年到2008年
	 * 
	 * @param begin
	 * @param end
	 * @return {2011, 2010, 2009, 2008}
	 */
	public String[] years() {
		DateFormat df = new SimpleDateFormat("yyyy");

		Date d = new Date();
		Calendar cb = Calendar.getInstance();
		int cy = cb.get(Calendar.YEAR);
		cb.set(Calendar.YEAR, 2007);
		cy = cy - cb.get(Calendar.YEAR);
		cb.setTime(d);
		cb.add(Calendar.YEAR, 1);
		String[] arr = new String[cy];
		for (int i = 0; i < cy; i++) {
			cb.add(Calendar.YEAR, -1);
			arr[i] = df.format(cb.getTime());
		}
		return arr;
	}

	/**
	 * 获得当前的时间的前第十二个月
	 * 
	 * @return
	 */
	public String get12thMonth() {
		DateFormat df = new SimpleDateFormat("yyyy-MM");
		Date d = new Date();
		GregorianCalendar cb = new GregorianCalendar();
		cb.setTime(d);
		cb.add(Calendar.MONTH, -11);
		return df.format(cb.getTime());
	}

	/**
	 * 获得当前年月
	 * 
	 * @return
	 */
	public String getCurrentMonth() {
		DateFormat df = new SimpleDateFormat("yyyy-MM");
		GregorianCalendar cd = new GregorianCalendar();
		return df.format(cd.getTime());
	}

	/**
	 * 获取当前时间段的中文表达式
	 */
	public String getChExpForNowTime() {
		String zone = "";
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		if (hour >= 0 && hour < 12) {// 0:00:00 - 11:59:59 算上午

			zone = "上午好";
		} else if (hour >= 12 && hour < 18) { // 12:00:00 - 17:59:59 算下午
			zone = "下午好";
		} else {
			// 18:00:00 - 23:59:59 算晚上
			zone = "晚上好";
		}
		return zone;
	}

	/**
	 * 判断给定的日期是星期几
	 */
	public static int dayForWeek(String pDate, String pattern) {

		SimpleDateFormat format = new SimpleDateFormat(pattern);

		format = new SimpleDateFormat(pattern);
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(format.parse(pDate));
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
		int dayForWeek = 0;
		if (c.get(Calendar.DAY_OF_WEEK) == 1) {
			dayForWeek = 7;
		} else {
			dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		}
		return dayForWeek;
	}

	/**
	 * 判断给定的日期是星期几
	 */
	public static int dayForWeek(String pDate) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(format.parse(pDate));
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
		int dayForWeek = 0;
		if (c.get(Calendar.DAY_OF_WEEK) == 1) {
			dayForWeek = 7;
		} else {
			dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		}
		return dayForWeek;
	}

	/**
	 * 判断给定的日期是星期几
	 */
	public static String dayForWeekCN(String pDate) {
		String dayForWeekCN = "";
		int en = dayForWeek(pDate);
		switch (en) {
		case 1:
			dayForWeekCN = "星期一";
			break;
		case 2:
			dayForWeekCN = "星期二";
			break;
		case 3:
			dayForWeekCN = "星期三";
			break;
		case 4:
			dayForWeekCN = "星期四";
			break;
		case 5:
			dayForWeekCN = "星期五";
			break;
		case 6:
			dayForWeekCN = "星期六";
			break;
		case 7:
			dayForWeekCN = "星期日";
			break;
		}
		return dayForWeekCN;
	}

	/**
	 * 判断给定的日期是星期几
	 */
	public static String dayForWeekCN(String pDate, String pattern) {
		String dayForWeekCN = "";
		int en = dayForWeek(pDate, pattern);
		switch (en) {
		case 1:
			dayForWeekCN = "星期一";
			break;
		case 2:
			dayForWeekCN = "星期二";
			break;
		case 3:
			dayForWeekCN = "星期三";
			break;
		case 4:
			dayForWeekCN = "星期四";
			break;
		case 5:
			dayForWeekCN = "星期五";
			break;
		case 6:
			dayForWeekCN = "星期六";
			break;
		case 7:
			dayForWeekCN = "星期日";
			break;
		}
		return dayForWeekCN;
	}

	/**
	 * 根据开始日期、结束日期得到两个时间段内所有的日期
	 * 
	 * @param start
	 *            开始日期
	 * @param end
	 *            结束日期
	 * @param pattern
	 *            日期的格式
	 * @return 两个日期之间的日期
	 */
	public static List<String> getDatesStr(String start, String end, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date dBegin = null;
		Date dEnd = null;
		try {
			dBegin = sdf.parse(start);
			dEnd = sdf.parse(end);
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
		List<String> lDate = new ArrayList<String>();
		lDate.add(start);

		Calendar cal = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		cal.setTime(dBegin);

		boolean bContinue = true;

		while (bContinue) {
			// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
			cal.add(Calendar.DAY_OF_MONTH, 1);
			// 测试此日期是否在指定日期之后
			if (dEnd.after(cal.getTime())) {
				lDate.add(sdf.format(cal.getTime()));
			} else {
				break;
			}
		}
		if (!start.equals(end)) {
			lDate.add(end);
		}
		return lDate;
	}

	/**
	 * 获取当前时间
	 */
	public static String getCurDateTime() {
		return STANDARD_TIME_PATTERN.format(new Date());
	}

	/**
	 * 获取当前日期
	 */
	public static String getCurDate() {
		return STANDARD_DATE_PATTERN.format(new Date());
	}

	/**
	 * 获取当前中文日期
	 */
	public static String getCurChineseDate() {
		return STANDARD_CHINESE_DATE_PATTERN.format(new Date());
	}

	/**
	 * 根据开始小时、结束小时指定间隔分钟的集合，默认24小时制 （开始小时与结束小时采用24小时制的形式输入）
	 * 
	 * @param startHour
	 *            开始小时
	 * @param endHour
	 *            结束小时
	 * @param interval
	 *            间隔分钟
	 * @param type
	 *            小时制（0：12小时制；1：24小时制）
	 * @return List<String>
	 */
	public static List<String> getMinuteIntervalList(String startHour, String endHour, String interval, String type) {
		List<String> strList = new ArrayList<String>();
		GregorianCalendar cal = new GregorianCalendar();
		int endHourInt = Integer.parseInt(endHour);
		cal.setTimeInMillis(System.currentTimeMillis());
		cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(startHour));
		cal.set(Calendar.MINUTE, 0);
		while (endHourInt > cal.get(Calendar.HOUR_OF_DAY)
				|| (endHourInt == cal.get(Calendar.HOUR_OF_DAY) && cal.get(Calendar.MINUTE) == 0)) {
			if (StrUtil.isNull(type) || "1".equals(type)) {
				strList.add(cal.get(Calendar.HOUR_OF_DAY) + ":"
						+ (cal.get(Calendar.MINUTE) == 0 ? "00" : cal.get(Calendar.MINUTE)));
			} else if ("0".equals(type)) {
				strList.add((cal.get(Calendar.HOUR_OF_DAY) > 12 ? cal.get(Calendar.HOUR_OF_DAY) - 12
						: cal.get(Calendar.HOUR_OF_DAY)) + ":"
						+ (cal.get(Calendar.MINUTE) == 0 ? "00" : cal.get(Calendar.MINUTE)));
			}
			cal.add(Calendar.MINUTE, Integer.parseInt(interval));
		}
		return strList;
	}

	public static List<String> getDatesStr(String start, String end) {
		String pattern = "yyyy-MM-dd";
		List<String> lDate = getDatesStr(start, end, pattern);
		return lDate;
	}

	/**
	 * 获取本周及其往后或者往后推几周的日期，从而获取某几周的时间范围
	 * 
	 * @param dateFormat
	 *            日期格式
	 * @param inWeekCount
	 *            周推移数
	 * @return List<String> 日期结果集
	 */
	public static List<String> getDateByWeek(String dateFormat, int inWeekCount) {
		List<String> dateList = new ArrayList<String>();
		String startDate = new DateUtil().getWeekBegin().substring(0, 10);
		String endDate = "";
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = dateFormatter.parse(startDate);
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			if (inWeekCount <= 0) {
				c.add(Calendar.DATE, 7 * inWeekCount - 7);
				endDate = new DateUtil().getWeekEnd().substring(0, 10);
				startDate = dateFormatter.format(c.getTime());
			} else {
				c.add(Calendar.DATE, 7 * inWeekCount - 1);
				endDate = dateFormatter.format(c.getTime());
			}
			startDate = DateTime.doStrDateFormat(startDate, "yyyy-MM-dd", dateFormat);
			endDate = DateTime.doStrDateFormat(endDate, "yyyy-MM-dd", dateFormat);
			dateList = getDatesStr(startDate, endDate, dateFormat);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateList;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(new DateUtil().getWeekBegin() + "==============");
		System.out.println(new DateUtil().getWeekEnd() + "===============");
		/*
		 * String start = "2013-02-01"; String end = "2013-02-06"; List<String> list =
		 * getDatesStr(start, end); for (String str : list) { System.out.println(str); }
		 */

		// System.out.println(DateUtil.dayForWeekCN("2013-11-28"));
		// if(10 > 2){
		//
		// System.out.println(du.getCurrentYearEnd());
		// System.out.println(du.getWeekBegin());
		// System.out.println(du.getWeekEnd());
		// }else{
		// System.out.println(du.getCurYearMin() + "~" + du.getCurYearMax());
		//
		// System.out.println(du.getCurMonthMin() + "~" + du.getCurMonthMax());
		//
		// System.out.println(du.getCurDay());
		//
		//
		// System.out.println(du.getPreviousMonthBegin());
		// System.out.println(du.getPreviousMonthEnd());
		//
		// System.out.println(du.getCurrentSeasonBegin());
		// System.out.println(du.getCurrentSeasonEnd());
		// int m = 0;
		// int t = -3;
		// Date d = new Date();
		// Calendar c = Calendar.getInstance();
		// c.setTime(d);
		// int month = (((c.get(Calendar.MONTH) + t) / 3) + m) * 3;
		// System.out.println(month);
		//
		//
		//
		// String[] arr = du.dates("2010-10-10", "2011-01-11");
		// int lg = arr.length;
		// for(int i=0; i < lg; i++){
		// System.out.println(arr[i]);
		// }
		// arr = du.months12();
		// lg = arr.length;
		// for(int i=0; i < lg; i++){
		// System.out.println(arr[i]);
		// }
		//
		// arr = du.years();
		// lg = arr.length;
		// for(int i=0; i < lg; i++){
		// System.out.println(arr[i]);
		// }
		// arr = du.months12(2011);
		// lg = arr.length;
		// for(int i=0; i < lg; i++){
		// System.out.println(arr[i]);
		// }
		// System.out.println(" -- - -");
		// arr = du.months("2010-10-10", "2011-01-11");
		// lg = arr.length;
		// for(int i=0; i < lg; i++){
		// System.out.println(arr[i]);
		// }
		//
		//
		//
		// System.out.println(du.getTodayBegin());
		// System.out.println(du.getTodayEnd());
		// System.out.println(" -- - ");
		// System.out.println(du.getYesterdayBegin());
		// System.out.println(du.getYesterdayEnd());
		// System.out.println(" -- - ");
		// System.out.println(du.getWeekBegin());
		// System.out.println(du.getWeekEnd());
		//
		// }
		// System.out.println(getCurChineseDate());
		/*
		 * String[] t = new DateUtil().dates("2014-07-01", "2014-08-01"); for (String
		 * string : t) { System.out.println(string); } List<String> lis = new
		 * DateUtil().getDatesStr("2014-07-01", "2014-08-01"); for (String string2 :
		 * lis) { System.out.println("=="+string2); }
		 * System.out.println(dayForWeek("2014-07-12"));
		 * System.out.println(dayForWeek("2014-07-13"));
		 * System.out.println(DateTime.doStrDateFormat("2014-07-01", "yyyy-MM-dd",
		 * "MM月dd日"));
		 */
		// getDateByWeek("yyyy年MM月dd日", -1);
	}
}