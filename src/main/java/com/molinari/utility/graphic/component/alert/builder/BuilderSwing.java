package com.molinari.utility.graphic.component.alert.builder;

import java.awt.Dimension;

import javax.swing.Icon;
import javax.swing.JOptionPane;

import com.molinari.utility.graphic.component.alert.DialogoBase;

public class BuilderSwing implements IBuilderDialogo {

	public static final int TYPE_INPUT = 1;
	public static final int TYPE_INFORMATION = 2;
	public static final int TYPE_CONFIRM = 3;
	private String titolo;
	private String message;
	private int messageType;
	private Icon icon;
	protected DialogoBase dialogo;

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
		this.messageType = tipoMessaggio;

	}

	@Override
	public void setIcon(final Icon icona) {
		this.icon = icona;
	}

	@Override
	public void setDimensioni(final Dimension dimensioni) {
		//Do nothing
	}

	@Override
	public DialogoBase creaDialogo() {
		if (messageType == TYPE_INPUT) {
			JOptionPane.showInputDialog(null, message, titolo, JOptionPane.QUESTION_MESSAGE);
		} else if (messageType == TYPE_INFORMATION) {
			JOptionPane.showMessageDialog(null, message, titolo, JOptionPane.INFORMATION_MESSAGE, icon);
		} else if (messageType == TYPE_CONFIRM) {
			JOptionPane.showConfirmDialog(null, message, titolo, JOptionPane.CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, icon);
		}

		return null;
	}

	@Override
	public void addNegativeButton() {
		//do nothing
	}

	@Override
	public void addPositiveButton() {
		//do nothing
	}

	@Override
	public void addCancelButton() {
		//do nothing
	}
}