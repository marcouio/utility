package com.molinari.utility.database;

import java.util.Map;

public class AlterBase extends OggettoSQL {

	public AlterBase() {
		super();
	}

	public boolean alterTable(final String comandoSql) throws Exception {
		return false;
	}

	public boolean alterTable(final String tabella, final Map<String, Object> bo) throws Exception {
		introComando();
		return false;
	}

	public void introComando() {
		//default do nothing
	}

}
