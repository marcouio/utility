package db;

import java.util.ArrayList;
import java.util.Iterator;

public class ObjConClausole extends OggettoSQL{

	ArrayList<Clausola> clausole = new ArrayList<Clausola>();
	
	public ObjConClausole() {
		super();
	}
	
	protected void scriviClausole() {
		if (!clausole.isEmpty()) {
			sbSQL.append(" WHERE 1=1");
			final Iterator<Clausola> where = clausole.iterator();
			while (where.hasNext()) {
				
				sbSQL.append(" AND ");

				final Clausola prossimo = (Clausola) where.next();
				
				sbSQL.append(prossimo);

			}
			
			if (where.hasNext()) {
				sbSQL.append(", ");
			}
		}
	}
	
	public void setClausole(final ArrayList<Clausola> clausole) {
		this.clausole = clausole;
	}
	
	public void putClausole(Clausola clausola){
		clausole.add(clausola);
	}
	
	public void putClausole(final String alias, String campo, String operatore, final String valore){
		Clausola clausola = new Clausola(alias, campo, operatore, valore);
		clausole.add(clausola);
	}

	public ArrayList<Clausola> getClausole() {
		return clausole;
	}
	
}
