package com.molinari.utility.paint.objects.painter;

import java.awt.Graphics;

import com.molinari.utility.paint.objects.Cerchio;
import com.molinari.utility.paint.objects.IFormaGeometrica;
import com.molinari.utility.paint.objects.IFormaGeometrica2D;

public class PainterCerchio extends Painter2D {

	Cerchio cerchio;

	public PainterCerchio(final IFormaGeometrica oggettoGrafico) {
		super((IFormaGeometrica2D) oggettoGrafico);
		cerchio = (Cerchio) oggettoGrafico;

	}

	@Override
	protected void disegnaTracciato(final Graphics g) {
		g.drawOval(cerchio.getX(), cerchio.getY(), cerchio.getRaggio(), cerchio.getRaggio());
	}

	@Override
	protected void disegnaBackground(final Graphics g) {
		g.fillOval(cerchio.getX(), cerchio.getY(), cerchio.getRaggio(), cerchio.getRaggio());

	}

}
