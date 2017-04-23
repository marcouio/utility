package com.molinari.utility.paint.objects.painter;

import java.awt.Graphics;

import com.molinari.utility.paint.objects.IFormaGeometrica;

public interface IPainter<T extends IFormaGeometrica> {

	public void paint(Graphics g);
}
