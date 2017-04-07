package com.molinari.utility.database.dao;

import java.util.logging.Level;

import com.molinari.utility.commands.beancommands.AbstractOggettoEntita;
import com.molinari.utility.controller.ControlloreBase;

public interface UtilityDAO {

	public static GenericDAO getDaoByTipo(Class<?> tipo) {
		try {
			return new GenericDAO<AbstractOggettoEntita>((AbstractOggettoEntita) tipo.newInstance());
		} catch (InstantiationException | IllegalAccessException e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
		return null;
	}

}
