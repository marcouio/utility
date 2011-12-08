package grafica.componenti.alert;

import java.awt.Dimension;

import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class AlertSwing {

	public class BuilderSwing implements IBuilderDialogo {

		public static final int TYPE_INPUT = 1;
		public static final int TYPE_INFORMATION = 2;
		public static final int TYPE_CONFIRM = 3;
		private String titolo;
		private String message;
		private int messageType;
		private Icon icon;
		private Dimension dimensioni;
		private int[] listaBottoni = new int[] { -1, -1, -1 };
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
			this.dimensioni = dimensioni;

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
	}

	public static void main(final String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				BuilderSwing builder = new AlertSwing().new BuilderSwing();
				builder.setTitolo("Ciao a tutti!");
				builder.setMessaggio("QUesto è il messaggio");
				//				builder.setMessageType(BuilderSwing.TYPE_CONFIRM);
				//				builder.setMessageType(BuilderSwing.TYPE_INFORMATION);
				builder.setMessageType(BuilderSwing.TYPE_INPUT);
				builder.creaDialogo();
			}
		});
	}
}
