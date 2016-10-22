package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Query {
	
	public boolean update(UpdateBase updateBase) throws Exception{
		return eseguiSqlFromString(updateBase.getUpdateQuery());
	}

	public boolean update(final String tabella, final HashMap<String, String> campi, ArrayList<Clausola> clausole) throws Exception{
		UpdateBase updateBase = new UpdateBase();
		return eseguiSqlFromString(updateBase.getUpdateQuery(tabella, campi, clausole));
	}
	
	public boolean insert(InsertBase insertBase) throws Exception{
		return eseguiSqlFromString(insertBase.getInsertQuery());
	}

	public boolean insert(final String tabella, final HashMap<String, String> campi) throws Exception{
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
	 * @throws Exception
	 */
	public ResultSet select(final String tabella, final ArrayList<Clausola> clausole) throws Exception{
		HashMap<String, String> mapTable = new HashMap<String, String>();
		mapTable.put(tabella, tabella);
		return select(tabella, clausole);
	}
	
	/**
	 * Metodo select, da per scontato che gli oggetti che costruiscono la query siano stati riempiti precedentemente, 
	 * quindi non richiede parametri
	 * @return
	 * @throws Exception
	 */
	public ResultSet select(SelectBase selectBase) throws Exception{
		return resultSetfromIstruzione(selectBase.getQuery());
	}
	
	/**
	 * Metodo per chiamare query complesse. Permette di inserire tutti i parametri.
	 * @param tabelle, tutte le tabelle su cui costruire il cursore
	 * @param campi, campi da estrarre
	 * @param clausole, classiche clausole 'where' con coppia campo - valore
	 * @param joins, oggetti join per collegare le diverse tabelle
	 * @return
	 * @throws Exception
	 */
	public ResultSet select(final HashMap<String, String> tabelle, 
							final HashMap<String, String> campi, 
							final ArrayList<Clausola> clausole, 
							final ArrayList<Join> joins) throws Exception{
		
		SelectBase selectBase = new SelectBase();
		return resultSetfromIstruzione(selectBase.getQuery(tabelle, campi, clausole, joins));
	}
	
	public boolean delete(final DeleteBase deleteBase) throws Exception{
		return eseguiSqlFromString(deleteBase.getDeleteQuery());
	}
	
	
	public boolean delete(final String tabella, final ArrayList<Clausola> clausole) throws Exception{
		DeleteBase deleteBase = new DeleteBase();
		return eseguiSqlFromString(deleteBase.getDeleteQuery(tabella, clausole));
	}
	
	
	public boolean eseguiSqlFromString(final String comandoSql) throws Exception {
		if (ConnectionPool.getSingleton().executeUpdate(comandoSql) != 0) {
			return true;
		}
		return false;

	}

	public ResultSet resultSetfromIstruzione(final String comandoSql) throws Exception {
		Connection connection = ConnectionPool.getSingleton().getConnection();
		final ResultSet rs = ConnectionPool.getSingleton().getResulSet(connection, comandoSql);
		return rs;
	}
	
	public ResultSetMetaData metaDataFromIstruzione(final String comandoSql) throws Exception {
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
			final HashMap<String, String> campi, final HashMap<String, String> clausole) {
		
		Connection cn = null;
		boolean ok = false;
		try {
			cn = ConnectionPool.getSingleton().getConnection();
			ok = false;
			// connection = ConnectionPool.getSingleton().getConnection();
			final StringBuffer sql = new StringBuffer();

			if (tabella != null && comando != null) {
				final String command = comando.toUpperCase();
				// comando
				if (command.equals("INSERT")) {
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
					final Statement st = cn.createStatement();
					if (st.executeUpdate(sql.toString()) != 0)
						ok = true;
					cn.close();
					System.out.println("Record inserito correttamente");
				} else if (command.equals("UPDATE")) {
					final Iterator<String> iterUpdate = campi.keySet().iterator();
					sql.append(command).append(" " + tabella).append(" SET ");
					while (iterUpdate.hasNext()) {
						final String prossimo = iterUpdate.next();
						sql.append(prossimo).append(" = ");
						try {
							if (campi.get(prossimo).contains(".")) {
								sql.append(Double.parseDouble(campi.get(prossimo)));
							} else
								sql.append(Integer.parseInt(campi.get(prossimo)));
						} catch (final NumberFormatException e) {
							sql.append("'" + campi.get(prossimo) + "'");
						}
						if (iterUpdate.hasNext()) {
							sql.append(", ");
						}
					}
					if (!clausole.isEmpty()) {
						sql.append(" WHERE 1=1");
						final Iterator<String> where = clausole.keySet().iterator();

						while (where.hasNext()) {
							sql.append(" AND ");
							final String prossimo = where.next();
							sql.append(prossimo).append(" = ");
							try {
								if (campi.get(prossimo).contains(".")) {
									sql.append(Double.parseDouble(clausole.get(prossimo)));
								} else
									sql.append(Integer.parseInt(clausole.get(prossimo)));
							} catch (final NumberFormatException e) {
								sql.append("'" + clausole.get(prossimo) + "'");
							}
							if (where.hasNext())
								sql.append(", ");
						}
					}
					final Statement st = cn.createStatement();
					if (st.executeUpdate(sql.toString()) != 0)
						ok = true;
					cn.close();
				} else if (command.equals("DELETE")) {
					sql.append(command).append(" FROM ").append(tabella);
					if (!clausole.isEmpty()) {
						sql.append(" WHERE 1=1");
						final Iterator<String> where = clausole.keySet().iterator();
						while (where.hasNext()) {
							sql.append(" AND ");

							final String prossimo = where.next();
							sql.append(prossimo).append(" = ");

							try {
								if (campi.get(prossimo).contains(".")) {
									sql.append(Double.parseDouble(clausole.get(prossimo)));
								} else
									sql.append(Integer.parseInt(clausole.get(prossimo)));
							} catch (final NumberFormatException e) {
								sql.append("'" + clausole.get(prossimo) + "'");
							}
						}
						if (where.hasNext())
							sql.append(", ");
						cn.close();
						final Statement st = cn.createStatement();
						if (st.executeUpdate(sql.toString()) != 0)
							ok = true;
						cn.close();
					}

				} else if (command.equals("SELECT")) {
					sql.append(command);
					if (campi == null)
						sql.append(" * ");
					else {
						final Iterator<String> iterSelect = clausole.keySet().iterator();
						while (iterSelect.hasNext()) {
							final String prossimo = iterSelect.next();
							sql.append(" " + prossimo);
						}
						if (iterSelect.hasNext())
							sql.append(", ");

					}
					if (!clausole.isEmpty()) {
						sql.append("WHERE 1=1");
						final Iterator<String> where = clausole.keySet().iterator();
						while (where.hasNext()) {
							sql.append(" AND ");
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
			}

		} catch (final Exception e) {
			e.printStackTrace();
		} finally {
			if(cn != null){
				try {
					ConnectionPool.getSingleton().chiudiOggettiDb(cn);
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		}
		return ok;
	}

}
