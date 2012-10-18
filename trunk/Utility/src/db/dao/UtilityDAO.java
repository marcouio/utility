package db.dao;

import command.javabeancommand.AbstractOggettoEntita;

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
