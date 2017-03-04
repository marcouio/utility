package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import controller.ControlloreBase;

public abstract class ExecuteResultSet<T> {


	public T execute(String sql) throws SQLException {
		ConnectionPool connectionPool = ConnectionPool.getSingleton();
		final Connection cn = connectionPool.getConnection();
		try {

			ControlloreBase.getLog().info("Query in esecuzione: " + sql);

			ResultSet resulSet = connectionPool.getResulSet(cn, sql);
			return doWithResultSet(resulSet);
		} finally {
			connectionPool.chiudiOggettiDb(cn);
		}
	}

	protected abstract T doWithResultSet(ResultSet resulSet) throws SQLException;

}