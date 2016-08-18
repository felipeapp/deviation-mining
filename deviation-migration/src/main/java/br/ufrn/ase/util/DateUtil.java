package br.ufrn.ase.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Manipulation of Dates.
 * 
 * @autor felipe
 * @author jadson - jadsonjs@gmail.com
 *
 */
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

	/**
	 * Verify if the data is inside a period
	 * @param time
	 * @param initialTime
	 * @param finalTime
	 * @return
	 */
	public static boolean isBetweenPeriod(LocalDateTime time, LocalDateTime initialTime, LocalDateTime finalTime){
		if(time == null || initialTime == null || finalTime == null )
			return false;
		return time.isAfter(initialTime) && time.isBefore(finalTime);
	}
	
	/**
	 * Convert Date to LocalDataTime of Java8
	 * @param date
	 * @return
	 */
	public static LocalDateTime toLocalDateTime(Date date) {
		if(date == null) return null;
		return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
	}
	
	/**
	 * Convert Date to LocalDataTime of Java8
	 * @param date
	 * @return
	 */
	public static Date toDate(LocalDateTime localDateTime) {
		if(localDateTime == null) return null;
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}
	
	/**
	 * return the next interval until the limit
	 * 
	 * @param INTERVAL in minutes
	 * @param initialTime
	 * @param finalTime
	 * @return
	 */
	public static LocalDateTime getNextInterval(final int INTERVAL, LocalDateTime time, LocalDateTime limit) {
		return time.plusMinutes(INTERVAL).compareTo(limit) < 0 ?  time.plusMinutes(INTERVAL) : limit;
	}

}
