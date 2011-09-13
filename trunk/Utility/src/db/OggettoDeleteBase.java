package db;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;

public class OggettoDeleteBase extends OggettoSQL{

	HashMap<String, Object> clausole = new HashMap<String, Object>();

	public OggettoDeleteBase(final Connection cn) {
		super(cn);
	}

	public boolean delete(final String comandoSql){
		return false;
	}

	public boolean delete() throws Exception{
		introComando(tabella);
		settaClausole();
		if(aggiornaSqlFromString(sbSQL.toString())){
			cn.close();
			return true;
		}
		cn.close();
		return false;
	}

	public boolean delete(final String tabella, final HashMap<String, Object> clausole) throws Exception{
		introComando(tabella);
		settaClausole(clausole);
		if(aggiornaSqlFromString(sbSQL.toString())){
			cn.close();
			return true;
		}
		cn.close();
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
			}
			if (where.hasNext()) {
				sbSQL.append(", ");
			}
		}
	}

	protected void settaClausole(final HashMap<String, Object> clausole) {
		this.clausole = clausole;
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
			}
			if (where.hasNext()) {
				sbSQL.append(", ");
			}
		}
	}

	private void introComando(final String tabella) {
		this.tabella = tabella;
		sbSQL.append(DELETE).append(FROM).append(tabella);
	}

	public HashMap<String, Object> getClausole() {
		return clausole;
	}

	public void setClausole(final HashMap<String, Object> clausole) {
		this.clausole = clausole;
	}
}
