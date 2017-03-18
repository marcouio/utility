package main.java.com.molinari.utility.paint.objects.painter;

import java.awt.Color;
import java.awt.Graphics;

import main.java.com.molinari.utility.paint.objects.IFormaGeometrica2D;

public abstract class Painter2D extends PainterBase implements IPainter{

	public Painter2D(IFormaGeometrica2D oggettoGrafico) {
		super(oggettoGrafico);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		IFormaGeometrica2D oggetto2d = (IFormaGeometrica2D) oggettoGrafico;
		g.setColor(oggetto2d.getBackground());
		disegnaBackground(g);
		g.setColor(Color.BLACK);
		disegnaTracciato(g);
	}

	protected abstract void disegnaTracciato(Graphics g);

	protected abstract void disegnaBackground(Graphics g);

}
