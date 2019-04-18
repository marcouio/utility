package com.molinari.utility.paint.objects.poligoni;

import com.molinari.utility.paint.objects.painter.Painter2D;

public class Quadrato extends Rettangolo {

	public Quadrato(final String nome, final Painter2D<Rettangolo> painter) {
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
