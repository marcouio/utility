package com.molinari.utility.servicesloader;

import java.util.Comparator;

public interface Extensible<T> {

	int getLevel();
	
	Comparator<Extensible<T>> getComparator();
	
	public class ComparatorExtendibile<T> implements Comparator<Extensible<T>> {

		@Override
		public int compare(Extensible<T> o1, Extensible<T> o2) {
			int level1 = o1.getLevel();
			int level2 = o2.getLevel();
			if(level1 > level2){
				return -1;
			}else if(level1 < level2){
				return 1;
			}
			return 0;
		}
	}
}
