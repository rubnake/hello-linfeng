package util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UtilTools {
	public static String dateFormat(Date date,String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}
}
