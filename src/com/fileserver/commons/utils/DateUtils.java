package com.fileserver.commons.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 日期工具包
 * 2015年8月31日 下午6:18:10
 * @author zhouyi
 */
public final class DateUtils {

	public static final long SECOND = 1000;

	public static final long MINUTE = SECOND * 60;

	public static final long HOUR = MINUTE * 60;

	public static final long DAY = HOUR * 24;

	public static final long WEEK = DAY * 7;

	public static final long YEAR = DAY * 365;

	public static final String FOMTER_TIMES = "yyyy-MM-dd HH:mm:ss";
	public static final String FOMTER_DATE = "yyyy-MM-dd";
	private static Log log = LogFactory.getLog(DateUtils.class);

	private static final Map<Integer, String> WEEK_DAY = new HashMap<Integer, String>();
	static {
		WEEK_DAY.put(7, "星期六");
		WEEK_DAY.put(1, "星期天");
		WEEK_DAY.put(2, "星期一");
		WEEK_DAY.put(3, "星期二");
		WEEK_DAY.put(4, "星期三");
		WEEK_DAY.put(5, "星期四");
		WEEK_DAY.put(6, "星期五");
	}

	/**
	 * 解析日期
	 * 
	 * @param date
	 *            日期字符串
	 * @param pattern
	 *            日期格式
	 * @return
	 */
	public static Date parse(String date, String pattern) {
		Date resultDate = null;
		try {
			resultDate = new SimpleDateFormat(pattern).parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return resultDate;
	}

	/**
	 * 解析日期 yyyy-MM-dd
	 * 
	 * @param date
	 *            日期字符串
	 * @param pattern
	 *            日期格式
	 * @return
	 */
	public static Timestamp parseSimple(String date) {
		Date result = null;
		try {
			DateFormat yyyyMMdd = new SimpleDateFormat(FOMTER_DATE);
			result = yyyyMMdd.parse(date);
		} catch (ParseException e) {
			log.error(e);
		}
		return result != null ? new Timestamp(result.getTime()) : null;
	}

	/**
	 * 解析日期字符串
	 * 
	 * @param date
	 * @return
	 */
	public static Timestamp parseFull(String date) {
		Date result = null;
		try {
			DateFormat yyyyMMddHHmmss = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			result = yyyyMMddHHmmss.parse(date);
		} catch (ParseException e) {
			log.error(e);
		}
		return result != null ? new Timestamp(result.getTime()) : null;
	}

	/**
	 * 解析日期 yyyy-MM-dd
	 * 
	 * @param date
	 *            日期字符串
	 * @return
	 */
	public static Timestamp parse(String date) {
		if (StringUtils.isEmpty(date))
			return null;
		try {
			if (date.length() == 10) {
				return parseSimple(date);
			} else if (date.length() == 8) {
				DateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
				Date d = yyyyMMdd.parse(date);
				return new Timestamp(d.getTime());
			} else if (date.length() == 19) {
				return parseFull(date);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 格式化日期字符串
	 * 
	 * @param date
	 *            日期
	 * @param pattern
	 *            日期格式
	 * @return
	 */
	public static String format(Date date, String pattern) {
		if (date == null) {
			return null;
		}
		DateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}

	/**
	 * 格式化日期
	 * 
	 * @param date
	 * @return yyyy年MM月dd日
	 */
	public static String formatCHS(Date date) {
		if (date == null) {
			return null;
		}
		DateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
		return format.format(date);
	}

	/**
	 * 格式化日期字符串
	 * 
	 * @param date
	 *            日期
	 * @return
	 */
	public static String format(Date date) {
		if (date == null) {
			return null;
		}
		DateFormat YYYY_MM_DD = new SimpleDateFormat(FOMTER_DATE);
		return YYYY_MM_DD.format(date);
	}

	/**
	 * 格式化日期
	 * 
	 * @param date
	 * @return
	 */
	public static String formatFull(Date date) {
		DateFormat YYYY_MM_DD_HH_MM_SS = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		return YYYY_MM_DD_HH_MM_SS.format(date);
	}

	/**
	 * 取得当前日期
	 * 
	 * @return
	 */
	public static Timestamp getNow() {
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * 取得当前日期
	 * 
	 * @return
	 */
	public static Integer getNowYear() {
		return getYear(getNow());
	}

	/**
	 * 取得当前月份
	 * 
	 * @return
	 */
	public static Integer getNowMonth() {
		return getMonth(getNow());
	}

	/**
	 * 取得年度
	 * 
	 * @param value
	 * @return
	 */
	public static Integer getYear(Object value) {
		Calendar c = Calendar.getInstance();
		Date date = getDate(value);
		c.setTime(date);
		return c.get(Calendar.YEAR);
	}

	/**
	 * 取得月份
	 * 
	 * @param value
	 * @return
	 */
	public static Integer getMonth(Object value) {
		Calendar c = Calendar.getInstance();
		Date date = getDate(value);
		c.setTime(date);
		return c.get(Calendar.MONTH) + 1;
	}

	/**
	 * 取得日
	 * 
	 * @param value
	 * @return
	 */
	public static Integer getDay(Object value) {
		Calendar c = Calendar.getInstance();
		Date date = getDate(value);
		c.setTime(date);
		return c.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 取得日期对象
	 * 
	 * @param value
	 * @return
	 */
	private static Date getDate(Object value) {
		Date date = null;
		if (value instanceof Date) {
			date = (Date) value;
		} else {
			date = parse((String) value);
		}
		if (date == null) {
			throw new RuntimeException("日期格式解析错误!date=" + value);
		}
		return date;
	}

	/**
	 * @param offsetYear
	 * @return 当前时间 + offsetYear
	 */
	public static Timestamp getNowExpiredYear(int offsetYear) {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.YEAR, offsetYear);
		return new Timestamp(now.getTime().getTime());
	}

	/**
	 * @param offset
	 * @return 当前时间 + offsetMonth
	 */
	public static Timestamp getNowExpiredMonth(int offset) {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.MONTH, offset);
		return new Timestamp(now.getTime().getTime());
	}

	/**
	 * @param offset
	 * @return 当前时间 + offsetDay
	 */
	public static Timestamp getNowExpiredDay(int offset) {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.DATE, offset);
		return new Timestamp(now.getTime().getTime());
	}

	/**
	 * @param offset
	 * @return 当前时间 + offsetDay
	 */
	public static Timestamp getNowExpiredHour(int offset) {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.HOUR, offset);
		return new Timestamp(now.getTime().getTime());
	}

	/**
	 * @param offsetSecond
	 * @return 当前时间 + offsetSecond
	 */
	public static Timestamp getNowExpiredSecond(int offsetSecond) {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.SECOND, offsetSecond);
		return new Timestamp(now.getTime().getTime());
	}

	/**
	 * @param offset
	 * @return 当前时间 + offset
	 */
	public static Timestamp getNowExpiredMinute(int offset) {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.MINUTE, offset);
		return new Timestamp(now.getTime().getTime());
	}

	/**
	 * @param offset
	 * @return 指定时间 + offsetDay
	 */
	public static Timestamp getExpiredDay(Date givenDate, int offset) {
		Calendar date = Calendar.getInstance();
		date.setTime(givenDate);
		date.add(Calendar.DATE, offset);
		return new Timestamp(date.getTime().getTime());
	}

	/**
	 * 实现ORACLE中ADD_MONTHS函数功能
	 * 
	 * @param offset
	 * @return 指定时间 + offsetMonth
	 */
	public static Timestamp getExpiredMonth(Date givenDate, int offset) {
		Calendar date = Calendar.getInstance();
		date.setTime(givenDate);
		date.add(Calendar.MONTH, offset);
		return new Timestamp(date.getTime().getTime());
	}

	/**
	 * @param second
	 * @return 指定时间 + offsetSecond
	 */
	public static Timestamp getExpiredSecond(Date givenDate, int second) {
		Calendar date = Calendar.getInstance();
		date.setTime(givenDate);
		date.add(Calendar.SECOND, second);
		return new Timestamp(date.getTime().getTime());
	}

	/**
	 * 根据日期取得日历
	 * 
	 * @param date
	 * @return
	 */
	public static Calendar getCalendar(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c;
	}

	/**
	 * @param offsetSecond
	 * @return 指定时间 + offsetSecond
	 */
	public static Timestamp getExpiredHour(Date givenDate, int offsetHour) {
		Calendar date = Calendar.getInstance();
		date.setTime(givenDate);
		date.add(Calendar.HOUR, offsetHour);
		return new Timestamp(date.getTime().getTime());
	}

	/**
	 * @return 给出指定日期的月份的第一天
	 */
	public static Date getMonthFirstDay(Date givenDate) {
		Date date = DateUtils.parse(DateUtils.format(givenDate, "yyyy-MM"),
				"yyyy-MM");
		return date;
	}

	/**
	 * 取得当前是周几？
	 * 
	 * @param givenDate
	 * @return
	 */
	public static int getDayOfWeek(Date givenDate) {
		Calendar c = Calendar.getInstance();
		c.setTime(givenDate);
		int day = c.get(Calendar.DAY_OF_WEEK);
		return day;
	}

	/**
	 * 取得中文星期？
	 * 
	 * @param dayOfWeek
	 * @return
	 */
	public static String getChineseDayOfWeek(int dayOfWeek) {
		return WEEK_DAY.get(dayOfWeek);
	}

	/**
	 * 给定日期是否在范围内
	 * 
	 * @param date
	 *            给定日期
	 * @param begin
	 *            开始日期
	 * @param end
	 *            结束日期
	 * @return true 在指定范围内
	 */
	public static Boolean between(Date date, Date begin, Date end) {
		if (date == null || begin == null || end == null) {
			return true;
		}
		return date.after(begin) && date.before(end);
	}

	/**
	 * 当前日期是否在范围内
	 * 
	 * @param begin
	 *            开始日期
	 * @param end
	 *            结束日期
	 * @return true 在指定范围内
	 */
	public static Boolean between(Date begin, Date end) {
		Date now = getNow();
		return between(now, begin, end);
	}

	/**
	 * 取得今天零点日期
	 * 
	 * @return
	 */
	public static Calendar getTodayZero() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c;
	}

	/**
	 * <p>
	 * Parses a string representing a date by trying a variety of different
	 * parsers.
	 * </p>
	 * 
	 * <p>
	 * The parse will try each parse pattern in turn. A parse is only deemed
	 * sucessful if it parses the whole of the input string. If no parse
	 * patterns match, a ParseException is thrown.
	 * </p>
	 * 
	 * @param str
	 *            the date to parse, not null
	 * @param parsePatterns
	 *            the date format patterns to use, see SimpleDateFormat, not
	 *            null
	 * @return the parsed date
	 * @throws IllegalArgumentException
	 *             if the date string or pattern array is null
	 * @throws ParseException
	 *             if none of the date patterns were suitable
	 */
	public static Date parseDate(String str, String[] parsePatterns)
			throws ParseException {
		if (str == null || parsePatterns == null) {
			throw new IllegalArgumentException(
					"Date and Patterns must not be null");
		}
		SimpleDateFormat parser = null;
		ParsePosition pos = new ParsePosition(0);
		for (int i = 0; i < parsePatterns.length; i++) {
			if (i == 0) {
				parser = new SimpleDateFormat(parsePatterns[0]);
			} else {
				parser.applyPattern(parsePatterns[i]);
			}
			pos.setIndex(0);
			Date date = parser.parse(str, pos);
			if (date != null && pos.getIndex() == str.length()) {
				return date;
			}
		}
		throw new ParseException("Unable to parse the date: " + str, -1);
	}

	/**
	 * 取得java.sql.Date
	 * 
	 * @param value
	 * @return
	 */
	public static java.sql.Date getSqlDate(Object value) {
		if (value == null) {
			return null;
		}
		if (value instanceof Date) {
			return new java.sql.Date(((Date) value).getTime());
		} else if (value instanceof String) {
			Timestamp date = parse((String) value);
			return date != null ? new java.sql.Date(date.getTime()) : null;
		}
		return null;
	}

	/**
	 * 取得系统日期变量
	 * 
	 * @return
	 */
	public static Map<String, Object> getDateVariableMap() {
		Map<String, Object> dateMap = new HashMap<String, Object>();
		Date now = getNow();
		dateMap.put("now", now);// 当前时间
		dateMap.put("year", getYear(now));// 年
		dateMap.put("month", getMonth(now));// 月
		dateMap.put("day", getDay(now));// 日
		dateMap.put("simple", format(now));// 简单日期
		dateMap.put("full", formatFull(now));// 全日期
		dateMap.put("chs", formatCHS(now));// 中文日期
		dateMap.put("month_next", getExpiredMonth(now, 1));// 下月
		dateMap.put("month_pre", getExpiredMonth(now, -1));// 上月
		dateMap.put("quarter_next", getExpiredMonth(now, 3));// 下季度
		dateMap.put("quarter_pre", getExpiredMonth(now, -3));// 上季度
		dateMap.put("year_next", getExpiredMonth(now, 12));// 明年
		dateMap.put("year_pre", getExpiredMonth(now, -12));// 今年
		dateMap.put("day_next", getExpiredDay(now, 1));// 明天
		dateMap.put("day_pre", getExpiredDay(now, -1));// 昨天
		int month = DateUtils.getMonth(now);
		int year = DateUtils.getYear(now);
		int yearjs = (month > 9 ? year + 1 : year);
		dateMap.put("yearjs", yearjs);// 计生年度
		dateMap.put("yearjs_pre", yearjs - 1);// 计生年度去年
		dateMap.put("yearjs_next", yearjs + 1);// 计生年度明年
		return dateMap;
	}
	
	/**
	 * 日期是否正确,格式必须为“YYYY-MM-DD” 
	 * @param sDate
	 * @return
	 */
	public static boolean isValidDate(String sDate) {
	     String datePattern1 = "\\d{4}-\\d{2}-\\d{2}";
	     String datePattern2 = "^((\\d{2}(([02468][048])|([13579][26]))"
	             + "[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|"
	             + "(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?"
	             + "((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?("
	             + "(((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?"
	             + "((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
	     if ((sDate != null)) {
	         Pattern pattern = Pattern.compile(datePattern1);
	         Matcher match = pattern.matcher(sDate);
	         if (match.matches()) {
	             pattern = Pattern.compile(datePattern2);
	             match = pattern.matcher(sDate);
	             return match.matches();
	         }
	         else {
	             return false;
	         }
	     }
	     return false;
	}

	public static void main(String[] args) {
		System.out.println(getYear(new Date()));
		System.out.println(getMonth(new Date()));
	}
	
	/**
	 * 将例如：Mon Mar 02 13:57:49 CST 2015，转成日期对象
	 * 2015年10月24日 下午6:15:20
	 * @return 返回date类型
	 * @author zhouyi
	 */
	public static Date getObjectDate(String value) {
		try {
			SimpleDateFormat sdf1 = new SimpleDateFormat ("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
			return sdf1.parse(value);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}