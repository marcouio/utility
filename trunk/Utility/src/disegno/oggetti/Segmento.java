package disegno.oggetti;

import java.awt.Point;

import disegno.oggetti.painter.PainterSegmento;

public class Segmento extends FormaGeometrica {

	Point origine;
	Point destinazione;

	public Segmento(final Point origine, final Point destinazione) {
		this.origine = origine;
		this.destinazione = destinazione;
		setPainter(new PainterSegmento(this));
	}

	@Override
	public Point getPuntoCentrale() {
		int x = (int) ((origine.getX() + destinazione.getX()) / 2);
		int y = (int) ((origine.getY() + destinazione.getY()) / 2);
		return new Point(x, y);
	}

	public int getLunghezza() {
		double base = Math.abs(origine.getX() - destinazione.getX());
		double altezza = Math.abs(origine.getY() - destinazione.getY());
		return (int) Math.sqrt(Math.pow(base, 2) + Math.pow(altezza, 2));
	}

	public Point getOrigine() {
		return origine;
	}

	public void setOrigine(final Point origine) {
		this.origine = origine;
	}

	public Point getDestinazione() {
		return destinazione;
	}

	public void setDestinazione(final Point destinazione) {
		this.destinazione = destinazione;
	}

	public static void main(final String[] args) {
		Segmento seg = new Segmento(new Point(20, 20), new Point(40, 40));
		System.out.println(seg.getLunghezza());
	}

	@Override
	public void ridimensiona(final Point mouse) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isInRegion(Point mouse) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void settaDistanzaDaMouse(Point puntatoreMouse) {
		// TODO Auto-generated method stub
		
	}
}
