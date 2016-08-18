package br.ufrn.ase.dao.relational.migration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import br.ufrn.ase.util.SettingsUtil;

public abstract class AbstractBasicRelationalDAO {
	protected static Connection connection;

	protected AbstractBasicRelationalDAO() {
		try {
			if (connection == null) {
				String db = SettingsUtil.getProperty("sql_db");
				String host = SettingsUtil.getProperty("sql_host");
				String port = SettingsUtil.getProperty("sql_port");
				String user = SettingsUtil.getProperty("sql_user");
				String pwd = SettingsUtil.getProperty("sql_password");

				String url = "jdbc:postgresql://" + host + ":" + port + "/" + db;

				connection = DriverManager.getConnection(url, user, pwd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
