package disegno.oggetti.painter;

import java.awt.Graphics;

import disegno.oggetti.IFormaGeometrica;

public class PainterPuntaBase extends PainterBase {

	public PainterPuntaBase(final IFormaGeometrica oggettoGrafico) {
		super(oggettoGrafico);
	}

	@Override
	public void paint(final Graphics g) {
		super.paint(g);
	}

}
