package grafica.componenti.componenteBase;

import java.awt.Component;
import java.awt.Container;

import javax.swing.JFrame;

public class ComponenteBaseFrame extends ComponenteBase {

	private static final long serialVersionUID = 1L;

	@Override
	public void aggiungiAlContenitore(final Container contenitorePadre2, final Component componenteFiglio) {
		Container cont = null;
		if (contenitorePadre2 != null) {
			JFrame frame = (JFrame) contenitorePadre2;
			cont = frame.getContentPane();
		}
		super.aggiungiAlContenitore(cont, componenteFiglio);
	}
}
