package main.java.com.molinari.utility.graphic.component.base;

import main.java.com.molinari.utility.common.LineStackTracePrinter;
import main.java.com.molinari.utility.graphic.component.alert.DialogoBase;
import main.java.com.molinari.utility.graphic.component.container.FrameBase;
import main.java.com.molinari.utility.graphic.component.container.PannelloBase;

import java.awt.Component;
import java.awt.Container;

public class ComponenteBaseConPadreContenitore extends ComponenteBase {

	public ComponenteBaseConPadreContenitore(IComponenteBase padre) {
		super(padre);
	}

	private static final long serialVersionUID = 1L;

	@Override
	public void aggiungiAlContenitore(final Container contenitorePadre, final Component componenteFiglio) {
		Container cont = null;
		if (contenitorePadre != null && componenteFiglio != null) {
			cont = contenitorePadre;
			if(contenitorePadre instanceof FrameBase && componenteFiglio instanceof PannelloBase){
				((FrameBase) contenitorePadre).setContentPane((Container) componenteFiglio);
			}else{
				super.aggiungiAlContenitore(cont, componenteFiglio);
			}
		} else if (contenitorePadre == null && !(componenteFiglio instanceof DialogoBase || componenteFiglio instanceof FrameBase)) {
			System.err.println("Si sta cercando di inserire il componente in un contenitore nullo. Componente: " + componenteFiglio);
			LineStackTracePrinter.scriviLineeErrore();
		} else if (componenteFiglio == null) {
			System.err.println("Si sta cercando di inserire un componente nullo");
			LineStackTracePrinter.scriviLineeErrore();
		}
	}

}
