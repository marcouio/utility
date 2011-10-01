package db;

import java.sql.Connection;
import java.util.HashMap;

public class OggettoAlterBase extends OggettoSQL {

	public static final String ADD = "add";
	public static final String DROP_COL = "drop column";
	public static final String ALTER_COL = ""; 

	HashMap<String, String> colonne = new HashMap<String, String>();

	public OggettoAlterBase(final Connection cn) {
		super(cn);
	}

	public boolean alterTable(final String comandoSql) throws Exception {
		return aggiornaSqlFromString(comandoSql);
	}

	public boolean alterTable(final String operazione,final String tabella, final HashMap<String, Object> bo) throws Exception {
		this.tabella = tabella;
		introComando();
		addOperazione(operazione);

		return false;
	}

	private void addOperazione(final String operazione) {
		sbSQL.append(" ").append(operazione).append(" ");
	}

	public void introComando() {
		sbSQL.append(ALTERTABLE).append(tabella);
	}

	public HashMap<String, String> getColonne() {
		return colonne;
	}

	public void setColonne(final HashMap<String, String> colonne) {
		this.colonne = colonne;
	}



}
