package com.molinari.utility.paint.objects.punte;

import java.awt.Color;
import java.awt.Point;

import com.molinari.utility.paint.UtilDisegno;
import com.molinari.utility.paint.objects.Segmento;
import com.molinari.utility.paint.objects.painter.PainterPuntaTriangolo;

public class PuntaTriangolo extends PuntaBase {

	private Color background = Color.WHITE;

	public PuntaTriangolo(final Segmento segmento, final int lunghezzaLati) {
		super(segmento, lunghezzaLati);
		setPainter(new PainterPuntaTriangolo(this));
	}

	public PuntaTriangolo(final Segmento segmento) {
		super(segmento);
		setPainter(new PainterPuntaTriangolo(this));
	}

	public Color getBackground() {
		return background;
	}

	public void setBackground(Color background) {
		this.background = background;
	}
	
	@Override
	public boolean isInRegion(Point mouse) {
		boolean inRegion = super.isInRegion(mouse);
		if(!inRegion){
			Segmento latoDestro = getLatoDestro();
			Segmento latoSinistro = getLatoSinistro();
			
			Point rightTarget = latoDestro.getDestinazione();
			Point leftTarget = latoSinistro.getDestinazione();
			
			int makeLengthByAngle = UtilDisegno.makeHeightByAngle(getArrowAngle(), rightTarget, leftTarget);
			for (int i = 0; i < makeLengthByAngle; i++) {
				
				double angoloLatoBasso = UtilDisegno.getArcoTangenteSegmento(new Segmento(rightTarget, leftTarget));
				Point pointByAngolo = UtilDisegno.makePointByAngle(leftTarget, angoloLatoBasso, i);
				boolean mouseNearPoint = UtilDisegno.isMouseNearPoint(mouse, pointByAngolo);
				if(mouseNearPoint){
					return true;
				}
			}
		}
		return inRegion;
	}
	
}
