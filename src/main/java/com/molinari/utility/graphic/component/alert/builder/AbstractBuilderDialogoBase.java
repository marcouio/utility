package com.molinari.utility.graphic.component.alert.builder;

import java.awt.Dimension;

import javax.swing.Icon;
import javax.swing.JOptionPane;

import com.molinari.utility.graphic.component.alert.DialogoBase;
import com.molinari.utility.graphic.component.button.ButtonBase;
import com.molinari.utility.graphic.component.container.PannelloBase;

public abstract class AbstractBuilderDialogoBase implements IBuilderDialogo {

	private String titolo;
	private String message;
	private Icon icon;
	private Dimension dimensioni;
	private int[] listaBottoni = new int[] { -1, -1, -1 };
	protected DialogoBase dialogo;
	protected PannelloBase pannelloButtonBar;

	private static final int INDEX_BTN_POSITIVE = 0;
	private static final int INDEX_BTN_NEGATIVE = 1;
	private static final int INDEX_BTN_CANCEL = 2;

	@Override
	public void setTitolo(final String titolo) {
		this.titolo = titolo;

	}

	@Override
	public void setMessaggio(final String messaggio) {
		this.message = messaggio;

	}

	@Override
	public void setMessageType(final int tipoMessaggio) {
	}

	@Override
	public void setIcon(final Icon icona) {
		this.icon = icona;
	}

	@Override
	public void setDimensioni(final Dimension dimensioni) {
		this.dimensioni = dimensioni;
	}

	@Override
	public void addNegativeButton() {
		getListaBottoni()[INDEX_BTN_NEGATIVE] = INDEX_BTN_NEGATIVE;
	}

	@Override
	public void addPositiveButton() {
		getListaBottoni()[INDEX_BTN_POSITIVE] = INDEX_BTN_POSITIVE;
	}

	@Override
	public void addCancelButton() {
		getListaBottoni()[INDEX_BTN_CANCEL] = INDEX_BTN_CANCEL;
	}

	public ButtonBase chiamaMetodoPerCreazioneButton(final int index) {
		if (index == INDEX_BTN_POSITIVE) {
			return creaPositiveButton();
		} else if (index == INDEX_BTN_NEGATIVE) {
			return creaNegativeButton();
		} else if (index == INDEX_BTN_CANCEL) {
			return creaCancelButton();
		}
		return null;
	}

	protected ButtonBase creaPositiveButton() {
		ButtonBase positive = new ButtonBase("Ok", pannelloButtonBar);
		positive.addActionListener(e -> dialogo.setOpzioneScelta(JOptionPane.OK_OPTION));
		return positive;
	}

	protected ButtonBase creaNegativeButton() {
		ButtonBase negative = new ButtonBase("No", pannelloButtonBar);
		negative.addActionListener(e -> dialogo.setOpzioneScelta(JOptionPane.NO_OPTION));
		return negative;
	}

	protected ButtonBase creaCancelButton() {
		ButtonBase cancel = new ButtonBase("Cancel", pannelloButtonBar);
		cancel.setMargin(null);
		cancel.addActionListener(e -> dialogo.setOpzioneScelta(JOptionPane.CANCEL_OPTION));
		return cancel;
	}

	@Override
	public DialogoBase creaDialogo() {

		if (this.titolo != null) {
			creaDialogoTitolo(this.titolo);
		}
		Object labelIcona = null;
		if (this.icon != null) {
			labelIcona = creaDialogoIcona(this.icon);
		}
		Object labelMessaggio = null;
		if (this.message != null) {
			labelMessaggio = creaDialogoMessaggio(this.message, labelIcona);
		}
		creaDialogButtonBar(this.getListaBottoni(), labelIcona, labelMessaggio);

		creaDialogoDimensioni(this.dimensioni);

		creaDialogLocation(this.dimensioni);

		return dialogo;
	}

	protected abstract Object creaDialogButtonBar(final int[] listaBottoni2, Object labelIcona, Object labelMessaggio);

	protected abstract Object creaDialogLocation(Dimension dimensioni2);

	protected abstract Object creaDialogoDimensioni(Dimension dimensioni2);

	protected abstract Object creaDialogoIcona(Icon icon2);

	protected abstract Object creaDialogoMessaggio(String message2, Object labelIcona);

	protected abstract Object creaDialogoTitolo(String titolo2);

	public int[] getListaBottoni() {
		return listaBottoni;
	}

	public void setListaBottoni(int[] listaBottoni) {
		this.listaBottoni = listaBottoni;
	}
}
