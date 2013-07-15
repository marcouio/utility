package db;

import java.util.HashMap;


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

	
	protected StringBuffer sbSQL = new StringBuffer();

	public OggettoSQL() {
	}
	
	protected void inserisciValore(final String prossimo, final HashMap<String, String> mappa) {
		try {
			String valueClausola = (String) mappa.get(prossimo);
			if (valueClausola == null) {
				valueClausola = "null";
			}else{
				if(valueClausola.contains(".")){
					Double.parseDouble(valueClausola);
				}else{
					Integer.parseInt(valueClausola);
				}
			}
			sbSQL.append(valueClausola);
		} catch (final NumberFormatException e) {
			sbSQL.append("'" + mappa.get(prossimo) + "'");
		}
	}	

	protected void inserisciValore(final Clausola prossimo) {
		try {
			String valueClausola = prossimo.getValore();
			if (valueClausola == null) {
				valueClausola = "null";
			}else{
				if(valueClausola.contains(".")){
					Double.parseDouble(valueClausola);
				}else{
					Integer.parseInt(valueClausola);
				}
			}
			sbSQL.append(valueClausola);
		} catch (final NumberFormatException e) {
			sbSQL.append("'" + prossimo.getValore() + "'");
		}
	}	


	public StringBuffer getSbSQL() {
		return sbSQL;
	}
	
	public void setSbSQL(final StringBuffer sbSQL) {
		this.sbSQL = sbSQL;
	}
}
