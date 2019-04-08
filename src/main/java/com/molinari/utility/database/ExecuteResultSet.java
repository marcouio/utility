package com.molinari.utility.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.molinari.utility.controller.ControlloreBase;

public class ExecuteResultSet<T> {

	private String sql;

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public T execute(String sql) throws SQLException {
		final ConnectionPool connectionPool = ConnectionPool.getSingleton();
		ControlloreBase.getLog().info(() -> "Query in esecuzione: " + sql);
		try(
			final Connection cn = connectionPool.getConnection();
			final ResultSet resulSet = connectionPool.getResulSet(cn, sql);
			){
			return doWithResultSet(resulSet);
		} 
	}
	
	public List<T> execute(ElaborateResultSet<T> elabRS) throws SQLException {
		final ConnectionPool connectionPool = ConnectionPool.getSingleton();
		ControlloreBase.getLog().info(() -> "Query in esecuzione: " + sql);
		try (
				final Connection cn = connectionPool.getConnection();
				final ResultSet resulSet = connectionPool.getResulSet(cn, sql);
			)
			{
			return doWithResultSet(elabRS, resulSet);
		}
	}

	protected List<T> doWithResultSet(ElaborateResultSet<T> elabRS, ResultSet resultSet) throws SQLException {
		return elabRS.elaborate(resultSet);
	}
	
	protected T doWithResultSet(ResultSet resultSet) throws SQLException {
		return null;
	}

	@FunctionalInterface
	public interface ElaborateResultSet<T>{
		List<T> elaborate(ResultSet resultSet);
	}
}