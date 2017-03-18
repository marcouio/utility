package main.java.com.molinari.utility.paint.objects;

import java.awt.Point;
import java.awt.geom.Point2D;

import main.java.com.molinari.utility.paint.objects.painter.PainterCerchio;

public class Cerchio extends FormaGeometrica2D {

	private int raggio;
	boolean isOnCirconferenza = false;

	public Cerchio(final Point2D estremi, final int raggio) {
		this.setX((int) estremi.getX());
		this.setY((int) estremi.getY());
		this.raggio = raggio;
		setPainter(new PainterCerchio(this));
	}

	@Override
	public Point getPuntoCentrale() {
		final double x = getX();
		final double y = getY();
		final int centroY = new Double(y + (raggio / 2)).intValue();
		final int centroX = new Double(x + (raggio / 2)).intValue();
		return new Point(centroX, centroY);
	}
	
	@Override
	public void ridimensiona(Point mouse) {
		
		double coseno = Math.cos(getAngoloFromCentre(mouse));
		double seno = Math.sin(getAngoloFromCentre(mouse));
		
		//sinistra
		if(coseno > 0){
			final double newX = mouse.getX() - distanzaMouseDaXY.getX();
			final int width = (int) (getX() - newX) + getRaggio();
			if (width > 3) {
				setRaggio(width);
				setX((int) (newX));
			}
		//destra
		}else{
			final int larghezza = (int) (mouse.getX() - getX() > 3 ? mouse.getX() - getX() : 3);
			setWidth(larghezza);
		}
		//alto
		if(seno > 0){
			final double newY = mouse.getY() - distanzaMouseDaXY.getY();
			final int height = (int) (getY() - newY) + getRaggio();
			if (height > 3) {
				setRaggio(height);
				setY((int) (newY));
			}
		//basso
		}else{
			final int altezza = (int) (mouse.getY() - getY() > 3 ? mouse.getY() - getY() : 3);
			setRaggio(altezza);
		}
		

		System.out.println("x mouse: "+mouse.getX());
		System.out.println("y mouse: "+ mouse.getY());
		System.out.println("x cerchio: "+this.getX());
		System.out.println("y cerchio: "+this.getY());
		System.out.println("raggio: "+this.getRaggio());
	}

//	@Override
//	public void ridimensiona(final Point mouse) {
//
//		//TODO non funge
//		
//		final Point centro = getPuntoCentrale();
//		
//		final boolean isBassoSinistro = isMouseASinistra(mouse) && isMouseInBasso(mouse);
//		final boolean isAltoSinistro = isMouseASinistra(mouse) && isMouseInAlto(mouse);
//		final boolean isBassoDestro = isMouseADestra(mouse) && isMouseInBasso(mouse);
//		final boolean isAltoDestro = isMouseADestra(mouse) && isMouseInAlto(mouse);
//
//		final int distMouseY = (int) Math.abs(centro.getY() - mouse.getY());
//		final int distMouseX = (int) Math.abs(centro.getX() - mouse.getX());
//		
//		double coseno = Math.cos(getAngoloFromCentre(mouse));
//		double seno = Math.sin(getAngoloFromCentre(mouse));
//		
//		if (isBassoSinistro) {
//			final double newX = mouse.getX() - distanzaMouseDaXY.getX();
//			setX((int) newX);
//			setRaggio((int) ((centro.getX()-newX) *2));
//			
//		} else if (isAltoSinistro) {
//			final double newX = mouse.getX() - distanzaMouseDaXY.getX();
//			final double newY = mouse.getY() - distanzaMouseDaXY.getY();
//			setRaggio((int) ((centro.getX()-newX) *2));
//			setX((int) newX);
//			setY((int) newY);
//			
//		} else if (isBassoDestro) {
//			double diametro = Math.abs(distMouseX / seno); 
//			setRaggio((int) (diametro*2));
//		} else if (isAltoDestro) {
//			final double newY = mouse.getY() - distanzaMouseDaXY.getY();
//			setY((int) newY);
//			double diametro = Math.abs(distMouseX / coseno); 
//			setRaggio((int) (diametro*2));
//		}
//
//	}
	
	/**
	 * 
	 * @param mouse
	 * @return l'angolo del segmento formato dai due punti: centro e puntatore
	 */
	public double getAngoloFromCentre(final Point mouse){
		final Point centro = getPuntoCentrale();
		
		final double angolo = Math.atan2(centro.getY() - mouse.getY(), centro.getX() - mouse.getX());
		return angolo;
	}
	
	/**
	 * La y diminuisce all'aumentare della x. 
	 * Viene usato il concetto di seno per calcolarla: si moltiplica
	 * il diametro per il seno dell'angolo che va da 1 a -1, raggiungendo i massimi
	 * positivi e negativi in corrispondenza della y == 0
	 * @return
	 */
	public double getYFromCentroToCirconferenza(final Point mouse){
		
		// questo è l'angolo del segmento formato dai due punti: centro e puntatore
		final double angolo = getAngoloFromCentre(mouse);
		
		final int diametro = raggio / 2;
		return Math.abs(diametro * Math.sin(angolo));
	}
	
	public double getXFromCentroToCirconferenza(final Point mouse){
		
		final int diametro = raggio / 2;

		// questo è l'angolo del segmento formato dai due punti: centro e puntatore
		final double angolo = getAngoloFromCentre(mouse);
		return Math.abs(diametro * Math.cos(angolo));
	}
	

	@Override
	public boolean isInRegion(final Point mouse) {

		final Point centro = getPuntoCentrale();
		final int distMouseY = (int) Math.abs(centro.getY() - mouse.getY());
		final int distMouseX = (int) Math.abs(centro.getX() - mouse.getX());

		// distanza coordinata X dal centro alla circonferenza considerando
		// l'angolo generato dall'incrocio fra puntatore mouse e centro
		final double distCirconfX = getXFromCentroToCirconferenza(mouse);

		// distanza coordinata Y dal centro alla circonferenza considerando
		// l'angolo generato dall'incrocio fra puntatore mouse e centro
		final double distCirconfY = getYFromCentroToCirconferenza(mouse); 

		if (distMouseX > distCirconfX || distMouseY > distCirconfY) {
			return false;
		}

		return true;
	}

	public int getRaggio() {
		return raggio;
	}

	public void setRaggio(final int raggio) {
		this.raggio = raggio;
	}

	public boolean setIsOnCirconferenza(Point mouse) {
		
		isOnCirconferenza = false;
	
		// TODO non va modificare
		final Point centro = getPuntoCentrale();
		double yToCirconferenza = getYFromCentroToCirconferenza(mouse);
		double xToCirconferenza = getXFromCentroToCirconferenza(mouse);

		final int distMouseY = (int) Math.abs(centro.getY() - mouse.getY());
		final int distMouseX = (int) Math.abs(centro.getX() - mouse.getX());
		
		double differenzaY = Math.abs(yToCirconferenza - distMouseY);
		double differenzaX = Math.abs(xToCirconferenza - distMouseX);
		boolean ridimensiona = differenzaX < 3 && differenzaY < 3;
		isOnCirconferenza = ridimensiona;
		return ridimensiona;
	}

	public boolean getIsOnCirconferenza() {
		return isOnCirconferenza;
	}

}
