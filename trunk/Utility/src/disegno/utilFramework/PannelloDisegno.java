package disegno.utilFramework;

import grafica.componenti.contenitori.PannelloBase;

import java.awt.Container;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import disegno.oggetti.FormaGeometrica;

public class PannelloDisegno extends PannelloBase {

	private LinkedHashMap<String, FormaGeometrica> mapOggetti = new LinkedHashMap<String, FormaGeometrica>();
	FormaGeometrica oggettoSelezionato;

	private static final long serialVersionUID = 1L;

	public PannelloDisegno(final Container contenitore) {
		super(contenitore);
		final MyMouseListener mouseListener = new MyMouseListener();
		this.addMouseListener(mouseListener.getMouseAdapter());
		this.addMouseMotionListener(mouseListener.getMouseMotionAdapter());
	}

	@Override
	public void paint(final Graphics g) {
		super.paint(g);
		for (int i = 0; i < getOggetti().size(); i++) {
			final FormaGeometrica oggetto = getOggetti().get(i);
			oggetto.draw(g);
		}
	}

	public void add(final FormaGeometrica oggetto) {
		mapOggetti.put(oggetto.getNome(), oggetto);
	}

	public ArrayList<FormaGeometrica> getOggetti() {
		return new ArrayList<FormaGeometrica>(mapOggetti.values());
	}

	public FormaGeometrica getOggettoSelezionato() {
		return oggettoSelezionato;
	}

	public void setOggettoSelezionato(final FormaGeometrica oggettoSelezionato) {
		this.oggettoSelezionato = oggettoSelezionato;
	}
}
