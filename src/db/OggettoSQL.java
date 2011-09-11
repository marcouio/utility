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
	
	public static Connection cn;
	
	public OggettoSQL(Connection cn) {
		OggettoSQL.cn = cn; 
	}
	
	
	public boolean aggiornaSqlFromString(final String comandoSql) throws SQLException{
		Statement st = cn.createStatement();
		if (st.executeUpdate(comandoSql) != 0){
			return true;
		}
		return false;
			
	}
	
	public ResultSet resultSetfromIstruzione(final String comandoSql) throws Exception{
		final Statement st = cn.createStatement();
		return st.executeQuery(comandoSql);
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
	public boolean eseguiIstruzioneSql(Connection cn, String comando, String tabella,
	                HashMap<String, String> campi, HashMap<String, String> clausole) {
		boolean ok = false;
		try {
			ok = false;
			// connection = DBUtil.getConnection();
			StringBuffer sql = new StringBuffer();
			String command = comando.toUpperCase();

			if (tabella != null && comando != null) {
				// comando
				if (command.equals("INSERT")) {
					sql.append(command).append(" INTO ").append(tabella);
					sql.append("(");
					Iterator<String> iterInsert = campi.keySet().iterator();

					while (iterInsert.hasNext()) {
						String prossimo = iterInsert.next();
						// aggiunge nome colonna
						sql.append(prossimo);
						if (iterInsert.hasNext())
							sql.append(", ");
					}
					sql.append(") ").append(" VALUES (");
					Iterator<String> iterInsert2 = campi.keySet().iterator();
					while (iterInsert2.hasNext()) {
						String prossimo = iterInsert2.next();
						try {
							sql.append(Integer.parseInt(campi.get(prossimo)));
						} catch (NumberFormatException e) {
							sql.append("'" + campi.get(prossimo) + "'");
						}
						if (iterInsert2.hasNext())
							sql.append(", ");
					}

					sql.append(")");
					Statement st = cn.createStatement();
					if (st.executeUpdate(sql.toString()) != 0)
						ok = true;
					cn.close();
					System.out.println("Record inserito correttamente");
				} else if (command.equals("UPDATE")) {
					Iterator<String> iterUpdate = campi.keySet().iterator();
					sql.append(command).append(" " + tabella).append(" SET ");
					while (iterUpdate.hasNext()) {
						String prossimo = iterUpdate.next();
						sql.append(prossimo).append(" = ");
						try {
							if (campi.get(prossimo).contains(".")) {
								sql.append(Double.parseDouble(campi.get(prossimo)));
							} else
								sql.append(Integer.parseInt(campi.get(prossimo)));
						} catch (NumberFormatException e) {
							sql.append("'" + campi.get(prossimo) + "'");
						}
						if (iterUpdate.hasNext()) {
							sql.append(", ");
						}
					}
					if (!clausole.isEmpty()) {
						sql.append(" WHERE 1=1");
						Iterator<String> where = clausole.keySet().iterator();

						while (where.hasNext()) {
							sql.append(" AND ");
							String prossimo = where.next();
							sql.append(prossimo).append(" = ");
							try {
								sql.append(Integer.parseInt(clausole.get(prossimo)));
							} catch (NumberFormatException e) {
								sql.append("'" + clausole.get(prossimo) + "'");
							}
							if (where.hasNext())
								sql.append(", ");
						}
					}
					Statement st = cn.createStatement();
					if (st.executeUpdate(sql.toString()) != 0)
						ok = true;
					cn.close();
				} else if (command.equals("DELETE")) {
					sql.append(command).append(" FROM ").append(tabella);
					if (!clausole.isEmpty()) {
						sql.append(" WHERE 1=1");
						Iterator<String> where = clausole.keySet().iterator();
						while (where.hasNext()) {
							sql.append(" AND ");

							String prossimo = where.next();
							sql.append(prossimo).append(" = ");

							try {
								sql.append(Integer.parseInt(clausole.get(prossimo)));
							} catch (NumberFormatException e) {
								sql.append("'" + clausole.get(prossimo) + "'");
							}
						}
						if (where.hasNext())
							sql.append(", ");
						cn.close();
						Statement st = cn.createStatement();
						if (st.executeUpdate(sql.toString()) != 0)
							ok = true;
						cn.close();
					}

				} else if (command.equals("SELECT")) {
					sql.append(command);
					if (campi == null)
						sql.append(" * ");
					else {
						Iterator<String> iterSelect = clausole.keySet().iterator();
						while (iterSelect.hasNext()) {
							String prossimo = iterSelect.next();
							sql.append(" " + prossimo);
						}
						if (iterSelect.hasNext())
							sql.append(", ");

					}
					if (!clausole.isEmpty()) {
						sql.append("WHERE 1=1");
						Iterator<String> where = clausole.keySet().iterator();
						while (where.hasNext()) {
							sql.append(" AND ");
							String prossimo = where.next();
							sql.append(prossimo).append(" = ");
							try {
								sql.append(Integer.parseInt(clausole.get(prossimo)));
							} catch (NumberFormatException e) {
								sql.append("'" + clausole.get(prossimo) + "'");
							}
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				cn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ok;
	}

}
