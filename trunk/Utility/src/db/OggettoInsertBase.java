package db;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;

public class OggettoInsertBase extends OggettoSQL{


	public OggettoInsertBase(final Connection cn) {
		super(cn);
	}

	public boolean insert(final String tabella, final HashMap<String, Object> campi) throws Exception{
		this.tabella = tabella;
		this.campi = campi;

		introComando();
		sbSQL.append("(");
		inserisciNomiColonne();
		sbSQL.append(")");
		middleCommand();
		sbSQL.append("(");
		inserisciValori();
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
		return sbSQL.append(INSERTINTO).append(tabella);
	}

	protected void inserisciValori() {
		final Iterator<String> iterInsert2 = campi.keySet().iterator();
		while (iterInsert2.hasNext()) {
			final String prossimo = iterInsert2.next();
			try {
				sbSQL.append(Integer.parseInt((String) campi.get(prossimo)));
			} catch (final NumberFormatException e) {
				sbSQL.append("'" + campi.get(prossimo) + "'");
			}
			if (iterInsert2.hasNext())
				sbSQL.append(", ");
		}
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

	public void putCampoValore(final String campo, final Object valore){
		campi.put(campo, valore);
	}
}
