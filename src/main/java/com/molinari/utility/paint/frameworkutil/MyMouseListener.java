package com.molinari.utility.paint.frameworkutil;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.List;

import com.molinari.utility.controller.ControlloreDisegno;
import com.molinari.utility.paint.UtilDisegno;
import com.molinari.utility.paint.objects.Cerchio;
import com.molinari.utility.paint.objects.FormaGeometrica;
import com.molinari.utility.paint.objects.poligoni.Poligono;

public class MyMouseListener {
	
	private PannelloDisegno p;

	public MyMouseListener(PannelloDisegno p) {
		super();
		this.setP(p);
	}

	MouseAdapter mouseAdapter = new MouseAdapter() {

		@Override
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

		@Override
		public void mouseReleased(final MouseEvent e) {
			ControlloreDisegno.mouseReleased(e);
		}

		@Override
		public void mouseClicked(final MouseEvent e) {
			//do nothing
		}
	};

	MouseMotionAdapter mouseMotionAdapter = new MouseMotionAdapter() {
		@Override
		public void mouseMoved(final MouseEvent e) {
			//do nothing
		}

		@Override
		public void mouseDragged(final MouseEvent e) {

			final int x = e.getX();
		    final int y = e.getY();
			final Point puntatore = new Point(x, y);

			if (getP().getOggettoSelezionato() instanceof Poligono) {
				final Poligono pol = (Poligono) getP().getOggettoSelezionato();
				if (!pol.getLatiVicinoMouse(puntatore).isEmpty()) {
					pol.ridimensiona(puntatore);
				} else if (pol.isInRegion(puntatore)) {
					pol.moveTo(x, y);
				}
			} else if (getP().getOggettoSelezionato() instanceof Cerchio) {
				final Cerchio cerchio = (Cerchio) getP().getOggettoSelezionato();
				
				if(cerchio.getIsOnCirconferenza()){
					cerchio.ridimensiona(puntatore);
				} else if (cerchio.isInRegion(puntatore)) {
					cerchio.moveTo(x, y);
				}
			}
			Image offscreen = null;
			Graphics bufferGraphics = null;
			final Object[] returns = UtilDisegno.getImmagineBufferizzata(new Dimension(getP().getWidth(), getP().getHeight()), offscreen);
			bufferGraphics = (Graphics) returns[0];
			offscreen = (Image) returns[1];
			bufferGraphics.setColor(Color.WHITE);
			bufferGraphics.fillRect(0, 0, getP().getWidth(), getP().getHeight());
			bufferGraphics.setColor(Color.BLACK);
			if (getP().getOggettoSelezionato() != null) {
				getP().paint(bufferGraphics);
				getP().getGraphics().drawImage(offscreen, 0, 0, null);
			}
		}

	};

	public MouseAdapter getMouseAdapter() {
		return mouseAdapter;
	}

	public MouseMotionAdapter getMouseMotionAdapter() {
		return mouseMotionAdapter;
	}

	public PannelloDisegno getP() {
		return p;
	}

	public void setP(PannelloDisegno p) {
		this.p = p;
	}
}
