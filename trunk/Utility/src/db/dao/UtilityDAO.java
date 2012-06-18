package db.dao;

import controller.ControlloreBase;

public abstract class UtilityDAO {

	
	private static UtilityDAO singleton;
	
	public static synchronized UtilityDAO getSingleton() throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		if(singleton == null){
			singleton = (UtilityDAO) Class.forName(ControlloreBase.utilityDaoClass).newInstance();
		}
		return singleton;
	}
	
	public abstract GenericDAO getDaoByTipo(Class<?> tipo);

}
