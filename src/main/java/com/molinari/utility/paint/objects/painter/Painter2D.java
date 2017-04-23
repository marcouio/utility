package com.molinari.utility.paint.objects.painter;

import java.awt.Color;
import java.awt.Graphics;

import com.molinari.utility.paint.objects.IFormaGeometrica2D;

public abstract class Painter2D<T extends IFormaGeometrica2D> extends PainterBase<T> implements IPainter<T>{

	public Painter2D(T oggettoGrafico) {
		super(oggettoGrafico);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		IFormaGeometrica2D oggetto2d = getOggettoGrafico();
		g.setColor(oggetto2d.getBackground());
		disegnaBackground(g);
		g.setColor(Color.BLACK);
		disegnaTracciato(g);
	}

	protected abstract void disegnaTracciato(Graphics g);

	protected abstract void disegnaBackground(Graphics g);

}
