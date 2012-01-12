package grafica.compComplessi;

import grafica.componenti.ExceptionGraphics;
import grafica.componenti.UtilComponenti;
import grafica.componenti.contenitori.PannelloBase;
import grafica.componenti.label.Label;
import grafica.componenti.style.StyleBase;
import grafica.componenti.textfield.TextFieldBase;
import grafica.componenti.textfield.testo.TextFieldTesto;

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
				JPanel pannello = UtilComponenti.initContenitoreFrame(null);
				PannelloTextLabel ptl = null;
				try {
					ptl = new PannelloTextLabel("LABEL", "TESTO", pannello);
				} catch (ExceptionGraphics e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ptl.setBackground(Color.CYAN);
			}
		});
	}

	public PannelloTextLabel(final String label, final String text, final Container contenitore) throws ExceptionGraphics {
		super(contenitore);
		this.setLayout(null);
		this.label = new Label(label, this);
		this.textField = new TextFieldTesto(text, this);
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

	@Override
	protected StyleBase settaStileOverride() {
		// TODO Auto-generated method stub
		return null;
	}

}