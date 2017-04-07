package com.molinari.utility.paint.objects.poligoni;

import com.molinari.utility.paint.objects.painter.IPainter;

public class Quadrato extends Rettangolo {

	public Quadrato(final String nome, final IPainter painter) {
		super(nome, painter);
	}

	public Quadrato(final String nome) {
		super(nome);
	}

	public Quadrato() {
		super();
	}

	public int getLato() {
		return getWidth();
	}

}
