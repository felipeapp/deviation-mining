package br.ufrn.ase.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class MigrationUtil {

	private static Properties properties;

	public static String formatDateWithMs(Date date) {
		return date == null ? null : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(date);
	}

	public static Date getDateFromDBTimestamp(Timestamp t) {
		return t == null ? null : new Date(t.getTime());
	}

	public static String getFirstLine(String text) {
		return text == null ? null : text.substring(0, text.indexOf('\n'));
	}

	public static String getProperty(String key) {
		if (properties == null) {
			properties = new Properties();

			try {
				properties.load(new FileInputStream("src/main/resources/database.properties"));
			} catch (FileNotFoundException e) {
				try {
					properties.load(new FileInputStream("database.properties"));
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return properties.getProperty(key);
	}

}
