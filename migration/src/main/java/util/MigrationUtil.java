package util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class MigrationUtil {

	public static String formatDateWithMs(Date date) {
		return date == null ? null : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(date);
	}

	public static Date getDateFromDBTimestamp(Timestamp t) {
		return t == null ? null : new Date(t.getTime());
	}

	public static String getFirstLine(String text) {
		return text == null ? null : text.substring(0, text.indexOf('\n'));
	}

}
