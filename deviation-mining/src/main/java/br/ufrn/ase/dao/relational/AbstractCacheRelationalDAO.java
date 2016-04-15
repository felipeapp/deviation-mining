package br.ufrn.ase.dao.relational;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import br.ufrn.ase.util.MigrationUtil;

public abstract class AbstractCacheRelationalDAO {
	protected static Connection connection;

	protected AbstractCacheRelationalDAO() {
		try {
			if (connection == null) {
				String db = MigrationUtil.getProperty("cache_db");
				String host = MigrationUtil.getProperty("cache_host");
				String port = MigrationUtil.getProperty("cache_port");
				String user = MigrationUtil.getProperty("cache_user");
				String pwd = MigrationUtil.getProperty("cache_password");

				String url = "jdbc:postgresql://" + host + ":" + port + "/" + db;

				connection = DriverManager.getConnection(url, user, pwd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
