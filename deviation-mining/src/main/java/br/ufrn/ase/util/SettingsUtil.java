package br.ufrn.ase.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public abstract class SettingsUtil {

	private static Properties properties;

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
