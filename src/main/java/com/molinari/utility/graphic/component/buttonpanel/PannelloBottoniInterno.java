package main.java.com.molinari.utility.graphic.component.buttonpanel;

import main.java.com.molinari.utility.graphic.ExceptionGraphics;
import main.java.com.molinari.utility.graphic.component.base.IComponenteBase;
import main.java.com.molinari.utility.graphic.component.button.ToggleBtnBase;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;

public class PannelloBottoniInterno extends PannelloBottoni{

	private static final long	serialVersionUID	= 1L;
	private ToggleBtnBase	bottoniRet;
	private ToggleBtnBase	bottoniPrimo;
	private ToggleBtnBase	bottoniSecondo;

	public PannelloBottoniInterno(final Container contenitore, final ImageIcon iconaRet,final String nomeUno,final ImageIcon iconaUno, final String nomeDue, final ImageIcon iconaDue) throws ExceptionGraphics {
		super(contenitore);
		initGui(contenitore, iconaRet, nomeUno, iconaUno, nomeDue, iconaDue);
	}

	private void initGui(final Container contenitore, 
			final ImageIcon iconaRet, 
			final String nomeUno, 
			final ImageIcon iconaUno, 
			final String nomeDue, 
			final ImageIcon iconaDue) throws ExceptionGraphics {

		bottoniRet = new ToggleBtnBase(null,iconaRet, this, 5,0);
		bottoniRet.settaggioBottoneStandard();
		bottoniRet.posizionaSottoA(null, 0, 0);
		bottoniRet.setVisible(false);

		bottoniRet.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				ToggleBtnBase toggle = (ToggleBtnBase) e.getSource();
				Container contenitorePadre = ((IComponenteBase)toggle).getContenitorePadre();
				if(contenitorePadre instanceof PannelloBottoniInterno){
					PannelloBottoniInterno panelInterno = (PannelloBottoniInterno)contenitorePadre;
					panelInterno.setVisible(false);
					Bottone bottone = (Bottone) panelInterno.getContenitorePadre();
					bottone.setVisible(true);
					bottone.getBottone().setSelected(false);
				}

			}
		});

		bottoniPrimo = new ToggleBtnBase(nomeUno, iconaUno, this);
		bottoniPrimo.settaggioBottoneStandard();
		bottoniPrimo.posizionaADestraDi(bottoniRet, 5, 1);
		bottoniPrimo.setVisible(false);

		bottoniSecondo = new ToggleBtnBase(nomeDue, iconaDue, this);
		bottoniSecondo.settaggioBottoneStandard();
		bottoniSecondo.posizionaSottoA(bottoniPrimo, 0, 4);
		bottoniSecondo.setVisible(false);
	}

	@Override
	public void setSize(final int width, final int height) {
		super.setSize(width, height);
		if(bottoniRet != null && bottoniPrimo != null && bottoniSecondo != null){
			bottoniRet.setSize(width/9, height);
			bottoniPrimo.setSize((int) (width - bottoniRet.getSize().getWidth()), height/2-1);
			bottoniSecondo.setSize((int) (width - bottoniRet.getSize().getWidth()), height/2-1);
		}
	}

	@Override
	public void setVisible(final boolean aFlag) {
		super.setVisible(aFlag);
		Component[] components = this.getComponents();
		for (int i = 0; i < components.length; i++) {
			Component component = components[i];
			if (component instanceof ToggleBtnBase) {
				component.setVisible(true);
			} 
		}
	}

	public ToggleBtnBase getBottoniRet() {
		return bottoniRet;
	}

	public ToggleBtnBase getBottoniPrimo() {
		return bottoniPrimo;
	}

	public ToggleBtnBase getBottoniSecondo() {
		return bottoniSecondo;
	}

}
