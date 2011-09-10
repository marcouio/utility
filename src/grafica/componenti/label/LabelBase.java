package grafica.componenti.label;

import grafica.componenti.StyleBase;

import javax.swing.JLabel;

public class LabelBase extends JLabel {

	private static final long serialVersionUID = 1L;
	protected StyleBase       style            = new StyleBaseL();

	public LabelBase() {
		super();
	}

	public LabelBase(final String string) {
		super(string);
	}

	protected void init() {
		this.settaStile();
	}

	protected void settaStile() {
		style.setPadre(this);
		this.setFont(style.getFont());
		this.setForeground(style.getForeground());
		this.setBackground(style.getBackground());
	}

	public class StyleBaseL extends StyleBase {

	}

}
