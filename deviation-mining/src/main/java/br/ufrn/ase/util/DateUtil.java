package br.ufrn.ase.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class DateUtil {

	public static String formatDateWithMs(Date date) {
		return date == null ? null : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(date);
	}

	public static Date getDateFromDBTimestamp(Timestamp t) {
		return t == null ? null : new Date(t.getTime());
	}

	public static Timestamp getDBTimestampFromDate(Date d) {
		return d == null ? null : new Timestamp(d.getTime());
	}

}
