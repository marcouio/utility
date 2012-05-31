package db;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;

public class ObjUpdateBase extends ObjConClausole {

	public ObjUpdateBase(final Connection cn) {
		super(cn);
	}

	public boolean update(final String tabella, final HashMap<String, String> campi, final HashMap<String, String> clausole) throws Exception{
		this.tabella = tabella;
		this.campi = campi;
		this.clausole = clausole;

		introComando();
		settaCampi();
		settaClausole();

		if (aggiornaSqlFromString(sbSQL.toString())) {
			close();
			return true;
		}
		return false;
	}

	protected void settaCampi() {
		final Iterator<String> iterUpdate = campi.keySet().iterator();
		while (iterUpdate.hasNext()) {
			final String prossimo = iterUpdate.next();
			sbSQL.append(prossimo).append(" = ");
			try {
				if (((String) campi.get(prossimo)).contains(".")) {
					sbSQL.append(Double.parseDouble((String) campi.get(prossimo)));
				} else {
					sbSQL.append(Integer.parseInt((String) campi.get(prossimo)));
				}
			} catch (final NumberFormatException e) {
				sbSQL.append("'" + campi.get(prossimo) + "'");
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


	public static void main(final String[] args) {
		UtilDb.getConnection2("../gestioneSpese.sqlite");
	}

}
