package db.cache;

import java.util.HashMap;

import command.javabeancommand.AbstractOggettoEntita;

import db.dao.GenericDAO;

public class CacheDao extends GenericDAO{

	HashMap<String, AbstractOggettoEntita> cache = null;
	
	boolean caricata = false;
	
	private CacheDao(AbstractOggettoEntita entita) {
		super(entita);
		cache = new HashMap<String, AbstractOggettoEntita>();
	}

	@Override
	public boolean update(Object oggettoEntita) throws Exception {
		AbstractOggettoEntita entita = (AbstractOggettoEntita)oggettoEntita;
		String idEntita = entita.getIdEntita();
		cache.put(idEntita, entita);
		return super.update(oggettoEntita);
	}
	
	@Override
	public boolean insert(Object oggettoEntita) throws Exception {
		AbstractOggettoEntita entita = (AbstractOggettoEntita)oggettoEntita;
		String idEntita = entita.getIdEntita();
		cache.put(idEntita, entita);
		return super.insert(oggettoEntita);
	}
	
	@Override
	public boolean delete(int id) throws Exception {
		cache.remove(id);
		return super.delete(id);
	}
	
	@Override
	public boolean deleteAll() throws Exception {
		cache.clear();
		return super.deleteAll();
	}
	
	@Override
	public Object selectAll() throws Exception {
		if(caricata){
			return cache.values();
		}
		
		Object selectAll = super.selectAll();
		caricata = true;
		return selectAll;
	}
	
	@Override
	public Object selectById(int id) throws Exception {
		String idString = Integer.toString(id);
		AbstractOggettoEntita entita = cache.get(idString);
		if(entita != null){
			return entita;
		}
		return super.selectById(id);
	}
}