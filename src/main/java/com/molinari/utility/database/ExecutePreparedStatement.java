package com.molinari.utility.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;

import com.molinari.utility.controller.ControlloreBase;

public abstract class ExecutePreparedStatement<T> {
	public boolean executeUpdate(String sql, T obj){

		ControlloreBase.getLog().info(() -> "Query in esecuzione: " + sql);
		
		try(
				final Connection cn = ConnectionPool.getSingleton().getConnection();
				final PreparedStatement ps = cn.prepareStatement(sql);
				
			){
			doWithPreparedStatement(ps, obj);
			ps.executeUpdate();
			return true;
		}catch (final SQLException e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
			return false;
		}
	}
	
	protected abstract void doWithPreparedStatement(PreparedStatement ps, T obj) throws SQLException;
}