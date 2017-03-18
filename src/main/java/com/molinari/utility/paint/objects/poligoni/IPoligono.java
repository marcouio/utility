package com.molinari.utility.paint.objects.poligoni;

import java.awt.Point;
import java.util.ArrayList;

import com.molinari.utility.paint.objects.IFormaGeometrica2D;

public interface IPoligono extends IFormaGeometrica2D {

	public ArrayList<Lato> getListaLati();

	public ArrayList<Lato> setMouseSuiLati(Point mouse);
}
