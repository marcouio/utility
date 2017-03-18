package com.molinari.utility.database;

import java.util.ArrayList;

public class DeleteBase extends ObjConClausole{

	private String tabella;

	public DeleteBase() {
		super();
	}

	public String getDeleteQuery() throws Exception{
		introComando();
		scriviClausole();
		return sbSQL.toString();
	}
	
	
	public String getDeleteQuery(final String tabella, final ArrayList<Clausola> clausole) throws Exception{
		this.tabella = tabella;
		introComando();
		setClausole(clausole);
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
		DeleteBase deleteBase = new DeleteBase();
		deleteBase.setTabella("tabNomi");
		deleteBase.putClausole("id", "id", "=", "1");
		deleteBase.putClausole("nome", "nome", "=", "Marco");
		String deleteQuery = deleteBase.getDeleteQuery();
		System.out.println(deleteQuery);
		
	}
}
