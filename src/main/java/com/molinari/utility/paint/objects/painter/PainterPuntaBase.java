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
		PuntaBase punta = getOggettoGrafico();
		path.moveTo(punta.getLatoDestro().getDestinazione().getX(), punta.getLatoDestro().getDestinazione().getY());
		path.lineTo(punta.getLatoDestro().getOrigine().getX(), punta.getLatoDestro().getOrigine().getY());
		path.lineTo(punta.getLatoSinistro().getDestinazione().getX(), punta.getLatoSinistro().getDestinazione().getY());
		path.lineTo(punta.getLatoSinistro().getOrigine().getX(), punta.getLatoSinistro().getOrigine().getY());
		path.closePath();
		return path;
	}
}
