package grafica.componenti.componenteBase;

import grafica.componenti.contenitori.ScrollPaneBase;

import java.awt.Component;
import java.awt.Container;

public class ComponenteBaseScrollPane extends ComponenteBase {

	public ComponenteBaseScrollPane(IComponenteBase padre) {
		super(padre);
	}

	private static final long serialVersionUID = 1L;

	@Override
	public void aggiungiAlContenitore(final Container contenitorePadre2, final Component componenteFiglio) {
		if (contenitorePadre2 != null) {
			ScrollPaneBase scrollpane = (ScrollPaneBase) contenitorePadre2;
			scrollpane.setViewportView(componenteFiglio);
		}
	}

}
