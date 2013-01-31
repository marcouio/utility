package disegno.oggetti.poligoni;

import java.awt.Point;

import disegno.oggetti.Segmento;

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
