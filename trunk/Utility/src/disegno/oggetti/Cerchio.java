package disegno.oggetti;

import java.awt.Point;
import java.awt.geom.Point2D;

import disegno.oggetti.painter.PainterCerchio;

public class Cerchio extends FormaGeometrica2D {

	private int raggio;

	public Cerchio(final Point2D estremi, final int raggio) {
		this.setX((int) estremi.getX());
		this.setY((int) estremi.getY());
		this.raggio = raggio;
		setPainter(new PainterCerchio(this));
	}

	@Override
	public Point getPuntoCentrale() {
		final double x = getX();
		final double y = getY();
		final int centroY = new Double(y + (raggio / 2)).intValue();
		final int centroX = new Double(x + (raggio / 2)).intValue();
		return new Point(centroX, centroY);
	}

	@Override
	public void ridimensiona(final Point mouse) {

	}

	@Override
	public boolean isInRegion(final Point mouse) {

		final Point centro = getPuntoCentrale();
		final int distMouseY = (int) Math.abs(centro.getY() - mouse.getY());
		final int distMouseX = (int) Math.abs(centro.getX() - mouse.getX());

		final int diametro = raggio / 2;
		// questo è l'angolo del segmento associato alla freccia
		final double angolo = Math.atan2(centro.getY() - mouse.getY(), centro.getX() - mouse.getX());

		// distanza coordinata X dal centro alla circonferenza considerando
		// l'angolo generato dall'incrocio fra puntatore mouse e centro
		final double distCirconfX = Math.abs(diametro * Math.cos(angolo));

		// distanza coordinata Y dal centro alla circonferenza considerando
		// l'angolo generato dall'incrocio fra puntatore mouse e centro
		final double distCirconfY = Math.abs(diametro * Math.sin(angolo));

		if (distMouseX > distCirconfX || distMouseY > distCirconfY) {
			return false;
		}

		return true;
	}

	public int getRaggio() {
		return raggio;
	}

	public void setRaggio(final int raggio) {
		this.raggio = raggio;
	}

	public boolean isOnCirconferenza() {
		// TODO Auto-generated method stub
		return false;
	}

}
