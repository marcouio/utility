package db;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;

public class OggettoUpdateBase extends OggettoSQL {

	HashMap<String, Object> clausole = new HashMap<String, Object>();

	public OggettoUpdateBase(final Connection cn) {
		super(cn);
	}

	public boolean update(final String tabella, final HashMap<String, Object> campi, final HashMap<String, Object> clausole) throws Exception{
		this.tabella = tabella;
		this.campi = campi;
		this.clausole = clausole;

		introComando();
		settaCampi();
		settaClausole();

		if (aggiornaSqlFromString(sbSQL.toString())) {
			cn.close();
			return true;
		}
		return false;
	}

	protected void settaClausole() {
		if (!clausole.isEmpty()) {
			sbSQL.append(" WHERE 1=1");
			final Iterator<String> where = clausole.keySet().iterator();

			while (where.hasNext()) {
				sbSQL.append(" AND ");
				final String prossimo = where.next();
				sbSQL.append(prossimo).append(" = ");
				try {
					sbSQL.append(Integer.parseInt((String) clausole.get(prossimo)));
				} catch (final NumberFormatException e) {
					sbSQL.append("'" + clausole.get(prossimo) + "'");
				}
				if (where.hasNext()) {
					sbSQL.append(", ");
				}
			}
		}
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

	public void putClausole(final String where, final Object valore){
		clausole.put(where, valore);
	}

	public HashMap<String, Object> getClausole() {
		return clausole;
	}

	public void setClausole(final HashMap<String, Object> clausole) {
		this.clausole = clausole;
	}

	public static void main(final String[] args) {
		UtilDb.getConnection2("../gestioneSpese.sqlite");
	}

}
