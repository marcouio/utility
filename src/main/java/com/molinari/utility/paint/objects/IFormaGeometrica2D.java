package com.molinari.utility.paint.objects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;

import com.molinari.utility.paint.objects.poligoni.Lato;

public interface IFormaGeometrica2D extends IFormaGeometrica {

	public int getWidth();

	public int getHeight();

	public void setWidth(final int width);

	public void setHeight(final int height);

	public void setSize(final int width, final int height);

	public Dimension getSize();

	public int getX();

	public int getY();

	public void setX(final int x);

	public void setY(final int y);

	public void setLocation(final int x, final int y);

	public Point getLocation();

	public Color getBackground();

	public void setBackground(Color background);
	
}
