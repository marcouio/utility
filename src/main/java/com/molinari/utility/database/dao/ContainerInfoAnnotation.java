package com.molinari.utility.database.dao;

import java.util.HashMap;
import java.util.Map;

public class ContainerInfoAnnotation {

	private static Map<String, ElaborateAnnotations<?>> map = new HashMap<>();
	
	public static <T> ElaborateAnnotations<T> getByClass(T entity){
		String name = entity.getClass().getName();
		if(map.containsKey(name)){
			return (ElaborateAnnotations<T>) map.get(name);
		}
		
		ElaborateAnnotations<T>  elab = new ElaborateAnnotations<T>(entity);
		
		map.put(name, elab);
		return elab;
	}
}
