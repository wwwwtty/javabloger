package org.hsc.novelSpider.dao.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**时间格式，解释
 * @author Administrator
 *
 */
public class DateFormat {
	private static final Logger log=LoggerFactory.getLogger(DateFormat.class);
	 /**yyyy-MM-dd HH:mm:ss*/
	public static final SimpleDateFormat DATETIME_FORMAT   = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	 /**yyyy-MM-dd*/
	public static final SimpleDateFormat DATE_FORMAT   = new SimpleDateFormat("yyyy-MM-dd");

	public final static String format(Date date){
		return DATETIME_FORMAT.format(date);
	}
	
	
	
	
	public final static Date parse(String source){
		try {
			return DATETIME_FORMAT.parse(source);
		} catch (ParseException e) {
			log.error(e.getMessage(),e);
			return null;
		}
	}
	
	public final static Date parse(String date,SimpleDateFormat format){
		try {
			return format.parse(date);
		} catch (ParseException e) {
			log.error("格式化时间【"+date+"】异常！",e);
			return null;
		}
	}
}
