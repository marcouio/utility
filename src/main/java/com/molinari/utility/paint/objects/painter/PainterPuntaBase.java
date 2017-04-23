package com.molinari.utility.paint.objects.painter;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;

import com.molinari.utility.paint.objects.punte.PuntaBase;

public class PainterPuntaBase extends PainterBase<PuntaBase> {


	public PainterPuntaBase(final PuntaBase punta) {
		super(punta);
	}

	@Override
	public void paint(final Graphics g) {
		final GeneralPath path = getPath();
		((Graphics2D) g).draw(path);

	}

	private GeneralPath getPath() {
		final GeneralPath path = new GeneralPath();
		path.moveTo(getOggettoGrafico().getLatoDestro().getDestinazione().getX(), getOggettoGrafico().getLatoDestro().getDestinazione().getY());
		path.lineTo(getOggettoGrafico().getLatoDestro().getOrigine().getX(), getOggettoGrafico().getLatoDestro().getOrigine().getY());
		path.lineTo(getOggettoGrafico().getLatoSinistro().getDestinazione().getX(), getOggettoGrafico().getLatoSinistro().getDestinazione().getY());
		path.lineTo(getOggettoGrafico().getLatoSinistro().getOrigine().getX(), getOggettoGrafico().getLatoSinistro().getOrigine().getY());
		path.closePath();
		return path;
	}
}
