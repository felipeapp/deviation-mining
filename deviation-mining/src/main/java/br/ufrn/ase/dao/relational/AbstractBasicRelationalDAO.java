package br.ufrn.ase.dao.relational;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import br.ufrn.ase.util.MigrationUtil;

public abstract class AbstractBasicRelationalDAO {
	protected static Connection connection;

	protected AbstractBasicRelationalDAO() {
		try {
			if (connection == null) {
				String db = MigrationUtil.getProperty("sql_db");
				String host = MigrationUtil.getProperty("sql_host");
				String port = MigrationUtil.getProperty("sql_port");
				String user = MigrationUtil.getProperty("sql_user");
				String pwd = MigrationUtil.getProperty("sql_password");

				String url = "jdbc:postgresql://" + host + ":" + port + "/" + db;

				connection = DriverManager.getConnection(url, user, pwd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
