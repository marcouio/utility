package com.molinari.utility.controller;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.List;

import com.molinari.utility.graphic.component.container.FrameBase;
import com.molinari.utility.paint.frameworkutil.PannelloDisegno;
import com.molinari.utility.paint.objects.Cerchio;
import com.molinari.utility.paint.objects.FormaGeometrica;
import com.molinari.utility.paint.objects.poligoni.Poligono;

public abstract class ControlloreDisegno extends ControlloreBase {

	private PannelloDisegno p;

	@Override
	public void mainOverridato(final FrameBase frame) {
		frame.setSize(400, 600);
		setP(getPannello(frame));
		getP().setSize(400, 600);
		getP().setBackground(Color.white);
	}

	protected abstract PannelloDisegno getPannello(FrameBase frame);

	public void mouseClicked(final MouseEvent e) {
	}

	public void mouseDragged(final MouseEvent e) {}

	public void mouseMoved(final MouseEvent e) {
	}

	public void mousePressed(final MouseEvent e) {

		final int x = e.getX(); 
		final int y = e.getY();
		final Point mouse = new Point(x, y);
		final List<FormaGeometrica> oggetti = getP().getOggetti();
		for (final FormaGeometrica formaGeometrica2 : oggetti) {
			final FormaGeometrica formaGeometrica = formaGeometrica2;
			if (formaGeometrica.isInRegion(mouse)) {
				getP().setOggettoSelezionato(formaGeometrica);
				break;
			}

		}
		if (getP().getOggettoSelezionato() != null) {
			final FormaGeometrica forma = getP().getOggettoSelezionato();
			if (forma instanceof Poligono) {
				((Poligono) forma).setMouseSuiLati(mouse);
			}
			if(forma instanceof Cerchio){
				((Cerchio) forma).setIsOnCirconferenza(mouse);
			}
			forma.settaDistanzaDaMouse(mouse);
		}
	}

	public static void mouseReleased(final MouseEvent e) {
	}

	public PannelloDisegno getP() {
		return p;
	}

	public void setP(PannelloDisegno p) {
		this.p = p;
	}
}
