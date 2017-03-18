package com.molinari.utility.database.dao;

import java.util.List;

import com.molinari.utility.database.Clausola;

public interface IDAO<T> {

	public T getEntitaPadre();

	public T selectById(int id) ;

	public List<T> selectWhere(List<Clausola> clausole, final String appentoToQuery) ;

	public List<T> selectAll() ;

	public boolean insert(T oggettoEntita) ;

	public boolean delete(int id) ;

	public boolean update(T oggettoEntita) ;

	public boolean deleteAll() ;
}
