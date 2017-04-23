package com.molinari.utility.paint;

import com.molinari.utility.paint.objects.IFormaGeometrica;

public interface IFormaComposta extends IFormaGeometrica {

	void add(IFormaGeometrica formaGeometrica);
	
	void remove(IFormaGeometrica formaGeometrica);
	
	void remove(int index);
	
	IFormaGeometrica get(int index);
}
