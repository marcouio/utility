package disegno.oggetti;

import java.awt.Point;
import java.awt.geom.Point2D;

import disegno.oggetti.painter.PainterCerchio;

public class Cerchio extends FormaGeometrica2D {

	private Point2D estremi;
	private int raggio;

	public Cerchio(final Point2D estremi, final int raggio) {
		this.estremi = estremi;
		this.raggio = raggio;
		setPainter(new PainterCerchio(this));
	}

	@Override
	public Point getPuntoCentrale() {
		final double x = estremi.getX();
		final double y = estremi.getY();
		final int centroY = new Double(y + (raggio / 2)).intValue();
		final int centroX = new Double(x + (raggio / 2)).intValue();
		return new Point(centroX, centroY);
	}

	@Override
	public void ridimensiona(final Point mouse) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isInRegion(final Point mouse) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void settaDistanzaDaMouse(final Point puntatoreMouse) {
		// TODO Auto-generated method stub

	}

	public int getRaggio() {
		return raggio;
	}

	public Point2D getEstremi() {
		return estremi;
	}

	public void setEstremi(final Point2D estremi) {
		this.estremi = estremi;
	}

}
