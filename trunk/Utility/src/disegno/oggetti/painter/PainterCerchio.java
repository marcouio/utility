package disegno.oggetti.painter;

import java.awt.Graphics;
import java.awt.Point;

import disegno.oggetti.Cerchio;
import disegno.oggetti.IFormaGeometrica;
import disegno.oggetti.IFormaGeometrica2D;

public class PainterCerchio extends Painter2D {

	int x;
	int y;
	int raggio;

	public PainterCerchio(final IFormaGeometrica oggettoGrafico) {
		super((IFormaGeometrica2D) oggettoGrafico);
		final Cerchio cerchio = (Cerchio) oggettoGrafico;
		final Point puntoEstremi = (Point) cerchio.getEstremi();
		x = new Double(puntoEstremi.getX()).intValue();
		y = new Double(puntoEstremi.getY()).intValue();
		raggio = cerchio.getRaggio();

	}

	@Override
	protected void disegnaTracciato(final Graphics g) {

		g.drawOval(x, y, raggio, raggio);
	}

	@Override
	protected void disegnaBackground(final Graphics g) {
		g.fillOval(x, y, raggio, raggio);

	}

}
