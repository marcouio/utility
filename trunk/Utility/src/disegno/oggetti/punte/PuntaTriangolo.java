package disegno.oggetti.punte;

import java.awt.Color;

import disegno.oggetti.Segmento;
import disegno.oggetti.painter.PainterPuntaTriangolo;

public class PuntaTriangolo extends PuntaBase {

	private Segmento latoBasso;
	private Color background = Color.WHITE;

	public PuntaTriangolo(final Segmento segmento, final int lunghezzaLati) {
		super(segmento, lunghezzaLati);
		aggiungiParteSpecificaPunta();
		setPainter(new PainterPuntaTriangolo(this));
	}

	public PuntaTriangolo(final Segmento segmento) {
		super(segmento);
		aggiungiParteSpecificaPunta();
	}

	private void aggiungiParteSpecificaPunta() {
		latoBasso = new Segmento(getLatoSinistro().getDestinazione(), getLatoDestro().getDestinazione());
//		add(latoBasso);
	}

	public Color getBackground() {
		return background;
	}

	public void setBackground(Color background) {
		this.background = background;
	}
	
}
