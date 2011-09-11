package db;

import java.sql.Connection;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;

public class OggettoInsertBase extends OggettoSQL{

	private String tabella;
	private HashMap<String, Object> campi = new HashMap<String, Object>();
	private StringBuffer sbSQL = new StringBuffer();
	public OggettoInsertBase(Connection cn) {
		super(cn);
	}
	
	public boolean insert(String tabella, HashMap<String, Object> campi) throws Exception{
		this.tabella = tabella;
		this.campi = campi;
		
		sbSQL.append(INSERTINTO).append(tabella);
		sbSQL.append("(");
		Iterator<String> iterInsert = campi.keySet().iterator();

		while (iterInsert.hasNext()) {
			String prossimo = iterInsert.next();
			// aggiunge nome colonna
			sbSQL.append(prossimo);
			if (iterInsert.hasNext())
				sbSQL.append(", ");
		}
		sbSQL.append(")").append(VALUES).append("(");
		Iterator<String> iterInsert2 = campi.keySet().iterator();
		while (iterInsert2.hasNext()) {
			String prossimo = iterInsert2.next();
			try {
				sbSQL.append(Integer.parseInt((String) campi.get(prossimo)));
			} catch (NumberFormatException e) {
				sbSQL.append("'" + campi.get(prossimo) + "'");
			}
			if (iterInsert2.hasNext())
				sbSQL.append(", ");
		}

		sbSQL.append(")");
		Statement st = cn.createStatement();
		if (st.executeUpdate(sbSQL.toString()) != 0)
			return true;
		cn.close();
		return false;

		
	}
	
	public boolean insert(String comandoInsert) throws Exception{
		return aggiornaSqlFromString(comandoInsert);			
	}
	
	public void putCampoValore(String campo, Object valore){
		campi.put(campo, valore);
	}

	public HashMap<String, Object> getCampi() {
		return campi;
	}

	public void setCampi(HashMap<String, Object> campi) {
		this.campi = campi;
	}

	public String getTabella() {
		return tabella;
	}

	public void setTabella(String tabella) {
		this.tabella = tabella;
	}
	
}
