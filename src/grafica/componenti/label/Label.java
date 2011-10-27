package grafica.componenti.label;

import grafica.componenti.StyleBase;

import java.awt.Container;

public class Label extends LabelBase {

	private static final long serialVersionUID = 1L;

	public Label(final Container contenitorePadre) {
		super(contenitorePadre);
	}

	public Label(final String string, final Container contenitorePadre) {
		super(string, contenitorePadre);
		settaStile();
	}

	@Override
	protected StyleBase settaStileOverride() {
		return null;
	}

}
