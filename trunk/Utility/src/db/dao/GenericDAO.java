package db.dao;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import sun.reflect.annotation.AnnotationType;

import command.javabeancommand.AbstractOggettoEntita;

import db.ConnectionPool;
import db.ObjDeleteBase;
import db.ObjSelectBase;
import db.ObjUpdateBase;

public class GenericDAO implements IDAO {

	private String nomeTabella;
	private String nomeCampoId;
	private AbstractOggettoEntita entita;
	private HashMap<String, Field> mappaColumnCampi = new HashMap<String, Field>();
	private HashMap<String, Field> mappaColumnJoin = new HashMap<String, Field>();

	public GenericDAO(String nomeTabella, final AbstractOggettoEntita entita) {
		this.nomeTabella = nomeTabella;
		this.entita = entita;
	}

	@Override
	public Object selectById(final int id) throws Exception {
		try {
			ObjSelectBase selectObj = new ObjSelectBase();
			selectObj.putTabelle(ObjSelectBase.NO_ALIAS, nomeTabella);
			String nomeCampoId = getNomeCampoId();
			selectObj.putClausole(nomeCampoId, Integer.toString(id));
			final ArrayList<Object> entities = costruisciEntitaFromRs(selectObj.select());
			ConnectionPool.chiudiOggettiDb(null);
			if (entities != null && entities.size() > 0) {
				return entities.get(0);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private ArrayList<Object> costruisciEntitaFromRs(ResultSet select) throws Exception {
		ArrayList<Object> listaReturn = new ArrayList<Object>();
		while (select.next()) {
			final Iterator<String> iterColumn = getMappaColumnCampi().keySet().iterator();
			final AbstractOggettoEntita ent = entita.getClass().newInstance();
			listaReturn.add(ent);
			while (iterColumn.hasNext()) {
				final String colonna = (String) iterColumn.next();
				final String colonnaValue = select.getString(colonna);
				final Field field = mappaColumnCampi.get(colonna);
				final Class<?> parameterTypes = field.getType();
				final String methodName = costruisciNomeSet(field.getName());
				final Method method = entita.getClass().getMethod(methodName,parameterTypes);
				
				setValue(method, colonnaValue, parameterTypes, ent);
			}
			final Iterator<String> iterJoin = getMappaColumnJoin().keySet().iterator();
			while (iterJoin.hasNext()) {
				final String join = (String) iterJoin.next();
				final int colonnaValue = select.getInt(join);
				final Field field = mappaColumnJoin.get(join);
				final Class<?> parameterTypes = field.getType();
				final String methodName = costruisciNomeSet(field.getName());
				final Method method = entita.getClass().getMethod(methodName,parameterTypes);

				setValue(method, colonnaValue, parameterTypes, ent);

			}
		}
		return listaReturn;
	}

	private void setValue(Method method, Object colonnaValue,Class<?> parameterTypes, AbstractOggettoEntita ent) throws Exception {
		GenericDAO dao = UtilityDAO.getSingleton().getDaoByTipo(parameterTypes);
		final Object selectById = dao.selectById(Integer.parseInt(colonnaValue
				.toString()));
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

	private void caricaMap() {
		Field[] fields = this.entita.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			Annotation[] annotations = field.getAnnotations();
			for (int j = 0; j < annotations.length; j++) {

				String nameColonna = null;
				Annotation annotation = (Annotation) annotations[j];
				Class<? extends Annotation> annotationType = annotation.annotationType();
				String name = annotationType.getName();
				if ("javax.persistence.Column".equals(name)) {
					AnnotationType instance = AnnotationType.getInstance(annotationType);
					Map<String, Method> members = instance.members();
					Method methodName = members.get("name");
					InvocationHandler invocationHandler = Proxy.getInvocationHandler(annotation);
					try {
						nameColonna = (String) invocationHandler.invoke(annotation, methodName, null);
					} catch (Throwable e) {
						e.printStackTrace();
					}
					mappaColumnCampi.put(nameColonna.substring(1, nameColonna.length() - 1),field);

				} else if ("javax.persistence.JoinColumn".equals(name)) {
					AnnotationType instance = AnnotationType.getInstance(annotationType);
					Map<String, Method> members = instance.members();
					Method methodName = members.get("name");
					InvocationHandler invocationHandler = Proxy.getInvocationHandler(annotation);
					try {
						nameColonna = (String) invocationHandler.invoke(annotation, methodName, null);
					} catch (Throwable e) {
						e.printStackTrace();
					}
					mappaColumnJoin.put(nameColonna.substring(1, nameColonna.length() - 1),field);
				}
			}
		}
	}

	public HashMap<String, Field> getMappaColumnJoin() {
		if (mappaColumnJoin.isEmpty()) {
			caricaMap();
		}
		return mappaColumnJoin;
	}

	public HashMap<String, Field> getMappaColumnCampi() {
		if (mappaColumnCampi.isEmpty()) {
			caricaMap();
		}
		return mappaColumnCampi;
	}

	protected String getNomeCampoId() throws Exception {

		if (nomeCampoId != null) {
			return nomeCampoId;
		}

		final Field[] fields = this.entita.getClass().getDeclaredFields();
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
						AnnotationType instance = AnnotationType.getInstance(annotationType);
						Map<String, Method> members = instance.members();
						Method methodName = members.get("name");
						InvocationHandler invocationHandler = Proxy.getInvocationHandler(annotation);
						try {
							nameColonna = (String) invocationHandler.invoke(annotation, methodName, null);
						} catch (Throwable e) {
							e.printStackTrace();
						}
					}
					if (idTrovato && nameColonna != null) {
						nomeCampoId = nameColonna.substring(1,nameColonna.length() - 1);
						break primo;
					}
				}
			}
		}
		return nomeCampoId;
	}

	@Override
	public Object selectAll() throws Exception {
		ObjSelectBase selectObj = new ObjSelectBase();
		selectObj.putTabelle(ObjSelectBase.NO_ALIAS, nomeTabella);

		return selectObj.select();
	}

	@Override
	public boolean insert(Object oggettoEntita) throws Exception {
		return false;
	}

	@Override
	public boolean delete(int id) throws Exception {
		final ObjDeleteBase deleteBase = new ObjDeleteBase();
		final String nomeCampoId = getNomeCampoId();
		deleteBase.setTabella(nomeTabella);
		deleteBase.putClausole(nomeCampoId, Integer.toString(id));
		deleteBase.delete();
		return true;
	}

	@Override
	public boolean update(Object oggettoEntita) throws Exception {
		final ObjUpdateBase updateBase = new ObjUpdateBase();
		final String campoId = getNomeCampoId();
		updateBase.putClausole(campoId, ((AbstractOggettoEntita)oggettoEntita).getIdEntita());
		
		final Iterator<String> iterColumn = getMappaColumnCampi().keySet().iterator();
		final AbstractOggettoEntita ent = entita.getClass().newInstance();
		while (iterColumn.hasNext()) {
			final String colonna = (String) iterColumn.next();
			final Field field = mappaColumnCampi.get(colonna);
			final Class<?> parameterTypes = field.getType();
			final String setMethodName = costruisciNomeSet(field.getName());
			final String getMethodName = costruisciNomeGet(field.getName());
			final Method method = entita.getClass().getMethod(setMethodName,parameterTypes);
			
		
		
		}
		return false;
	}

	@Override
	public boolean deleteAll() throws Exception {
		final ObjDeleteBase deleteBase = new ObjDeleteBase();
		deleteBase.setTabella(nomeTabella);
		deleteBase.delete();
		return true;
	}

	@Override
	public AbstractOggettoEntita getEntitaPadre() {
		return entita;
	}

	@Override
	public Iterator<Object> selectWhere(HashMap<String, String> clausole) throws Exception {
		ObjSelectBase selectObj = new ObjSelectBase();
		selectObj.putTabelle(ObjSelectBase.NO_ALIAS, nomeTabella);
		selectObj.setClausole(clausole);
		final ArrayList<Object> entities = costruisciEntitaFromRs(selectObj.select());
		ConnectionPool.chiudiOggettiDb(null);
		return entities.iterator();
	}

}
