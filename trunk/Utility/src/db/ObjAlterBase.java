package db;

import java.util.HashMap;

public class ObjAlterBase extends OggettoSQL {

	public ObjAlterBase() {
		super();
	}

	public boolean alterTable(final String comandoSql) throws Exception {
		return aggiornaSqlFromString(comandoSql);
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
