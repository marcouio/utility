package grafica.componenti.label;

import grafica.componenti.StyleBase;

import javax.swing.JLabel;

public abstract class LabelBase extends JLabel {

	private static final long serialVersionUID = 1L;
	protected StyleBase       style            = new StyleBaseL();

	public LabelBase() {
		super();
		init();
	}

	public LabelBase(final String string) {
		super(string);
		init();
	}

	protected void init() {
		this.settaStile();
	}

	protected void settaStile() {
		style=settaStileOverride()!=null?settaStileOverride():style;
		style.setPadre(this);
		this.setFont(style.getFont());
		this.setForeground(style.getForeground());
		this.setBackground(style.getBackground());
	}

	protected abstract StyleBase settaStileOverride();

	public class StyleBaseL extends StyleBase {

	}

}
