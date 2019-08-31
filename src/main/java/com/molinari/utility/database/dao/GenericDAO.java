package com.molinari.utility.database.dao;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.logging.Level;

import com.molinari.utility.commands.beancommands.AbstractOggettoEntita;
import com.molinari.utility.controller.ControlloreBase;
import com.molinari.utility.database.Clausola;
import com.molinari.utility.database.DeleteBase;
import com.molinari.utility.database.ExecuteResultSet;
import com.molinari.utility.database.ExecuteResultSet.ElaborateResultSet;
import com.molinari.utility.database.InsertBase;
import com.molinari.utility.database.Query;
import com.molinari.utility.database.SelectBase;
import com.molinari.utility.database.UpdateBase;
import com.molinari.utility.database.dao.columninfo.ColumnDefinition;
import com.molinari.utility.database.dao.columninfo.Definition;

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

			List<T> retList = new ExecuteResultSet<T>().executeList(this::returnEntity, selectObj.getQuery());
			return retList.get(0);

		} catch (Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
		return null;
	}

	private List<T> returnEntity(ResultSet rs) {
		return elabAnnotation.costruisciEntitaFromRs(rs, false);
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
			ElaborateResultSet<T> elabRS = rs -> elabAnnotation.costruisciEntitaFromRs(rs, false);
			return new ExecuteResultSet<T>().executeList(elabRS, selectObj.getQuery());
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
				
				Query q = new Query();
				q.insert(insertBase);
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
				String idEntita = oggettoEntita.getIdEntita();
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
			
			ElaborateResultSet<T> elabRS = rs -> elabAnnotation.costruisciEntitaFromRs(rs, false);
			return new ExecuteResultSet<T>().executeList(elabRS, selectObj.getQuery());
			
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	public T getEntita() {
		return entita;
	}
}
