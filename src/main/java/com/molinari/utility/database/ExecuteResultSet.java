package com.molinari.utility.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.molinari.utility.controller.ControlloreBase;

public class ExecuteResultSet<T> {

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
	
	public List<T> execute(ElaborateResultSet<T> elabRS, String sql) throws SQLException {
		final ConnectionPool connectionPool = ConnectionPool.getSingleton();
		ControlloreBase.getLog().info(() -> "Query in esecuzione: " + sql);
		try (
				final Connection cn = connectionPool.getConnection();
				final ResultSet resulSet = connectionPool.getResulSet(cn, sql);
			){
			return elabRS.elaborate(resulSet);
		}
	}

	protected T doWithResultSet(ResultSet resultSet) throws SQLException {
		return null;
	}

	@FunctionalInterface
	public interface ElaborateResultSet<T>{
		List<T> elaborate(ResultSet resultSet);
	}
}