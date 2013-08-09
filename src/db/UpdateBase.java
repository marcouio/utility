package db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class UpdateBase extends ObjConClausole {

	private String tabella;
	private HashMap<String, String> campiUpdate = new HashMap<String, String>();
	public UpdateBase() {
		super();
	}
	
	public String getUpdateQuery() throws Exception{
		return getUpdateQuery(tabella, campiUpdate, clausole);
	}

	public String getUpdateQuery(final String tabella, final HashMap<String, String> campi, ArrayList<Clausola> clausole) throws Exception{
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

	public HashMap<String, String> getCampiUpdate() {
		return campiUpdate;
	}

	public void setCampiUpdate(HashMap<String, String> campiUpdate) {
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
