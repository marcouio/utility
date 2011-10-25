package db;

import java.sql.Connection;
import java.util.HashMap;

public class OggettoAlterBase extends OggettoSQL {

	public OggettoAlterBase(final Connection cn) {
		super(cn);
	}

	public boolean alterTable(final String comandoSql) throws Exception {
		return aggiornaSqlFromString(comandoSql);
	}

	public boolean alterTable(final String tabella, final HashMap<String, Object> bo) throws Exception {
		this.tabella = tabella;
		introComando();
		return false;
	}

	public void introComando() {
		sbSQL.append(ALTERTABLE).append(tabella);
	}

}
