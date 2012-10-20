package db.dao;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import sun.reflect.annotation.AnnotationType;

import command.javabeancommand.AbstractOggettoEntita;

import db.ConnectionPool;
import db.ObjDeleteBase;
import db.ObjInsertBase;
import db.ObjSelectBase;
import db.ObjUpdateBase;

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
			ObjSelectBase selectObj = new ObjSelectBase();
			selectObj.putTabelle(ObjSelectBase.NO_ALIAS, getNomeTabella());
			String nomeCampoId = getNomeCampoId();
			selectObj.putClausole(nomeCampoId, Integer.toString(id));
			final ArrayList<Object> entities = costruisciEntitaFromRs(selectObj.select());
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

	private void setValue(Method method, Object colonnaValue,Class<?> parameterTypes, AbstractOggettoEntita ent) throws Exception {
		GenericDAO dao = UtilityDAO.getDaoByTipo(parameterTypes);
		final Object selectById = dao.selectById(Integer.parseInt(colonnaValue.toString()));
		method.invoke(ent, selectById);
	}

	private void setValue(Method method, String colonnaValue, Class<?> parameterTypes, AbstractOggettoEntita ent) throws Exception {
		if (int.class.equals(parameterTypes)) {
			method.invoke(ent, Integer.parseInt(colonnaValue));
		} else if (double.class.equals(parameterTypes)) {
			method.invoke(ent, Double.parseDouble(colonnaValue));
		} else if (String.class.equals(parameterTypes)) {
			method.invoke(ent, colonnaValue);
		}
	}

	private String costruisciNomeSet(String name) {
		final String upper = name.substring(0, 1).toUpperCase();
		final String restante = name.substring(1);
		String nome = "set" + upper + restante;
		return nome;
	}
	private String costruisciNomeGet(String name) {
		final String upper = name.substring(0, 1).toUpperCase();
		final String restante = name.substring(1);
		String nome = "get" + upper + restante;
		return nome;
	}
	
	

	private void caricaMaps() {
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
	
	public String getNomeTabella() {
		if(nomeTabella == null){
			cercaNomeTabella();
		}
		return nomeTabella;
	}

	public HashMap<String, Field> getMappaColumnJoin() {
		if (mappaColumnJoin == null) {
			caricaMaps();
		}
		return mappaColumnJoin;
	}

	public HashMap<String, Field> getMappaColumnCampi() {
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
	
	private void cercaNomeTabella() {
		
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
	
	private String getNomeTabella(Annotation annotation) {
		String nameColonna = null;
		final Class<? extends Annotation> annotationType = annotation.annotationType();
		final AnnotationType instance = AnnotationType.getInstance(annotationType);
		final Map<String, Method> members = instance.members();
		final Method methodName = members.get("name");
		final InvocationHandler invocationHandler = Proxy.getInvocationHandler(annotation);
		try {
			nameColonna = (String) invocationHandler.invoke(annotation, methodName, null);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return nameColonna;
	}

	private String getNomeColonnaByJoinColumnsAnnotation(Annotation annotation) {
		String nameColonna = null;
		final Class<? extends Annotation> annotationType = annotation.annotationType();
		final AnnotationType instance = AnnotationType.getInstance(annotationType);
		final Map<String, Method> members = instance.members();
		final Method methodName = members.get("value");
		final InvocationHandler invocationHandler = Proxy.getInvocationHandler(annotation);
		try {
			Annotation[] joinColumn = (Annotation[]) invocationHandler.invoke(annotation, methodName, null);
			if(joinColumn != null){
				Annotation column = joinColumn[0];
				final Class<? extends Annotation> annotationType2 = column.annotationType();
				final Method method = annotationType2.getMethod("name", null);
				nameColonna = (String) method.invoke(column, new Object[0]);
			}
			
		} catch (Throwable e) {
			
		}
		return nameColonna;
	}
	
	private String getNomeColonnaByAnnotation(Annotation annotation) {
		String nameColonna = null;
		final Class<? extends Annotation> annotationType = annotation.annotationType();
		final AnnotationType instance = AnnotationType.getInstance(annotationType);
		final Map<String, Method> members = instance.members();
		final Method methodName = members.get("name");
		final InvocationHandler invocationHandler = Proxy.getInvocationHandler(annotation);
		try {
			nameColonna = (String) invocationHandler.invoke(annotation, methodName, null);
		} catch (Throwable e) {
			
		}
		return nameColonna;
	}

	@Override
	public Object selectAll() throws Exception {
		ObjSelectBase selectObj = new ObjSelectBase();
		selectObj.putTabelle(ObjSelectBase.NO_ALIAS, getNomeTabella());
		
		ResultSet rs = selectObj.select();
		return costruisciEntitaFromRs(rs);
	}

	@Override
	public boolean insert(Object oggettoEntita) throws Exception {
		if(oggettoEntita != null){
			ObjInsertBase insertBase = new ObjInsertBase();
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
			return insertBase.insert();
		}
		return false;
	}

	private boolean isIdValido(Object getterCampo) {
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

	private void inserisciCampiValue(String colonna, Object getterCampo,Class<?> parameterTypes, ObjInsertBase insertBase) {
		String valore = null;
		//TODO gestire i campi date
		if(getterCampo instanceof AbstractOggettoEntita){
			valore = ((AbstractOggettoEntita) getterCampo).getIdEntita();
		}else if(getterCampo != null){
			valore = getterCampo.toString();
		}
		insertBase.putCampoValore(colonna, valore);
	}

	@Override
	public boolean delete(int id) throws Exception {
		final ObjDeleteBase deleteBase = new ObjDeleteBase();
		final String nomeCampoId = getNomeCampoId();
		deleteBase.setTabella(getNomeTabella());
		deleteBase.putClausole(nomeCampoId, Integer.toString(id));
		deleteBase.delete();
		return true;
	}

	@Override
	public boolean update(Object oggettoEntita) throws Exception {
		if(oggettoEntita != null){
			final ObjUpdateBase updateBase = new ObjUpdateBase();
			updateBase.setTabella(getNomeTabella());
			final String campoId = getNomeCampoId();
			updateBase.putClausole(campoId, ((AbstractOggettoEntita)oggettoEntita).getIdEntita());
			
			final Iterator<String> iterColumn = getMappaColumnCampi().keySet().iterator();
			while (iterColumn.hasNext()) {
				final String colonna = (String) iterColumn.next();
				final Field field = getMappaColumnCampi().get(colonna);
				final String getMethodName = costruisciNomeGet(field.getName());
				final Method method = getEntita().getClass().getMethod(getMethodName);
				final Object getterCampo = method.invoke(oggettoEntita);
				inserisciCampiUpdate(colonna, getterCampo, field.getType(), updateBase);
				
			}
			updateBase.update();
		}
		return false;
	}
	
	private void inserisciCampiUpdate(String colonna, Object getterCampo, Class<?> parameterTypes, ObjUpdateBase updateBase) {
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
		final ObjDeleteBase deleteBase = new ObjDeleteBase();
		deleteBase.setTabella(getNomeTabella());
		deleteBase.delete();
		return true;
	}

	@Override
	public AbstractOggettoEntita getEntitaPadre() {
		return getEntita();
	}

	@Override
	public Iterator<Object> selectWhere(HashMap<String, String> clausole) throws Exception {
		ObjSelectBase selectObj = new ObjSelectBase();
		selectObj.putTabelle(ObjSelectBase.NO_ALIAS, getNomeTabella());
		selectObj.setClausole(clausole);
		final ArrayList<Object> entities = costruisciEntitaFromRs(selectObj.select());
		ConnectionPool.getSingleton().chiudiOggettiDb(null);
		return entities.iterator();
	}

	public AbstractOggettoEntita getEntita() {
		return entita;
	}

}
