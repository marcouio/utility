package com.molinari.utility.database;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class InsertBase extends OggettoSQL{

	private String tabella;
	protected Map<String, String> campiInsert = new HashMap<>();

	public InsertBase() {
		super();
	}
	
	public String getInsertQuery() {
		return getInsertQuery(tabella, campiInsert);
	}

	public String getInsertQuery(final String tabella, final Map<String, String> campi) {
		this.tabella = tabella;
		this.campiInsert = campi;

		introComando();
		sbSQL.append("(");
		inserisciNomiColonne();
		sbSQL.append(")");
		middleCommand();
		sbSQL.append("(");
		final Iterator<String> iterInsert2 = campi.keySet().iterator();
		while (iterInsert2.hasNext()) {
			final String prossimo = iterInsert2.next();
			inserisciValore(prossimo, campi);
			if(iterInsert2.hasNext()){
				sbSQL.append(", ");
			}
		}
		sbSQL.append(")");

		return sbSQL.toString();
	}

	private void middleCommand() {
		sbSQL.append(VALUES);
	}

	private StringBuilder introComando() {
		return sbSQL.append(INSERTINTO).append(tabella).append(" ");
	}

	protected void inserisciNomiColonne() {
		final Iterator<String> iterInsert = campiInsert.keySet().iterator();

		while (iterInsert.hasNext()) {
			final String prossimo = iterInsert.next();
			// aggiunge nome colonna
			sbSQL.append(prossimo);
			if (iterInsert.hasNext())
				sbSQL.append(", ");
		}
	}

	public static String getDateForDatabase(Date data){
		return UtilDb.dataToString(data, "yyyy-mm-dd");
	}

	public void putCampoValore(final String campo, final String valore){
		campiInsert.put(campo, valore);
	}
	
	public Map<String, String> getCampiInsert() {
		return campiInsert;
	}

	public void setCampiInsert(Map<String, String> campiInsert) {
		this.campiInsert = campiInsert;
	}

	public String getTabella() {
		return tabella;
	}

	public void setTabella(String tabella) {
		this.tabella = tabella;
	}
}
