package grafica.componenti.componenteBase;

import grafica.componenti.contenitori.ScrollPaneBase;

import java.awt.Component;
import java.awt.Container;

public class ComponenteBaseScrollPane extends ComponenteBaseConPadreContenitore{

	public ComponenteBaseScrollPane(final IComponenteBase padre) {
		super(padre);
	}

	private static final long serialVersionUID = 1L;

	@Override
	public void aggiungiAlContenitore(final Container contenitorePadre, final Component componenteFiglio) {
		if (contenitorePadre != null) {
			if(contenitorePadre instanceof ScrollPaneBase){
				ScrollPaneBase scrollpane = (ScrollPaneBase) contenitorePadre;
				scrollpane.setViewportView(componenteFiglio);
			}else{
				super.aggiungiAlContenitore(contenitorePadre, componenteFiglio);
			}
		}
	}

}
