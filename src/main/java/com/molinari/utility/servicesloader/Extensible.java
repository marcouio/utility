package com.molinari.utility.servicesloader;

import java.util.Comparator;

public interface Extensible<T> {

	LoaderLevel getLevel();
	
	Comparator<Extensible<T>> getComparator();
	
	public class ComparatorExtendibile<T> implements Comparator<Extensible<T>> {

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
	
	public T createInstance(Object...args);
}
