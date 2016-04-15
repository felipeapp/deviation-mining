package br.ufrn.ase.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public abstract class SettingsUtil {

	private static Properties properties = new Properties();

	public static String getProperty(String key) {
		if (properties.isEmpty()) {
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

	public static void setProperty(String key, String value) {
		properties.setProperty(key, value);
	}

}
