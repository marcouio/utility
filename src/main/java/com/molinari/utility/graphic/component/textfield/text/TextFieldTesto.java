package com.molinari.utility.graphic.component.textfield.text;

import java.awt.Container;

import com.molinari.utility.graphic.component.textfield.IFormatterTF;
import com.molinari.utility.graphic.component.textfield.TextFieldBase;

public class TextFieldTesto extends TextFieldBase {

	private static final long serialVersionUID = 6036094963702519181L;

	public TextFieldTesto(final String testo, final IFormatterTF formatter, final Container contenitorePadre) {
		super(testo, formatter, contenitorePadre);
		this.setFormatter(formatter);
	}

	public TextFieldTesto(final String testo, final Container contenitorePadre) {
		super(testo, contenitorePadre);
		setFormatter(new FormatterTesto());
	}

	public TextFieldTesto(final Container contenitorePadre) {
		super(contenitorePadre);
		setFormatter(new FormatterTesto());
	}
	
	@Override
	public void focusLost(FocusEvent arg0) {
		if(getTestoConvertitoInTipo() != null) {
			setText(getTestoConvertitoInTipo().toString());
		} 
	}
}
