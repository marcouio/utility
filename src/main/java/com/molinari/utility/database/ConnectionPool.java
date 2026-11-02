package com.molinari.utility.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
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

	private static final long CONNECTION_WAIT_TIMEOUT_MS = 30_000L;

	private final int maxConnections;
	private int createdConnections;
	private final Map<Connection, List<Statement>> statementsByConnection;

	protected ConnectionPool() {
		freeConnections = new ArrayList<>();
		statementsByConnection = new IdentityHashMap<>();
		maxConnections = Math.max(1, getNumeroConnessioniDisponibili());
		dbUrl = getDbUrl();
		dbDriver = getDriver();
		dbUser = getUser();
		dbPassword = getPassword();

		loadDriver();

		for (int i = 0; i < maxConnections; i++) {
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
		final Connection connection = getConnection();
		try {
			return f.apply(connection);
		} finally {
			releaseConnection(connection);
		}
	}

	/**
	 * Restituisce la prima connessione disponibile all'interno del pool di
	 * connessioni disponibili, rimuovendola dal pool fino al rilascio.
	 * Se il pool e' vuoto crea una nuova connessione entro il limite massimo,
	 * altrimenti attende il rilascio di una connessione.
	 * 
	 * @return {@link Connection}
	 */
	private synchronized Connection getConnection() throws SQLException {
		final long deadline = System.currentTimeMillis() + CONNECTION_WAIT_TIMEOUT_MS;
		while (true) {
			while (!freeConnections.isEmpty()) {
				final Connection cn = freeConnections.remove(0);
				if (isUsable(cn)) {
					ControlloreBase.getLog().fine(() -> "Connection removed. Connections available: " + freeConnections.size());
					return cn;
				}
				createdConnections--;
				closeQuietly(cn);
			}

			if (createdConnections < maxConnections) {
				final Connection cn = newConnection();
				createdConnections++;
				return cn;
			}

			final long remaining = deadline - System.currentTimeMillis();
			if (remaining <= 0) {
				throw new SQLException("Nessuna connessione disponibile nel pool entro il timeout di "
						+ CONNECTION_WAIT_TIMEOUT_MS + " ms");
			}
			ControlloreBase.getLog().warning("In attesa di una connessione libera nel pool.");
			try {
				wait(remaining);
			} catch (final InterruptedException e) {
				Thread.currentThread().interrupt();
				throw new SQLException("Interrotto mentre si attendeva una connessione libera", e);
			}
		}
	}

	private boolean isUsable(final Connection cn) {
		if (cn == null) {
			return false;
		}
		try {
			return !cn.isClosed();
		} catch (final SQLException e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
			return false;
		}
	}

	/**
	 * Il metodo newConnection restituisce una nuova connessione
	 * 
	 * @return {@link Connection}
	 */
	protected Connection newConnection() {

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
			throw new DBException(e);
		}
		// restituisce la nuova connessione
		return con;
	}

	/**
	 * Crea una nuova connessione e la inserisce nella coda delle connessioni
	 * libere, rispettando il limite massimo del pool.
	 */
	public synchronized void releaseNewConnection() {
		if (createdConnections >= maxConnections) {
			ControlloreBase.getLog().fine("Numero massimo di connessioni nel pool gia' raggiunto.");
			return;
		}
		final Connection cn = newConnection();
		createdConnections++;
		freeConnections.add(cn);
		notifyAll();
		ControlloreBase.getLog().fine("Add a new connection to pool.");
		ControlloreBase.getLog().finest(() -> "Now are available " + freeConnections.size() + " connections");
	}

	/**
	 * Il metodo releaseConnection rilascia una connessione inserendola nella
	 * coda delle connessioni libere. Le risorse (Statement) ancora associate
	 * alla connessione vengono chiuse. Una connessione gia' chiusa viene
	 * scartata e non reinserita nel pool.
	 */
	public synchronized void releaseConnection(final Connection con) {
		if (con == null) {
			return;
		}
		closeStatements(con);
		try {
			if (con.isClosed()) {
				createdConnections--;
			} else {
				freeConnections.add(con);
			}
		} catch (final SQLException e) {
			createdConnections--;
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
		if (createdConnections < 0) {
			createdConnections = 0;
		}
		notifyAll();
	}

	public int executeUpdate(final String sql) throws SQLException {
		
		ControlloreBase.getLog().info(() -> "Query in esecuzione: " + sql);
		
		int ritorno = 0;
		final Connection cn = getConnection();
		try (final Statement st = createStatement(cn)) {
			if (sql != null) {
				ritorno = st.executeUpdate(sql);
			}
		} catch (final Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
			throw new DBException(e);
		} finally {
			releaseConnection(cn);
		}
		return ritorno;
	}

	private Statement createStatement(final Connection cn) throws SQLException {
		return cn.createStatement();
	}

	public ResultSet getResulSet(Connection cn, final String sql) throws SQLException {

		if (cn == null || sql == null) {
			return null;
		}
		final Statement st = createStatement(cn);
		registerStatement(cn, st);
		try {
			return createResultSet(sql, st);
		} catch (final SQLException e) {
			unregisterStatement(cn, st);
			closeQuietly(st);
			throw e;
		}
	}

	private synchronized void registerStatement(final Connection cn, final Statement st) {
		List<Statement> statements = statementsByConnection.get(cn);
		if (statements == null) {
			statements = new ArrayList<>();
			statementsByConnection.put(cn, statements);
		}
		statements.add(st);
	}

	private synchronized void unregisterStatement(final Connection cn, final Statement st) {
		final List<Statement> statements = statementsByConnection.get(cn);
		if (statements != null) {
			statements.remove(st);
			if (statements.isEmpty()) {
				statementsByConnection.remove(cn);
			}
		}
	}

	private synchronized void closeStatements(final Connection cn) {
		final List<Statement> statements = statementsByConnection.remove(cn);
		if (statements != null) {
			for (final Statement st : statements) {
				closeQuietly(st);
			}
		}
	}

	private void closeQuietly(final AutoCloseable resource) {
		if (resource == null) {
			return;
		}
		try {
			resource.close();
		} catch (final Exception e) {
			ControlloreBase.getLog().log(Level.FINE, e.getMessage(), e);
		}
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
