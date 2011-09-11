package grafica.componenti.label;

import grafica.componenti.StyleBase;
import grafica.componenti.UtilComponenti;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class LabelTestoPiccolo extends Label{

	private static final long serialVersionUID = 1L;

	public LabelTestoPiccolo() {
		super();
	}

	public LabelTestoPiccolo(final String string) {
		super(string);
		settaStile();
	}

	public class StyleBaseLTP extends StyleBaseL {

	}

	@Override
	protected void settaStile() {
		super.settaStile();
	}

	public static void main(final String[] args) {
		final JPanel panel = UtilComponenti.initContenitoreFrame(null);
		final JLabel label = new JLabel("Label");
		panel.add(label);
		label.setBounds(10, 90, 200, 30);
		final Label base = new Label("Base");
		base.setBounds(10, 0, 200, 30);
		final LabelTestoPiccolo piccolo = new LabelTestoPiccolo("Piccolo");
		piccolo.setBounds(10, 40, 200, 30);
		final LabelTestoMoltoPiccolo moltoPiccolo = new LabelTestoMoltoPiccolo("Molto piccolo");
		moltoPiccolo.setBounds(10, 120, 200, 30);
		panel.add(base);
		panel.add(piccolo);
		panel.add(moltoPiccolo);
	}

	@Override
	protected StyleBase settaStileOverride() {
		return new StyleBaseLTP();
	}

}