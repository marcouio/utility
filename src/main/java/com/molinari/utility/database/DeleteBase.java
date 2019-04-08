package com.molinari.utility.database;

import java.util.List;

public class DeleteBase extends ObjConClausole{

	private String tabella;

	public DeleteBase() {
		super();
	}

	public String getDeleteQuery() {
		introComando();
		scriviClausole();
		return sbSQL.toString();
	}
	
	
	public String getDeleteQuery(final String tabella, final List<Clausola> clausole) {
		this.tabella = tabella;
		introComando();
		setClausole(clausole);
		scriviClausole();
		return sbSQL.toString();
	}
	
	private void introComando() {
		if(tabella != null && !"".equals(tabella)) {
			sbSQL.append(DELETE).append(FROM).append(tabella);
		}
	}

	public String getTabella() {
		return tabella;
	}

	public void setTabella(String tabella) {
		this.tabella = tabella;
	}

}
