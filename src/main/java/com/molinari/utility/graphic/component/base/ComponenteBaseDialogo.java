package com.molinari.utility.graphic.component.base;

import java.awt.Component;
import java.awt.Container;

public class ComponenteBaseDialogo extends ComponenteBase {

	public ComponenteBaseDialogo(IComponenteBase padre) {
		super(padre);
	}

	private static final long serialVersionUID = 1L;

	@Override
	public void aggiungiAlContenitore(final Container contenitorePadre2, final Component componenteFiglio) {
		//do nothing
	}
}
