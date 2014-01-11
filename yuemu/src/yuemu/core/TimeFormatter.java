package yuemu.core;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeFormatter {
	
	private static final DateFormat dateFormat = 
			new SimpleDateFormat("yyyyMMdd");
	
	private static final DateFormat dateTimeFormat = 
			new SimpleDateFormat("yyyyMMdd hh:mm:ss");
	
	/**形如: yyyyMMdd
	 * 
	 * @return
	 */
	public static long getDate(String time) {
		try {
			return dateFormat.parse(time).getTime();
		} catch (ParseException e) {
			throw new ProjectException(e);
		}
	}
	
	/**形如: yyyyMMdd hh:MM:ss
	 * 
	 * @return
	 */
	public static long getDateTime(String time) {
		try {
			return dateTimeFormat.parse(time).getTime();
		} catch (ParseException e) {
			throw new ProjectException(e);
		}
	}
	
	public static String getDateTimeString(long time) {
		return dateTimeFormat.format(new Date(time));
	}
	
}
