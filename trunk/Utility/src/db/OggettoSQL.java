package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;

public class OggettoSQL {

	public static final String SELECT = "select";
	public static final String UPDATE = "update";
	public static final String DELETE = "delete";
	public static final String INSERT = "insert"; 
	public static final String INSERTINTO = "insert into ";
	public static final String FROM = "from";
	public static final String INTO = " into ";
	public static final String WHERE = "where";
	public static final String WHERE1_1 = "where 1=1";
	public static final String AND = "and";
	public static final String SF = "select * from ";
	public static final String VALUES = " values ";
	public static final String SET = "set";

	protected String tabella;
	protected HashMap<String, Object> campi = new HashMap<String, Object>();
	protected StringBuffer sbSQL = new StringBuffer();
	public static Connection cn;

	public OggettoSQL(final Connection cn) {
		OggettoSQL.cn = cn; 
	}


	public boolean aggiornaSqlFromString(final String comandoSql) throws SQLException{
		final Statement st = cn.createStatement();
		if (st.executeUpdate(comandoSql) != 0){
			cn.close();
			return true;
		}
		cn.close();
		return false;

	}

	public ResultSet resultSetfromIstruzione(final String comandoSql) throws Exception{
		final Statement st = cn.createStatement();
		final ResultSet rs = st.executeQuery(comandoSql);
		cn.close();
		return rs;
	}

	public ResultSetMetaData metaDataFromIstruzione(final String comandoSql) throws Exception{
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
	public boolean eseguiIstruzioneSql(final Connection cn, final String comando, final String tabella,
			final HashMap<String, String> campi, final HashMap<String, String> clausole) {
		boolean ok = false;
		try {
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
			try {
				cn.close();
			} catch (final SQLException e) {
				e.printStackTrace();
			}
		}
		return ok;
	}

	public HashMap<String, Object> getCampi() {
		return campi;
	}

	public void setCampi(final HashMap<String, Object> campi) {
		this.campi = campi;
	}

	public String getTabella() {
		return tabella;
	}

	public void setTabella(final String tabella) {
		this.tabella = tabella;
	}

	public StringBuffer getSbSQL() {
		return sbSQL;
	}

	public void setSbSQL(final StringBuffer sbSQL) {
		this.sbSQL = sbSQL;
	}


}
