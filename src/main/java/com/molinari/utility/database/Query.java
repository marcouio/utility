package com.molinari.utility.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import com.molinari.utility.controller.ControlloreBase;

public class Query {
	
	private static final String AND = " AND ";

	public boolean update(UpdateBase updateBase) throws SQLException {
		return eseguiSqlFromString(updateBase.getUpdateQuery());
	}

	public boolean update(final String tabella, final Map<String, String> campi, List<Clausola> clausole) throws SQLException {
		UpdateBase updateBase = new UpdateBase();
		return eseguiSqlFromString(updateBase.getUpdateQuery(tabella, campi, clausole));
	}
	
	public boolean insert(InsertBase insertBase) throws SQLException {
		return eseguiSqlFromString(insertBase.getInsertQuery());
	}

	public boolean insert(final String tabella, final Map<String, String> campi) throws SQLException {
		InsertBase insertBase = new InsertBase();
		return eseguiSqlFromString(insertBase.getInsertQuery(tabella, campi));
		
	}
	
	/**
	 * Metodo per select semplici su una singola tabella, in cui estrarre tutti i campi, e filtrate in base 
	 * alle clausole where passate come parametro
	 * 
	 * @param tabella
	 * @param clausole
	 * @return
	 * @
	 */
	public ResultSet select(final String tabella, final List<Clausola> clausole) {
		HashMap<String, String> mapTable = new HashMap<>();
		mapTable.put(tabella, tabella);
		return select(tabella, clausole);
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
		
		SelectBase selectBase = new SelectBase();
		return resultSetfromIstruzione(selectBase.getQuery(tabelle, campi, clausole, joins));
	}
	
	public boolean delete(final DeleteBase deleteBase) throws SQLException {
		return eseguiSqlFromString(deleteBase.getDeleteQuery());
	}
	
	
	public boolean delete(final String tabella, final List<Clausola> clausole) throws SQLException {
		DeleteBase deleteBase = new DeleteBase();
		return eseguiSqlFromString(deleteBase.getDeleteQuery(tabella, clausole));
	}
	
	
	public boolean eseguiSqlFromString(final String comandoSql) throws SQLException {
		if (ConnectionPool.getSingleton().executeUpdate(comandoSql) != 0) {
			return true;
		}
		return false;

	}

	public ResultSet resultSetfromIstruzione(final String comandoSql) throws SQLException  {
		Connection connection = ConnectionPool.getSingleton().getConnection();
		return ConnectionPool.getSingleton().getResulSet(connection, comandoSql);
	}
	
	public ResultSetMetaData metaDataFromIstruzione(final String comandoSql) throws SQLException  {
		final ResultSet rs = resultSetfromIstruzione(comandoSql);
		return rs.getMetaData();
	}

	/**
	 * 
	 * Esegue un'istruzione SQL specificando come parametri il comando, la
	 * tabella, i campi di riferimento e clausole where. Non permette funzioni
	 * complesse.
	 * 
	 * @param comando
	 * @param tabella
	 * @param campi
	 * @param clausole
	 * @return boolean
	 */
	public boolean eseguiIstruzioneSql(final String comando, final String tabella,
			final Map<String, String> campi, final Map<String, String> clausole) {
		
		Connection cn = null;
		boolean ok = false;
		try {
			cn = ConnectionPool.getSingleton().getConnection();
			ok = false;
			final StringBuilder sql = new StringBuilder();

			if (tabella != null && comando != null) {
				final String command = comando.toUpperCase();
				// comando
				if ("INSERT".equals(command)) {
					ok = appendInsert(tabella, campi, cn, ok, sql, command);
				} else if ("UPDATE".equals(command)) {
					ok = appendUpdate(tabella, campi, clausole, cn, ok, sql, command);
				} else if ("DELETE".equals(command)) {
					ok = appendDelete(tabella, campi, clausole, cn, ok, sql, command);

				} else if ("SELECT".equals(command)) {
					appendSelect(campi, clausole, sql, command);
				}
			}

		} catch (final Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		} finally {
			if(cn != null){
				try {
					ConnectionPool.getSingleton().chiudiOggettiDb(cn);
				} catch (Exception e) {
					ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
				} 
			}
		}
		return ok;
	}

	private boolean appendInsert(final String tabella, final Map<String, String> campi, Connection cn, boolean ok,
			final StringBuilder sql, final String command) throws SQLException {
		
		sql.append(command).append(" INTO ").append(tabella);
		sql.append("(");
		final Iterator<String> iterInsert = campi.keySet().iterator();

		while (iterInsert.hasNext()) {
			final String prossimo = iterInsert.next();
			// aggiunge nome colonna
			sql.append(prossimo);
			if (iterInsert.hasNext())
				sql.append(", ");
		}
		sql.append(") ").append(" VALUES (");
		final Iterator<String> iterInsert2 = campi.keySet().iterator();
		while (iterInsert2.hasNext()) {
			final String prossimo = iterInsert2.next();
			try {
				sql.append(Integer.parseInt(campi.get(prossimo)));
			} catch (final NumberFormatException e) {
				sql.append("'" + campi.get(prossimo) + "'");
			}
			if (iterInsert2.hasNext())
				sql.append(", ");
		}

		sql.append(")");
		return executeUpdate(cn, ok, sql);
	}

	private boolean appendUpdate(final String tabella, final Map<String, String> campi,
			final Map<String, String> clausole, Connection cn, boolean ok, final StringBuilder sql,
			final String command) throws SQLException {
		final Iterator<String> iterUpdate = campi.keySet().iterator();
		sql.append(command).append(" " + tabella).append(" SET ");
		while (iterUpdate.hasNext()) {
			final String prossimo = iterUpdate.next();
			sql.append(prossimo).append(" = ");
			appendCampo(campi, campi, sql, prossimo);
			appendVirgola(sql, iterUpdate);
		}
		if (!clausole.isEmpty()) {
			sql.append(" WHERE 1=1");
			final Iterator<String> where = clausole.keySet().iterator();

			while (where.hasNext()) {
				sql.append(AND);
				final String prossimo = where.next();
				sql.append(prossimo).append(" = ");
				appendCampo(campi, clausole, sql, prossimo);
				
				appendVirgola(sql, where);
			}
		}
		return executeUpdate(cn, ok, sql);
	}

	private void appendVirgola(final StringBuilder sql, final Iterator<String> where) {
		if (where.hasNext()){
			sql.append(", ");
		}
	}

	private void appendCampo(final Map<String, String> campi, final Map<String, String> clausole,
			final StringBuilder sql, final String prossimo) {
		try {
			
			if (campi.get(prossimo).contains(".")) {
				sql.append(Double.parseDouble(clausole.get(prossimo)));
			} else
				sql.append(Integer.parseInt(clausole.get(prossimo)));
		} catch (final NumberFormatException e) {
			sql.append("'" + clausole.get(prossimo) + "'");
		}
	}

	private boolean appendDelete(final String tabella, final Map<String, String> campi,
			final Map<String, String> clausole, Connection cn, boolean ok, final StringBuilder sql,
			final String command) throws SQLException {
		boolean result = ok;
		sql.append(command).append(" FROM ").append(tabella);
		if (!clausole.isEmpty()) {
			sql.append(" WHERE 1=1");
			final Iterator<String> where = clausole.keySet().iterator();
			while (where.hasNext()) {
				sql.append(AND);

				final String prossimo = where.next();
				sql.append(prossimo).append(" = ");

				appendCampo(campi, clausole, sql, prossimo);
			}
			if (where.hasNext())
				sql.append(", ");
			cn.close();
			result = executeUpdate(cn, result, sql);
		}
		return result;
	}

	private boolean executeUpdate(Connection cn, boolean ok, final StringBuilder sql) throws SQLException {
		boolean result = ok;
		final Statement st = createStatemt(cn);
		if (st.executeUpdate(sql.toString()) != 0){
			result = true;
		}
		cn.close();
		return result;
	}

	private void appendSelect(final Map<String, String> campi, final Map<String, String> clausole,
			final StringBuilder sql, final String command) {
		sql.append(command);
		if (campi == null)
			sql.append(" * ");
		else {
			final Iterator<String> iterSelect = clausole.keySet().iterator();
			while (iterSelect.hasNext()) {
				final String prossimo = iterSelect.next();
				sql.append(" " + prossimo);
			}
			appendVirgola(sql, iterSelect);

		}
		if (!clausole.isEmpty()) {
			sql.append("WHERE 1=1");
			final Iterator<String> where = clausole.keySet().iterator();
			while (where.hasNext()) {
				sql.append(AND);
				final String prossimo = where.next();
				sql.append(prossimo).append(" = ");
				try {
					sql.append(Integer.parseInt(clausole.get(prossimo)));
				} catch (final NumberFormatException e) {
					sql.append("'" + clausole.get(prossimo) + "'");
				}
			}
		}
	}

	private Statement createStatemt(Connection cn) throws SQLException {
		return cn.createStatement();
	}

}
