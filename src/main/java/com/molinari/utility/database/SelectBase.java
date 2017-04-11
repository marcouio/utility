package com.molinari.utility.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class SelectBase extends ObjConClausole{

	private Map<String, String> tabelle = new HashMap<>();
	private List<Join> joins = new ArrayList<>();
	private Map<String, String> campiSelect = new HashMap<>();
	private String appendToQuery = null;
	
	public SelectBase() {
		super();
	}
	
	/**
	 * Metodo per select semplici su una singola tabella, in cui estrarre tutti i campi, e filtrate in base 
	 * alle clausole where passate come parametro
	 * 
	 * @param tabella
	 * @param clausole
	 * @return
	 * @throws Exception
	 */
	public String getQuery(final String tabella, final List<Clausola> clausole) {
		final HashMap<String, String> mapTable = new HashMap<>();
		mapTable.put(tabella, tabella);
		return getQuery(mapTable, null, clausole, null);
	}
	
	/**
	 * Metodo select, da per scontato che gli oggetti che costruiscono la query siano stati riempiti precedentemente, 
	 * quindi non richiede parametri
	 * @return
	 * @throws Exception
	 */
	public String getQuery() {
		return getQuery(tabelle, campiSelect, clausole, joins);
	}
	
	/**
	 * Metodo per chiamare query complesse. Permette di inserire tutti i parametri.
	 * @param tabelle, tutte le tabelle su cui costruire il cursore
	 * @param campi, campi da estrarre
	 * @param clausole, classiche clausole 'where' con coppia campo - valore
	 * @param joins, oggetti join per collegare le diverse tabelle
	 * @return
	 * @throws Exception
	 */
	public String getQuery(final Map<String, String> tabelle, 
							final Map<String, String> campi, 
							final List<Clausola> clausole, 
							final List<Join> joins) {
		
		setTable(tabelle);
		setCampiSelect(campi);
		setJoins(joins);
		setClausole(clausole);
		
		introComando();
		appendCampi();
		sbSQL.append(FROM);
		appendTabelle();
		scriviClausole();
		appendJoins();
		appendToQuery();
		return sbSQL.toString();
	}
	
	private void appendJoins() {
		if(joins != null){
			final Iterator<Join> iterJoins = joins.iterator();
			while (iterJoins.hasNext()) {
				final Join oggettoJoin = iterJoins.next();
				sbSQL.append(AND);
				sbSQL.append(oggettoJoin.toString());
			}
		}
	}
	
	protected void appendToQuery(){
		if(appendToQuery != null){
			sbSQL.append(appendToQuery);
		}
	}

	protected void appendTabelle() {
		final Iterator<String> iterAliasTabelle = tabelle.keySet().iterator();
		while (iterAliasTabelle.hasNext()) {
			final String aliasTabelle = iterAliasTabelle.next();
			if(aliasTabelle.equals(tabelle.get(aliasTabelle))){
				sbSQL.append(tabelle.get(aliasTabelle));
			}else{
				sbSQL.append(tabelle.get(aliasTabelle) + " " + aliasTabelle);
			}
			if(iterAliasTabelle.hasNext()){
				sbSQL.append(", ");
			}
		}
	}

	protected void appendCampi() {
		if(campiSelect == null || campiSelect.get("*") != null || campiSelect.isEmpty()){
			sbSQL.append("*");
		}else{
			final Iterator<String> iterCampi = campiSelect.keySet().iterator();
			
			while (iterCampi.hasNext()) {
				final String alias = iterCampi.next();
				if(alias.equals(campiSelect.get(alias))){
					sbSQL.append(campiSelect.get(alias));
				}else{
					final String campoConAlias = getCampoAlias(campiSelect.get(alias), alias);	
					sbSQL.append(campoConAlias);
				}
				if(iterCampi.hasNext()){
					sbSQL.append(", ");
				}
			}
		}
	}
	

	public String getCampoAlias(final String campo, final String alias){
		return alias + "." + campo;
	}
	
	private void introComando() {
		sbSQL.append(SELECT).append(" ");
	}

	protected void setTable(final Map<String, String> table) {
		this.tabelle = table;
	}
	
	public void putTabelle(final String alias, final String tabella){
		tabelle.put(alias, tabella);
	}

	public Map<String, String> getTabelle() {
		return tabelle;
	}
	
	public void putJoin(Join join){
		joins.add(join);
	}
	
	public List<Join> getJoins() {
		return joins;
	}

	public void setJoins(List<Join> joins) {
		this.joins = joins;
	}

	public Map<String, String> getCampiSelect() {
		return campiSelect;
	}

	public void setCampiSelect(Map<String, String> campiSelect) {
		this.campiSelect = campiSelect;
	}
	
	public void putCampiSelect(String alias, String campo){
		campiSelect.put(alias, campo);
	}

	public String getAppendToQuery() {
		return appendToQuery;
	}

	public void setAppendToQuery(String appendToQuery) {
		this.appendToQuery = appendToQuery;
	}
}
