package db;

import java.sql.Connection;
import java.util.HashMap;

public class ObjDeleteBase extends ObjConClausole{


	public ObjDeleteBase(final Connection cn) {
		super(cn);
	}

	public boolean delete(final String comandoSql){
		return false;
	}

	public boolean delete() throws Exception{
		introComando(tabella);
		settaClausole();
		if(aggiornaSqlFromString(sbSQL.toString())){
			close();
			return true;
		}
		close();
		return false;
	}
	
	public boolean delete(final String tabella, final HashMap<String, String> clausole) throws Exception{
		introComando(tabella);
		settaClausole(clausole);
		if(aggiornaSqlFromString(sbSQL.toString())){
			close();
			return true;
		}
		close();
		return false;
	}
	
	private void introComando(final String tabella) {
		this.tabella = tabella;
		sbSQL.append(DELETE).append(FROM).append(tabella);
	}

	public HashMap<String, String> getClausole() {
		return clausole;
	}

	public void setClausole(final HashMap<String, String> clausole) {
		this.clausole = clausole;
	}
	
	public static void main(String[] args) throws Exception {
		ObjDeleteBase oggettoDeleteBase = new ObjDeleteBase(cn);
		HashMap<String, String> clausole = new HashMap<String, String>();
		clausole.put("id", "1");
		clausole.put("nome", "Marco");
		oggettoDeleteBase.delete("nomeTab", clausole);
		
	}
}
