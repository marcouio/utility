package main.java.com.molinari.utility.thread;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import main.java.com.molinari.utility.database.UtilDb;


public class ContainerDb {

	private Connection				connection;
	private ArrayList<ResultSet>	listRs			= new ArrayList<ResultSet>();
	private ArrayList<Statement>	listStatement	= new ArrayList<Statement>();

	public ContainerDb() throws Exception {
	}

	public void chiudi() throws SQLException {
		if (connection != null && !connection.isClosed()) {
			connection.close();
		}
		for (ResultSet rs : listRs) {
			rs.close();
		}

		for (Statement stat : listStatement) {
			stat.close();
		}
	}
	
	public Statement creaPreparedStatement(String query) throws Exception {

		PreparedStatement prepareStatement = getConnessione().prepareStatement(query);
		listStatement.add(prepareStatement);
		return prepareStatement;
	}

	public Statement creaStatement() throws Exception {

		Statement prepareStatement = getConnessione().createStatement();
		listStatement.add(prepareStatement);
		return prepareStatement;
	}
	
	public ResultSet creaResultSet(Object query) throws Exception{
		Statement statement = getConnessione().createStatement();
		listStatement.add(statement);
		String sql = query.toString();
		
		ResultSet rs = statement.executeQuery(sql);
		listRs.add(rs);
		return rs;
	}

	public ResultSet creaResultSet(Statement statement, Object query) throws SQLException{

		listStatement.add(statement);
		ResultSet rs = null;
		if(statement instanceof PreparedStatement){
			rs = ((PreparedStatement)statement).executeQuery();
		}else{
			rs = statement.executeQuery(query.toString());
		}
		listRs.add(rs);
		return rs;
	}
	
	 private final String toDinamycStringSQL(String sqlStatement, Serializable[] oggettiAssegnati) {
	        final StringBuffer sb = new StringBuffer();
	        final int stringSize = sqlStatement.length();
	        final char[] stringa = sqlStatement.toCharArray();
	        boolean inLiteral = false;
	        int index = 0;
	        for (int i = 0; i < stringSize; i++) {
	            final char thisChar = stringa[i];
	            /*else*/ if (thisChar == '\'') {
	                inLiteral = (!inLiteral);
	                sb.append(thisChar);
	            } // if
	            else if ((!inLiteral) && (thisChar == '?')) {
	                if (index == oggettiAssegnati.length) {
	                    throw new RuntimeException("Troppi caratteri '?' trovati!");
	                }
	                sb.append(UtilDb.toString(oggettiAssegnati[index]));
	                index++;
	            } // else if
	            else {
	                sb.append(thisChar);
	            }
	        } // for
	        if (index != oggettiAssegnati.length) {
	            throw new RuntimeException("Pochi caratteri '?' trovati!");
	        }
	        return sb.toString();
	    } // toDinamycStringSQL

	 private Connection getConnessione() throws Exception {

	     if (connection == null) {
	         connection = DriverManager.getConnection("", "", "");
	     }
	     return connection;
	 }
}

