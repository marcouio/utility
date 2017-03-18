package main.java.com.molinari.utility.paint.objects.punte;

import java.awt.Color;

import main.java.com.molinari.utility.paint.objects.Segmento;
import main.java.com.molinari.utility.paint.objects.painter.PainterPuntaTriangolo;

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
	
}
