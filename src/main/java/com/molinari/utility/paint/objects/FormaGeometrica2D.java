package com.molinari.utility.paint.objects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

import com.molinari.utility.paint.objects.painter.IPainter;

public abstract class FormaGeometrica2D extends FormaGeometrica implements IFormaGeometrica2D {

	private int width;
	private int height;
	private int x;
	private int y;
	private Point distanzaMouseDaXY;
	private Color background = Color.WHITE;

	public FormaGeometrica2D(final String nome, final IPainter<? extends IFormaGeometrica2D> painter) {
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
	public Color getBackground() {
		return background;
	}

	@Override
	public void setBackground(final Color background) {
		this.background = background;
	}

	/**
	 * Sposta l'oggetto grafico in relazione alla posizione x, y
	 * 
	 * @param x
	 * @param y
	 */
	public void moveTo(final int x, final int y) {
		if (getDistanzaMouseDaXY() != null) {
			setX((x - (int) getDistanzaMouseDaXY().getX()));
			setY(y - (int) getDistanzaMouseDaXY().getY());
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
	@Override
	public void settaDistanzaDaMouse(final Point puntatoreMouse) {
		if (puntatoreMouse != null) {
			final Point distanza = new Point((int) puntatoreMouse.getX() - getX(), (int) puntatoreMouse.getY() - getY());
			setDistanzaMouseDaXY(distanza);
		}
	}

	public void setDistanzaMouseDaXY(Point distanzaMouseDaXY) {
		this.distanzaMouseDaXY = distanzaMouseDaXY;
	}
}
