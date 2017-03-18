package main.java.com.molinari.utility.graphic.component.textfield.text;

import java.awt.Container;

import main.java.com.molinari.utility.graphic.component.textfield.IFormatterTF;
import main.java.com.molinari.utility.graphic.component.textfield.TextFieldBase;

public class TextFieldTesto extends TextFieldBase {

	private static final long serialVersionUID = 6036094963702519181L;

	public TextFieldTesto(final String testo, final IFormatterTF formatter, final Container contenitorePadre) {
		super(testo, formatter, contenitorePadre);
		this.formatter = new FormatterTesto();
	}

	public TextFieldTesto(final String testo, final Container contenitorePadre) {
		super(testo, contenitorePadre);
		formatter = new FormatterTesto();
	}

	public TextFieldTesto(final Container contenitorePadre) {
		super(contenitorePadre);
		formatter = new FormatterTesto();
	}
}
