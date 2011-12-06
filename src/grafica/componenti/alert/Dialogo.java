package grafica.componenti.alert;

import grafica.componenti.UtilComponenti;
import grafica.componenti.label.Label;

import java.awt.Container;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Dialogo extends Dialog {

	private Dialogo(final JFrame frame) {
		super(frame);
	}

	private Dialogo(final Dialog owner) {
		super(owner);
	}

	private static final long serialVersionUID = 1L;
	private String titolo;
	private String message;
	private int messageType;
	private Icon icon;
	private Container padre;

	public class Builder {

		public Builder(final Container padre) {
			if (padre instanceof JFrame) {
				dialogo = new Dialogo((JFrame) padre);
			} else if (padre instanceof Dialog) {
				dialogo = new Dialogo((Dialog) padre);
			}
			dialogo.padre = padre;
		}

		private Dialogo dialogo;

		public void setTitolo(final String titolo) {
			Dialogo.this.titolo = titolo;
		}

		public void setMessaggio(final String messaggio) {
			Dialogo.this.message = messaggio;
		}

		public void setMessageType(final int tipoMessaggio) {
			Dialogo.this.messageType = tipoMessaggio;
		}

		public void setIcon(final Icon icona) {
			Dialogo.this.icon = icona;
		}

		public Dialogo creaDialogo() {

			int altezza = ((padre.getY() + padre.getHeight()) / 2) - (dialogo.getHeight() / 2);
			int larghezza = ((padre.getX() + padre.getWidth()) / 2) - (dialogo.getWidth() / 2);
			dialogo.setLocation(larghezza, altezza);

			if (dialogo.titolo != null) {
				dialogo.setTitle(titolo);
			}

			if (dialogo.message != null) {
				Label labelMessaggio = new Label(dialogo.message, dialogo.padre);

			}

			if (dialogo.icon != null) {
				Label labelIcona = new Label(dialogo.icon, dialogo.padre);

			}

			return dialogo;
		}
	}

	public static void main(final String[] args) {
		final JPanel panello = UtilComponenti.initContenitoreFrame(null);
		JButton bottone = new JButton("premi");
		bottone.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				//				Dialogo.Builder builder = Dialogo.Builder(panello);
			}
		});

	}

}