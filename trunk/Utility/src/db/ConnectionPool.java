package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import controller.ControlloreBase;

public abstract class ConnectionPool {

	private ArrayList<Connection> freeConnections; // La coda di connessioni libere
	private String dbUrl;           // Il nome del database
	private String dbDriver;        // Il driver del database
	private String dbUser;         // Il login per il database
	private String dbPassword;      // La password di accesso al database
	
	private static ConnectionPool singleton;
	
	public static synchronized ConnectionPool getSingleton() throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		if(singleton == null){
			singleton = (ConnectionPool) Class.forName(ControlloreBase.connectionClassName).newInstance();
		}
		return singleton;
	}
	
	private ConnectionPool() {
		this.freeConnections = new ArrayList<Connection>();
		
		this.dbUrl = getDbUrl();
		this.dbDriver = getDriver();
		this.dbUser = getUser();
		this.dbPassword = getPassword();
		
		loadDriver();
		
	}
	
	// Funzione privata che carica il driver per l'accesso al database.
	// In caso di errore durante il caricamento del driver solleva un'eccezione.
	private void  loadDriver() {
		try {
			java.lang.Class.forName(
					dbDriver + "?user=" +
							dbUser + "&password=" + dbPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
	
	public synchronized Connection getConnection(){
		Connection cn;
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
			cn = newConnection();
		}
		
		return cn;
	}
	
	// Il metodo newConnection restituisce una nuova connessione
	private Connection newConnection(){

		Connection con = null;

		try {
			// crea la connessione
			con = DriverManager.getConnection(dbUrl);
		} catch(SQLException e) {
			e.printStackTrace();
		}
        // restituisce la nuova connessione
        return con;
	}
	
	// Il metodo releaseConnection rilascia una connessione inserendola
	// nella coda delle connessioni libere
	public synchronized void releaseConnection(Connection con) {
	        // Inserisce la connessione nella coda
	        freeConnections.add(con);
	}

	protected abstract String getPassword();

	protected abstract String getUser();

	protected abstract String getDriver();

	protected abstract String getDbUrl();
}
