package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.MigrationUtil;

public abstract class AbstractDAO<T> {

	protected static Connection connection;

	protected abstract T getAttributesFromRS(ResultSet rs) throws SQLException;

	protected abstract String getTableName();

	protected abstract String getPKFieldName();

	public AbstractDAO() {
		try {
			if (connection == null) {
				String db = MigrationUtil.getProperty("sql_db");
				String host = MigrationUtil.getProperty("sql_host");
				String port = MigrationUtil.getProperty("sql_port");
				String user = MigrationUtil.getProperty("sql_user");
				String pwd = MigrationUtil.getProperty("sql_password");

				connection = DriverManager.getConnection("jdbc:postgresql://" + host + ":" + port + "/" + db, user,
						pwd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected List<T> findByIdEntrada(int id_entrada) {
		List<T> logs = new ArrayList<T>();

		try {
			PreparedStatement stmt = connection.prepareStatement(
					"select * from " + getTableName() + " where id_registro_entrada = ? order by " + getPKFieldName());

			stmt.setInt(1, id_entrada);

			ResultSet rs = stmt.executeQuery();

			while (rs.next())
				logs.add(getAttributesFromRS(rs));

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return logs;
	}

}
