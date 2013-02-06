package controller;

import grafica.componenti.contenitori.FrameBase;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import disegno.UtilDisegno;
import disegno.oggetti.Cerchio;
import disegno.oggetti.FormaGeometrica;
import disegno.oggetti.poligoni.Poligono;
import disegno.utilFramework.PannelloDisegno;

public abstract class ControlloreDisegno extends ControlloreBase {

	public static PannelloDisegno p;

	@Override
	public void mainOverridato(final FrameBase frame) {
		frame.setSize(400, 600);
		p = getPannello(frame);
		p.setSize(400, 600);
		p.setBackground(Color.white);
	}

	protected abstract PannelloDisegno getPannello(FrameBase frame);

	public static void mouseClicked(final MouseEvent e) {
	}

	public static void mouseDragged(final MouseEvent e) {
		final int x = e.getX(), y = e.getY();
		final Point puntatore = new Point(x, y);

		if (p.getOggettoSelezionato() instanceof Poligono) {
			final Poligono pol = (Poligono) p.getOggettoSelezionato();
			// System.out.println("n lati vicini mouse: " +
			// ret.isMouseSuiLati(puntatore).size());
			if (pol.getLatiVicinoMouse(puntatore).size() > 0) {
				pol.ridimensiona(puntatore);
			} else if (pol.isInRegion(puntatore)) {
				pol.moveTo(x, y);
			}
		} else if (p.getOggettoSelezionato() instanceof Cerchio) {
			final Cerchio cerchio = (Cerchio) p.getOggettoSelezionato();
			if (cerchio.isOnCirconferenza()) {
				cerchio.ridimensiona(puntatore);
			} else if (cerchio.isInRegion(puntatore)) {
				cerchio.moveTo(x, y);
			}
		}
		Image offscreen = null;
		Graphics bufferGraphics = null;
		final Object[] returns = UtilDisegno.getImmagineBufferizzata(new Dimension(p.getWidth(), p.getHeight()), offscreen, bufferGraphics);
		bufferGraphics = (Graphics) returns[0];
		offscreen = (Image) returns[1];
		bufferGraphics.setColor(Color.WHITE);
		bufferGraphics.fillRect(0, 0, p.getWidth(), p.getHeight());
		bufferGraphics.setColor(Color.BLACK);
		if (p.getOggettoSelezionato() != null) {
			p.paint(bufferGraphics);
			p.getGraphics().drawImage(offscreen, 0, 0, null);
		}

		// System.out.println("*********************************");
		// System.out.println("X Rettangolo: " + ret.getX());
		// System.out.println("Y Rettangolo: " + ret.getY());
		// System.out.println("Larghezza rettangolo: " + ret.getWidth());
		// System.out.println("Altezza Rettangolo: " + ret.getHeight());
		// System.out.println("Mouse X: " + puntatore.getX());
		// System.out.println("Mouse Y: " + puntatore.getY());
		// System.out.println("PuntoCentrale X,Y: " +
		// ret.getPuntoCentrale().getX() + ", " +
		// ret.getPuntoCentrale().getY());
		// System.out.println("*********************************");
	}

	public static void mouseMoved(final MouseEvent e) {
	}

	public static void mousePressed(final MouseEvent e) {

		final int x = e.getX(), y = e.getY();
		final Point mouse = new Point(x, y);
		final ArrayList<FormaGeometrica> oggetti = p.getOggetti();
		for (final FormaGeometrica formaGeometrica2 : oggetti) {
			final FormaGeometrica formaGeometrica = formaGeometrica2;
			if (formaGeometrica.isInRegion(mouse)) {
				p.setOggettoSelezionato(formaGeometrica);
				break;
			}

		}
		if (p.getOggettoSelezionato() != null) {
			final FormaGeometrica forma = p.getOggettoSelezionato();
			if (forma instanceof Poligono) {
				((Poligono) forma).setMouseSuiLati(mouse);
			}
			forma.settaDistanzaDaMouse(mouse);
		}
	}

	public static void mouseReleased(final MouseEvent e) {
	}
}