package com.molinari.utility.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.function.Function;
import java.util.logging.Level;

import com.molinari.utility.controller.ControlloreBase;

/**
 * @author marco.molinari
 *
 */
public abstract class ConnectionPool {

	private final ArrayList<Connection> freeConnections; // La coda di
															// connessioni
															// libere
	private String dbUrl; // Il nome del database
	private final String dbDriver; // Il driver del database
	private final String dbUser; // Il login per il database
	private final String dbPassword; // La password di accesso al database

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
	
	public <R> R useConnection(Function<Connection, R> f) throws SQLException {
		try (Connection connection = getConnection();){
			return f.apply(connection);
		}finally {
			releaseNewConnection();
		}
	}

	/**
	 * Restituisce la prima connessione disponibile all'interno del pool di
	 * connessioni disponibili
	 * 
	 * @return {@link Connection}
	 */
	@SuppressWarnings("resource")
	private synchronized Connection getConnection() {
		Connection cn = null;
		if (!freeConnections.isEmpty()) {
			cn = freeConnections.get(0);
			if (freeConnections.size() == 1) {
				ControlloreBase.getLog().warning("Utilizzata ultima connessione disponibile.");
			}
			freeConnections.remove(0);
			ControlloreBase.getLog().fine(() -> "Connection removed. Connections available: " + freeConnections.size());

			try {
				if (cn != null && cn.isClosed()) {
					releaseNewConnection();
					cn = getConnection();
				}
			} catch (final SQLException e) {
				cn = getConnection();
				ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
			}

		}
		while(freeConnections.isEmpty()) {
			ControlloreBase.getLog().warning("I'm wating for a free connection");
		}
		cn = freeConnections.get(0);
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
		} catch (final SQLException e) {
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
		ControlloreBase.getLog().fine("Add a new connection to pool.");
		ControlloreBase.getLog().finest(() -> "Now are available " + freeConnections.size() + " connections");
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
		
		ControlloreBase.getLog().info(() -> "Query in esecuzione: " + sql);
		
		int ritorno = 0;
		try(final Connection cn = getConnection();
			final Statement st = createStatement(cn);
			){
			if (cn != null && sql != null) {
				ritorno = st.executeUpdate(sql);
			}
		}catch (Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}finally {
			releaseNewConnection();
		}
		return ritorno;
	}

	private Statement createStatement(final Connection cn) throws SQLException {
		return cn.createStatement();
	}

	public ResultSet getResulSet(Connection cn, final String sql) throws SQLException {

		ResultSet rs = null;
		if (cn != null && sql != null) {
			final Statement st = createStatement(cn);
			rs = createResultSet(sql, st);
		}
		return rs;
	}

	private ResultSet createResultSet(final String sql, final Statement st) throws SQLException {
		return st.executeQuery(sql);
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
