package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;

public class OggettoSQL {

	public static final String SELECT = "select";
	public static final String ALTER = "alter";
	public static final String ALTERTABLE = "alter table ";
	public static final String UPDATE = "update";
	public static final String DELETE = "delete";
	public static final String INSERT = "insert";
	public static final String INSERTINTO = "insert into ";
	public static final String FROM = " from ";
	public static final String INTO = " into ";
	public static final String WHERE = " where ";
	public static final String WHERE1_1 = " where 1=1";
	public static final String AND = " and ";
	public static final String SF = "select * from ";
	public static final String VALUES = " values ";
	public static final String SET = "set";

	
	protected StringBuffer sbSQL = new StringBuffer();

	public OggettoSQL() {
	}
	

	public boolean aggiornaSqlFromString(final String comandoSql) throws Exception {
		if (ConnectionPool.getSingleton().executeUpdate(comandoSql) != 0) {
			return true;
		}
		return false;

	}

	public ResultSet resultSetfromIstruzione(final String comandoSql) throws Exception {
		final ResultSet rs = ConnectionPool.getSingleton().getResulSet(comandoSql);
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
			// connection = DBUtil.getConnection();
			final StringBuffer sql = new StringBuffer();
			final String command = comando.toUpperCase();

			if (tabella != null && comando != null) {
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
	
	protected void inserisciValore(final String prossimo, final HashMap<String, String> mappa) {
		try {
			String valueClausola = (String) mappa.get(prossimo);
			if (valueClausola == null) {
				valueClausola = "null";
			}else{
				if(valueClausola.contains(".")){
					Double.parseDouble(valueClausola);
				}else{
					Integer.parseInt(valueClausola);
				}
			}
			sbSQL.append(valueClausola);
		} catch (final NumberFormatException e) {
			sbSQL.append("'" + mappa.get(prossimo) + "'");
		}
	}	

	public StringBuffer getSbSQL() {
		return sbSQL;
	}

	public void setSbSQL(final StringBuffer sbSQL) {
		this.sbSQL = sbSQL;
	}
}
