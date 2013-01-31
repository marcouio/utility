package disegno.oggetti.poligoni;

import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import disegno.oggetti.FormaGeometrica2D;
import disegno.oggetti.painter.IPainter;

public abstract class Poligono extends FormaGeometrica2D implements IPoligono {

	private ArrayList<Lato> listaLati = new ArrayList<Lato>();
	private ArrayList<Lato> latiVicinoMouse;

	public Poligono(final String nome, final IPainter painter) {
		super(nome, painter);
	}

	public Poligono() {
		super();
	}

	public ArrayList<Lato> getLatiVicinoMouse(final Point puntatore) {
		if (latiVicinoMouse == null) {
			setMouseSuiLati(puntatore);
		}
		return latiVicinoMouse;
	}

	@Override
	public ArrayList<Lato> getListaLati() {
		return listaLati;
	}

	@Override
	public ArrayList<Lato> setMouseSuiLati(final Point mouse) {
		latiVicinoMouse = new ArrayList<Lato>();
		for (final Object element : listaLati) {
			final Lato lato = (Lato) element;
			final double distanza = Line2D.ptLineDist(lato.getOrigine().getX(), lato.getOrigine().getY(), lato.getDestinazione().getX(), lato.getDestinazione().getY(),
					mouse.getX(), mouse.getY());
			if (distanza < 3) {
				latiVicinoMouse.add(lato);
			}
		}
		return latiVicinoMouse;
	}
}
