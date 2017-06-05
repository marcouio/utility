package com.molinari.utility.database.dao;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Set;
import java.util.logging.Level;

import com.molinari.utility.commands.beancommands.AbstractOggettoEntita;
import com.molinari.utility.controller.ControlloreBase;
import com.molinari.utility.database.Clausola;
import com.molinari.utility.database.ConnectionPool;
import com.molinari.utility.database.DeleteBase;
import com.molinari.utility.database.ExecuteResultSet;
import com.molinari.utility.database.InsertBase;
import com.molinari.utility.database.Query;
import com.molinari.utility.database.SelectBase;
import com.molinari.utility.database.UpdateBase;
import com.molinari.utility.database.dao.columninfo.ColumnDefinition;
import com.molinari.utility.database.dao.columninfo.Definition;
import com.molinari.utility.database.dao.columninfo.ManyToManyDefinitionBase;
import com.molinari.utility.database.dao.columninfo.ManyToOneDefinitionBase;

public class GenericDAO<T extends AbstractOggettoEntita> extends Observable implements IDAO<T> {

	private ElaborateAnnotations<T> elabAnnotation;
	private T entita;
	
	public GenericDAO(final T entita) {
		this.entita = entita;
		elabAnnotation = ContainerInfoAnnotation.getByClass(entita);
	}

	@Override
	public T selectById(final int id) {
		try {

			SelectBase selectObj = new SelectBase();
			selectObj.putTabelle(elabAnnotation.getNomeTabella(), elabAnnotation.getNomeTabella());
			String nomeCampoIdLoc = getNomeCampoId();
			Clausola clausolaId = new Clausola(null, nomeCampoIdLoc, "=", Integer.toString(id));
			selectObj.putClausole(clausolaId);

			ExecuteResultSet<T> executeResultSet = new ExecuteResultSet<T>();
			executeResultSet.setSql(selectObj.getQuery());
			List<T> retList = executeResultSet.execute( rs -> returnEntity(rs));
			T execute = retList.get(0);
			
			List<Definition> columnList = elabAnnotation.getColumnList();
			manyToManySelect(id, nomeCampoIdLoc, execute, columnList);
			
			//TODO ALtro... ManyToOne
			for (Definition definition : columnList) {
				if(definition instanceof ManyToOneDefinitionBase){
					
				}
			}
			
			return execute;

		} catch (Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
		return null;
	}

	private void manyToManySelect(final int id, String nomeCampoIdLoc, T execute, List<Definition> columnList)
			throws InstantiationException, IllegalAccessException, SQLException, NoSuchMethodException {
		for (Definition definition : columnList) {
			if(definition instanceof ManyToManyDefinitionBase){
				ManyToManyDefinitionBase manyToManyDefinitionBase = (ManyToManyDefinitionBase) definition;
				Class<?> linkedEntityClass = manyToManyDefinitionBase.getLinkedEntityClass();
				ElaborateAnnotations elab = new ElaborateAnnotations<>(linkedEntityClass.newInstance());
				String nomeTabella = elab.getNomeTabella();
				ColumnDefinition idDef = elab.getId();
				String sql = "SELECT * FROM " + nomeTabella + " WHERE " + idDef.getColumnName() + " IN ( SELECT " + manyToManyDefinitionBase.getInverseJoinColumn() + " FROM "+ manyToManyDefinitionBase.getRelationTable()  +" WHERE "+ nomeCampoIdLoc +" = "+ id +")";
				
				ExecuteResultSet execLink = new  ExecuteResultSet<>();
				execLink.setSql(sql);
				List manyExecute = execLink.execute(rs -> elab.costruisciEntitaFromRs(rs));
				Set ret = new HashSet<>(manyExecute);
				
				String methodName = elab.costruisciNomeSet(manyToManyDefinitionBase.getField().getName());
				final Method method = getEntita().getClass().getMethod(methodName, Set.class);
				elab.setListValue(method, ret, execute);
			}
		}
	}

	private List<T> returnEntity(ResultSet rs) {
		return elabAnnotation.costruisciEntitaFromRs(rs);
	}

	protected String getNomeCampoId() {
		List<Definition> columnList = elabAnnotation.getColumnList();
		for (Definition definition : columnList) {
			if(definition instanceof ColumnDefinition){
				boolean primaryKey = ((ColumnDefinition) definition).isPrimaryKey();
				if(primaryKey){
					return ((ColumnDefinition) definition).getColumnName();
				}
			}
		}
		return null;
	}

	@Override
	public List<T> selectAll() {
		try {
			SelectBase selectObj = new SelectBase();
			selectObj.putTabelle(elabAnnotation.getNomeTabella(), elabAnnotation.getNomeTabella());
			Query query = new Query();
			ResultSet rs = query.select(selectObj);
			return elabAnnotation.costruisciEntitaFromRs(rs);
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	@Override
	public boolean insert(T oggettoEntita) {
		try {
			if (oggettoEntita != null) {
				InsertBase insertBase = new InsertBase();
				insertBase.setTabella(elabAnnotation.getNomeTabella());
				
				List<Definition> columnList = elabAnnotation.getColumnList();
				for (Definition definition : columnList) {
					if(definition instanceof ColumnDefinition){
						Field field = definition.getField();
						String colonna = ((ColumnDefinition) definition).getColumnName();
						final String getMethodName = elabAnnotation.costruisciNomeGet(field.getName());
						final Method method = getEntita().getClass().getMethod(getMethodName);
						Object getterCampo = method.invoke(oggettoEntita);
						if (!colonna.equals(getNomeCampoId()) || isIdValido(getterCampo)) {
							inserisciCampiValue(colonna, getterCampo, field.getType(), insertBase);
						}
					}
				}
				
				//TODO JOIN
			}	
			return false;
			
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	protected boolean isIdValido(Object getterCampo) {
		boolean valido = false;
		if (getterCampo != null) {
			if (getterCampo instanceof Number) {
				Long id = new Long(getterCampo.toString());
				if (id.intValue() > 0) {
					valido = true;
				}
			} else {
				valido = true;
			}
		}
		return valido;
	}

	protected void inserisciCampiValue(String colonna, Object getterCampo, Class<?> parameterTypes,
			InsertBase insertBase) {
		String valore = null;
		if (getterCampo instanceof AbstractOggettoEntita) {
			valore = ((AbstractOggettoEntita) getterCampo).getIdEntita();
		} else if (getterCampo instanceof Date) {
			// gestire i campi date
		} else if (getterCampo != null) {
			valore = getterCampo.toString();
		}
		insertBase.putCampoValore(colonna, valore);
	}

	@Override
	public boolean delete(int id) {
		try {
			final DeleteBase deleteBase = new DeleteBase();
			final String nomeCampoIdLoc = getNomeCampoId();
			deleteBase.setTabella(elabAnnotation.getNomeTabella());
			deleteBase.putClausole(null, nomeCampoIdLoc, "=", Integer.toString(id));
			Query query = new Query();
			return query.delete(deleteBase);
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	@Override
	public boolean update(T oggettoEntita) {
		try {
			if (oggettoEntita != null) {
				final UpdateBase updateBase = new UpdateBase();
				updateBase.setTabella(elabAnnotation.getNomeTabella());
				final String campoId = getNomeCampoId();
				String idEntita = ((AbstractOggettoEntita) oggettoEntita).getIdEntita();
				updateBase.putClausole(null, campoId, "=", idEntita);
				
				List<Definition> columnList = elabAnnotation.getColumnList();
				for (Definition definition : columnList) {
					if(definition instanceof ColumnDefinition){
						Field field = definition.getField();
						String colonna = ((ColumnDefinition) definition).getColumnName();
						final String getMethodName = elabAnnotation.costruisciNomeGet(field.getName());
						final Method method = getEntita().getClass().getMethod(getMethodName);
						Object getterCampo = method.invoke(oggettoEntita);
						inserisciCampiUpdate(colonna, getterCampo, field.getType(), updateBase);
					}
				}
				
				//TODO JOIN e altro...

				Query query = new Query();
				return query.update(updateBase);
			}
			return false;
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	protected void inserisciCampiUpdate(String colonna, Object getterCampo, Class<?> parameterTypes,
			UpdateBase updateBase) {
		String valore = null;
		// gestire i campi date
		if (getterCampo instanceof AbstractOggettoEntita) {
			final String id = ((AbstractOggettoEntita) getterCampo).getIdEntita();
			valore = id;
		} else if (getterCampo != null) {
			valore = getterCampo.toString();
		}

		updateBase.putCampiUpdate(colonna, valore);
	}

	@Override
	public boolean deleteAll() {
		try {
			final DeleteBase deleteBase = new DeleteBase();
			deleteBase.setTabella(elabAnnotation.getNomeTabella());
			return new Query().delete(deleteBase);
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	@Override
	public T getEntitaPadre() {
		return getEntita();
	}

	@Override
	public List<T> selectWhere(List<Clausola> clausole, String appendToQuery) {
		try {
			SelectBase selectObj = new SelectBase();
			selectObj.putTabelle(elabAnnotation.getNomeTabella(), elabAnnotation.getNomeTabella());
			selectObj.setClausole(clausole);
			selectObj.setAppendToQuery(appendToQuery);
			ResultSet select = new Query().select(selectObj);
			final ArrayList<T> entities = elabAnnotation.costruisciEntitaFromRs(select);
			ConnectionPool.getSingleton().chiudiOggettiDb(null);
			return entities;
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	public T getEntita() {
		return entita;
	}
}
