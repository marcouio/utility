package db;

import java.util.HashMap;

public class ObjDeleteBase extends ObjConClausole{

	private String tabella;

	public ObjDeleteBase() {
		super();
	}

	public boolean delete(final String comandoSql){
		return false;
	}

	public boolean delete() throws Exception{
		introComando();
		settaClausole();
		if(aggiornaSqlFromString(sbSQL.toString())){
			return true;
		}
		return false;
	}
	
	
	public boolean delete(final String tabella, final HashMap<String, String> clausole) throws Exception{
		this.tabella = tabella;
		introComando();
		settaClausole(clausole);
		if(aggiornaSqlFromString(sbSQL.toString())){
			return true;
		}
		return false;
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
		ObjDeleteBase oggettoDeleteBase = new ObjDeleteBase();
		HashMap<String, String> clausole = new HashMap<String, String>();
		clausole.put("id", "1");
		clausole.put("nome", "Marco");
		oggettoDeleteBase.delete("nomeTab", clausole);
		
	}
}
