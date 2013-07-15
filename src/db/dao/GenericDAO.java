package db.dao;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import sun.reflect.annotation.AnnotationType;

import command.javabeancommand.AbstractOggettoEntita;

import db.Clausola;
import db.ConnectionPool;
import db.DeleteBase;
import db.InsertBase;
import db.SelectBase;
import db.UpdateBase;
import db.Query;

public class GenericDAO implements IDAO {

	private String nomeTabella;
	private String nomeCampoId;
	private AbstractOggettoEntita entita;
	private HashMap<String, Field> mappaColumnCampi = null;
	private HashMap<String, Field> mappaColumnJoin = null;

	public GenericDAO(final AbstractOggettoEntita entita) {
		this.entita = entita;
	}

	@Override
	public Object selectById(final int id) throws Exception {
		try {
			SelectBase selectObj = new SelectBase();
			selectObj.putTabelle(null, getNomeTabella());
			String nomeCampoId = getNomeCampoId();
			Clausola clausolaId = new Clausola(null, nomeCampoId, "=", Integer.toString(id));
			selectObj.putClausole(clausolaId);
			Query query = new Query();
			final ArrayList<Object> entities = costruisciEntitaFromRs(query.select(selectObj));
			ConnectionPool.getSingleton().chiudiOggettiDb(null);
			if (entities != null && entities.size() > 0) {
				return entities.get(0);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	protected ArrayList<Object> costruisciEntitaFromRs(ResultSet select) throws Exception {
		ArrayList<Object> listaReturn = new ArrayList<Object>();
		while (select.next()) {
			final Iterator<String> iterColumn = getMappaColumnCampi().keySet().iterator();
			final AbstractOggettoEntita ent = getEntita().getClass().newInstance();
			listaReturn.add(ent);
			while (iterColumn.hasNext()) {
				final String colonna = (String) iterColumn.next();
				final String colonnaValue = select.getString(colonna);
				final Field field = getMappaColumnCampi().get(colonna);
				final Class<?> parameterTypes = field.getType();
				final String methodName = costruisciNomeSet(field.getName());
				final Method method = getEntita().getClass().getMethod(methodName,parameterTypes);
				
				setValue(method, colonnaValue, parameterTypes, ent);
			}
			final Iterator<String> iterJoin = getMappaColumnJoin().keySet().iterator();
			while (iterJoin.hasNext()) {
				final String join = (String) iterJoin.next();
				final int colonnaValue = select.getInt(join);
				final Field field = getMappaColumnJoin().get(join);
				final Class<?> parameterTypes = field.getType();
				final String methodName = costruisciNomeSet(field.getName());
				final Method method = getEntita().getClass().getMethod(methodName,parameterTypes);

				setValue(method, colonnaValue, parameterTypes, ent);

			}
		}
		return listaReturn;
	}

	protected void setValue(Method method, Object colonnaValue,Class<?> parameterTypes, AbstractOggettoEntita ent) throws Exception {
		GenericDAO dao = UtilityDAO.getDaoByTipo(parameterTypes);
		final Object selectById = dao.selectById(Integer.parseInt(colonnaValue.toString()));
		method.invoke(ent, selectById);
	}

	protected void setValue(Method method, String colonnaValue, Class<?> parameterTypes, AbstractOggettoEntita ent) throws Exception {
		if (int.class.equals(parameterTypes)) {
			method.invoke(ent, Integer.parseInt(colonnaValue));
		} else if (double.class.equals(parameterTypes)) {
			method.invoke(ent, Double.parseDouble(colonnaValue));
		} else if (String.class.equals(parameterTypes)) {
			method.invoke(ent, colonnaValue);
		}
	}

	protected String costruisciNomeSet(String name) {
		final String upper = name.substring(0, 1).toUpperCase();
		final String restante = name.substring(1);
		String nome = "set" + upper + restante;
		return nome;
	}
	
	protected String costruisciNomeGet(String name) {
		final String upper = name.substring(0, 1).toUpperCase();
		final String restante = name.substring(1);
		String nome = "get" + upper + restante;
		return nome;
	}
	
	

	protected void caricaMaps() {
		mappaColumnCampi = new HashMap<String, Field>();
		mappaColumnJoin = new HashMap<String, Field>();
		Field[] fields = getEntita().getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			Annotation[] annotations = field.getAnnotations();
			if(annotations != null && annotations.length > 0){
				for (int j = 0; j < annotations.length; j++) {
	
					String nameColonna = null;
					Annotation annotation = (Annotation) annotations[j];
					Class<? extends Annotation> annotationType = annotation.annotationType();
					String name = annotationType.getName();
					
					if ("javax.persistence.Column".equals(name)) {
						nameColonna = getNomeColonnaByAnnotation(annotation);
						if(nameColonna != null && !nameColonna.equals("")){
							getMappaColumnCampi().put(nameColonna,field);
						}else{
							getMappaColumnCampi().put(field.getName(), field);
						}
	
					} else if ("javax.persistence.JoinColumns".equals(name)) {
						nameColonna = getNomeColonnaByJoinColumnsAnnotation(annotation);
						if(nameColonna != null && !nameColonna.equals("")){
							getMappaColumnJoin().put(nameColonna,field);
						}else{
							getMappaColumnJoin().put(field.getName(), field);
						}
					}
				}
			}else{
				getMappaColumnCampi().put(field.getName(), field);
			}
		}
	}
	
	protected String getNomeTabella() {
		if(nomeTabella == null){
			cercaNomeTabella();
		}
		return nomeTabella;
	}

	protected HashMap<String, Field> getMappaColumnJoin() {
		if (mappaColumnJoin == null) {
			caricaMaps();
		}
		return mappaColumnJoin;
	}

	protected HashMap<String, Field> getMappaColumnCampi() {
		if (mappaColumnCampi == null) {
			caricaMaps();
		}
		return mappaColumnCampi;
	}

	protected String getNomeCampoId() throws Exception {

		if (nomeCampoId != null) {
			return nomeCampoId;
		}

		final Field[] fields = getEntita().getClass().getDeclaredFields();
		if (fields != null) {
			primo:
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				boolean idTrovato = false;
				Annotation[] annotations = field.getAnnotations();
				for (int j = 0; j < annotations.length; j++) {

					String nameColonna = null;
					Annotation annotation = (Annotation) annotations[j];
					Class<? extends Annotation> annotationType = annotation.annotationType();
					String name = annotationType.getName();
					if ("javax.persistence.Id".equals(name)) {
						idTrovato = true;
					}
					if ("javax.persistence.Column".equals(name)) {
						nameColonna = getNomeColonnaByAnnotation(annotation);
					}
					if (idTrovato && nameColonna != null) {
						nomeCampoId = nameColonna;
						break primo;
					}
				}
			}
		}
		return nomeCampoId;
	}
	
	protected void cercaNomeTabella() {
		
		Annotation[] annotations = getEntita().getClass().getAnnotations();
		
		for (int j = 0; j < annotations.length; j++) {

			Annotation annotation = (Annotation) annotations[j];
			Class<? extends Annotation> annotationType = annotation.annotationType();
			String name = annotationType.getName();
			if("javax.persistence.Entity".equals(name) || "javax.persistence.Table".equals(name)){
				nomeTabella = getNomeTabella(annotation);
				if(nomeTabella != null && !nomeTabella.equals(""))return;
			}
		
		}
		if(nomeTabella == null && !"".equals(nomeTabella)){
			nomeTabella = getEntita().getClass().getSimpleName();
		}
	}
	
	protected Object getOggettoByAnnotation(Annotation annotation, String tipo){
		Object oggetto = null;
		final Class<? extends Annotation> annotationType = annotation.annotationType();
		final AnnotationType instance = AnnotationType.getInstance(annotationType);
		final Map<String, Method> members = instance.members();
		final Method methodName = members.get(tipo);
		final InvocationHandler invocationHandler = Proxy.getInvocationHandler(annotation);
		try {
			oggetto = invocationHandler.invoke(annotation, methodName, null);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return oggetto;
	}
	
	protected String getNomeTabella(Annotation annotation) {
		return (String) getOggettoByAnnotation(annotation, "name");
	}

	protected String getNomeColonnaByJoinColumnsAnnotation(Annotation annotation) {
		String nameColonna = null;
		try{
			Annotation[] joinColumn = (Annotation[]) getOggettoByAnnotation(annotation, "value");
			
			if(joinColumn != null){
				Annotation column = joinColumn[0];
				final Class<? extends Annotation> annotationType2 = column.annotationType();
				final Method method = annotationType2.getMethod("name");
				nameColonna = (String) method.invoke(column, new Object[0]);
			}
		}catch (Exception e) {
			
		}
		return nameColonna;
	}
	
	private String getColumnDefinitionByAnnotation(Annotation annotation) {
		return (String) getOggettoByAnnotation(annotation, "columnDefinition");
	}
	
	protected String getNomeColonnaByAnnotation(Annotation annotation) {
		return (String) getOggettoByAnnotation(annotation, "name");
	}

	@Override
	public Object selectAll() throws Exception {
		SelectBase selectObj = new SelectBase();
		selectObj.putTabelle(getNomeTabella(), getNomeTabella());
		Query query = new Query();
		ResultSet rs = query.select(selectObj);
		return costruisciEntitaFromRs(rs);
	}

	@Override
	public boolean insert(Object oggettoEntita) throws Exception {
		if(oggettoEntita != null){
			InsertBase insertBase = new InsertBase();
			insertBase.setTabella(getNomeTabella());
			final Iterator<String> iterColumn = getMappaColumnCampi().keySet().iterator();
			while (iterColumn.hasNext()) {
				final String colonna = (String) iterColumn.next();
				final Field field = getMappaColumnCampi().get(colonna);
				final String getMethodName = costruisciNomeGet(field.getName());
				final Method method = getEntita().getClass().getMethod(getMethodName);
				Object getterCampo = method.invoke(oggettoEntita);
				if(!colonna.equals(getNomeCampoId()) || isIdValido(getterCampo)){
					inserisciCampiValue(colonna, getterCampo, field.getType(), insertBase);
				}
				
			}
			Iterator<String> iterJoinColumn = getMappaColumnJoin().keySet().iterator();
			while(iterJoinColumn.hasNext()){
				final String colonna = (String) iterJoinColumn.next();
				final Field field = getMappaColumnJoin().get(colonna);
				final String getMethodName = costruisciNomeGet(field.getName());
				final Method method = getEntita().getClass().getMethod(getMethodName);
				Object getterCampo = method.invoke(oggettoEntita);
				inserisciCampiValue(colonna, getterCampo, field.getType(), insertBase);
			}
			Query query = new Query();
			return query.insert(insertBase);
		}
		return false;
	}

	protected boolean isIdValido(Object getterCampo) {
		boolean valido = false;
		if(getterCampo != null){
			if(getterCampo instanceof Number){
				Long id = new Long(getterCampo.toString());
				if(id.longValue() > 0){
					valido = true;
				}
			}else{
				valido = true;
			}
		}
		return valido;
	}

	protected void inserisciCampiValue(String colonna, Object getterCampo,Class<?> parameterTypes, InsertBase insertBase) {
		String valore = null;
		//TODO gestire i campi date
		if(getterCampo instanceof AbstractOggettoEntita){
			valore = ((AbstractOggettoEntita) getterCampo).getIdEntita();
		}else if(getterCampo instanceof Date){
			
		}else if(getterCampo != null){
			valore = getterCampo.toString();
		}
		insertBase.putCampoValore(colonna, valore);
	}

	@Override
	public boolean delete(int id) throws Exception {
		final DeleteBase deleteBase = new DeleteBase();
		final String nomeCampoId = getNomeCampoId();
		deleteBase.setTabella(getNomeTabella());
		deleteBase.putClausole(null, nomeCampoId, "=", Integer.toString(id));
		Query query = new Query();
		return query.delete(deleteBase);
	}

	@Override
	public boolean update(Object oggettoEntita) throws Exception {
		if(oggettoEntita != null){
			final UpdateBase updateBase = new UpdateBase();
			updateBase.setTabella(getNomeTabella());
			final String campoId = getNomeCampoId();
			String idEntita = ((AbstractOggettoEntita)oggettoEntita).getIdEntita();
			updateBase.putClausole(null, campoId, "=", idEntita);
			final Iterator<String> iterColumn = getMappaColumnCampi().keySet().iterator();
			while (iterColumn.hasNext()) {
				final String colonna = (String) iterColumn.next();
				final Field field = getMappaColumnCampi().get(colonna);
				final String getMethodName = costruisciNomeGet(field.getName());
				final Method method = getEntita().getClass().getMethod(getMethodName);
				final Object getterCampo = method.invoke(oggettoEntita);
				inserisciCampiUpdate(colonna, getterCampo, field.getType(), updateBase);
				
			}
			Query query = new Query();
			return query.update(updateBase);
		}
		return false;
	}
	
	protected void inserisciCampiUpdate(String colonna, Object getterCampo, Class<?> parameterTypes, UpdateBase updateBase) {
		String valore = null;
		//TODO gestire i campi date
		if(getterCampo instanceof AbstractOggettoEntita){
			final String id = ((AbstractOggettoEntita)getterCampo).getIdEntita();
			valore = id;
		}else if(getterCampo != null){
			valore = getterCampo.toString();
		}
		
		updateBase.putCampiUpdate(colonna, valore);
	}

	@Override
	public boolean deleteAll() throws Exception {
		final DeleteBase deleteBase = new DeleteBase();
		deleteBase.setTabella(getNomeTabella());
		return new Query().delete(deleteBase);
	}

	@Override
	public AbstractOggettoEntita getEntitaPadre() {
		return getEntita();
	}

	@Override
	public Iterator<Object> selectWhere(ArrayList<Clausola> clausole, String appendToQuery) throws Exception {
		SelectBase selectObj = new SelectBase();
		selectObj.putTabelle(getNomeTabella(), getNomeTabella());
		selectObj.setClausole(clausole);
		selectObj.setAppendToQuery(appendToQuery);
		final ArrayList<Object> entities = costruisciEntitaFromRs(new Query().select(selectObj));
		ConnectionPool.getSingleton().chiudiOggettiDb(null);
		return entities.iterator();
	}

	public AbstractOggettoEntita getEntita() {
		return entita;
	}

}
