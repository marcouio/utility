package com.molinari.utility.graphic.complexcomponent;

import java.awt.Color;
import java.awt.Container;
import java.util.logging.Level;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.plaf.BorderUIResource;

import com.molinari.utility.controller.ControlloreBase;
import com.molinari.utility.graphic.ExceptionGraphics;
import com.molinari.utility.graphic.UtilComponenti;
import com.molinari.utility.graphic.component.container.PannelloBase;
import com.molinari.utility.graphic.component.label.Label;
import com.molinari.utility.graphic.component.textfield.TextFieldBase;
import com.molinari.utility.graphic.component.textfield.text.TextFieldTesto;

public class PannelloTextLabel extends PannelloBase {

	private static final long serialVersionUID = 1L;
	private final TextFieldBase textField;
	private Label label;

	public static void main(final String[] args) {
		SwingUtilities.invokeLater(() -> {
			final JPanel pannello = UtilComponenti.initContenitoreFrame(null);
			PannelloTextLabel ptl = null;
			try {
				ptl = new PannelloTextLabel("LABEL", "TESTO", pannello);
				ptl.setBackground(Color.CYAN);
			} catch (final ExceptionGraphics e) {
				ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
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
