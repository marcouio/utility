package com.molinari.utility.servicesloader;

public interface Extensible<T> {

	LoaderLevel getLevel();
	
	public T createInstance(Object...args);
}
