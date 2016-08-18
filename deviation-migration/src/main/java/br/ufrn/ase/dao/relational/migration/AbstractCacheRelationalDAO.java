package br.ufrn.ase.dao.relational.migration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import br.ufrn.ase.util.SettingsUtil;

public abstract class AbstractCacheRelationalDAO {
	protected static Connection connection;

	protected AbstractCacheRelationalDAO() {
		try {
			if (connection == null) {
				String db = SettingsUtil.getProperty("cache_db");
				String host = SettingsUtil.getProperty("cache_host");
				String port = SettingsUtil.getProperty("cache_port");
				String user = SettingsUtil.getProperty("cache_user");
				String pwd = SettingsUtil.getProperty("cache_password");

				String url = "jdbc:postgresql://" + host + ":" + port + "/" + db;

				connection = DriverManager.getConnection(url, user, pwd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
