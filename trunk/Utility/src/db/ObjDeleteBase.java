package db;

import java.util.ArrayList;

public class ObjDeleteBase extends ObjConClausole{

	private String tabella;

	public ObjDeleteBase() {
		super();
	}

	public String getDeleteQuery() throws Exception{
		introComando();
		settaClausole();
		return sbSQL.toString();
	}
	
	
	public String getDeleteQuery(final String tabella, final ArrayList<Clausola> clausole) throws Exception{
		this.tabella = tabella;
		introComando();
		settaClausole(clausole);
		return sbSQL.toString();
	}
	
	private void introComando() {
		sbSQL.append(DELETE).append(FROM).append(tabella);
	}

	public String getTabella() {
		return tabella;
	}

	public void setTabella(String tabella) {
		this.tabella = tabella;
	}

	public static void main(String[] args) throws Exception {
//		ObjDeleteBase oggettoDeleteBase = new ObjDeleteBase();
//		HashMap<String, String> clausole = new HashMap<String, String>();
//		clausole.put("id", "1");
//		clausole.put("nome", "Marco");
//		oggettoDeleteBase.delete("nomeTab", clausole);
		
	}
}
