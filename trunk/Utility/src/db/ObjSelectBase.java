package db;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public class ObjSelectBase extends ObjConClausole{

	public static final String NO_ALIAS = "NO_ALIAS";
	private HashMap<String, String> tabelle = new HashMap<String, String>();
	private ArrayList<OggettoJoin> joins = new ArrayList<ObjSelectBase.OggettoJoin>();
	private HashMap<String, String> campiSelect = new HashMap<String, String>();
	private String appendToQuery = null;
	
	public ObjSelectBase() {
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
	public ResultSet select(final String tabella, final HashMap<String, String> clausole) throws Exception{
		HashMap<String, String> mapTable = new HashMap<String, String>();
		mapTable.put(NO_ALIAS, tabella);
		return select(mapTable, null, clausole, null);
	}
	
	/**
	 * Metodo select, da per scontato che gli oggetti che costruiscono la query siano stati riempiti precedentemente, 
	 * quindi non richiede parametri
	 * @return
	 * @throws Exception
	 */
	public ResultSet select() throws Exception{
		return select(tabelle, campiSelect, clausole, joins);
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
	public ResultSet select(final HashMap<String, String> tabelle, 
							final HashMap<String, String> campi, 
							final HashMap<String, String> clausole, 
							final ArrayList<OggettoJoin> joins) throws Exception{
		
		setTabelle(tabelle);
		setCampiSelect(campi);
		setJoins(joins);
		
		introComando();
		settaCampi();
		sbSQL.append(FROM);
		settaTabelle();
		settaClausole(clausole);
		settaJoins();
		appendToQuery();
		return resultSetfromIstruzione(sbSQL.toString());
	}
	
	private void settaJoins() {
		if(joins != null){
			final Iterator<OggettoJoin> iterJoins = joins.iterator();
			while (iterJoins.hasNext()) {
				ObjSelectBase.OggettoJoin oggettoJoin = (ObjSelectBase.OggettoJoin) iterJoins.next();
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

	private void settaTabelle() {
		final Iterator<String> iterAliasTabelle = tabelle.keySet().iterator();
		while (iterAliasTabelle.hasNext()) {
			final String aliasTabelle = (String) iterAliasTabelle.next();
			if(aliasTabelle.startsWith(NO_ALIAS)){
				sbSQL.append(tabelle.get(aliasTabelle));
			}else{
				sbSQL.append(tabelle.get(aliasTabelle) + " " + aliasTabelle);
			}
			if(iterAliasTabelle.hasNext()){
				sbSQL.append(", ");
			}
		}
	}

	private void settaCampi() {
		if(campiSelect == null || campiSelect.get("*") != null || campiSelect.isEmpty()){
			sbSQL.append("*");
		}else{
			final Iterator<String> iterCampi = campiSelect.keySet().iterator();
			
			while (iterCampi.hasNext()) {
				String alias = (String) iterCampi.next();
				if(alias.startsWith(NO_ALIAS)){
					sbSQL.append(campiSelect.get(alias));
				}else{
					String campoConAlias = getCampoAlias(campiSelect.get(alias), alias);	
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

	public class OggettoJoin {
		private String alias1;
		private String campo1;
		private String alias2;
		private String campo2;
		
		public OggettoJoin(final String alias1, final String campo1, final String alias2, final String campo2) {
			this.alias1 = alias1;
			this.alias2 = alias2;
			this.campo1 = campo1;
			this.campo2 = campo2;
		}
		
		@Override
		public String toString() {
			final String campoAlias1 = getCampoAlias(campo1, alias1);
			final String campoAlias2 = getCampoAlias(campo2, alias2);
			return campoAlias1 + " = " + campoAlias2;
		}
	}
	
	protected void settaTable(final HashMap<String, String> table) {
		this.tabelle = table;
		settaTabelle();
	}
	
	public void putTabelle(final String alias, final String tabella){
		tabelle.put(alias, tabella);
	}

	public HashMap<String, String> getTabelle() {
		return tabelle;
	}
	
	public void putJoin(OggettoJoin join){
		joins.add(join);
	}
	
	public ArrayList<OggettoJoin> getJoins() {
		return joins;
	}

	public void setJoins(ArrayList<OggettoJoin> joins) {
		this.joins = joins;
	}

	public HashMap<String, String> getCampiSelect() {
		return campiSelect;
	}

	public void setCampiSelect(HashMap<String, String> campiSelect) {
		this.campiSelect = campiSelect;
	}
	
	public void putCampiSelect(String alias, String campo){
		campiSelect.put(alias, campo);
	}

	public void setTabelle(HashMap<String, String> tabelle) {
		this.tabelle = tabelle;
	}

	public String getAppendToQuery() {
		return appendToQuery;
	}

	public void setAppendToQuery(String appendToQuery) {
		this.appendToQuery = appendToQuery;
	}

	public static void main(String[] args) throws Exception {
		
		ObjSelectBase objSelectBase = new ObjSelectBase();
		objSelectBase.putTabelle("en", "ENTRATE");
		objSelectBase.putTabelle("us", "USCITE");
		
		objSelectBase.putCampiSelect("en", "nome");
		objSelectBase.putClausole("en.nome", "marco");
		OggettoJoin join = objSelectBase.new OggettoJoin("en", "id", "us", "id");
		objSelectBase.putJoin(join);
		objSelectBase.select();
	}
	
}
