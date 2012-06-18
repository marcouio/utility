package db;

import java.util.HashMap;
import java.util.Iterator;

public class ObjUpdateBase extends ObjConClausole {

	private String tabella;
	private HashMap<String, String> campiUpdate = new HashMap<String, String>();
	public ObjUpdateBase() {
		super();
	}

	public boolean update(final String tabella, final HashMap<String, String> campi, final HashMap<String, String> clausole) throws Exception{
		this.tabella = tabella;
		this.campiUpdate = campi;
		this.clausole = clausole;

		introComando();
		settaCampi();
		settaClausole();

		if (aggiornaSqlFromString(sbSQL.toString())) {
			return true;
		}
		return false;
	}

	protected void settaCampi() {
		final Iterator<String> iterUpdate = campiUpdate.keySet().iterator();
		while (iterUpdate.hasNext()) {
			final String prossimo = iterUpdate.next();
			sbSQL.append(prossimo).append(" = ");
			try {
				if (((String) campiUpdate.get(prossimo)).contains(".")) {
					sbSQL.append(Double.parseDouble((String) campiUpdate.get(prossimo)));
				} else {
					sbSQL.append(Integer.parseInt((String) campiUpdate.get(prossimo)));
				}
			} catch (final NumberFormatException e) {
				sbSQL.append("'" + campiUpdate.get(prossimo) + "'");
			}
			if (iterUpdate.hasNext()) {
				sbSQL.append(", ");
			}
		}
	}

	protected void introComando() {
		sbSQL.append(UPDATE).append(" " + tabella).append(" SET ");
	}

	public boolean update(final String comandoSql) throws Exception{
		return aggiornaSqlFromString(comandoSql);
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

	public static void main(final String[] args) {
		UtilDb.getConnection("../gestioneSpese.sqlite");
	}

}
