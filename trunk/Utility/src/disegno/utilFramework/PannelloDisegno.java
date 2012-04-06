package disegno.utilFramework;

import grafica.componenti.contenitori.PannelloBase;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import disegno.oggetti.FormaGeometrica;
import disegno.oggetti.Rettangolo;
import disegno.oggetti.Segmento;
import disegno.oggetti.punte.PuntaTriangolo;

public class PannelloDisegno extends PannelloBase {

	ArrayList<FormaGeometrica> oggetti = new ArrayList<FormaGeometrica>();
	FormaGeometrica oggettoSelezionato;

	private static final long serialVersionUID = 1L;

	public PannelloDisegno(final Container contenitore) {
		super(contenitore);
		Rettangolo ret = new Rettangolo();
		ret.setSize(100, 100);
		ret.setLocation(0, 0);
		ret.setBackground(Color.GREEN);
		oggetti.add(ret);
		Point uno = new Point(120, 120);
		Point due = new Point(240, 120);
		Segmento l = new Segmento(uno, due);
		PuntaTriangolo punta = new PuntaTriangolo(l, 20);
		punta.setBackground(Color.RED);
		oggetti.add(l);
		oggetti.add(punta);
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
