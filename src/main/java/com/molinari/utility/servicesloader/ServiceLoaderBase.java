package com.molinari.utility.servicesloader;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

import com.google.common.collect.Lists;
import com.molinari.utility.GenericException;

public class ServiceLoaderBase<T extends Extensible<T>> {

	public T load(Class<T> clazz){
		ServiceLoader<T> serviceLoader = ServiceLoader.load(clazz, Thread.currentThread().getContextClassLoader());
		T result = null;
		Iterator<T> iterator = serviceLoader.iterator();
		List<T> lista = Lists.newArrayList(iterator);
			
		if(!lista.isEmpty()){
			Comparator<Extensible<T>> comparator = lista.get(0).getComparator();
			Collections.sort(lista, comparator);
			result = lista.get(0);
		}
		
		if ( result == null ) {
			throw new GenericException("Cannot find implementation for: " + clazz);
		}	
		return result;
	}
}