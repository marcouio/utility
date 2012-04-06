package disegno.oggetti.punte;

import grafica.componenti.contenitori.PannelloBase;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import disegno.oggetti.FormaGeometricaComplessa;
import disegno.oggetti.Segmento;

public class PuntaBase extends FormaGeometricaComplessa {

	private Segmento linea;
	private Point estremoCentrale;
	private Segmento latoSinistro;
	private Segmento latoDestro;
	private int lunghezzaLati = 10;
	final double ARROW_ANGLE = 45;

	public PuntaBase(final Segmento segmento, final int lunghezzaLati) {
		this.linea = segmento;
		this.lunghezzaLati = lunghezzaLati;
		calcolaAngoloLati();

		add(latoSinistro);
		add(latoDestro);
	}

	private void calcolaAngoloLati() {

		Point origine = linea.getOrigine();
		Point destinazione = linea.getDestinazione();

		estremoCentrale = destinazione;

		//questo è l'angolo del segmento associato alla freccia
		double angolo = Math.atan2(origine.getY() - destinazione.getY(), origine.getX() - destinazione.getX());

		//l'angolo della parte sinistra della punta
		double angoloUno = angolo + Math.toRadians(ARROW_ANGLE);

		//il punto di destinazione del lato sinistro della freccia
		Point pointSinistro = new Point((int) estremoCentrale.getX() + (int) (Math.cos(angoloUno) * lunghezzaLati), (int) (estremoCentrale.getY() + (int) (Math.sin(angoloUno) * lunghezzaLati)));

		latoSinistro = new Segmento(estremoCentrale, pointSinistro);

		//l'angolo della parte destra della punta
		double angoloDue = angolo - Math.toRadians(ARROW_ANGLE);

		//il punto di destinazione del lato destro della freccia
		Point pointDestro = new Point((int) estremoCentrale.getX() + (int) (Math.cos(angoloDue) * lunghezzaLati), (int) estremoCentrale.getY() + (int) (Math.sin(angoloDue) * lunghezzaLati));

		latoDestro = new Segmento(estremoCentrale, pointDestro);

	}

	public PuntaBase(final Segmento segmento) {
		this.linea = segmento;
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
				JFrame f = new JFrame();
				f.setVisible(true);
				f.getContentPane().add(new PannelloBase(f) {
					
					private static final long serialVersionUID = 1L;

					@Override
					protected void paintComponent(final Graphics g) {
						super.paintComponent(g);
						Point uno = new Point(120, 120);
						Point due = new Point(240, 120);
						Segmento l = new Segmento(uno, due);
						l.draw(g);
						//	    					ArrowHead ah = new ArrowHead();
						//	    					ArrowHead.NONE.draw((Graphics2D) g, uno, due);
						//	    					ah.draw((Graphics2D) g, uno, due);
						PuntaTriangolo punta = new PuntaTriangolo(l, 20);
						punta.setBackground(Color.RED);
						//						PuntaBase punta = new PuntaBase(l, 20);
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
	public boolean isInRegion(Point mouse) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void settaDistanzaDaMouse(Point puntatoreMouse) {
		// TODO Auto-generated method stub
		
	}

}
