package com.molinari.utility.paint.frameworkutil;

import java.awt.Container;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.molinari.utility.graphic.component.container.PannelloBase;
import com.molinari.utility.paint.objects.FormaGeometrica;

public class PannelloDisegno extends PannelloBase {

	private final LinkedHashMap<String, FormaGeometrica> mapOggetti = new LinkedHashMap<>();
	private transient FormaGeometrica oggettoSelezionato;

	private static final long serialVersionUID = 1L;

	public PannelloDisegno(final Container contenitore) {
		super(contenitore);
		final MyMouseListener mouseListener = new MyMouseListener(this);
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
	
	public List<FormaGeometrica> getOggetti() {
		return new ArrayList<>(mapOggetti.values());
	}

	public FormaGeometrica getOggettoSelezionato() {
		return oggettoSelezionato;
	}

	public void setOggettoSelezionato(final FormaGeometrica oggettoSelezionato) {
		this.oggettoSelezionato = oggettoSelezionato;
	}
}
