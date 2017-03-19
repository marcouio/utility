package com.molinari.utility.database;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;

import com.molinari.utility.controller.ControlloreBase;

public class UtilDb {
	
	private static final String AND = " AND ";
	private static final String FROM = " FROM ";

	private UtilDb() {
		// do nothing
	}
	
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
	public static String convertiMeseWithTwoDigit(final int corrente) {
		String mesi;
		if (corrente < 10) {
			mesi = "0" + corrente;
		} else if (corrente > 12) {
			throw new IllegalArgumentException("Mese non esistente");

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
	 * vale 1 restituisce il mese corrente, se vale 0 restituisce il mese precedente
	 * 
	 * @param corrente
	 * @return String
	 */
	public static String convertiMese(final int corrente) {
		String mese = null;
		if (corrente == 1) {
			final int month = new GregorianCalendar().get(Calendar.MONTH) + 1;
			mese = convertiMese(month);
		} else if (corrente == 0) {
			final int month = new GregorianCalendar().get(Calendar.MONTH);
			mese = convertiMese(month);
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
			ControlloreBase.getLog().log(Level.SEVERE, "ERRORE del metodo: stringToDate " + e.getMessage(), e);
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
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
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
				ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
			}
		}

	}

	/**
	 * Il metodo fornisce l'ultimo giorno del mese.
	 */
	public static final int getLastDayMonth(final int mese, final int anno) {
		
		Month month = Month.of(mese);
		final boolean leapYear = new GregorianCalendar().isLeapYear(anno);
		return month.length(leapYear);

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
	public static boolean eseguiIstruzioneSql(final String comando, final String tabella, final Map<String, String> campi,
			final Map<String, String> clausole) {
		boolean ok = false;
		try {
			ok = false;
			final StringBuilder sql = new StringBuilder();
			final String command = comando.toUpperCase();

			if (tabella != null) {
				// comando
				if ("INSERT".equals(command)) {
					ok = gestioneIstruzioneInsert(tabella, campi, sql, command);
				} else if ("UPDATE".equals(command)) {
					ok = gestioneIstruzioneUpdate(tabella, campi, clausole, sql, command);
				} else if ("DELETE".equals(command)) {
					ok = gestioneIstruzioneDelete(tabella, clausole, ok, sql, command);
				} else if ("SELECT".equals(command)) {
					gestioneIstruzioneSelect(campi, clausole, sql, command);
				}
			}

		} catch (final Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		} 
		return ok;
	}

	private static void gestioneIstruzioneSelect(final Map<String, String> campi, final Map<String, String> clausole,
			final StringBuilder sql, final String command) {
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
			elaborateClause(clausole, sql);
		}
	}

	private static boolean gestioneIstruzioneUpdate(final String tabella, final Map<String, String> campi,
			final Map<String, String> clausole, final StringBuilder sql, final String command)
					throws SQLException {
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
			elaborateClause(clausole, sql);
		}

		return ConnectionPool.getSingleton().executeUpdate(sql.toString()) != 0;
	}
	
	private static boolean gestioneIstruzioneDelete(final String tabella, final Map<String, String> clausole, boolean ok,
			final StringBuilder sql, final String command) throws SQLException {
		
		boolean ret = ok;
		
		sql.append(command).append(FROM).append(tabella);
		if (!clausole.isEmpty()) {
			elaborateClause(clausole, sql);

			if (ConnectionPool.getSingleton().executeUpdate(sql.toString()) != 0) {
				ret = true;
			}
		}
		return ret;
	}

	private static void elaborateClause(final Map<String, String> clausole, final StringBuilder sql) {
		sql.append(" WHERE 1=1");
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
			if (where.hasNext()) {
				sql.append(", ");
			}
		}
	}

	private static boolean gestioneIstruzioneInsert(final String tabella, final Map<String, String> campi,
			final StringBuilder sql, final String command) throws SQLException {
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


		return ConnectionPool.getSingleton().executeUpdate(sql.toString()) != 0;
	}

	public static String[] trovaOggettoFromStringSQL(final String sqlParam, final String nome, final String partenza, final String fine, final String splitter) {
		final String sql = sqlParam.toLowerCase();
		if (nome != null && splitter != null) {
			if (sql.contains(nome)) {
				final String[] sqlSplittato = sql.split(nome);
				String per = sqlSplittato[1];
				per = per.substring(1);
				int indice = per.indexOf(' ');
				indice = indice == -1 ? per.indexOf(splitter) : indice;
				indice = indice == -1 ? per.length() : indice;
				return new String[] { per.substring(0, indice) };
			}
		} else if (partenza != null && fine != null && splitter != null) {
			//non so come volevo fare
		}
		return new String[]{};
	}

	public static Object toString(Serializable serializable) {
		return serializable.toString();
	}

}
