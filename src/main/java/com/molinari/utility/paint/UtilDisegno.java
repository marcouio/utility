package com.molinari.utility.paint;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Transparency;

import com.molinari.utility.paint.images.UtilImage;
import com.molinari.utility.paint.objects.Segmento;

public class UtilDisegno {

	public static final int SENSIBILITA = 3;
	
	public static Object[] getImmagineBufferizzata(final Dimension dimensioniImage, Image offscreen, Graphics bufferGraphics) {
		Object[] returns = new Object[2];
		if (offscreen == null) {
			offscreen = UtilImage.createVolatileImage(((int) dimensioniImage.getWidth()), ((int) dimensioniImage.getHeight()), Transparency.TRANSLUCENT);
		}
		returns[0] = bufferGraphics = offscreen.getGraphics();
		returns[1] = offscreen;
		return returns;

	}

	/**
	 * Controlla se il punto centrale dell'oggettoGrafico è all'interno
	 * dell'area di un rettangolo
	 * 
	 * @param rectangle
	 * @return
	 */
	public static boolean isInRectangle(final Rectangle rectangle, final Point posizioneOggettoGrafico) {
		final Point posizioneRettangolo = new Point((int) rectangle.getX(), (int) rectangle.getY());

		if ((posizioneOggettoGrafico.getY() > posizioneRettangolo.getY()) && (posizioneOggettoGrafico.getY() < posizioneRettangolo.getY() + rectangle.getHeight())) {
			if ((posizioneOggettoGrafico.getX() > posizioneRettangolo.getX()) && (posizioneOggettoGrafico.getX() < posizioneRettangolo.getX() + rectangle.getWidth())) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isInRegion(final int x, final int y, final Rectangle rectangle) {

		if ((x >= rectangle.getX() && x <= rectangle.getX() + rectangle.getWidth()) && (y >= rectangle.getY() && y <= rectangle.getY() + rectangle.getHeight() - 1)) {
			return true;
		} else {
			return false;
		}

	}
	
	public static boolean isMouseNearPoint(Point mouse, Point reference) {
		boolean xNear = Math.abs(mouse.getX() - reference.getX()) < SENSIBILITA;
		boolean yNear = Math.abs(mouse.getY() - reference.getY()) < SENSIBILITA;
		return xNear && yNear;
	}
	
	public static double getArcoTangenteSegmento(Segmento linea){
		final Point origine = linea.getOrigine();
		final Point destinazione = linea.getDestinazione();
		return Math.atan2(origine.getY() - destinazione.getY(), origine.getX() - destinazione.getX());
	}
	
	public static boolean isInRegion(final Point mouse, final Rectangle rectangle) {
			return isInRegion((int)mouse.getX(), (int)mouse.getY(), rectangle);
	}

	public static Point makePointByAngle(Point target, final double angolo, int refLength) {
		int xDestLatoDestro = (int) target.getX() + (int) (Math.cos(angolo) * refLength);
		int yDestLatoDestro = (int) target.getY() + (int) (Math.sin(angolo) * refLength);
		final Point pointDestro = new Point(xDestLatoDestro, yDestLatoDestro);
		return pointDestro;
	}

	public static int makeLengthByAngle(final double angolo, Point source, Point target){
		int lato = (int) ((source.getX() - target.getX()) / Math.cos(angolo));
		return Math.abs(lato);
	}
	
	public static int makeHeightByAngle(final double angolo, Point source, Point target){
		int lato = (int) ((source.getY() - target.getY()) / Math.cos(angolo));
		return Math.abs(lato);
	}
}
