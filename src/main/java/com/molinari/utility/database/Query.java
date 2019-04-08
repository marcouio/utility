package com.molinari.utility.database;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class Query {
	
	public boolean update(UpdateBase updateBase) throws SQLException {
		return eseguiSqlFromString(updateBase.getUpdateQuery());
	}

	public boolean update(final String tabella, final Map<String, String> campi, List<Clausola> clausole) throws SQLException {
		final UpdateBase updateBase = new UpdateBase();
		return eseguiSqlFromString(updateBase.getUpdateQuery(tabella, campi, clausole));
	}
	
	public boolean insert(InsertBase insertBase) throws SQLException {
		return eseguiSqlFromString(insertBase.getInsertQuery());
	}

	public boolean insert(final String tabella, final Map<String, String> campi) throws SQLException {
		final InsertBase insertBase = new InsertBase();
		return eseguiSqlFromString(insertBase.getInsertQuery(tabella, campi));
		
	}
	
	public boolean delete(final DeleteBase deleteBase) throws SQLException {
		return eseguiSqlFromString(deleteBase.getDeleteQuery());
	}
	
	
	public boolean delete(final String tabella, final List<Clausola> clausole) throws SQLException {
		final DeleteBase deleteBase = new DeleteBase();
		return eseguiSqlFromString(deleteBase.getDeleteQuery(tabella, clausole));
	}
	
	
	public boolean eseguiSqlFromString(final String comandoSql) throws SQLException {
		return ConnectionPool.getSingleton().executeUpdate(comandoSql) != 0;
	}
}
