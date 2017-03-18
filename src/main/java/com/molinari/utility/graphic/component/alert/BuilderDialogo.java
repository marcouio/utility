package com.molinari.utility.graphic.component.alert;

import com.molinari.utility.graphic.component.alert.builder.AbstractBuilderDialogoBase;
import com.molinari.utility.graphic.component.button.ButtonBase;
import com.molinari.utility.graphic.component.container.PannelloBase;
import com.molinari.utility.graphic.component.label.Label;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;

public class BuilderDialogo extends AbstractBuilderDialogoBase {

	public BuilderDialogo(final Container padre) {
		if (padre instanceof JFrame) {
			dialogo = new DialogoBase((JFrame) padre);
		} else if (padre instanceof Dialog) {
			dialogo = new DialogoBase((Dialog) padre);
		}
		pannelloButtonBar = new PannelloBase(dialogo);
		dialogo.padre = padre;
	}

	@Override
	public DialogoBase creaDialogo() {
		super.creaDialogo();

		dialogo.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialogo.setVisible(true);

		return dialogo;
	}

	@Override
	protected Object creaDialogButtonBar(final int[] listaBottoni2, final Object labelIcona, final Object labelMessaggio) {

		if (labelIcona != null) {
			pannelloButtonBar.posizionaSottoA((Label) labelIcona, 0, 0);
		} else if (labelMessaggio != null) {
			pannelloButtonBar.posizionaSottoA((Label) labelMessaggio, 0, 0);
		}
		ButtonBase buttonOld = null;
		for (int buttonPresente : listaBottoni2) {
			if (buttonPresente != -1) {
				ButtonBase button = chiamaMetodoPerCreazioneButton(buttonPresente);
				if (buttonOld != null) {
					button.posizionaADestraDi(buttonOld, 10, 0);
				}
				buttonOld = button;
			}
		}
		pannelloButtonBar.setSize(pannelloButtonBar.getLarghezza(), pannelloButtonBar.getAltezza());
		return null;
	}

	private void accentra(final PannelloBase pannelloButtonBar, final DialogoBase dialogo) {
		int x = ((dialogo.getX() + dialogo.getWidth()) / 2) - (pannelloButtonBar.getWidth() / 2);
		pannelloButtonBar.setLocation(x, pannelloButtonBar.getY());

	}

	@Override
	protected Object creaDialogLocation(final Dimension dimensioni2) {
		int y = ((dialogo.padre.getY() + dialogo.padre.getHeight()) / 2) - (dialogo.getHeight() / 2);
		int x = ((dialogo.padre.getX() + dialogo.padre.getWidth()) / 2) - (dialogo.getWidth() / 2);
		Point puntoLocation = new Point(x, y);
		dialogo.setLocation(puntoLocation);
		return puntoLocation;
	}

	@Override
	protected Object creaDialogoDimensioni(final Dimension dimensioni2) {
		if (dimensioni2 != null) {
			dialogo.setSize(dimensioni2);
		} else {
			dialogo.setSize(dialogo.getLarghezza(), dialogo.getAltezza());
		}
		accentra(pannelloButtonBar, dialogo);
		return dimensioni2;
	}

	@Override
	protected Object creaDialogoIcona(final Icon icon2) {
		return new Label(icon2, dialogo);
	}

	@Override
	protected Object creaDialogoMessaggio(final String message2, final Object iconaLabel) {
		Label labelMessaggio = new Label(message2, dialogo);
		if (iconaLabel != null) {
			labelMessaggio.posizionaSottoA((Component) iconaLabel, 10, 0);
		}
		return labelMessaggio;
	}

	@Override
	protected Object creaDialogoTitolo(final String titolo2) {
		dialogo.setTitle(titolo2);
		return titolo2;
	}
}