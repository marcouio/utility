package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import controller.ControlloreBase;

/**
 * @author marco.molinari
 *
 */
public abstract class ConnectionPool {

	private static ArrayList<Connection> freeConnections; // La coda di connessioni libere
	private static Connection lastConnection;
	private static String dbUrl;           // Il nome del database
	private static String dbDriver;        // Il driver del database
	private static String dbUser;         // Il login per il database
	private static String dbPassword;      // La password di accesso al database
	private static HashMap<Connection, ResultSet> mappaRS = new HashMap<Connection, ResultSet>(); 
	private static HashMap<Connection, Statement> mappaStatement = new HashMap<Connection, Statement>(); 
	
	private static ConnectionPool singleton;
	
	public static synchronized ConnectionPool getSingleton() throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		if(singleton == null){
			singleton = (ConnectionPool) Class.forName(ControlloreBase.connectionClassName).newInstance();
		}
		return singleton;
	}
	
	protected ConnectionPool() {
		ConnectionPool.freeConnections = new ArrayList<Connection>();
		
		ConnectionPool.dbUrl = getDbUrl();
		ConnectionPool.dbDriver = getDriver();
		ConnectionPool.dbUser = getUser();
		ConnectionPool.dbPassword = getPassword();
		
		loadDriver();
		
		for(int i = 0; i < getNumeroConnessioniDisponibili(); i++){
			releaseNewConnection();
		}
	}
	
	/**
	 * Funzione privata che carica il driver per l'accesso al database. 
	 * In caso di errore durante il caricamento del driver solleva un'eccezione.
	 */
	private void  loadDriver() {
		try {
			Class.forName(dbDriver);
		} catch (final ClassNotFoundException e) {
			e.printStackTrace();
		}
	} 
	
	/**
	 * Restituisce la prima connessione disponibile all'interno del pool di connessioni disponibili
	 * @return {@link Connection} 
	 */
	public synchronized Connection getConnection(){
		Connection cn = null;
		if (freeConnections.size() > 0) {
			cn = freeConnections.get(0);
			freeConnections.remove(0);
			
			try {
				if(cn.isClosed()){
					cn = getConnection();
				}
			} catch (SQLException e) {
				cn = getConnection();
			}
			
		}else{
			releaseNewConnection();
			cn = getConnection();
		}
		lastConnection = cn;
		return cn;
	}
	

	/**
	 * Il metodo newConnection restituisce una nuova connessione
	 * @return {@link Connection} 
	 */
	private static Connection newConnection(){

		Connection con = null;

		try {
			// crea la connessione
			String pass = "";
			String user = "";
			if(dbPassword != null && dbUser != null){
				user = "?user=" + dbUser;
				pass = "&password=" + dbPassword;
			}
			con = DriverManager.getConnection(dbUrl + user + pass);
		} catch(SQLException e) {
			e.printStackTrace();
		}
        // restituisce la nuova connessione
        return con;
	}
	

	/**
	 * 	Il metodo releaseConnection rilascia una connessione inserendola
	 *  nella coda delle connessioni libere
	 */
	public synchronized static void releaseNewConnection() {
		final Connection cn = newConnection();
		releaseConnection(cn);
	}

	/**
	 * 	Il metodo releaseConnection rilascia una connessione inserendola
	 *  nella coda delle connessioni libere
	 */
	public synchronized static void releaseConnection(final Connection con) {
	        // Inserisce la connessione nella coda
	        freeConnections.add(con);
	}
	
	public int executeUpdate(final String sql) throws SQLException{
		int ritorno = 0;
		final Connection cn = getConnection();
		if(cn != null && sql != null){
			final Statement st = cn.createStatement();
			final Statement statementDaMap = mappaStatement.get(cn);
			if(statementDaMap != null){
				statementDaMap.close();
			}
			mappaStatement.put(cn, st);
			ritorno = st.executeUpdate(sql);
		}
		chiudiOggettiDb(cn);
		return ritorno;
	}
	
	public ResultSet getResulSet(final String sql) throws SQLException{
		final Connection cn = getConnection();
		ResultSet rs = null;
		if(cn != null && sql != null){
			final Statement st = cn.createStatement();
			rs = st.executeQuery(sql);
			ResultSet rsDaMappa = mappaRS.get(cn);
			if(rsDaMappa != null){
				rsDaMappa.close();
			}
			mappaRS.put(cn, rs);
		}
		return rs;
	}
	
	/**
	 * Chiude la connessione e se c'è il relativo resultSet o la statement
	 * @param cn
	 * @throws SQLException
	 */
	public void chiudiOggettiDb(Connection cn) throws SQLException{
		if(cn == null){
			cn = lastConnection;
		}
		if(cn != null){
			final ResultSet resultSet = mappaRS.get(cn);
			final Statement statement = mappaStatement.get(cn);
			if(resultSet != null){
				resultSet.close();
			}
			if(statement != null){
				statement.close();
			}
			cn.close();
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
	
	public static void setDbUrl(String dbUrl) {
		ConnectionPool.dbUrl = dbUrl;
	}

	/**
	 * @return numero massimo di connessioni disponibili
	 */
	protected abstract int getNumeroConnessioniDisponibili();
}