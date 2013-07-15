package db.dao;

import java.util.ArrayList;

import command.javabeancommand.AbstractOggettoEntita;

import db.Clausola;

public interface IDAO {

	public AbstractOggettoEntita getEntitaPadre() throws Exception;

	public Object selectById(int id) throws Exception;

	public Object selectWhere(final ArrayList<Clausola> clausole, final String appentoToQuery) throws Exception;

	public Object selectAll() throws Exception;

	public boolean insert(Object oggettoEntita) throws Exception;

	public boolean delete(int id) throws Exception;

	public boolean update(Object oggettoEntita) throws Exception;

	public boolean deleteAll() throws Exception;
}
