package com.molinari.utility.graphic.component.label;

import java.awt.Container;

import javax.swing.Icon;

public class Label extends LabelBase {

	private static final long serialVersionUID = 1L;

	public Label(final Container contenitorePadre) {
		super(contenitorePadre);
	}

	public Label(final String string, final Container contenitorePadre) {
		super(string, contenitorePadre);
	}

	public Label(final Icon image, final Container contenitorePadre) {
		super(image, contenitorePadre);
	}
}
