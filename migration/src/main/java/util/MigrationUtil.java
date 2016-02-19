package util;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class MigrationUtil {

	public static String formatDateWithMs(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(date);
	}

}
