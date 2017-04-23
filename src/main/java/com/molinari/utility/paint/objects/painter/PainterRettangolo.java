package com.molinari.utility.paint.objects.painter;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;

import com.molinari.utility.paint.objects.poligoni.Rettangolo;

public class PainterRettangolo extends Painter2D<Rettangolo> {

	public PainterRettangolo(Rettangolo oggettoGrafico) {
		super(oggettoGrafico);
	}
	

	private GeneralPath getPath(Rettangolo rettangolo) {
		GeneralPath path = new GeneralPath();
		path.moveTo(rettangolo.getLatoAlto().getOrigine().getX(), rettangolo.getLatoAlto().getOrigine().getY());
		path.lineTo(rettangolo.getLatoAlto().getDestinazione().getX(), rettangolo.getLatoAlto().getDestinazione().getY());
		path.lineTo(rettangolo.getLatoBasso().getDestinazione().getX(), rettangolo.getLatoBasso().getDestinazione().getY());
		path.lineTo(rettangolo.getLatoBasso().getOrigine().getX(), rettangolo.getLatoBasso().getOrigine().getY());
		path.closePath();
		return path;
	}
	
	

	@Override
	protected void disegnaTracciato(Graphics g) {
		Rettangolo rettangolo = (Rettangolo) getOggettoGrafico();
		GeneralPath path = getPath(rettangolo);
		((Graphics2D)g).draw(path);
	}

	@Override
	protected void disegnaBackground(Graphics g) {
		Rettangolo rettangolo = (Rettangolo) getOggettoGrafico();
		GeneralPath path = getPath(rettangolo);
		((Graphics2D)g).fill(path);
	}

}
