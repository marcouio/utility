package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;

public class UtilDb {

	/**
	 * Metodo utile quando puo' capitare di ottenere in input un integer come
	 * mese con una sola cifra. In tal caso lo trasforma in stringa
	 * aggiungendogli uno zero davanti. In questa maniera e' possibile
	 * utilizzarlo per comporre una data
	 * 
	 * @param corrente
	 * @return String
	 * @throws Exception
	 */
	public static String convertiMese2(final int corrente) throws Exception {
		String mesi = null;
		if (corrente < 10) {
			mesi = "0" + corrente;
		} else if (corrente > 12) {
			throw new Exception("Mese non esistente");

		} else {
			mesi = Integer.toString(corrente);
		}
		return mesi;
	}

	/**
	 * Metodo utile quando puo' capitare di ottenere in input un giorno come int
	 * con una sola cifra. In tal caso lo trasforma in stringa aggiungendogli
	 * uno zero davanti. In questa maniera e' possibile utilizzarlo per comporre
	 * una data
	 * 
	 * @param corrente
	 * @return String
	 */
	public static String convertiGiorno(final int corrente) {
		String mesi;
		if (corrente < 10) {
			mesi = "0" + corrente;
		} else {
			mesi = Integer.toString(corrente);
		}
		return mesi;
	}

	/**
	 * Aggiunge uno zero davanti a Calendar.MONTH se necessario. Se il parametro
	 * Ã¨ 1 restituisce il mese corrente, se Ã¨ 0 restituisce il mese precedente
	 * 
	 * @param corrente
	 * @return String
	 */
	public static String convertiMese(final int corrente) {
		String mese = null;
		if (corrente == 1) {
			final int MESE = new GregorianCalendar().get(Calendar.MONTH) + 1;
			if (new GregorianCalendar().get(Calendar.MONTH) + 1 < 10) {
				mese = "0" + MESE;
			} else {
				mese = Integer.toString(MESE);
			}
		} else if (corrente == 0) {
			final int MESE = new GregorianCalendar().get(Calendar.MONTH);
			if (new GregorianCalendar().get(Calendar.MONTH) < 10) {
				mese = "0" + MESE;
			} else {
				mese = Integer.toString(MESE);
			}
		}
		return mese;

	}

	// CONVERSIONE FORMATO STRINGA --> DATA

	/**
	 * Il metodo da la possibilitï¿½ di convertire una stringa in una data,
	 * passandogli il formato in cui verrï¿½ ï¿½ presentata nella stringa
	 * 
	 * @param date
	 * @param format
	 * @return Date
	 */
	public static Date stringToDate(final String date, final String format) {
		final SimpleDateFormat formatter = new SimpleDateFormat(format);
		Date dataConvertita = null;
		try {
			dataConvertita = formatter.parse(date);
		} catch (final ParseException e) {
			System.out.println("ERRORE del metodo: stringToDate " + e.getMessage());
		}
		return dataConvertita;
	}

	// CONVERSIONE FORMATO DATA --> STRINGA

	/**
	 * Trasforma una data in una stringa seguendo il formato specificato
	 * 
	 * @param date
	 * @param format
	 * @return String
	 */
	public static String dataToString(final Date date, final String format) {
		final SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date);
	}

	public static Connection getConnection(final String url) {
		Connection connection2 = null;
		try {
			connection2 = DriverManager.getConnection(url);
		} catch (final SQLException e) {
			e.printStackTrace();
		}
		return connection2;
	}

	/**
	 * Metodo per chiudere una connessione al database
	 */
	public static void closeConnection(final Connection connection) {

		if (connection != null) {
			try {
				connection.close();
			} catch (final SQLException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Il metodo fornisce l'ultimo giorno del mese.
	 */
	public static final int getLastDayMonth(final int mese, final int anno) {
		switch (mese) {
		case (1):
			return 31;
		case (2):
			return new GregorianCalendar().isLeapYear(anno) ? 29 : 28;
		case (3):
			return 31;
		case (4):
			return 30;
		case (5):
			return 31;
		case (6):
			return 30;
		case (7):
			return 31;
		case (8):
			return 31;
		case (9):
			return 30;
		case (10):
			return 31;
		case (11):
			return 30;
		case (12):
			return 31;
		default:
			throw new ArrayIndexOutOfBoundsException(mese);
		}
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
	public boolean eseguiIstruzioneSql(final Connection cn, final String comando, final String tabella, final HashMap<String, String> campi, final HashMap<String, String> clausole) {
		boolean ok = false;
		try {
			ok = false;
			// connection = DBUtil.getConnection();
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
						if (iterInsert.hasNext()) {
							sql.append(", ");
						}
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
						if (iterInsert2.hasNext()) {
							sql.append(", ");
						}
					}

					sql.append(")");
					final Statement st = cn.createStatement();
					if (st.executeUpdate(sql.toString()) != 0) {
						ok = true;
					}
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
							} else {
								sql.append(Integer.parseInt(campi.get(prossimo)));
							}
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
							if (where.hasNext()) {
								sql.append(", ");
							}
						}
					}
					final Statement st = cn.createStatement();
					if (st.executeUpdate(sql.toString()) != 0) {
						ok = true;
					}
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
						if (where.hasNext()) {
							sql.append(", ");
						}
						cn.close();
						final Statement st = cn.createStatement();
						if (st.executeUpdate(sql.toString()) != 0) {
							ok = true;
						}
						cn.close();
					}

				} else if (command.equals("SELECT")) {
					sql.append(command);
					if (campi == null) {
						sql.append(" * ");
					} else {
						final Iterator<String> iterSelect = clausole.keySet().iterator();
						while (iterSelect.hasNext()) {
							final String prossimo = iterSelect.next();
							sql.append(" " + prossimo);
						}
						if (iterSelect.hasNext()) {
							sql.append(", ");
						}

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

	public static String[] trovaOggettoFromStringSQL(final String SQL, final String nome, final String partenza, final String fine, final String splitter) {
		final String sql = SQL.toLowerCase();
		if (nome != null && splitter != null) {
			if (sql.contains(nome)) {
				final String[] sqlSplittato = sql.split(nome);
				String per = sqlSplittato[1];
				per = per.substring(1);
				int indice = per.indexOf(" ");
				indice = indice == -1 ? per.indexOf(splitter) : indice;
				indice = indice == -1 ? per.length() : indice;
				return new String[] { per.substring(0, indice) };
			}
		} else if (partenza != null && fine != null && splitter != null) {

		}
		return null;
	}

	public static void main(final String[] args) {
		final String sql = "SELECT * FROM ENTRATE order by nome";
		final String parola = trovaOggettoFromStringSQL(sql, "order by", null, null, ",")[0];
		System.out.println(parola);
	}
}
