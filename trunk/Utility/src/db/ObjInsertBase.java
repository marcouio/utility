package db;

import java.util.HashMap;
import java.util.Iterator;

public class ObjInsertBase extends OggettoSQL{


	private String tabella;
	protected HashMap<String, String> campiInsert = new HashMap<String, String>();

	public ObjInsertBase() {
		super();
	}
	
	public boolean insert() throws Exception{
		return insert(tabella, campiInsert);
	}

	public boolean insert(final String tabella, final HashMap<String, String> campi) throws Exception{
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

		if(aggiornaSqlFromString(sbSQL.toString())){
			return true;
		}
		return false;
	}

	private void middleCommand() {
		sbSQL.append(VALUES);
	}

	private StringBuffer introComando() {
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

	public boolean insert(final String comandoInsert) throws Exception{
		return aggiornaSqlFromString(comandoInsert);			
	}

	public void putCampoValore(final String campo, final String valore){
		campiInsert.put(campo, valore);
	}
	
	public HashMap<String, String> getCampiInsert() {
		return campiInsert;
	}

	public void setCampiInsert(HashMap<String, String> campiInsert) {
		this.campiInsert = campiInsert;
	}

	public String getTabella() {
		return tabella;
	}

	public void setTabella(String tabella) {
		this.tabella = tabella;
	}

	public static void main(String[] args) throws Exception {
		ObjInsertBase oggetto = new ObjInsertBase();
		HashMap<String, String> campi = new HashMap<String, String>();
		campi.put("id", "1");
		campi.put("nome", "Marco");
		oggetto.insert("nomeTab", campi);
	}
}
