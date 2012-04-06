package disegno.oggetti;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import disegno.oggetti.painter.IPainter;

public abstract class FormaGeometrica2D extends FormaGeometricaComplessa implements IFormaGeometrica2D, IFormaGeometrica{

	private int width;
	private int height;
	private int x;
	private int y;
	public Point distanzaMouseDaXY;
	private ArrayList<Lato> listaLati = new ArrayList<Lato>();
	private ArrayList<Lato> latiVicinoMouse;

	public FormaGeometrica2D(final String nome, final IPainter painter) {
		super(nome, painter);
	}

	public FormaGeometrica2D() {
		super();
	}

	@Override
	public Dimension getSize() {
		return new Dimension(width, height);
	}

	@Override
	public void setSize(final int width, final int height) {
		setWidth(width);
		setHeight(height);
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public void setWidth(final int width) {
		this.width = width;
	}

	@Override
	public void setHeight(final int height) {
		this.height = height;
	}

	@Override
	public Point getLocation() {
		return new Point(x, y);
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public void setX(final int x) {
		this.x = x;
	}

	@Override
	public void setY(final int y) {
		this.y = y;
	}

	@Override
	public void setLocation(final int x, final int y) {
		setX(x);
		setY(y);
	}

	@Override
	public ArrayList<Lato> getListaLati() {
		return listaLati;
	}

	/**
	* Sposta l'oggetto grafico in relazione alla posizione x, y
	* 
	* @param x
	* @param y
	*/
	public void moveTo(final int x, final int y) {
		if (distanzaMouseDaXY != null) {
			setX((x - (int) distanzaMouseDaXY.getX()));
			setY(y - (int) distanzaMouseDaXY.getY());
			this.setLocation(getX(), getY());
		}
	} // moveTo

	public Point getDistanzaMouseDaXY() {
		return distanzaMouseDaXY;
	}

	/**
	 * Seleziona l'oggetto grafico e setta le coordinate del puntatore del mouse
	 * 
	 * @param puntatoreMouse
	 */
	public void settaDistanzaDaMouse(final Point puntatoreMouse) {
		if (puntatoreMouse != null) {
			final Point distanza = new Point((int) puntatoreMouse.getX() - getX(), (int) puntatoreMouse.getY() - getY());
			this.distanzaMouseDaXY = distanza;
		}
	}

	@Override
	public ArrayList<Lato> setMouseSuiLati(final Point mouse) {
		latiVicinoMouse = new ArrayList<Lato>();
		for (Object element : listaLati) {
			Lato lato = (Lato) element;
			double distanza = Line2D.ptLineDist(lato.getOrigine().getX(), lato.getOrigine().getY(), lato.getDestinazione().getX(), lato.getDestinazione().getY(), mouse.getX(), mouse.getY());
			if (distanza < 3) {
				latiVicinoMouse.add(lato);
			}
		}
		return latiVicinoMouse;
	}

	public ArrayList<Lato> getLatiVicinoMouse() {
		return latiVicinoMouse;
	}
}
