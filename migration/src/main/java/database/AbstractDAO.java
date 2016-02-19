package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class AbstractDAO {

	protected Connection connection;

	public AbstractDAO() {
		try {
			connection = DriverManager.getConnection("jdbc:postgresql://localhost/sistemas_log", "sistemas_log",
					"sistemas_log");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
