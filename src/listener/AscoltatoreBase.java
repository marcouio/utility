package listener;

import grafica.componenti.componenteBase.IComponenteBase;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import aggiornatori.AggiornatoreBase;
import aggiornatori.AggiornatoreManager;
import aggiornatori.IAggiornatore;

public abstract class AscoltatoreBase implements ActionListener {

	protected AggiornatoreBase aggiornatore;

	public AscoltatoreBase(final String classe, final Object[] parametri, IComponenteBase componenteBase) {
		this.aggiornatore = (AggiornatoreBase) AggiornatoreManager.getSingleton().creaAggiornatoreByClasse(classe);
		
		if(parametri != null && componenteBase != null){
			
			this.aggiornatore.setComp(componenteBase);
			this.aggiornatore.setParametri(parametri);
		}
	}

	public AscoltatoreBase(final IAggiornatore aggiornatore, final Object[] parametri, IComponenteBase componenteBase) {
		this.aggiornatore = (AggiornatoreBase) aggiornatore;
		
		if(parametri != null && componenteBase != null){
			this.aggiornatore.setComp(componenteBase);
			this.aggiornatore.setParametri(parametri);
		}
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				actionPerformedOverride(e);
				if(aggiornatore != null){
					aggiornatore.aggiorna();
				}
			}
		});
	}

	protected abstract void actionPerformedOverride(final ActionEvent e);

}
