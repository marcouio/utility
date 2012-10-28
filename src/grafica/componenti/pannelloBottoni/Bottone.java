package grafica.componenti.pannelloBottoni;

import grafica.componenti.ExceptionGraphics;
import grafica.componenti.button.ToggleBtnBase;
import grafica.componenti.contenitori.PannelloBase;

import java.awt.Container;

import javax.swing.AbstractButton;

public class Bottone extends PannelloBase {

	private static final long	serialVersionUID	= 1L;
	ToggleBtnBase				button				= null;
	PannelloBottoni panelInterno = null;

	public Bottone(final ToggleBtnBase button, final Container contenitore) throws ExceptionGraphics {
		super(contenitore);
		this.button = button;
		this.button.posizionaSottoA(null, 0, 0);
	}

	public Bottone(final Container contenitore) throws ExceptionGraphics {
		super(contenitore);
	}

	public AbstractButton getBottone() {
		return button;
	}

	public void setBottone(final ToggleBtnBase button) {
		this.button = button;
		this.button.posizionaSottoA(null, 0, 0);
	}

	public PannelloBottoni getPanelInterno() {
		return panelInterno;
	}

	@Override
	public void setVisible(final boolean aFlag) {
		super.setVisible(aFlag);
		this.button.setVisible(aFlag);
	}

	public void setPanelInterno(final PannelloBottoni panelInterno) {
		this.panelInterno = panelInterno;
	}

	@Override
	public void setSize(final int width, final int height) {
		super.setSize(width, height);
		if(panelInterno != null){
			panelInterno.setSize(width, height);
		}
		if(button != null){
			button.setSize(width, height);
		}
	}

}
