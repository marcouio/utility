package com.molinari.utility.paint.objects.painter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;

import com.molinari.utility.paint.objects.IFormaGeometrica2D;
import com.molinari.utility.paint.objects.punte.PuntaTriangolo;

public class PainterPuntaTriangolo extends Painter2D{

	public PainterPuntaTriangolo(final IFormaGeometrica2D oggettoGrafico) {
		super(oggettoGrafico);
	}

	private GeneralPath getPath(PuntaTriangolo puntaTriangolo) {
		GeneralPath path = new GeneralPath();
		path.moveTo(puntaTriangolo.getLatoDestro().getDestinazione().getX(), puntaTriangolo.getLatoDestro().getDestinazione().getY());
		path.lineTo(puntaTriangolo.getLatoSinistro().getDestinazione().getX(), puntaTriangolo.getLatoSinistro().getDestinazione().getY());
		path.lineTo(puntaTriangolo.getEstremoCentrale().getX(), puntaTriangolo.getEstremoCentrale().getY());
		path.closePath();
		return path;
	}

	@Override
	protected void disegnaTracciato(Graphics g) {
		PuntaTriangolo puntaTriangolo = (PuntaTriangolo) getOggettoGrafico();
		GeneralPath path = getPath(puntaTriangolo);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.BLACK);
		g2.draw(path);
		
	}

	@Override
	protected void disegnaBackground(Graphics g) {
		PuntaTriangolo puntaTriangolo = (PuntaTriangolo) getOggettoGrafico();
		GeneralPath path = getPath(puntaTriangolo);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(puntaTriangolo.getBackground());
		g2.fill(path);		
	}
}
