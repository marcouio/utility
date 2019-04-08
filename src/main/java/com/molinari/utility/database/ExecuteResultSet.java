package com.molinari.utility.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Function;

import com.molinari.utility.GenericException;
import com.molinari.utility.controller.ControlloreBase;

public class ExecuteResultSet<T> {

	public T execute(String sql) throws SQLException {

		ControlloreBase.getLog().info(() -> "Query in esecuzione: " + sql);

		try {
			return ConnectionPool.getSingleton().useConnection(cn -> {
				try (final ResultSet resulSet = ConnectionPool.getSingleton().getResulSet(cn, sql);) {
					return doWithResultSet(resulSet);
				} catch (SQLException e1) {
					throw new GenericException(e1);
				}
			});
		} catch (GenericException e) {
			throw new SQLException(e);
		}
	}

	public T execute(Function<ResultSet, T> elabRS, String sql) throws SQLException {

		ControlloreBase.getLog().info(() -> "Query in esecuzione: " + sql);

		try {
			return ConnectionPool.getSingleton().useConnection(cn -> {
				try (final ResultSet resulSet = ConnectionPool.getSingleton().getResulSet(cn, sql);) {
					return elabRS.apply(resulSet);
				} catch (SQLException e) {
					throw new GenericException(e);
				}
			});

		} catch (GenericException e) {
			throw new SQLException(e);
		}
	}

	public List<T> executeList(ElaborateResultSet<T> elabRS, String sql) throws SQLException {
		ControlloreBase.getLog().info(() -> "Query in esecuzione: " + sql);
		try {
			return ConnectionPool.getSingleton().useConnection(cn -> {
				try (final ResultSet resulSet = ConnectionPool.getSingleton().getResulSet(cn, sql);) {
					List<T> elaborate = elabRS.elaborate(resulSet);
					return elaborate;
				} catch (Exception e) {
					throw new GenericException(e);
				}
			});
		} catch (GenericException e) {
			throw new SQLException(e);
		}
	}

	protected T doWithResultSet(ResultSet resultSet) throws SQLException {
		return null;
	}

	@FunctionalInterface
	public interface ElaborateResultSet<T> {
		List<T> elaborate(ResultSet resultSet);
	}
}