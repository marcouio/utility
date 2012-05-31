package db;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;

public class ObjInsertBase extends OggettoSQL{


	public ObjInsertBase(final Connection cn) {
		super(cn);
	}

	public boolean insert(final String tabella, final HashMap<String, String> campi) throws Exception{
		this.tabella = tabella;
		this.campi = campi;

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
		final Iterator<String> iterInsert = campi.keySet().iterator();

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
		campi.put(campo, valore);
	}
	
	public static void main(String[] args) throws Exception {
		ObjInsertBase oggetto = new ObjInsertBase(cn);
		HashMap<String, String> campi = new HashMap<String, String>();
		campi.put("id", "1");
		campi.put("nome", "Marco");
		oggetto.insert("nomeTab", campi);
	}
}
