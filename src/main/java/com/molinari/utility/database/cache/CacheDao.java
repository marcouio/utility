package com.molinari.utility.database.cache;

import java.util.HashMap;
import java.util.List;

import com.molinari.utility.commands.beancommands.AbstractOggettoEntita;
import com.molinari.utility.database.dao.GenericDAO;

public class CacheDao<T extends AbstractOggettoEntita> extends GenericDAO<T>{

	private HashMap<String, T> cache = null;
	
	boolean caricata = false;
	
	public CacheDao(T entita) {
		super(entita);
		cache = new HashMap<>();
	}

	@Override
	public boolean update(T oggettoEntita)  {
		String idEntita = oggettoEntita.getIdEntita();
		cache.put(idEntita, oggettoEntita);
		return super.update(oggettoEntita);
	}
	
	@Override
	public boolean insert(T oggettoEntita)  {
		String idEntita = oggettoEntita.getIdEntita();
		cache.put(idEntita, oggettoEntita);
		return super.insert(oggettoEntita);
	}
	
	@Override
	public boolean delete(int id)  {
		cache.remove(id);
		return super.delete(id);
	}
	
	@Override
	public boolean deleteAll()  {
		cache.clear();
		return super.deleteAll();
	}
	
	@Override
	public List<T> selectAll()  {
		if(caricata){
			return (List<T>) cache.values();
		}
		
		List<T> selectAll = super.selectAll();
		caricata = true;
		return selectAll;
	}
	
	@Override
	public T selectById(int id)  {
		String idString = Integer.toString(id);
		T entita = cache.get(idString);
		if(entita != null){
			return entita;
		}
		return super.selectById(id);
	}
}