package db.dao;

import command.javabeancommand.AbstractOggettoEntita;

public abstract class UtilityDAO {

	public static GenericDAO getDaoByTipo(Class<?> tipo) throws Exception{
		return new GenericDAO((AbstractOggettoEntita) tipo.newInstance());
	}

}
