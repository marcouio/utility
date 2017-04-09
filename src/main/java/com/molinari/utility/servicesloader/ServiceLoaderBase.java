package com.molinari.utility.servicesloader;

import java.util.ServiceLoader;

import com.molinari.utility.GenericException;

public class ServiceLoaderBase<T> {

	public T load(Class<T> clazz){
		ServiceLoader<T> serviceLoader = ServiceLoader.load(clazz, Thread.currentThread().getContextClassLoader());
		T result = null;
		for (T t : serviceLoader) {
			if(t != null){
				result = t;
				break;
			}
		}
		
		if ( result == null ) {
			throw new GenericException("Cannot find implementation for: " + clazz);
		}	
		return result;
	}
}
