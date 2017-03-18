package main.java.com.molinari.utility.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;

import main.java.com.molinari.utility.controller.ControlloreBase;

public abstract class ExecutePreparedStatement<T> {
	public boolean executeUpdate(String sql, T obj){
		
		final Connection cn = ConnectionPool.getSingleton().getConnection();
		try{
			
			ControlloreBase.getLog().info("Query in esecuzione: " + sql);
			
			PreparedStatement ps = cn.prepareStatement(sql);
			doWithPreparedStatement(ps, obj);
			ps.executeUpdate();
			return true;
		}catch (SQLException e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
			return false;
		}finally{
			ConnectionPool.getSingleton().chiudiOggettiDb(cn);
		}
	}
	
	protected abstract void doWithPreparedStatement(PreparedStatement ps, T obj) throws SQLException;
}