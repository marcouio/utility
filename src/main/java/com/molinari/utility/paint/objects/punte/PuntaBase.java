package com.molinari.utility.paint.objects.punte;

import com.molinari.utility.graphic.component.container.PannelloBase;
import com.molinari.utility.paint.objects.FormaGeometrica2D;
import com.molinari.utility.paint.objects.Segmento;
import com.molinari.utility.paint.objects.painter.PainterPuntaBase;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class PuntaBase extends FormaGeometrica2D {

	private Segmento linea;
	private Point estremoCentrale;
	private Segmento latoSinistro;
	private Segmento latoDestro;
	private int lunghezzaLati = LUNGHEZZA_LATI_DEF;
	final double ARROW_ANGLE = 45;

	public static final int LUNGHEZZA_LATI_DEF = 10;
	
	public PuntaBase(final Segmento segmento, final int lunghezzaLati) {
		linea = segmento;
		this.lunghezzaLati = lunghezzaLati;
		calcolaAngoloLati();
		setPainter(new PainterPuntaBase(this));
		// add(latoSinistro);
		// add(latoDestro);
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
		// TODO Auto-generated method stub

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

	public static void main(final String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				final JFrame f = new JFrame();
				f.setVisible(true);
				f.getContentPane().add(new PannelloBase(f) {

					private static final long serialVersionUID = 1L;

					@Override
					protected void paintComponent(final Graphics g) {
						super.paintComponent(g);
						final Point uno = new Point(120, 120);
						final Point due = new Point(240, 120);
						final Segmento l = new Segmento(uno, due);
						l.draw(g);
						// ArrowHead ah = new ArrowHead();
						// ArrowHead.NONE.draw((Graphics2D) g, uno, due);
						// ah.draw((Graphics2D) g, uno, due);
						final PuntaTriangolo punta = new PuntaTriangolo(l, 20);
						punta.setBackground(Color.RED);
						// PuntaBase punta = new PuntaBase(l, 20);
						punta.draw(g);
					}
				});
				f.setBounds(0, 0, 600, 500);
				f.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(final WindowEvent event) {
						System.exit(0);
					}
				});
			}
		});
	}

	@Override
	public boolean isInRegion(final Point mouse) {
		// TODO Auto-generated method stub
		return false;
	}
}
