package com.f2b.sugar.tools;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Author: wangmin  Date: 13-6-21, 上午15:10
 */
public final class DateTimeUtils {

	private static final GregorianCalendar CALENDAR = new GregorianCalendar();
	private static AtomicReference<DateTimeProvider> PROVIDER = new AtomicReference<DateTimeProvider>(new DefaultDateTimeProvider());

	//
	private static final int MIN_YEAR = 0;
	private static final int MAX_YEAR = 9999;
	private static final int MIN_MONTH = 0;
	private static final int MAX_MONTH = 11;
	private static final int MIN_DAY_OF_MONTH = 1;
	private static final int MAX_DAY_OF_MONTH = 31;
	private static final int DAYS_OF_MONTH[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

	/**
	 *
	 */
	public static Date currentDate() {
		return date(now());
	}

	public static Date currentTime() {
		return now();
	}

	public static long currentTimeInMillis() {
		return now().getTime();
	}

	/**
	 *
	 */
	public static String format(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		sdf.setLenient(false);
		if (date == null) {
			return null;
		}
		return sdf.format(date);
	}

	public static String format(long date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		sdf.setLenient(false);
		return sdf.format(new Date(date));
	}

	public static String formatCurrentDate(String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		sdf.setLenient(false);
		return sdf.format(currentDate());
	}

	public static String formatCurrentTime(String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		sdf.setLenient(false);
		return sdf.format(currentTime());
	}

	public static String lenientlyFormat(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		sdf.setLenient(true);
		return sdf.format(date);
	}

	public static String lenientlyFormat(long date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		sdf.setLenient(true);
		return sdf.format(new Date(date));
	}

	public static String lenientlyFormatCurrentDate(String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		sdf.setLenient(true);
		return sdf.format(currentDate());
	}

	public static String lenientlyFormatCurrentTime(String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		sdf.setLenient(true);
		return sdf.format(currentTime());
	}


	/**
	 *
	 */
	public static Date parse(String pattern, String source) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			sdf.setLenient(false);
			return sdf.parse(source);
		} catch (Exception e) {
			throw new IllegalArgumentException("failed to parse " + source + " by pattern: " + pattern, e);
		}
	}

	public static Date lenientlyParse(String pattern, String source) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			sdf.setLenient(true);
			return sdf.parse(source);
		} catch (Exception e) {
			throw new IllegalArgumentException("failed to parse " + source + " by pattern: " + pattern, e);
		}
	}

	/**
	 *
	 */
	public static boolean isWeekend(Date date) {
		final int dayOfWeek = getDayOfWeek(date);
		return dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY;
	}

	public static boolean isMonday(Date date) {
		final int dayOfWeek = getDayOfWeek(date);
		return dayOfWeek == Calendar.MONDAY;
	}

	public static boolean isLeapYear(Date date) {
		return isLeapYear(getYear(date));
	}

	public static boolean isLeapYear(int year) {
		return CALENDAR.isLeapYear(year);
	}

	public static boolean isSameDay(Date d1, Date d2) {
		//
		assertNotNull(d1, "invalid parameter d1");
		assertNotNull(d2, "invalid parameter d2");

		//
		return date(d1).compareTo(date(d2)) == 0;
	}

	public static boolean isValidDate(int year, int month, int dayOfMonth) {
		//
		if (year < MIN_YEAR || year > MAX_YEAR) {
			return false;
		}
		if (month < MIN_MONTH || month > MAX_MONTH) {
			return false;
		}
		if (dayOfMonth < MIN_DAY_OF_MONTH || dayOfMonth > MAX_DAY_OF_MONTH) {
			return false;
		}

		//
		if (month == 1) {
			if (isLeapYear(year)) {
				if (dayOfMonth > 29) {
					return false;
				}
			} else {
				if (dayOfMonth > 28) {
					return false;
				}
			}
		} else {
			if (dayOfMonth > DAYS_OF_MONTH[month]) {
				return false;
			}
		}

		//
		Date gregorianChange = new GregorianCalendar().getGregorianChange();
		final int gcYear = DateTimeUtils.getYear(gregorianChange); // 1582
		final int gcMonth = DateTimeUtils.getMonth(gregorianChange); // Calendar.OCTOBER
		if (year == gcYear && month == gcMonth && (dayOfMonth >= 5 && dayOfMonth <= 14)) {
			return false;
		}

		//
		return true;
	}

	/**
	 *
	 */
	public static Date date(Date date) {
		//
		assertNotNull(date, "invalid parameter date");

		//
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	public static java.sql.Date toSqlDate(Date date) {
		if (date == null) {
			return null;
		} else {
			return new java.sql.Date(date.getTime());
		}
	}

	public static Date valueOf(java.sql.Date date) {
		if (date == null) {
			return null;
		} else {
			return new Date(date.getTime());
		}
	}

	public static java.sql.Timestamp toTimestamp(Date date) {
		if (date == null) {
			return null;
		} else {
			return new java.sql.Timestamp(date.getTime());
		}
	}

	public static Date valueOf(java.sql.Timestamp timestamp) {
		if (timestamp == null) {
			return null;
		} else {
			return new Date(timestamp.getTime());
		}
	}

	public static Date valueOf(long milliSeconds) {
		return new Date(milliSeconds);
	}

	public static Date valueOf(int year, int month, int dayOfMonth) {
		// Precondition checking
		if (!isValidDate(year, month, dayOfMonth)) {
			throw new IllegalArgumentException("invalid parameters, year: " + year + ", month: " + month + ", dayOfMonth: " + dayOfMonth);
		}

		//
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month);
		c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	/**
	 *
	 */
	public static int getYear(Date date) {
		//
		assertNotNull(date, "invalid parameter date");

		//
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.YEAR);
	}

	public static int getMonth(Date date) {
		//
		assertNotNull(date, "invalid parameter date");

		//
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MONTH);
	}

	public static int getDayOfMonth(Date date) {
		//
		assertNotNull(date, "invalid parameter date");

		//
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DAY_OF_MONTH);
	}

	public static int getDayOfWeek(Date date) {
		//
		assertNotNull(date, "invalid parameter date");

		//
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DAY_OF_WEEK);
	}

	public static int getHourOfDay(Date date) {
		//
		assertNotNull(date, "invalid parameter date");

		//
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.HOUR_OF_DAY);
	}

	public static int getMinute(Date date) {
		//
		assertNotNull(date, "invalid parameter date");

		//
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MINUTE);
	}

	public static int getSecond(Date date) {
		//
		assertNotNull(date, "invalid parameter date");

		//
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.SECOND);
	}

	public static int getMilliSecond(Date date) {
		//
		assertNotNull(date, "invalid parameter date");

		//
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MILLISECOND);
	}

	public static int getLastDayOfMonth(Date date) {
		//
		assertNotNull(date, "invalid parameter date");

		//
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	public static Date getFirstDateOfMonth(Date date) {
		//
		assertNotNull(date, "invalid parameter date");

		//
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, 1);
		return c.getTime();
	}

	public static Date getLastDateOfMonth(Date date) {
		//
		assertNotNull(date, "invalid parameter date");

		//
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, getLastDayOfMonth(date));
		return c.getTime();
	}

	public static int getLastDayOfWeek(Date date) {
		//
		assertNotNull(date, "invalid parameter date");

		//
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.getActualMaximum(Calendar.DAY_OF_WEEK);
	}

	public static Date getFirstDateOfWeek(Date date) {
		//
		assertNotNull(date, "invalid parameter date");

		//
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, 1);
		return c.getTime();
	}

	public static Date getLastDateOfWeek(Date date) {
		//
		assertNotNull(date, "invalid parameter date");

		//
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, getLastDayOfWeek(date));
		return c.getTime();
	}

	/**
	 *
	 */
	public static Date addYear(Date date, int delta) {
		//
		assertNotNull(date, "invalid parameter date");

		//
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.YEAR, delta);
		return c.getTime();
	}

	public static Date addMonth(Date date, int delta) {
		//
		assertNotNull(date, "invalid parameter date");

		//
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, delta);
		return c.getTime();
	}

	public static Date addDayOfMonth(Date date, int delta) {
		//
		assertNotNull(date, "invalid parameter date");

		//
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, delta);
		return c.getTime();
	}

	public static Date addWeekOfYear(Date date, int delta) {
		//
		assertNotNull(date, "invalid parameter date");

		//
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.WEEK_OF_YEAR, delta);
		return c.getTime();
	}

	public static Date addHourOfDay(Date date, int delta) {
		//
		assertNotNull(date, "invalid parameter date");

		//
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.HOUR_OF_DAY, delta);
		return c.getTime();
	}

	public static Date addMinute(Date date, int delta) {
		//
		assertNotNull(date, "invalid parameter date");

		//
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MINUTE, delta);
		return c.getTime();
	}

	public static Date addSecond(Date date, int delta) {
		//
		assertNotNull(date, "invalid parameter date");

		//
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.SECOND, delta);
		return c.getTime();
	}

	public static Date addMilliSecond(Date date, int delta) {
		//
		assertNotNull(date, "invalid parameter date");

		//
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MILLISECOND, delta);
		return c.getTime();
	}


	/**
	 *
	 */
	public static DateTimeProvider getProvider() {
		return PROVIDER.get();
	}

	public static void setProvider(DateTimeProvider provider) {
		PROVIDER.set(provider);
	}

	/**
	 *
	 */
	private static Date now() {
		return getProvider().now();
	}

	private static void assertNotNull(Date date, String message) {
		if (date == null) {
			throw new IllegalArgumentException(message);
		}
	}

	public static long getDiffTime(Date fromDate, Date toDate) throws Exception {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d1 = df.parse(df.format(fromDate));
		Date d2 = df.parse(df.format(toDate));
		long diff = d2.getTime() - d1.getTime();
		return diff;
	}

	public static long getDiffSecondsTime(Date fromDate, Date toDate) {
		if (fromDate == null || toDate == null) {
			return 0;
		}
		long fromDateLong = fromDate.getTime();
		long toDateLong = toDate.getTime();

		return fromDateLong - toDateLong;
	}

	public static Date getPlusDate(Date date, long delta) throws Exception {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = df.parse(df.format(date));
		long plus = d.getTime() + delta;
		Date finalDate = new Date(plus);
		return finalDate;
	}

	/**
	 *
	 */
	public static interface DateTimeProvider {

		Date now();
	}

	public static class DefaultDateTimeProvider implements DateTimeProvider {

		public Date now() {
			return new Date();
		}
	}

	/**
	 * 计算两个日期间隔天数
	 *
	 * @param beginDate 日期
	 * @param endDate   日期
	 * @return long 间隔天数（可为负数）
	 */
	public static long getDifferDaysWithTwoDate(Date beginDate, Date endDate) {
		if (beginDate == null || endDate == null) {
			return 0;
		}
		long begin = beginDate.getTime();
		long end = endDate.getTime();
		long inter = end - begin;
		//转为绝对值
		if (inter < 0) {
			inter = inter * (-1);
		}
		long dateMillSec = 24 * 60 * 60 * 1000;
		long dateCnt = inter / dateMillSec;
		long remainder = inter % dateMillSec;
		if (remainder != 0) {
			dateCnt++;
		}
		return dateCnt;
	}

	/**
	 * 格式化日期为字符串（yyyy-MM-dd）
	 *
	 * @param date date日期
	 * @return string 字符串
	 */
	public static final String formatDateToStringWithOnlyDate(Date date) {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		sdf.setLenient(false);
		if (date == null) {
			return null;
		}
		return sdf.format(date);
	}

	/**
	 * 格式化日期为字符串
	 *
	 * @param date date日期
	 * @return string 字符串
	 */
	public static final String formatDateToStringWithOnlyDate(Date date, String pattern) {
		if (pattern == null) pattern = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		sdf.setLenient(false);
		if (date == null) {
			return null;
		}
		return sdf.format(date);
	}

	/**
	 * 取开始日期与结束日期中所有的日期的集合
	 *
	 * @param startDate 开始日期
	 * @param endDate   结束日期
	 * @return 开始日期与结束日期中所有的日期
	 */
	public static List<String> getDates(Date startDate, Date endDate) {
		Calendar calstart = Calendar.getInstance();
		Calendar calend = Calendar.getInstance();
		calstart.setTime(startDate);
		calend.setTime(endDate);
		// 设置时间为0时
		calstart.set(Calendar.HOUR_OF_DAY, 0);
		calstart.set(Calendar.MINUTE, 0);
		calstart.set(Calendar.SECOND, 0);
		calend.set(Calendar.HOUR_OF_DAY, 0);
		calend.set(Calendar.MINUTE, 0);
		calend.set(Calendar.SECOND, 0);
		long start = calstart.getTimeInMillis();
		long end = calend.getTimeInMillis();
		List<String> date = new ArrayList<String>();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		do {
			Date time = new Date(start);
			Date nowDate = new Date();
			boolean flag = time.before(nowDate);
			if (flag) {
				date.add(df.format(time));
			}
		} while ((start += 1000 * 60 * 60 * 24) <= end);
		return date;
	}

	public static List<String> getRangeMonths(Date startDate, Date endDate) {

		String pattern = "yyyy-M";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String pattern2 = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf2 = new SimpleDateFormat(pattern2);

		Calendar cursorCal = Calendar.getInstance();
		cursorCal.setTime(startDate);
		cursorCal.set(Calendar.DATE, 1);
		cursorCal.set(Calendar.HOUR, 0);
		cursorCal.set(Calendar.MINUTE, 0);
		cursorCal.set(Calendar.SECOND, 0);

		Calendar endCal = Calendar.getInstance();
		endCal.setTime(endDate);
		endCal.add(Calendar.SECOND, 1);
		endDate = endCal.getTime();

		List<String> rangeMonths = new ArrayList<String>();

		while (endDate.after(cursorCal.getTime())) {
			//logger.info(sdf2.format(cursorCal.getTime()));
			rangeMonths.add(sdf.format(cursorCal.getTime()));
			cursorCal.add(Calendar.MONTH, 1);
		}

		return rangeMonths;
	}

	/**
	 * 取当天（包含当天）之前一周
	 *
	 * @param date
	 * @return
	 */
	public static String lastWeek(Date date) {
		int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(date));
		int month = Integer.parseInt(new SimpleDateFormat("MM").format(date));
		int day = Integer.parseInt(new SimpleDateFormat("dd").format(date)) - 6;

		if (day < 1) {
			month -= 1;
			if (month == 0) {
				year -= 1;
				month = 12;
			}
			if (month == 4 || month == 6 || month == 9 || month == 11) {
				day = 30 + day;
			} else if (month == 1 || month == 3 || month == 5 || month == 7
					|| month == 8 || month == 10 || month == 12) {
				day = 31 + day;
			} else if (month == 2) {
				if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0))
					day = 29 + day;
				else
					day = 28 + day;
			}
		}
		String y = year + "";
		String m = "";
		String d = "";
		if (month < 10)
			m = "0" + month;
		else
			m = month + "";
		if (day < 10)
			d = "0" + day;
		else
			d = day + "";

		return y + "-" + m + "-" + d;
	}

	/**
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static List<String> getEveryday(String startDate, String endDate) {
		SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd");
		Date start = new Date();
		Date end = new Date();
		try {
			if (null != startDate && !"".equals(startDate) && null != endDate && !"".equals(endDate)) {
				start = dformat.parse(startDate);
				end = dformat.parse(endDate);
			} else if (null != startDate && !"".equals(startDate) || null == endDate && "".equals(endDate)) {
				Calendar theCa = Calendar.getInstance();
				theCa.setTime(dformat.parse(startDate));
				theCa.add(theCa.DATE, +30);
				Date date = theCa.getTime();
				start = dformat.parse(startDate);
				end = dformat.parse(dformat.format(date.getTime()));
			} else if (null == startDate && "".equals(startDate) || null != endDate && !"".equals(endDate)) {
				Calendar theCa = Calendar.getInstance();
				theCa.setTime(dformat.parse(endDate));
				theCa.add(theCa.DATE, -30);
				Date date = theCa.getTime();
				start = dformat.parse(dformat.format(date.getTime()));
				end = dformat.parse(endDate);
			} else {
				start = dformat.parse(DateTimeUtils.lastWeek(new Date()));
				end = new Date();
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return DateTimeUtils.getDates(start, end);
	}


	/**
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static List<String> getEveryYear(String startDate, String endDate) {
		SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM");
		String start = null;
		String end = null;
		try {
			if (null != startDate && !"".equals(startDate) && null != endDate && !"".equals(endDate)) {
				return DateTimeUtils.getAllMonths(startDate, endDate);
			} else if (null != startDate && !"".equals(startDate) || null == endDate && "".equals(endDate)) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(dformat.parse(startDate));
				calendar.add(Calendar.MONTH, +24);
				Date date = calendar.getTime();
				start = startDate;
				end = dformat.format(date);
			} else if (null == startDate && "".equals(startDate) || null != endDate && !"".equals(endDate)) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(dformat.parse(endDate));
				calendar.add(Calendar.MONTH, -24);
				Date date = calendar.getTime();
				start = dformat.format(date);
				end = endDate;
			} else {
				start = DateTimeUtils.getBeforeYearMonth(dformat.format(new Date()));
				end = dformat.format(new Date());
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return DateTimeUtils.getAllMonths(start, end);
	}

	public static String getBeforeYearMonth(String startDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(sdf.parse(startDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		calendar.add(Calendar.MONTH, -6);
		Date date = calendar.getTime();
		return sdf.format(date);
	}

	public static List<String> getAllMonths(String start, String end) {
		List<String> list = new ArrayList<String>();
		String splitSign = "-";
		String regex = "\\d{4}" + splitSign + "(([0][1-9])|([1][012]))"; // 判断YYYY-MM时间格式的正则表达式
		if (!start.matches(regex) || !end.matches(regex)) {
			return list;
		}
		String temp = start; // 从最小月份开始
		while (temp.compareTo(start) >= 0 && temp.compareTo(end) < 0) {
			list.add(temp); // 首先加上最小月份,接着计算下一个月份
			String[] arr = temp.split(splitSign);
			int year = Integer.valueOf(arr[0]);
			int month = Integer.valueOf(arr[1]) + 1;
			if (month > 12) {
				month = 1;
				year++;
			}
			if (month < 10) {// 补0操作
				temp = year + splitSign + "0" + month;
			} else {
				temp = year + splitSign + month;
			}
		}
		list.add(end);
		return list;
	}


	/**
	 * 格式化日期为字符串（yyyy-MM-dd HH:mm:ss）,带时间
	 *
	 * @param date date日期
	 * @return string 字符串
	 */
	public static final String formatDateToStringWithTime(Date date) {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		sdf.setLenient(false);
		if (date == null) {
			return null;
		}
		return sdf.format(date);
	}

    public static final String formatDateToString(Date date) {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        sdf.setLenient(false);
        if (date == null) {
            return null;
        }
        return sdf.format(date);
    }

	public static final String formatDateToStringHH(Date date) {
		String pattern = "HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		sdf.setLenient(false);
		if (date == null) {
			return null;
		}
		return sdf.format(date);
	}

	public static final Integer getBirthdayYearByAge(Integer age) {

		if (age == null) {
			return null;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.YEAR, -age);

		return cal.get(Calendar.YEAR);
	}

	public static int getBetweenDays(String t1, String t2) throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		int betweenDays = 0;
		Date d1 = format.parse(t1);
		Date d2 = format.parse(t2);
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(d1);
		c2.setTime(d2);
		// 保证第二个时间一定大于第一个时间
		if (c1.after(c2)) {
			c1 = c2;
			c2.setTime(d1);
		}
		int betweenYears = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
		betweenDays = c2.get(Calendar.DAY_OF_YEAR)
				- c1.get(Calendar.DAY_OF_YEAR);
		for (int i = 0; i < betweenYears; i++) {
			c1.set(Calendar.YEAR, (c1.get(Calendar.YEAR) + 1));
			betweenDays += c1.getMaximum(Calendar.DAY_OF_YEAR);
		}
		return betweenDays;
	}

	public static Date getBeginOfDay(Date date){

		Calendar cursorCal = Calendar.getInstance();
		cursorCal.setTime(date);

		cursorCal.set(Calendar.HOUR_OF_DAY, 0);
		cursorCal.set(Calendar.MINUTE, 0);
		cursorCal.set(Calendar.SECOND, 0);

		return cursorCal.getTime();
	}


	public static Date getEndOfDay(Date date){

		Calendar cursorCal = Calendar.getInstance();
		cursorCal.setTime(date);

		cursorCal.set(Calendar.HOUR, 23);
		cursorCal.set(Calendar.MINUTE, 59);
		cursorCal.set(Calendar.SECOND, 59);

		return cursorCal.getTime();
	}
}
