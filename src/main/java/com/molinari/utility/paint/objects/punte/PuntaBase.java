package com.molinari.utility.paint.objects.punte;

import java.awt.Point;

import com.molinari.utility.paint.UtilDisegno;
import com.molinari.utility.paint.objects.FormaGeometrica2D;
import com.molinari.utility.paint.objects.Segmento;
import com.molinari.utility.paint.objects.painter.PainterPuntaBase;

public class PuntaBase extends FormaGeometrica2D {

	private Segmento linea;
	private Point estremoCentrale;
	private Segmento latoSinistro;
	private Segmento latoDestro;
	private int lunghezzaLati = LUNGHEZZA_LATI_DEF;
	private static final double ARROW_ANGLE = 45;

	public static final int LUNGHEZZA_LATI_DEF = 10;

	public PuntaBase(final Segmento segmento, final int lunghezzaLati) {
		linea = segmento;
		this.lunghezzaLati = lunghezzaLati;
		calcolaAngoloLati();
		setPainter(new PainterPuntaBase(this));
	}
	
	public PuntaBase(final Segmento segmento) {
		this(segmento, LUNGHEZZA_LATI_DEF);
	}

	protected double getLeftAngle() {
		
		final double angolo = getArrowAngle();

		// l'angolo della parte sinistra della punta
		return angolo + Math.toRadians(ARROW_ANGLE);

	}
	
	protected double getRightAngle() {
		
		final double angolo = getArrowAngle();
		
		// l'angolo della parte sinistra della punta
		return angolo - Math.toRadians(ARROW_ANGLE);
		
	}
	
	@Override
	public void moveTo(int x, int y) {
		
		if (getDistanzaMouseDaXY() != null) {
			final double angolo = getArrowAngle();
			int latoLinea = UtilDisegno.makeLengthByAngle(linea.getOrigine(), linea.getDestinazione());
			estremoCentrale = new Point(x - (int) getDistanzaMouseDaXY().getX(), y - (int) getDistanzaMouseDaXY().getY());
			setX((int)estremoCentrale.getX());
			setY((int)estremoCentrale.getY());
			
			Point pointByAngle = UtilDisegno.makePointByAngle(estremoCentrale, angolo, latoLinea);
			
			linea.setDestinazione(estremoCentrale);
			linea.setOrigine(pointByAngle);
			calcolaAngoloLati();
			
		}
	}
	
	public static void main(String[] args) {
		double cos = Math.cos(Math.toRadians(0));
		System.out.println(cos);
	}
	
	public double getArrowAngle() {
		
		// questo è l'angolo del segmento associato alla freccia
		return UtilDisegno.getArcoTangenteSegmento(linea);
	}
	
	@Override
	public void settaDistanzaDaMouse(Point puntatoreMouse) {
		final Point distanza = new Point((int) puntatoreMouse.getX() - (int)estremoCentrale.getX(), (int) puntatoreMouse.getY() - (int)estremoCentrale.getY());
		setDistanzaMouseDaXY(distanza);
	}

	private void calcolaAngoloLati() {

		estremoCentrale = linea.getDestinazione();

		// l'angolo della parte destro della punta
		final double angoloUno = getLeftAngle();

		// il punto di destinazione del lato destro della freccia
		final Point pointDestro = UtilDisegno.makePointByAngle(estremoCentrale, angoloUno, lunghezzaLati);

		latoDestro = new Segmento(estremoCentrale, pointDestro);

		// l'angolo della parte destra della punta
		final double angoloDue = getRightAngle();

		// il punto di destinazione del lato destro della freccia
		final Point pointLeft = UtilDisegno.makePointByAngle(estremoCentrale, angoloDue, lunghezzaLati);

		latoSinistro = new Segmento(estremoCentrale, pointLeft);

	}

	public Segmento getLatoAlto() {
		Point destinazioneDestra = latoDestro.getDestinazione();
		double yDestra = destinazioneDestra.getY();

		Point destinazioneSinistra = latoSinistro.getDestinazione();
		double ySinistra = destinazioneSinistra.getY();

		if (yDestra > ySinistra) {
			return latoDestro;
		}
		return latoSinistro;

	}

	public Segmento getLatoBasso() {
		Point destinazioneDestra = latoDestro.getDestinazione();
		double yDestra = destinazioneDestra.getY();

		Point destinazioneSinistra = latoSinistro.getDestinazione();
		double ySinistra = destinazioneSinistra.getY();

		if (yDestra < ySinistra) {
			return latoDestro;
		}
		return latoSinistro;

	}

	public boolean isPuntaVersoAlto() {

		final Point origine = linea.getOrigine();
		final Point destinazione = linea.getDestinazione();

		return origine.getY() < destinazione.getY();

	}

	public boolean isPuntaVersoBasso() {
		final Point origine = linea.getOrigine();
		final Point destinazione = linea.getDestinazione();

		return origine.getY() > destinazione.getY();
	}

	public boolean isPuntaVersoDestra() {

		final Point origine = linea.getOrigine();
		final Point destinazione = linea.getDestinazione();

		return destinazione.getX() > origine.getX();

	}

	public boolean isPuntaVersoSinistra() {

		final Point origine = linea.getOrigine();
		final Point destinazione = linea.getDestinazione();

		return destinazione.getX() < origine.getX();

	}

	@Override
	public Point getPuntoCentrale() {
		return estremoCentrale;
	}

	@Override
	public void ridimensiona(final Point mouse) {
		// do nothing
	}

	public Segmento getLinea() {
		return linea;
	}

	public void setLinea(final Segmento linea) {
		this.linea = linea;
	}

	public Point getEstremoCentrale() {
		return estremoCentrale;
	}

	public void setEstremoCentrale(final Point estremoCentrale) {
		this.estremoCentrale = estremoCentrale;
	}

	public Segmento getLatoSinistro() {
		return latoSinistro;
	}

	public void setLatoSinistro(final Segmento latoSinistro) {
		this.latoSinistro = latoSinistro;
	}

	public Segmento getLatoDestro() {
		return latoDestro;
	}

	public void setLatoDestro(final Segmento latoDestro) {
		this.latoDestro = latoDestro;
	}

	public int getLunghezzaLati() {
		return lunghezzaLati;
	}

	public void setLunghezzaLati(final int lunghezzaLati) {
		this.lunghezzaLati = lunghezzaLati;
	}

	public double getARROW_ANGLE() {
		return ARROW_ANGLE;
	}

	@Override
	public boolean isInRegion(final Point mouse) {
		
		for (int i = 0; i < lunghezzaLati; i++) {
			Point pointByAngolo = UtilDisegno.makePointByAngle(estremoCentrale, getLeftAngle(), i);
			boolean mouseNearPoint = UtilDisegno.isMouseNearPoint(mouse, pointByAngolo);
			if(mouseNearPoint){
				return true;
			}
		}
		for (int i = 0; i < lunghezzaLati; i++) {
			Point pointByAngolo = UtilDisegno.makePointByAngle(estremoCentrale, getRightAngle(), i);
			boolean mouseNearPoint = UtilDisegno.isMouseNearPoint(mouse, pointByAngolo);
			if(mouseNearPoint){
				return true;
			}
		}
		
		return false;
	}
	
}
