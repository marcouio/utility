package com.molinari.utility.database;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class UpdateBase extends ObjConClausole {

	private String tabella;
	private Map<String, String> campiUpdate = new HashMap<>();
	public UpdateBase() {
		super();
	}
	
	public String getUpdateQuery(){
		return getUpdateQuery(tabella, campiUpdate, clausole);
	}

	public String getUpdateQuery(final String tabella, final Map<String, String> campi, List<Clausola> clausole) {
		this.tabella = tabella;
		this.campiUpdate = campi;
		this.clausole = clausole;

		introComando();
		scriviCampi();
		scriviClausole();

		return sbSQL.toString();
	}

	protected void scriviCampi() {
		final Iterator<String> iterUpdate = campiUpdate.keySet().iterator();
		while (iterUpdate.hasNext()) {
			final String prossimo = iterUpdate.next();
			sbSQL.append(prossimo).append(" = ");
			String updateValue = campiUpdate.get(prossimo);
			try {
				if(updateValue == null){
					sbSQL.append("null");
				}else if (updateValue.contains(".")) {
					sbSQL.append(Double.parseDouble(updateValue));
				} else {
					sbSQL.append(Integer.parseInt(updateValue));
				}
			} catch (final NumberFormatException e) {
				String testoCorretto = FormatterSqlText.correggi(updateValue);
				sbSQL.append("'" + testoCorretto + "'");
			}
			if (iterUpdate.hasNext()) {
				sbSQL.append(", ");
			}
		}
	}

	protected void introComando() {
		sbSQL.append(UPDATE).append(" " + tabella).append(" SET ");
	}

	public Map<String, String> getCampiUpdate() {
		return campiUpdate;
	}

	public void setCampiUpdate(Map<String, String> campiUpdate) {
		this.campiUpdate = campiUpdate;
	}
	
	public void putCampiUpdate(String alias, String campo){
		campiUpdate.put(alias, campo);
	}

	public String getTabella() {
		return tabella;
	}

	public void setTabella(String tabella) {
		this.tabella = tabella;
	}

	public static void main(final String[] args) {
		UtilDb.getConnection("../gestioneSpese.sqlite");
	}

}
