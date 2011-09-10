package grafica.componenti.label;

import grafica.componenti.StyleBase;

public class Label extends LabelBase {

	private static final long serialVersionUID = 1L;


	public Label() {
		super();
	}

	public Label(final String string) {
		super(string);
		settaStile();
	}

	@Override
	protected StyleBase settaStileOverride() {
		return null;
	}

}
