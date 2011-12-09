package grafica.componenti.alert;

import grafica.componenti.button.ButtonBase;
import grafica.componenti.contenitori.PannelloBase;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JOptionPane;

public abstract class AbstractBuilderDialogoBase implements IBuilderDialogo {

	private String titolo;
	private String message;
	private int messageType;
	private Icon icon;
	private Dimension dimensioni;
	private int[] listaBottoni = new int[] { -1, -1, -1 };
	protected DialogoBase dialogo;
	PannelloBase pannelloButtonBar;

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
		this.messageType = tipoMessaggio;
	}

	@Override
	public void setIcon(final Icon icona) {
		this.icon = icona;
	}

	@Override
	public void setDimensioni(final Dimension dimensioni) {
		this.dimensioni = dimensioni;
	}

	public void addNegativeButton() {
		listaBottoni[INDEX_BTN_NEGATIVE] = INDEX_BTN_NEGATIVE;
	}

	public void addPositiveButton() {
		listaBottoni[INDEX_BTN_POSITIVE] = INDEX_BTN_POSITIVE;
	}

	public void addCancelButton() {
		listaBottoni[INDEX_BTN_CANCEL] = INDEX_BTN_CANCEL;
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
		positive.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				dialogo.setOpzioneScelta(JOptionPane.OK_OPTION);
			}
		});
		return positive;
	}

	protected ButtonBase creaNegativeButton() {
		ButtonBase negative = new ButtonBase("No", pannelloButtonBar);
		negative.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				dialogo.setOpzioneScelta(JOptionPane.NO_OPTION);
			}
		});
		return negative;
	}

	protected ButtonBase creaCancelButton() {
		ButtonBase cancel = new ButtonBase("Cancel", pannelloButtonBar);
		cancel.setMargin(null);
		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				dialogo.setOpzioneScelta(JOptionPane.CANCEL_OPTION);
			}
		});
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
		creaDialogButtonBar(this.listaBottoni, labelIcona, labelMessaggio);

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
}
