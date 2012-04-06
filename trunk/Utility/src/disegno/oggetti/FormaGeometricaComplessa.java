package disegno.oggetti;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

import disegno.oggetti.painter.IPainter;

public abstract class FormaGeometricaComplessa extends FormaGeometrica implements IFormaGeometricaComplessa {

	ArrayList<IFormaGeometrica> listaOggetti = new ArrayList<IFormaGeometrica>();
	
	public FormaGeometricaComplessa(String nome, IPainter painter) {
		super(nome, painter);
	}
	
	public FormaGeometricaComplessa() {
		super();
	}	
	
	public FormaGeometricaComplessa(final IPainter painter) {
		this.painter = painter;
	}

	@Override
	public void draw(final Graphics g) {
		for (Iterator<IFormaGeometrica> iterator = listaOggetti.iterator(); iterator.hasNext();) {
			IFormaGeometrica oggettoGrafico = (IFormaGeometrica) iterator.next();
			oggettoGrafico.draw(g);
		}		
		super.draw(g);
	}

	@Override
	public void add(final IFormaGeometrica oggettoGrafico) {
		listaOggetti.add(oggettoGrafico);
	}

	@Override
	public void remove(final IFormaGeometrica oggettoGrafico) {
		listaOggetti.remove(oggettoGrafico);
	}

	@Override
	public IFormaGeometrica getChild(final int index) {
		return listaOggetti.get(index);
	}

}
