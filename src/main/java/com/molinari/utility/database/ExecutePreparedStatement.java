package com.molinari.utility.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.function.Function;
import java.util.logging.Level;

import com.molinari.utility.GenericException;
import com.molinari.utility.controller.ControlloreBase;

public abstract class ExecutePreparedStatement<T> {
	public boolean executeUpdate(String sql, T obj) {

		ControlloreBase.getLog().info(() -> "Query in esecuzione: " + sql);

		try {
			return ConnectionPool.getSingleton().useConnection(execute(sql, obj));
		} catch (SQLException e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
			return false;
		}
		
	}
	
	Function<Connection, Boolean> execute(String sql, T obj){
		return cn -> {
			try(final PreparedStatement ps = cn.prepareStatement(sql);){
				doWithPreparedStatement(ps, obj);
				ps.executeUpdate();
				return true;
			} catch (SQLException e) {
				throw new GenericException(e);
			}
		};
	}

	protected abstract void doWithPreparedStatement(PreparedStatement ps, T obj) throws SQLException;
}