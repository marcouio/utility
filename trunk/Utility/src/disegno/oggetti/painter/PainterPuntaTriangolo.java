package disegno.oggetti.painter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;

import disegno.oggetti.IFormaGeometrica2D;
import disegno.oggetti.punte.PuntaTriangolo;

public class PainterPuntaTriangolo extends Painter2D{

	public PainterPuntaTriangolo(final IFormaGeometrica2D oggettoGrafico) {
		super(oggettoGrafico);
	}

	@Override
	public void paint(final Graphics g) {
		super.paint(g);
		PuntaTriangolo puntaTriangolo = (PuntaTriangolo) getOggettoGrafico();
		GeneralPath path = getPath(puntaTriangolo);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.WHITE);
		g2.fill(path);

		g2.setColor(Color.BLACK);
		g2.draw(path);
	}

	private GeneralPath getPath(PuntaTriangolo puntaTriangolo) {
		GeneralPath path = new GeneralPath();
		path.moveTo(puntaTriangolo.getLatoDestro().getDestinazione().getX(), puntaTriangolo.getLatoDestro().getDestinazione().getY());
		path.lineTo(puntaTriangolo.getLatoSinistro().getDestinazione().getX(), puntaTriangolo.getLatoSinistro().getDestinazione().getY());
		path.lineTo(puntaTriangolo.getEstremoCentrale().getX(), puntaTriangolo.getEstremoCentrale().getY());
		path.closePath();
		return path;
	}

	@Override
	protected void disegnaTracciato(Graphics g) {
		PuntaTriangolo puntaTriangolo = (PuntaTriangolo) getOggettoGrafico();
		GeneralPath path = getPath(puntaTriangolo);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.BLACK);
		g2.draw(path);
		
	}

	@Override
	protected void disegnaBackground(Graphics g) {
		PuntaTriangolo puntaTriangolo = (PuntaTriangolo) getOggettoGrafico();
		GeneralPath path = getPath(puntaTriangolo);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(puntaTriangolo.getBackground());
		g2.fill(path);		
	}
}
