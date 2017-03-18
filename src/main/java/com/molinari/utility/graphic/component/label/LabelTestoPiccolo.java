package main.java.com.molinari.utility.graphic.component.label;

import main.java.com.molinari.utility.graphic.UtilComponenti;
import main.java.com.molinari.utility.graphic.component.base.IComponenteBase;
import main.java.com.molinari.utility.graphic.component.style.StyleBase;

import java.awt.Container;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class LabelTestoPiccolo extends Label {

	private static final long serialVersionUID = 1L;

	public LabelTestoPiccolo(final Container contenitorePadre) {
		super(contenitorePadre);
	}

	public LabelTestoPiccolo(final String string, final Container contenitorePadre) {
		super(string, contenitorePadre);
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
		final LabelTestoMoltoPiccolo moltoPiccolo = new LabelTestoMoltoPiccolo("Molto piccolo", panel);
		moltoPiccolo.setBounds(10, 120, 200, 30);
	}

	@Override
	public void applicaStile(final StyleBase styleBase, IComponenteBase padre){
		super.applicaStile(new StyleBase("StyleBaseLTP"),padre);
	}
}
