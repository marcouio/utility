package main.java.com.molinari.utility.paint.objects.poligoni;

import java.awt.Point;

import main.java.com.molinari.utility.paint.objects.Segmento;

public class Lato extends Segmento {

	public Lato(final Point origine, final Point destinazione) {
		super(origine, destinazione);
	}

	public Lato() {
		super(new Point(0, 0), new Point(0, 0));
	}

	public Lato(final String nome) {
		super(new Point(0, 0), new Point(0, 0));
		setNome(nome);
	}

}
