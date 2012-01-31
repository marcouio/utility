package disegno;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Transparency;

import disegno.immagini.UtilImage;

public class UtilDisegno {

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
	
	public static boolean isInRegion(final Point mouse, final Rectangle rectangle) {
			return isInRegion((int)mouse.getX(), (int)mouse.getY(), rectangle);
	}
}
