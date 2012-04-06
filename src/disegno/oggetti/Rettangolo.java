package disegno.oggetti;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import disegno.UtilDisegno;
import disegno.oggetti.painter.IPainter;
import disegno.oggetti.painter.PainterRettangolo;

public class Rettangolo extends FormaGeometrica2D implements IFormaGeometrica2D {

	private Lato latoAlto = new Lato("Alto");
	private Lato latoBasso = new Lato("Basso");
	private Lato latoSinistra = new Lato("Sinistra");
	private Lato latoDestra = new Lato("Destra");
	private ArrayList<Lato> latiSuMouse;
	private boolean mouseIsInRegion;
	private Color background = Color.WHITE;

	public static void main(String[] args) {
		
	}
	
	public boolean isMouseIsInRegion() {
		return mouseIsInRegion;
	}

	public ArrayList<Lato> getLatiSuMouse() {
		return latiSuMouse;
	}

	public Rettangolo(final String nome, final IPainter painter) {
		super(nome, painter);
		init();
	}

	private void init() {
		add(latoAlto);
		add(latoBasso);
		add(latoDestra);
		add(latoSinistra);
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
		int puntoCentraleX = getX() + (getWidth() / 2);
		int puntoCentrateY = getY() + (getHeight() / 2);
		return new Point(puntoCentraleX, puntoCentrateY);
	}

	@Override
	public void setSize(final int width, final int height) {
		super.setSize(width, height);
		settaLati();

	}

	private void settaLati() {
		this.latoAlto.setOrigine(new Point(getX(), getY()));
		this.latoAlto.setDestinazione(new Point(getX() + getWidth(), getY()));
		this.latoBasso.setOrigine(new Point(getX(), getY() + getHeight()));
		this.latoBasso.setDestinazione(new Point(getX() + getWidth(), getY() + getHeight()));
		this.latoDestra.setOrigine(new Point(getX() + getWidth(), getY()));
		this.latoDestra.setDestinazione(new Point(getX() + getWidth(), getY() + getHeight()));
		this.latoSinistra.setOrigine(new Point(getX(), getY()));
		this.latoSinistra.setDestinazione(new Point(getX(), getY() + getHeight()));
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

		if(latiSuMouse.contains(latoSinistra)){
			ridimensionaClickSuLatoSinistro(mouse);
		}
		if(latiSuMouse.contains(latoDestra)){
			ridimensionaClickSuLatoDestro(mouse);
		}
		if(latiSuMouse.contains(latoAlto)){
			ridimensionaClickSuLatoAlto(mouse);
		}
		if(latiSuMouse.contains(latoBasso)){
			ridimensionaClickSuLatoBasso(mouse);
		}
		
		setLocation(getX(), getY());
		setSize(getWidth(), getHeight());
	}

	private void ridimensionaClickSuLatoBasso(final Point mouse) {
		setHeight((int) (Math.abs(mouse.getY() - getY())));
	}

	private void ridimensionaClickSuLatoAlto(final Point mouse) {
		double newY = mouse.getY() - distanzaMouseDaXY.getY();
		setHeight((int) (getY() - newY) + getHeight());
		setY((int) (newY));

	}

	private void ridimensionaClickSuLatoDestro(final Point mouse) {
		setWidth((int) (Math.abs(mouse.getX() - getX())));
	}

	private void ridimensionaClickSuLatoSinistro(final Point mouse) {
		double newX = mouse.getX() - distanzaMouseDaXY.getX();
		setWidth((int) (getX() - newX) + getWidth());
		setX((int) (newX));
	}

	public void settaLatiSuMouse(final Point mouse) {
		latiSuMouse = this.setMouseSuiLati(mouse);
	}

	private boolean checkIfmouseIsInRegion(final Point mouse) {
		Rectangle rect = new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight());
		return mouseIsInRegion = UtilDisegno.isInRegion(mouse, rect);
	}

	public Color getBackground() {
		return background;
	}

	public void setBackground(Color background) {
		this.background = background;
	}

	@Override
	public boolean isInRegion(Point mouse) {
		return checkIfmouseIsInRegion(mouse);
	}
	
}
