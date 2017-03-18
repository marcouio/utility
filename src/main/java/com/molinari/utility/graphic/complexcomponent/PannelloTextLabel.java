package main.java.com.molinari.utility.graphic.complexcomponent;

import main.java.com.molinari.utility.graphic.ExceptionGraphics;
import main.java.com.molinari.utility.graphic.UtilComponenti;
import main.java.com.molinari.utility.graphic.component.container.PannelloBase;
import main.java.com.molinari.utility.graphic.component.label.Label;
import main.java.com.molinari.utility.graphic.component.textfield.TextFieldBase;
import main.java.com.molinari.utility.graphic.component.textfield.text.TextFieldTesto;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.plaf.BorderUIResource;

public class PannelloTextLabel extends PannelloBase {

	private static final long serialVersionUID = 1L;
	private final TextFieldBase textField;
	private Label label;

	public static void main(final String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				final JPanel pannello = UtilComponenti.initContenitoreFrame(null);
				PannelloTextLabel ptl = null;
				try {
					ptl = new PannelloTextLabel("LABEL", "TESTO", pannello);
					ptl.setBackground(Color.CYAN);
				} catch (final ExceptionGraphics e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	public PannelloTextLabel(final String label, final String text, final Container contenitore) throws ExceptionGraphics {
		super(contenitore);
		this.setLayout(null);
		this.label = new Label(label, this);
		textField = new TextFieldTesto(text, this);
		textField.posizionaSottoA(this.label, 0, 0);
		this.setBorder(BorderUIResource.getLoweredBevelBorderUIResource());
		this.setSize(getMaxDimensionX(), getMaxDimensionY());
		this.setPreferredSize(this.getSize());
	}

	public Label getLabel() {
		return label;
	}

	public void setLabel(final Label label) {
		this.label = label;
	}

	public TextFieldBase getTextField() {
		return textField;
	}
}
