package main.java.com.molinari.utility.paint.frameworkutil;

import main.java.com.molinari.utility.graphic.component.container.PannelloBase;
import main.java.com.molinari.utility.paint.objects.FormaGeometrica;

import java.awt.Container;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedHashMap;

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
		String nome = oggetto.getNome();
		if(nome == null){
			nome = creaNome();
		}
		mapOggetti.put(nome, oggetto);
	}

	public String creaNome() {
		
		String nome = null;
		boolean nomeOk = false;
		int size = getOggetti().size();
		
		while(!nomeOk){
			
			nome = Integer.toString(++size);
			if(!mapOggetti.containsKey(nome)){
				nomeOk = true;
			}
		}
		return nome;
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
