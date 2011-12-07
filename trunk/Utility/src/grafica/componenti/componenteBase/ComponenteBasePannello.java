package grafica.componenti.componenteBase;

import java.awt.Component;
import java.awt.Container;

public class ComponenteBasePannello extends ComponenteBase {

	private static final long serialVersionUID = 1L;

	@Override
	public void aggiungiAlContenitore(final Container contenitorePadre2, final Component componenteFiglio) {
		Container cont = null;
		if (contenitorePadre2 != null) {
			cont = contenitorePadre2;
		}
		super.aggiungiAlContenitore(cont, componenteFiglio);
	}

}
