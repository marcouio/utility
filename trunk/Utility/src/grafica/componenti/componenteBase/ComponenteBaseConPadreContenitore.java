package grafica.componenti.componenteBase;

import grafica.componenti.alert.DialogoBase;
import grafica.componenti.contenitori.FrameBase;

import java.awt.Component;
import java.awt.Container;

import comuni.LineStackTracePrinter;

public class ComponenteBaseConPadreContenitore extends ComponenteBase {

	private static final long serialVersionUID = 1L;

	@Override
	public void aggiungiAlContenitore(final Container contenitorePadre, final Component componenteFiglio) {
		Container cont = null;
		if (contenitorePadre != null && componenteFiglio != null) {
			cont = contenitorePadre;
			super.aggiungiAlContenitore(cont, componenteFiglio);
		} else if (contenitorePadre == null && !(componenteFiglio instanceof DialogoBase || componenteFiglio instanceof FrameBase)) {
			System.err.println("Si sta cercando di inserire il componente in un contenitore nullo. Componente: " + componenteFiglio);
			LineStackTracePrinter.scriviLineeErrore();
		} else if (componenteFiglio == null) {
			System.err.println("Si sta cercando di inserire un componente nullo");
			LineStackTracePrinter.scriviLineeErrore();
		}
	}

}
