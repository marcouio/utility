package com.molinari.utility.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
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
	
	/**
	 * Metodo per select semplici su una singola tabella, in cui estrarre tutti i campi, e filtrate in base 
	 * alle clausole where passate come parametro
	 * 
	 * @param tabella
	 * @param clausole
	 * @return
	 * @throws SQLException 
	 * @
	 */
	public ResultSet select(final String tabella, final List<Clausola> clausole) throws SQLException {
		final HashMap<String, String> mapTable = new HashMap<>();
		mapTable.put(tabella, tabella);
		return select(mapTable, null, clausole, null);
	}
	
	/**
	 * Metodo select, da per scontato che gli oggetti che costruiscono la query siano stati riempiti precedentemente, 
	 * quindi non richiede parametri
	 * @return
	 * @throws SQLException 
	 * @
	 */
	public ResultSet select(SelectBase selectBase) throws SQLException {
		return resultSetfromIstruzione(selectBase.getQuery());
	}
	
	/**
	 * Metodo per chiamare query complesse. Permette di inserire tutti i parametri.
	 * @param tabelle, tutte le tabelle su cui costruire il cursore
	 * @param campi, campi da estrarre
	 * @param clausole, classiche clausole 'where' con coppia campo - valore
	 * @param joins, oggetti join per collegare le diverse tabelle
	 * @return
	 * @throws SQLException 
	 * @
	 */
	public ResultSet select(final Map<String, String> tabelle, 
							final Map<String, String> campi, 
							final List<Clausola> clausole, 
							final List<Join> joins) throws SQLException {
		
		final SelectBase selectBase = new SelectBase();
		return resultSetfromIstruzione(selectBase.getQuery(tabelle, campi, clausole, joins));
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

	public ResultSet resultSetfromIstruzione(final String comandoSql) throws SQLException  {
		final Connection connection = ConnectionPool.getSingleton().getConnection();
		return ConnectionPool.getSingleton().getResulSet(connection, comandoSql);
	}
	
	public ResultSetMetaData metaDataFromIstruzione(final String comandoSql) throws SQLException  {
		final ResultSet rs = resultSetfromIstruzione(comandoSql);
		return rs.getMetaData();
	}
}
