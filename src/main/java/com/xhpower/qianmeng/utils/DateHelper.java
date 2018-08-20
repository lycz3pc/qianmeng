package com.xhpower.qianmeng.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
*	日期格式工具
* @author jerryi
*/
public final class DateHelper {
	
	/**
	 * 日期格式化
	 * yyyy-MM-dd
	 * @Version 
	 * @param date
	 * @return
	 */
	public static final String formatDate(Date date) {
		return format(date, "yyyy-MM-dd");
	}
	
	/**
	 * 时间格式化
	 * yyyy-MM-dd HH:mm:ss
	 * @Version 
	 * @param date
	 * @return
	 */
	public static final String formatTime(Date date) {
		return format(date, "yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 格式化Date对象
	 * @Version 
	 * @param date
	 * @param format 需要格式化的格式
	 * @return
	 */
	public static final String format(Date date, String format) {
		SimpleDateFormat format2 = new SimpleDateFormat(format);
		return format2.format(date);
	}
	
	/**
	 * 字符串转Date对象
	 * yyyy-MM-dd
	 * @Version 
	 * @param date
	 * @return 错误时返回null
	 */
	public static final Date parseDate(String date) {
		return parse(date, "yyyy-MM-dd");
	}
	
	/**
	 * 字符串转Date对象
	 * yyyy-MM-dd HH:mm:ss
	 * @Version 
	 * @param date
	 * @return 错误时返回null
	 */
	public static final Date parseTime(String date) {
		return parse(date, "yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 字符串转Date对象
	 * @Version 
	 * @param date 转化的字符串
	 * @param format 需要转化的时间格式
	 * @return 错误时返回null
	 */
	public static final Date parse(String date, String format) {
		SimpleDateFormat format2 = new SimpleDateFormat(format);
		try {
			return format2.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}
	
	/**
	 * 取得当前日期是多少周
	 * 
	 * @param date
	 * @return
	 */
	public static int getWeekOfYear(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setMinimalDaysInFirstWeek(7);
		c.setTime(date);
		return c.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 得到某一年周的�?�?
	 * 
	 * @param year
	 * @return
	 */
	public static int getMaxWeekNumOfYear(int year) {
		Calendar c = new GregorianCalendar();
		c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);

		return getWeekOfYear(c.getTime());
	}

	/**
	 * 得到某年某周的第�?��
	 * 
	 * @param year
	 * @param week
	 * @return
	 */
	public static Date getFirstDayOfWeek(int year, int week) {
		Calendar c = new GregorianCalendar();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, Calendar.JANUARY);
		c.set(Calendar.DATE, 1);

		Calendar cal = (GregorianCalendar) c.clone();
		cal.add(Calendar.DATE, week * 7);

		return getFirstDayOfWeek(cal.getTime());
	}

	/**
	 * 得到某年某周的最后一�?
	 * 
	 * @param year
	 * @param week
	 * @return
	 */
	public static Date getLastDayOfWeek(int year, int week) {
		Calendar c = new GregorianCalendar();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, Calendar.JANUARY);
		c.set(Calendar.DATE, 1);

		Calendar cal = (GregorianCalendar) c.clone();
		cal.add(Calendar.DATE, week * 7);

		return getLastDayOfWeek(cal.getTime());
	}

	/**
	 * 取得当前日期�?��周的第一�?
	 * 
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfWeek(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
		return c.getTime();
	}

	/**
	 * 取得当前日期�?��周的�?���?��
	 * 
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfWeek(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
		return c.getTime();
	}

	/**
	 * 取得今天是本周第几天 以周�?��第一�?
	 * 
	 * @int
	 */
	public static int getNumofWeek() {
		// 取得当前系统时间
		Calendar c = Calendar.getInstance();
		// -2�?
		c.add(Calendar.DAY_OF_MONTH, -2);
		// 取得当前是这周的第几�?
		int temp = c.get(Calendar.DAY_OF_WEEK);
		// 由于java计算是以周日为第�?��
		return temp;
		// if (temp == 1) {
		// // 当是第一天时返回周日
		// return 7;
		// } else {
		// // 其他情况直接返回数字-1
		// return temp;
		// }
	}

	/**
	 * 得到几天前的时间
	 * 
	 * @param d
	 * @param day
	 * @return
	 */
	public static Date getDateBefore(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
		return now.getTime();
	}

	/**
	 * 得到几天后的时间
	 * 
	 * @param d
	 * @param day
	 * @return
	 */
	public static Date getDateAfter(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		return now.getTime();
	}

	/**
	 * 得到某年1�?�?
	 * 
	 * @param year
	 * @param week
	 * @return
	 */
	public static Date getFirstDayOfThisYear() {
		Calendar c = new GregorianCalendar();
		Calendar now = Calendar.getInstance();
		c.set(Calendar.YEAR, now.get(Calendar.YEAR));
		c.set(Calendar.MONTH, Calendar.JANUARY);
		c.set(Calendar.DATE, 1);
		Calendar cal = (GregorianCalendar) c.clone();
		return getFirstDayOfWeek(cal.getTime());
	}
	
	/**
	 * 取得当前月份的第�?��
	 * 
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfMonth(int Month) {
		Calendar c = new GregorianCalendar();
		Calendar now = Calendar.getInstance();
		c.set(Calendar.YEAR, now.get(Calendar.YEAR));
		c.set(Calendar.MONDAY, Month-1);
		c.set(Calendar.DAY_OF_MONTH, 1);
		return c.getTime();
	}
	/**
	 * 取得当前月份的最后一�?
	 * 
	 * @param date
	 * @return
	 */
	public static Date getLatDayOfMonth(int Month) {
		Calendar c = new GregorianCalendar();
		Calendar now = Calendar.getInstance();
		c.set(Calendar.YEAR, now.get(Calendar.YEAR));
		c.set(Calendar.MONTH, Month);
		c.set(Calendar.DAY_OF_MONTH, 1);
		return getDateBefore(c.getTime(), 1);
	}
	/**
	 * 取得当前月份
	 * 
	 * @param date
	 * @return
	 */
	public static int getNowMonth() {
		Calendar now = Calendar.getInstance();
		now.setTime(new Date());
		return now.get(Calendar.MONTH);
	}
	/**
	 * 取得当前月份的最后一�?
	 * 
	 * @param date
	 * @return
	 */
	public static Date getMonth() {
		Calendar c = new GregorianCalendar();
		Calendar now = Calendar.getInstance();
		c.set(Calendar.YEAR, now.get(Calendar.YEAR));
		c.set(Calendar.MONTH, now.get(Calendar.MONTH));
		c.set(Calendar.DAY_OF_MONTH, 1);
		return c.getTime();
	}
	public static Date getMonth(int i) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MONTH, i-1);
		return c.getTime();
	}
	
	public static final String getTwresulttime() {
		Date date=new Date();
		SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		String formatDate=time.format(date);
		return formatDate;
	}
	
	public static String  getcurrentDatetime(String style) {
		SimpleDateFormat   formatter=new   SimpleDateFormat(style);   
        Date   currentTime  =   new   Date();   
        String   currentDatetime   =   formatter.format(currentTime);
        return currentDatetime;
	}
	
	public static Date String2Date(String dateString,String style ){
		Date date = new Date() ;
        SimpleDateFormat strToDate = new SimpleDateFormat (style);
        // parse format String to date
        try {
			date = strToDate.parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return date;
	}
	
	public static Timestamp String2Timestamp(String dateString,String style ){
        SimpleDateFormat df = new SimpleDateFormat (style);
        String time = df.format(String2Date(dateString, style));
        Timestamp ts = Timestamp.valueOf(time);
        return ts;
	}
	
	public static Date getDate(int yeay,int month,int day){
		Calendar c = new GregorianCalendar();
		c.set(Calendar.YEAR, yeay);
		c.set(Calendar.MONTH, month-1);
		c.set(Calendar.DAY_OF_MONTH,day);
		return c.getTime();
	}
	
	/** 
     * 得到两个日期相差的天数 
     */  
    public static int getBetweenDay(Date date1, Date date2) {  
        Calendar d1 = new GregorianCalendar();  
        d1.setTime(date1);  
        Calendar d2 = new GregorianCalendar();  
        d2.setTime(date2);  
        int days = d2.get(Calendar.DAY_OF_YEAR)- d1.get(Calendar.DAY_OF_YEAR);  
        int y2 = d2.get(Calendar.YEAR);  
        if (d1.get(Calendar.YEAR) != y2) {  
//          d1 = (Calendar) d1.clone();  
            do {  
                days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);  
                d1.add(Calendar.YEAR, 1);  
            } while (d1.get(Calendar.YEAR) != y2);  
        }  
        return days;  
    } 
    
    /**
     * 获取当前时间与传入时间的天数差
     * @Version 
     * @param date1
     * @return
     */
    public static int getBetweenDay(Date date1) {
    	return getBetweenDay(date1, new Date());
    }
    
    /**
	 * 获取的时间
	 * (去除时分秒数据)
	 * @Version 
	 * @param date
	 * @return
	 */
	public static Date getDayDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND , 0);
		calendar.set(Calendar.MILLISECOND , 0);
		return calendar.getTime();
	}
	
	/**
	 * 对比当前时间与传入时间是否在范围内
	 * @Version 
	 * @param end 结束时间
	 * @param range 范围(毫秒)
	 * @return
	 */
	public static boolean contrastCurrentRange(Date start, long range) {
		return contrastRange(start, new Date(), range);
	}
	/**
	 * 对比开始时间与结束时间是否在范围内
	 * @Version 
	 * @param start 开始时间
	 * @param end 结束时间
	 * @param range 范围(毫秒)
	 * @return
	 */
	public static boolean contrastRange(Date start, Date end, long range) {
		return end.getTime() - start.getTime() < range;
	}

}
