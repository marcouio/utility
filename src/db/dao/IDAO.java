package db.dao;

import java.util.List;

import command.javabeancommand.AbstractOggettoEntita;
import db.Clausola;

public interface IDAO {

	public AbstractOggettoEntita getEntitaPadre();

	public Object selectById(int id) ;

	public Object selectWhere(List<Clausola> clausole, final String appentoToQuery) ;

	public Object selectAll() ;

	public boolean insert(Object oggettoEntita) ;

	public boolean delete(int id) ;

	public boolean update(Object oggettoEntita) ;

	public boolean deleteAll() ;
}
