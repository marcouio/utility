package disegno.oggetti.painter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;

import disegno.oggetti.Rettangolo;

public class PainterRettangolo extends PainterBase {

	public PainterRettangolo(Rettangolo oggettoGrafico) {
		super(oggettoGrafico);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Rettangolo rettangolo = (Rettangolo) oggettoGrafico;
		GeneralPath path = new GeneralPath();
		path.moveTo(rettangolo.getLatoAlto().getOrigine().getX(), rettangolo.getLatoAlto().getOrigine().getY());
		path.lineTo(rettangolo.getLatoAlto().getDestinazione().getX(), rettangolo.getLatoAlto().getDestinazione().getY());
		path.lineTo(rettangolo.getLatoBasso().getDestinazione().getX(), rettangolo.getLatoBasso().getDestinazione().getY());
		path.moveTo(rettangolo.getLatoBasso().getOrigine().getX(), rettangolo.getLatoBasso().getOrigine().getY());
		path.closePath();
		((Graphics2D)g).draw(path);
		((Graphics2D)g).setColor(Color.WHITE);
		((Graphics2D)g).fill(path);
	}

}
