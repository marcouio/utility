package com.molinari.utility.servicesloader;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

import com.google.common.collect.Lists;
import com.molinari.utility.GenericException;

public class ServiceLoaderStarter<T extends Extensible<T>> {
	
	public class ComparatorExtendibile implements Comparator<Extensible<T>> {

		@Override
		public int compare(Extensible<T> o1, Extensible<T> o2) {
			int level1 = o1.getLevel().getValue();
			int level2 = o2.getLevel().getValue();
			if(level1 > level2){
				return -1;
			}else if(level1 < level2){
				return 1;
			}
			return 0;
		}
	}
	
	public T load(Comparator<Extensible<T>> comparator, Class<T> clazz){
		ServiceLoader<T> serviceLoader = ServiceLoader.load(clazz, Thread.currentThread().getContextClassLoader());
		T result = null;
		Iterator<T> iterator = serviceLoader.iterator();
		List<T> lista = Lists.newArrayList(iterator);
			
		if(!lista.isEmpty()){
			Collections.sort(lista, comparator);
			result = lista.get(0);
		}
		
		if ( result == null ) {
			throw new GenericException("Cannot find implementation for: " + clazz);
		}	
		return result;
	}

	public T load(Class<T> clazz){
		return load(new ComparatorExtendibile(), clazz);
	}
}