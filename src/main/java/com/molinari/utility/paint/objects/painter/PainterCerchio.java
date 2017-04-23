package com.molinari.utility.paint.objects.painter;

import java.awt.Graphics;

import com.molinari.utility.paint.objects.Cerchio;

public class PainterCerchio extends Painter2D<Cerchio> {

	public PainterCerchio(final Cerchio oggettoGrafico) {
		super(oggettoGrafico);
	}

	@Override
	protected void disegnaTracciato(final Graphics g) {
		g.drawOval(getOggettoGrafico().getX(), getOggettoGrafico().getY(), (int)getOggettoGrafico().getRaggio(), (int)getOggettoGrafico().getRaggio());
	}

	@Override
	protected void disegnaBackground(final Graphics g) {
		g.fillOval(getOggettoGrafico().getX(), getOggettoGrafico().getY(), (int)getOggettoGrafico().getRaggio(), (int)getOggettoGrafico().getRaggio());

	}

}
