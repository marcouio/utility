package main.java.com.molinari.utility.database;

import java.util.HashMap;

public class AlterBase extends OggettoSQL {

	public AlterBase() {
		super();
	}

	public boolean alterTable(final String comandoSql) throws Exception {
		return false;
//		return aggiornaSqlFromString(comandoSql);
	}

	public boolean alterTable(final String tabella, final HashMap<String, Object> bo) throws Exception {
//		this.tabella = tabella;
		introComando();
		return false;
	}

	public void introComando() {
//		sbSQL.append(ALTERTABLE).append(tabella);
	}

}
