package Listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import aggiornatori.AggiornatoreManager;
import aggiornatori.IAggiornatore;

public abstract class AscoltatoreBase implements ActionListener {

	protected IAggiornatore aggiornatore;

	public AscoltatoreBase(final String classe) {
		aggiornatore = AggiornatoreManager.getSingleton().creaAggiornatore(classe);
	}

	public AscoltatoreBase(final IAggiornatore aggiornatore) {
		this.aggiornatore = aggiornatore;
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
