package Listener;

import grafica.componenti.IComponenteBase;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import aggiornatori.AggiornatoreBase;
import aggiornatori.AggiornatoreManager;
import aggiornatori.IAggiornatore;

public abstract class AscoltatoreBase implements ActionListener {

	protected AggiornatoreBase aggiornatore;

	public AscoltatoreBase(final String classe, final Object[] parametri, final IComponenteBase componente) {
		this.aggiornatore = (AggiornatoreBase) AggiornatoreManager.getSingleton().creaAggiornatore(classe);
		this.aggiornatore.setComp(componente);
		this.aggiornatore.setParametri(parametri);
	}

	public AscoltatoreBase(final IAggiornatore aggiornatore, final Object[] parametri, final IComponenteBase componente) {
		this.aggiornatore = (AggiornatoreBase) aggiornatore;
		this.aggiornatore.setComp(componente);
		this.aggiornatore.setParametri(parametri);
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				actionPerformedOverride(e);
				aggiornatore.aggiorna();
			}
		});
	}

	protected abstract void actionPerformedOverride(final ActionEvent e);

}
