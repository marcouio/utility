package com.molinari.utility.paint.objects;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public abstract class FormaGeometricaComplessaBase extends FormaGeometrica implements IFormaGeometricaComplessa {


	private List<Point> points = new ArrayList<>();

	@Override
	public List<Point> getPoints() {
		return points;
	}

	public void setPoints(List<Point> points) {
		this.points = points;
	}
}
