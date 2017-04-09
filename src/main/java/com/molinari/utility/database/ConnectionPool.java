package com.molinari.utility.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;

import com.molinari.utility.controller.ControlloreBase;

/**
 * @author marco.molinari
 *
 */
public abstract class ConnectionPool {

	private ArrayList<Connection> freeConnections; // La coda di
															// connessioni
															// libere
	private Connection lastConnection;
	private String dbUrl; // Il nome del database
	private String dbDriver; // Il driver del database
	private String dbUser; // Il login per il database
	private String dbPassword; // La password di accesso al database
	private HashMap<Connection, ResultSet> mappaRS = new HashMap<>();
	private HashMap<Connection, Statement> mappaStatement = new HashMap<>();

	private static ConnectionPool singleton;

	protected ConnectionPool() {
		freeConnections = new ArrayList<>();
		dbUrl = getDbUrl();
		dbDriver = getDriver();
		dbUser = getUser();
		dbPassword = getPassword();

		loadDriver();

		for (int i = 0; i < getNumeroConnessioniDisponibili(); i++) {
			releaseNewConnection();
		}
	}
	
	public static synchronized ConnectionPool getSingleton() {
		if (singleton == null) {
			try {
				singleton = (ConnectionPool) Class.forName(ControlloreBase.getSingleton().getConnectionClassName()).newInstance();
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
			}
		}
		return singleton;
	}

	/**
	 * Funzione privata che carica il driver per l'accesso al database. In caso
	 * di errore durante il caricamento del driver solleva un'eccezione.
	 */
	private void loadDriver() {
		try {
			Class.forName(dbDriver);
		} catch (final ClassNotFoundException e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
	}

	/**
	 * Restituisce la prima connessione disponibile all'interno del pool di
	 * connessioni disponibili
	 * 
	 * @return {@link Connection}
	 */
	@SuppressWarnings("resource")
	public synchronized Connection getConnection() {
		Connection cn = null;
		if (!freeConnections.isEmpty()) {
			cn = freeConnections.get(0);
			if (freeConnections.size() == 1) {
				ControlloreBase.getLog().warning("Utilizzata ultima connessione disponibile.");
			}
			freeConnections.remove(0);
			ControlloreBase.getLog().info("Connection removed. Connections available: " + freeConnections.size());

			try {
				if (cn != null && cn.isClosed()) {
					cn = getConnection();
				}
			} catch (SQLException e) {
				cn = getConnection();
				ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
			}

			lastConnection = cn;
		}
		return cn;
	}

	/**
	 * Il metodo newConnection restituisce una nuova connessione
	 * 
	 * @return {@link Connection}
	 */
	private Connection newConnection() {

		Connection con = null;

		try {
			// crea la connessione
			String pass = "";
			String user = "";
			if (dbPassword != null && dbUser != null) {
				user = "?user=" + dbUser;
				pass = "&password=" + dbPassword;
			}
			con = DriverManager.getConnection(dbUrl + user + pass);
		} catch (SQLException e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
		// restituisce la nuova connessione
		return con;
	}

	/**
	 * Il metodo releaseConnection rilascia una connessione inserendola nella
	 * coda delle connessioni libere
	 */
	public synchronized void releaseNewConnection() {
		final Connection cn = newConnection();
		releaseConnection(cn);
		ControlloreBase.getLog().info("add a new connection to pool for closed previous connection.");
		ControlloreBase.getLog().info("Now are available " + freeConnections.size() + " connections");
	}

	/**
	 * Il metodo releaseConnection rilascia una connessione inserendola nella
	 * coda delle connessioni libere
	 */
	public synchronized void releaseConnection(final Connection con) {
		// Inserisce la connessione nella coda
		freeConnections.add(con);
	}

	public int executeUpdate(final String sql) throws SQLException {
		
		int ritorno = 0;
		final Connection cn = getConnection();
		if (cn != null && sql != null) {
			final Statement st = createStatement(cn);
			final Statement statementDaMap = mappaStatement.get(cn);
			if (statementDaMap != null) {
				statementDaMap.close();
			}
			mappaStatement.put(cn, st);
			ritorno = st.executeUpdate(sql);
		}
		chiudiOggettiDb(cn);
		return ritorno;
	}

	private Statement createStatement(final Connection cn) throws SQLException {
		return cn.createStatement();
	}

	public ResultSet getResulSet(Connection cn, final String sql) throws SQLException {

		ResultSet rs = null;
		if (cn != null && sql != null) {
			final Statement st = createStatement(cn);
			final Statement statementDaMap = mappaStatement.get(cn);
			if (statementDaMap != null) {
				statementDaMap.close();
			}
			mappaStatement.put(cn, st);
			rs = createResultSet(sql, st);
			ResultSet rsDaMappa = mappaRS.get(cn);
			if (rsDaMappa != null) {
				rsDaMappa.close();
			}
			mappaRS.put(cn, rs);
		}
		return rs;
	}

	private ResultSet createResultSet(final String sql, final Statement st) throws SQLException {
		return st.executeQuery(sql);
	}

	/**
	 * Chiude la connessione e se c'è il relativo resultSet o la statement
	 * 
	 * @param cn
	 * @throws SQLException
	 */
	public void chiudiOggettiDb(Connection cn) {
		Connection conn = cn;
		try {
			if (conn == null) {
				conn = lastConnection;
			}
			if (conn != null) {
				final ResultSet resultSet = mappaRS.get(cn);
				final Statement statement = mappaStatement.get(cn);
				if (resultSet != null) {
					resultSet.close();
				}
				if (statement != null) {
					statement.close();
				}
				conn.close();
			}
		} catch (SQLException e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		} finally {
			releaseNewConnection();
		}

	}

	/**
	 * @return password di accesso al db. se non necessario lasciare a null
	 */
	protected abstract String getPassword();

	/**
	 * @return nome utente di accesso a db. se non necessario lasciare a null
	 */
	protected abstract String getUser();

	/**
	 * @return nome e path della classe che funge da driver jdbc
	 */
	protected abstract String getDriver();

	/**
	 * @return url di connessione al db
	 */
	protected abstract String getDbUrl();

	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}

	/**
	 * @return numero massimo di connessioni disponibili
	 */
	protected abstract int getNumeroConnessioniDisponibili();
}
