package disegno.oggetti;

import java.awt.Point;

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
