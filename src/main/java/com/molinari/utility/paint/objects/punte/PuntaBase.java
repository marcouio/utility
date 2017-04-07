package com.molinari.utility.paint.objects.punte;

import java.awt.Point;

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

	private void calcolaAngoloLati() {

		final Point origine = linea.getOrigine();
		final Point destinazione = linea.getDestinazione();

		estremoCentrale = destinazione;

		// questo è l'angolo del segmento associato alla freccia
		final double angolo = Math.atan2(origine.getY() - destinazione.getY(), origine.getX() - destinazione.getX());

		// l'angolo della parte sinistra della punta
		final double angoloUno = angolo + Math.toRadians(ARROW_ANGLE);

		// il punto di destinazione del lato sinistro della freccia
		final Point pointSinistro = new Point((int) estremoCentrale.getX() + (int) (Math.cos(angoloUno) * lunghezzaLati),
				(int) (estremoCentrale.getY() + (int) (Math.sin(angoloUno) * lunghezzaLati)));

		latoSinistro = new Segmento(estremoCentrale, pointSinistro);

		// l'angolo della parte destra della punta
		final double angoloDue = angolo - Math.toRadians(ARROW_ANGLE);

		// il punto di destinazione del lato destro della freccia
		final Point pointDestro = new Point((int) estremoCentrale.getX() + (int) (Math.cos(angoloDue) * lunghezzaLati), (int) estremoCentrale.getY()
				+ (int) (Math.sin(angoloDue) * lunghezzaLati));

		latoDestro = new Segmento(estremoCentrale, pointDestro);

	}

	public PuntaBase(final Segmento segmento) {
		this(segmento, LUNGHEZZA_LATI_DEF);
	}

	@Override
	public Point getPuntoCentrale() {
		return estremoCentrale;
	}

	@Override
	public void ridimensiona(final Point mouse) {
		//do nothing
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
		return false;
	}
}
