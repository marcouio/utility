package disegno.utilFramework;

import grafica.componenti.contenitori.PannelloBase;

import java.awt.Container;
import java.awt.Graphics;
import java.util.ArrayList;

import disegno.oggetti.FormaGeometrica;

public class PannelloDisegno extends PannelloBase {

	protected ArrayList<FormaGeometrica> oggetti = new ArrayList<FormaGeometrica>();
	FormaGeometrica oggettoSelezionato;

	private static final long serialVersionUID = 1L;

	public PannelloDisegno(final Container contenitore) {
		super(contenitore);
		MyMouseListener mouseListener = new MyMouseListener();
		this.addMouseListener(mouseListener.getMouseAdapter());
		this.addMouseMotionListener(mouseListener.getMouseMotionAdapter());
	}

	@Override
	public void paint(final Graphics g) {
		super.paint(g);
		for (int i = 0; i < oggetti.size(); i++) {
			FormaGeometrica oggetto = oggetti.get(i);
			oggetto.draw(g);
		}
	}

	public ArrayList<FormaGeometrica> getOggetti() {
		return oggetti;
	}

	public FormaGeometrica getOggettoSelezionato() {
		return oggettoSelezionato;
	}

	public void setOggettoSelezionato(FormaGeometrica oggettoSelezionato) {
		this.oggettoSelezionato = oggettoSelezionato;
	}
}
