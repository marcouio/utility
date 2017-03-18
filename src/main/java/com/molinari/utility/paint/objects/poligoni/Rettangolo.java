package main.java.com.molinari.utility.paint.objects.poligoni;

import java.awt.Point;
import java.awt.Rectangle;

import main.java.com.molinari.utility.paint.UtilDisegno;
import main.java.com.molinari.utility.paint.objects.IFormaGeometrica2D;
import main.java.com.molinari.utility.paint.objects.painter.IPainter;
import main.java.com.molinari.utility.paint.objects.painter.PainterRettangolo;

public class Rettangolo extends Poligono implements IFormaGeometrica2D {

	private Lato latoAlto = new Lato("Alto");
	private Lato latoBasso = new Lato("Basso");
	private Lato latoSinistra = new Lato("Sinistra");
	private Lato latoDestra = new Lato("Destra");
	private boolean mouseIsInRegion;

	public static void main(final String[] args) {

	}

	public boolean isMouseIsInRegion() {
		return mouseIsInRegion;
	}

	public Rettangolo(final String nome, final IPainter painter) {
		super(nome, painter);
		init();
	}

	private void init() {

		getListaLati().add(latoAlto);
		getListaLati().add(latoBasso);
		getListaLati().add(latoDestra);
		getListaLati().add(latoSinistra);
	}

	public Rettangolo(final String nome) {
		setNome(nome);
		setPainter(new PainterRettangolo(this));
		init();
	}

	public Rettangolo() {
		setPainter(new PainterRettangolo(this));
		init();
	}

	public int getPerimetro() {
		return latoAlto.getLunghezza() + latoBasso.getLunghezza() + latoSinistra.getLunghezza() + latoDestra.getLunghezza();
	}

	public int getArea() {
		return getWidth() * getHeight();
	}

	@Override
	public Point getPuntoCentrale() {
		final int puntoCentraleX = getX() + (getWidth() / 2);
		final int puntoCentrateY = getY() + (getHeight() / 2);
		return new Point(puntoCentraleX, puntoCentrateY);
	}

	@Override
	public void setSize(final int width, final int height) {
		super.setSize(width, height);
		settaLati();

	}

	private void settaLati() {
		latoAlto.setOrigine(new Point(getX(), getY()));
		latoAlto.setDestinazione(new Point(getX() + getWidth(), getY()));
		latoBasso.setOrigine(new Point(getX(), getY() + getHeight()));
		latoBasso.setDestinazione(new Point(getX() + getWidth(), getY() + getHeight()));
		latoDestra.setOrigine(new Point(getX() + getWidth(), getY()));
		latoDestra.setDestinazione(new Point(getX() + getWidth(), getY() + getHeight()));
		latoSinistra.setOrigine(new Point(getX(), getY()));
		latoSinistra.setDestinazione(new Point(getX(), getY() + getHeight()));
	}

	@Override
	public Point getLocation() {
		return super.getLocation();
	}

	@Override
	public void setLocation(final int x, final int y) {
		super.setLocation(x, y);
		settaLati();
	}

	public Lato getLatoAlto() {
		return latoAlto;
	}

	public Lato getLatoBasso() {
		return latoBasso;
	}

	public Lato getLatoSinistra() {
		return latoSinistra;
	}

	public Lato getLatoDestra() {
		return latoDestra;
	}

	@Override
	public void ridimensiona(final Point mouse) {

		if (getLatiVicinoMouse(mouse).contains(latoSinistra)) {
			ridimensionaClickSuLatoSinistro(mouse);
		}
		if (getLatiVicinoMouse(mouse).contains(latoDestra)) {
			ridimensionaClickSuLatoDestro(mouse);
		}
		if (getLatiVicinoMouse(mouse).contains(latoAlto)) {
			ridimensionaClickSuLatoAlto(mouse);
		}
		if (getLatiVicinoMouse(mouse).contains(latoBasso)) {
			ridimensionaClickSuLatoBasso(mouse);
		}

		setLocation(getX(), getY());
		setSize(getWidth(), getHeight());
	}

	private void ridimensionaClickSuLatoBasso(final Point mouse) {
		final int altezza = (int) (mouse.getY() - getY() > 3 ? mouse.getY() - getY() : 3);
		setHeight(altezza);
	}

	private void ridimensionaClickSuLatoAlto(final Point mouse) {
		final double newY = mouse.getY() - distanzaMouseDaXY.getY();
		final int height = (int) (getY() - newY) + getHeight();
		if (height > 3) {
			setHeight(height);
			setY((int) (newY));
		}
	}

	private void ridimensionaClickSuLatoDestro(final Point mouse) {
		final int larghezza = (int) (mouse.getX() - getX() > 3 ? mouse.getX() - getX() : 3);
		setWidth(larghezza);
	}

	private void ridimensionaClickSuLatoSinistro(final Point mouse) {
		final double newX = mouse.getX() - distanzaMouseDaXY.getX();
		final int width = (int) (getX() - newX) + getWidth();
		if (width > 3) {
			setWidth(width);
			setX((int) (newX));
		}
	}

	private boolean checkIfmouseIsInRegion(final Point mouse) {
		final Rectangle rect = new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight());
		return mouseIsInRegion = UtilDisegno.isInRegion(mouse, rect);
	}

	@Override
	public boolean isInRegion(final Point mouse) {
		return checkIfmouseIsInRegion(mouse);
	}

}
