package main.java.com.molinari.utility.database.dao;

import main.java.com.molinari.utility.commands.beancommands.AbstractOggettoEntita;

public abstract class UtilityDAO {

	public static GenericDAO getDaoByTipo(Class<?> tipo) {
		try {
			return new GenericDAO((AbstractOggettoEntita) tipo.newInstance());
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

}
