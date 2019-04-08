package com.molinari.utility.database.dao;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;

import com.molinari.utility.controller.ControlloreBase;
import com.molinari.utility.database.ExecuteResultSet;
import com.molinari.utility.database.ExecuteResultSet.ElaborateResultSet;
import com.molinari.utility.database.dao.columninfo.ColumnDefinition;
import com.molinari.utility.database.dao.columninfo.ColumnDefinitionBase;
import com.molinari.utility.database.dao.columninfo.Definition;
import com.molinari.utility.database.dao.columninfo.JoinColumnDefinition;
import com.molinari.utility.database.dao.columninfo.ManyToManyDefinition;
import com.molinari.utility.database.dao.columninfo.ManyToManyDefinitionBase;

import sun.reflect.annotation.AnnotationType;

public class ElaborateAnnotations<T> {

	private List<Definition> columnList = new ArrayList<>();
	private String nomeTabella;
	private T entita;

	public ElaborateAnnotations(T entita) {
		this.entita = entita;
		caricaMaps();
	}

	public ColumnDefinition getId() {
		for (Definition definition : columnList) {
			if (definition instanceof ColumnDefinition) {
				boolean primaryKey = ((ColumnDefinition) definition).isPrimaryKey();
				if (primaryKey) {
					return ((ColumnDefinition) definition);
				}
			}
		}
		return null;
	}

	protected ArrayList<T> costruisciEntitaFromRs(ResultSet select, boolean derived) {
		try {

			ArrayList<T> listaReturn = new ArrayList<>();
			if (select != null) {
				while (select.next()) {

					final T ent = (T) getEntita().getClass().newInstance();
					listaReturn.add(ent);

					List<Definition> columnList = getColumnList();

					for (Definition definition : columnList) {
						if (definition instanceof ColumnDefinition) {

							if (definition instanceof JoinColumnDefinition && !derived) {
								String colonna = ((ColumnDefinition) definition).getColumnName();
								final String colonnaValue = select.getString(colonna);
								final Field field = definition.getField();
								final Class<?> parameterTypes = field.getType();
								final String methodName = costruisciNomeSet(field.getName());
								final Method method = getEntita().getClass().getMethod(methodName, parameterTypes);

								JoinColumnDefinition joinColumnDefinition = (JoinColumnDefinition) definition;
								ElaborateAnnotations<?> elabAnnotation = ContainerInfoAnnotation
										.getByClass(joinColumnDefinition.getTargetEntity().newInstance());
								String sql = "SELECT * FROM " + elabAnnotation.getNomeTabella() + " WHERE "
										+ joinColumnDefinition.getColumnName() + " = " + colonnaValue;

								List manyExecute = new ExecuteResultSet<>().executeList(makeEntity(elabAnnotation), sql);
								if (manyExecute != null && !manyExecute.isEmpty()) {
									try {
										method.invoke(ent, manyExecute.get(0));
									} catch (IllegalAccessException | IllegalArgumentException
											| InvocationTargetException e) {
										throw new DAOException(e);
									}
								}
							} else {

								String colonna = ((ColumnDefinition) definition).getColumnName();
								final String colonnaValue = select.getString(colonna);
								final Field field = definition.getField();
								final Class<?> parameterTypes = field.getType();
								final String methodName = costruisciNomeSet(field.getName());
								final Method method = getEntita().getClass().getMethod(methodName, parameterTypes);

								setValue(method, colonnaValue, parameterTypes, ent);
							}
						} else if (definition instanceof ManyToManyDefinition && !derived) {

							ManyToManyDefinitionBase manyToManyDefinitionBase = (ManyToManyDefinitionBase) definition;
							Class<?> linkedEntityClass = manyToManyDefinitionBase.getLinkedEntityClass();
							ElaborateAnnotations elab = new ElaborateAnnotations<>(linkedEntityClass.newInstance());
							String nomeTabella = elab.getNomeTabella();
							ColumnDefinition idDef = elab.getId();
							String sql = "SELECT * FROM " + nomeTabella + " WHERE " + idDef.getColumnName()
									+ " IN ( SELECT " + manyToManyDefinitionBase.getInverseJoinColumn() + " FROM "
									+ manyToManyDefinitionBase.getRelationTable() + " WHERE " + getId().getColumnName()
									+ " = " + select.getString(getId().getColumnName()) + ")";

							List manyExecute = new ExecuteResultSet<>().executeList(makeEntity(elab), sql);
							Set ret = new HashSet<>(manyExecute);

							String methodName = elab.costruisciNomeSet(manyToManyDefinitionBase.getField().getName());
							final Method method = getEntita().getClass().getMethod(methodName, Set.class);
							elab.setListValue(method, ret, ent);

						}
					}
				}
			}

			return listaReturn;
		} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException
				| SQLException e) {
			throw new DAOException(e);
		}
	}

	private ElaborateResultSet makeEntity(ElaborateAnnotations<?> elabAnnotation) {
		return rs -> elabAnnotation.costruisciEntitaFromRs(rs, true);
	}

	protected void setValue(Method method, Object colonnaValue, Class parameterTypes, T ent) {
		try {
			GenericDAO dao = UtilityDAO.getDaoByTipo(parameterTypes);
			final Object selectById = dao.selectById(Integer.parseInt(colonnaValue.toString()));
			method.invoke(ent, selectById);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new DAOException(e);
		}
	}

	protected void setListValue(Method method, Set value, T ent) {
		try {
			method.invoke(ent, value);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void setValue(Method method, String colonnaValue, Class<?> parameterTypes, T ent) {
		try {
			if (int.class.equals(parameterTypes)) {
				method.invoke(ent, Integer.parseInt(colonnaValue));
			} else if (double.class.equals(parameterTypes)) {
				method.invoke(ent, Double.parseDouble(colonnaValue));
			} else if (String.class.equals(parameterTypes)) {
				method.invoke(ent, colonnaValue);
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new DAOException(e);
		}
	}

	protected String costruisciNomeSet(String name) {
		final String upper = name.substring(0, 1).toUpperCase();
		final String restante = name.substring(1);
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("set");
		stringBuilder.append(upper);
		stringBuilder.append(restante);
		return stringBuilder.toString();
	}

	protected String costruisciNomeGet(String name) {
		final String upper = name.substring(0, 1).toUpperCase();
		final String restante = name.substring(1);
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("get");
		stringBuilder.append(upper);
		stringBuilder.append(restante);
		return stringBuilder.toString();
	}

	protected void caricaMaps() {

		T entitaLoc = getEntita();

		Object entity = getRealEntity(entitaLoc);
		Field[] fields = entity.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			Annotation[] annotations = field.getAnnotations();
			if (annotations != null && annotations.length > 0) {
				for (int j = 0; j < annotations.length; j++) {

					elaborateAnnotations(field, annotations, j);
				}
			}
		}
	}

	protected String getNomeColonnaByAnnotation(Annotation annotation) {
		return (String) getOggettoByAnnotation(annotation, "name");
	}

	protected String getNomeTabella(Annotation annotation) {
		return (String) getOggettoByAnnotation(annotation, "name");
	}

	protected String getNomeColonnaByJoinColumnsAnnotation(Annotation annotation) {
		String nameColonna = null;
		try {
			Annotation[] joinColumn = (Annotation[]) getOggettoByAnnotation(annotation, "value");

			if (joinColumn != null) {
				Annotation column = joinColumn[0];
				final Class<? extends Annotation> annotationType2 = column.annotationType();
				final Method method = annotationType2.getMethod("name");
				nameColonna = (String) method.invoke(column, new Object[0]);
			}

		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
		return nameColonna;
	}

	@SuppressWarnings("restriction")
	protected Object getOggettoByAnnotation(Annotation annotation, String tipo) {
		Object oggetto = null;
		final Class<? extends Annotation> annotationType = annotation.annotationType();
		final AnnotationType instance = AnnotationType.getInstance(annotationType);
		final Map<String, Method> members = instance.members();
		final Method methodName = members.get(tipo);
		final InvocationHandler invocationHandler = Proxy.getInvocationHandler(annotation);
		try {
			oggetto = invocationHandler.invoke(annotation, methodName, null);
		} catch (Throwable e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
		return oggetto;
	}

	private void elaborateAnnotations(Field field, Annotation[] annotations, int j) {
		String nameColonna;
		Annotation annotation = annotations[j];
		Class<? extends Annotation> annotationType = annotation.annotationType();
		String name = annotationType.getName();

		if ("javax.persistence.Column".equals(name)) {
			nameColonna = getNomeColonnaByAnnotation(annotation);

			if (stringIsEmpty(nameColonna)) {
				nameColonna = field.getName();
			}

			Id idAnnotation = field.getAnnotation(Id.class);

			ColumnDefinitionBase colDef = new ColumnDefinitionBase();
			colDef.setColumnName(nameColonna);
			colDef.setField(field);
			colDef.setPrimaryKey(idAnnotation != null);

			columnList.add(colDef);

		} else if ("javax.persistence.ManyToOne".equals(name)) {
			JoinColumnDefinition colDef = new JoinColumnDefinition();
			colDef.setField(field);

			JoinColumn joinColumnannotation = field.getAnnotation(JoinColumn.class);
			String colonna = joinColumnannotation.name();
			colDef.setColumnName(colonna);

			ManyToOne manyToOneAnn = field.getAnnotation(ManyToOne.class);
			Class targetEntity = manyToOneAnn.targetEntity();
			if (targetEntity == null) {
				targetEntity = field.getType();
			}

			colDef.setTargetEntity(targetEntity);

			columnList.add(colDef);

		} else if ("javax.persistence.JoinColumns".equals(name)) {

			// TODO da rivedere

			// nameColonna = getNomeColonnaByJoinColumnsAnnotation(annotation);
			//
			// if (stringIsEmpty(nameColonna)) {
			// nameColonna = field.getName();
			// }
			// getMappaColumnJoin().put(nameColonna, field);
		} else if ("javax.persistence.ManyToMany".equals(name)) {

			JoinTable joinTableAnn = field.getAnnotation(JoinTable.class);

			String relationTable = joinTableAnn.name();

			JoinColumn[] join = joinTableAnn.joinColumns();
			JoinColumn[] inverseJoin = joinTableAnn.inverseJoinColumns();

			ManyToManyDefinitionBase def = new ManyToManyDefinitionBase();
			def.setField(field);
			def.setRelationTable(relationTable);

			String joinName = join[0].name();
			def.setJoinColumn(joinName);

			String referencedColumnName = join[0].referencedColumnName();
			def.setRefJoinColumn(referencedColumnName);

			String inverseJoinName = inverseJoin[0].name();
			def.setInverseJoinColumn(inverseJoinName);

			String refinverseJoinName = inverseJoin[0].referencedColumnName();
			def.setRefInverseJoinColumn(refinverseJoinName);

			Class<?> targetEntityClass = (Class<?>) getOggettoByAnnotation(annotation, "targetEntity");
			if (targetEntityClass == null) {
				ParameterizedType param = (ParameterizedType) field.getGenericType();
				Type type = param.getActualTypeArguments()[0];
				targetEntityClass = type.getClass();
			}
			def.setLinkedEntityClass(targetEntityClass);

			columnList.add(def);

		}
	}

	protected void cercaNomeTabella() {
		Object realEntity = getRealEntity(getEntita());
		Class<? extends Object> class1 = realEntity.getClass();
		nomeTabella = cercaNomeTabella(class1);
	}

	protected String cercaNomeTabella(Class<?> classe) {
		String ret = null;
		Annotation[] annotations = classe.getAnnotations();

		for (int j = 0; j < annotations.length; j++) {

			Annotation annotation = annotations[j];
			Class<? extends Annotation> annotationType = annotation.annotationType();
			String name = annotationType.getName();
			if ("javax.persistence.Entity".equals(name) || "javax.persistence.Table".equals(name)) {
				ret = getNomeTabella(annotation);
				if (ret != null && !"".equals(ret)) {
					return ret;
				}
			}

		}
		if (ret == null || "".equals(ret)) {
			ret = classe.getSimpleName().toUpperCase();
		}

		return ret;

	}

	private Object getRealEntity(T entitaLoc) {
		Object entity = entitaLoc;

		if (entitaLoc instanceof FakeEntity) {
			entity = ((FakeEntity) entitaLoc).getRealEntity();
		}
		return entity;
	}

	private boolean stringIsEmpty(String nameColonna) {
		return nameColonna == null || "".equals(nameColonna);
	}

	protected String getNomeTabella() {
		if (nomeTabella == null) {
			cercaNomeTabella();
		}
		return nomeTabella;
	}

	public T getEntita() {
		return entita;
	}

	public void setEntita(T entita) {
		this.entita = entita;
	}

	public List<Definition> getColumnList() {
		return columnList;
	}
}
