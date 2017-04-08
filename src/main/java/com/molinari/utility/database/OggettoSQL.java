package com.molinari.utility.database;

import java.util.Map;

public class OggettoSQL {

	public static final String SELECT = "select";
	public static final String ALTER = "alter";
	public static final String ALTERTABLE = "alter table ";
	public static final String UPDATE = "update";
	public static final String DELETE = "delete";
	public static final String INSERT = "insert";
	public static final String INSERTINTO = "insert into ";
	public static final String FROM = " from ";
	public static final String INTO = " into ";
	public static final String WHERE = " where ";
	public static final String WHERE1_1 = " where 1=1";
	public static final String AND = " and ";
	public static final String SF = "select * from ";
	public static final String VALUES = " values ";
	public static final String SET = "set";
	
	public static final String NUMSTRING = "@@@";
	
	protected StringBuilder sbSQL = new StringBuilder();

	public OggettoSQL() {
		//do nothing
	}
	
	protected void inserisciValore(final String prossimo, final Map<String, String> mappa) {
		String valueClausola = mappa.get(prossimo);
		
		try {
			if (valueClausola == null) {
				valueClausola = "null";
			}else{
				checkIsNumber(valueClausola);
			}
			sbSQL.append(valueClausola);
		} catch (final NumberFormatException e) {
			String testoCorretto = FormatterSqlText.correggi(valueClausola);
			sbSQL.append("'" + testoCorretto + "'");
		}
	}

	private Number checkIsNumber(String valueClausola) {
		if(valueClausola.contains(".")){
			return Double.parseDouble(valueClausola);
		}else{
			return Integer.parseInt(valueClausola);
		}
	}	

	protected void inserisciValore(final Clausola prossimo) {
		String valueClausola = prossimo.getValore();

		try {
			if (valueClausola == null) {
				valueClausola = "null";
			}else{
				checkIsNumber(valueClausola);
			}
			sbSQL.append(valueClausola);
		} catch (final NumberFormatException e) {
			String testoCorretto = FormatterSqlText.correggi(valueClausola);
			sbSQL.append("'" + testoCorretto + "'");
		}
	}	


	public StringBuilder getSbSQL() {
		return sbSQL;
	}
	
	public void setSbSQL(final StringBuilder sbSQL) {
		this.sbSQL = sbSQL;
	}
}
