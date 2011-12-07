package grafica.componenti.componenteBase;

import java.awt.Component;
import java.awt.Container;

import javax.swing.JDialog;

public class ComponenteBaseDialogo extends ComponenteBase {

	private static final long serialVersionUID = 1L;

	@Override
	public void aggiungiAlContenitore(final Container contenitorePadre2, final Component componenteFiglio) {
		Container cont = null;
		if (contenitorePadre2 != null) {
			JDialog dialogo = (JDialog) contenitorePadre2;
			cont = dialogo.getContentPane();
		}
		super.aggiungiAlContenitore(cont, componenteFiglio);
	}
}
