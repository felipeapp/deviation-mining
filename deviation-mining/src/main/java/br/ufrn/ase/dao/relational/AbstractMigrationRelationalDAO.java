package br.ufrn.ase.dao.relational;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMigrationRelationalDAO<T> extends AbstractBasicRelationalDAO {

	protected abstract T getAttributesFromRS(ResultSet rs) throws SQLException;

	protected abstract String getTableName();

	protected abstract String getPKFieldName();

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
