package grafica.componenti.label;

import grafica.componenti.StyleBase;
import grafica.componenti.UtilComponenti;

import java.awt.Container;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class LabelTestoMoltoPiccolo extends LabelTestoPiccolo {

	private static final long serialVersionUID = 1L;

	public LabelTestoMoltoPiccolo(final Container contenitorePadre) {
		super(contenitorePadre);
	}

	public LabelTestoMoltoPiccolo(final String string, final Container contenitorePadre) {
		super(string, contenitorePadre);
	}

	public class StyleBaseLTMP extends StyleBaseL {

	}

	public static void main(final String[] args) {
		final JPanel panel = UtilComponenti.initContenitoreFrame(null);
		final JLabel label = new JLabel("Label");
		panel.add(label);
		label.setBounds(10, 90, 200, 30);
		final Label base = new Label("Base", panel);
		base.setBounds(10, 0, 200, 30);
		final LabelTestoPiccolo piccolo = new LabelTestoPiccolo("Piccolo", panel);
		piccolo.setBounds(10, 40, 200, 30);
	}

	@Override
	protected StyleBase settaStileOverride() {
		return new StyleBaseLTMP();
	}

}
