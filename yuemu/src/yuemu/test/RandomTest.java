package yuemu.test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import yuemu.core.ProjectException;

public class RandomTest {
	
	private final static DateFormat format = new SimpleDateFormat("yyyyMMdd");
	
	/**时间随机函数, 时间格式是"yyyyMMdd", 如"19910202"
	 * 
	 * @param from
	 * @param end
	 * @return
	 */
	public static long randomDate(String from, String end) {
		try {
			long fromTime = format.parse(from).getTime();
			long toTime = format.parse(end).getTime();
			return (long)(Math.random() * (toTime - fromTime) + fromTime);
		} catch (ParseException e) {
			throw new ProjectException(e);
		}
	}
	
	public static String showTime(long time) {
		String expression = format.format(new Date(time));
		return expression;
	}
	
}
