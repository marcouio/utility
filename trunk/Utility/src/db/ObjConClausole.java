package db;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;

public class ObjConClausole extends OggettoSQL{

	HashMap<String, String> clausole = new HashMap<String, String>();
	
	public ObjConClausole(Connection cn) {
		super(cn);
	}
	
	protected void settaClausole() {
		if (!clausole.isEmpty()) {
			sbSQL.append(" WHERE 1=1");
			final Iterator<String> where = clausole.keySet().iterator();
			while (where.hasNext()) {
				
				sbSQL.append(" AND ");

				final String prossimo = where.next();
				
				sbSQL.append(prossimo).append(" = ");

				inserisciValore(prossimo, clausole);
			}
			
			if (where.hasNext()) {
				sbSQL.append(", ");
			}
		}
	}
	
	protected void settaClausole(final HashMap<String, String> clausole) {
		this.clausole = clausole;
		settaClausole();
	}
	
	public void putClausole(final String where, final String valore){
		clausole.put(where, valore);
	}

	public HashMap<String, String> getClausole() {
		return clausole;
	}

	public void setClausole(final HashMap<String, String> clausole) {
		this.clausole = clausole;
	}
	
}
