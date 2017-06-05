package com.molinari.utility.paint;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Transparency;

import com.molinari.utility.paint.images.UtilImage;
import com.molinari.utility.paint.objects.Segmento;

public class UtilDisegno {
	
	private UtilDisegno() {
		//do nothing
	}

	public static final int SENSIBILITA = 3;
	
	public static Object[] getImmagineBufferizzata(final Dimension dimensioniImage, Image offscreenPar) {
		Image offscreen = offscreenPar;
		Object[] returns = new Object[2];
		if (offscreen == null) {
			offscreen = UtilImage.createVolatileImage(((int) dimensioniImage.getWidth()), ((int) dimensioniImage.getHeight()), Transparency.TRANSLUCENT);
		}
		returns[0] = offscreen.getGraphics();
		returns[1] = offscreen;
		return returns;

	}

	public static boolean isInRegion(final int x, final int y, final Rectangle rectangle) {

		return ((x >= rectangle.getX() && x <= rectangle.getX() + rectangle.getWidth()) && (y >= rectangle.getY() && y <= rectangle.getY() + rectangle.getHeight() - 1));

	}
	
	public static boolean isMouseNearPoint(Point mouse, Point reference) {
		boolean xNear = Math.abs(mouse.getX() - reference.getX()) <= SENSIBILITA;
		boolean yNear = Math.abs(mouse.getY() - reference.getY()) <= SENSIBILITA;
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
		return new Point(xDestLatoDestro, yDestLatoDestro);
	}

	public static int makeLengthByAngle(Point source, Point target){
		double angolo = getArcoTangenteSegmento(new Segmento(source, target));
		int lato = (int) ((source.getX() - target.getX()) / Math.cos(angolo));
		return Math.abs(lato);
	}
	
	public static int makeHeightByAngle(Point source, Point target){
		double angolo = getArcoTangenteSegmento(new Segmento(source, target));
		int lato = (int) ((source.getY() - target.getY()) / Math.sin(angolo));
		return Math.abs(lato);
	}
}
