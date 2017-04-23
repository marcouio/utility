package com.molinari.utility.paint.objects.painter;

import java.awt.Graphics;

import com.molinari.utility.paint.objects.Segmento;


public class PainterSegmento extends PainterBase<Segmento>{

	public PainterSegmento(Segmento oggettoGrafico) {
		super(oggettoGrafico);
	}

	@Override
	public void paint(Graphics g) {
		Segmento seg = getOggettoGrafico();
		int x1 = (int) seg.getOrigine().getX();
		int y1 = (int) seg.getOrigine().getY();
		int x2 = (int) seg.getDestinazione().getX();
		int y2 = (int) seg.getDestinazione().getY();
		g.drawLine(x1, y1, x2, y2);
	}

}
