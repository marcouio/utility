package com.molinari.utility.paint.images;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.awt.print.PageFormat;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.molinari.utility.controller.ControlloreBase;

public class UtilImage extends JPanel {

	private static final long serialVersionUID = 1L;

	public static void main(final String[] args) {
		final JFrame f = new JFrame();
		f.getContentPane().add(new UtilImage());
		f.setBounds(0, 0, 800, 600);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	/**
	 * Potrebbe non funzionare: verificare se il parametro image deve essere un bufferedImage
	 * 
	 * @param image
	 * @param scale
	 */
	public static Image getScaledImage(Image parImage, final double scale) {
		Image image = parImage;
		int w = image.getWidth(null);
		int h = image.getHeight(null);
		BufferedImage scaled = new BufferedImage((int) (w * scale), (int) (h * scale), ((BufferedImage) image).getType());
		AffineTransform at = AffineTransform.getScaleInstance(scale, scale);
		Graphics2D g2d = (Graphics2D) scaled.getGraphics();
		g2d.drawImage((BufferedImage) image, new AffineTransformOp(at, AffineTransformOp.TYPE_NEAREST_NEIGHBOR), 0, 0);
		return scaled;
	}

	@Override
	public void paint(final Graphics g) {
		super.paint(g);
		rotate(g, "Ciaociao", 140, 140, 180);
		rotate2(g, "ciaociao22222", 50, 150, 270);
	}

	public void rotate2(final Graphics g, final String stringa, final int x, final int y, final int gradi) {
		final Graphics2D g2 = (Graphics2D) g;
		g2.rotate(Math.toRadians(gradi), x, y);
		g2.drawString(stringa, x, y);
	}

	public void rotate(final Graphics g, final String stringa, final int x, final int y, final int gradi) {
		final Graphics2D g2 = (Graphics2D) g;
		final AffineTransform at = g2.getTransform();
		final AffineTransform newAt = new AffineTransform();
		newAt.rotate(Math.toRadians(gradi), x, y);
		g2.transform(newAt);
		g2.drawString(stringa, x, y);
		g2.setTransform(at);
	}

	/**
	 * Ancora non funziona!!! Attenzione!!! Muove, ridimensiona e scala
	 * un'immagine
	 * 
	 * @param image
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param pageFormat
	 * @return
	 */
	public BufferedImage moveResizeAndScale(final Image image, final int x, final int y, final int width, final int height, final PageFormat pageFormat) {
		final BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		final Graphics2D g2 = (Graphics2D) bi.getGraphics();
		g2.translate(x, y);

		// calcola una costante per scalare la grandezza dell'immagine
		double costante = (pageFormat.getImageableWidth() - pageFormat.getImageableX()) / image.getWidth(null);
		if (image.getHeight(null) * costante > pageFormat.getImageableHeight()) {
			costante = (pageFormat.getImageableHeight() - pageFormat.getImageableY()) / image.getHeight(null);
		}

		// il graphics viene scalato per far rientrare l'immagine nella pagina
		g2.scale(costante, costante);
		g2.drawImage(image, 0, 0, null);
		return bi;

	}

	/**
	 * Restituisce la sezione di immagine contenuta nel rettangolo
	 * 
	 * @param immagine
	 * @param rettangolo
	 * @return
	 */
	public static Image getSubImage(final Image immagine, final Rectangle rettangolo) {
		final BufferedImage b = new BufferedImage(immagine.getWidth(null), immagine.getHeight(null), BufferedImage.TYPE_INT_RGB);
		final Graphics2D g = b.createGraphics();
		g.drawImage(immagine, 0, 0, null);
		g.dispose();

		final BufferedImage sottoimmagine = b.getSubimage((int) rettangolo.getX(), (int) rettangolo.getY(), (int) rettangolo.getWidth(), (int) rettangolo.getHeight());
		return Toolkit.getDefaultToolkit().createImage(sottoimmagine.getSource());
	}

	/**
	 * Trasforma un BufferedImage in un array di byte
	 * 
	 * @param image
	 * @return byte[]
	 */
	public static byte[] getImgBytes(final BufferedImage image) {
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] arrayByte = null;
		try {
			ImageIO.write(image, "JPEG", baos);
			baos.flush();
			baos.close();
			arrayByte = baos.toByteArray();
		} catch (final IOException ex) {
			ControlloreBase.getLog().log(Level.SEVERE, ex.getMessage(), ex);
		}

		return arrayByte;
	}

	/**
	 * Trasforma un'Image in un BufferedImage
	 * 
	 * @param image
	 * @return BufferedImage
	 */
	public static BufferedImage getBufferedImage(final Image image) {
		final int width = image.getWidth(null);
		final int height = image.getHeight(null);
		final BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		final Graphics g = bi.getGraphics();
		g.drawImage(image, 0, 0, null);
		return bi;
	}

	public static ImageIcon resizeImage(final int nuovaLarghezza, final int nuovaAltezza, final ImageIcon imageicon) {
		final Image newimg = imageicon.getImage().getScaledInstance(nuovaLarghezza, nuovaAltezza, Image.SCALE_SMOOTH);
		return new ImageIcon(newimg);
	}

	/**
	 * Invece di creare una semplice Image per
	 * il double buffering creiamo una VolatileImage che sfrutta maggiormente la
	 * scheda grafica e garantisce performance maggiori.
	 * 
	 * @param width
	 * @param height
	 * @param transparency
	 * @return
	 */
	public static VolatileImage createVolatileImage(final int width, final int height, final int transparency) {
		//prende la configurazione grafica dell'ambiente
		final GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		final GraphicsConfiguration gc = ge.getDefaultScreenDevice().getDefaultConfiguration();

		//creazione
		VolatileImage image = gc.createCompatibleVolatileImage(width, height);

		final int valid = image.validate(gc);

		//verifica sul corretto contenuto e sulla validità dell'immagine (VolatileImage è più performante ma può perdere il suo contenuto) 
		if (valid == VolatileImage.IMAGE_INCOMPATIBLE) {
			image = createVolatileImage(width, height, transparency);
			return image;
		}

		return image;
	}

}
