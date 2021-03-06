package com.molinari.utility.commands;

import java.util.ArrayList;
import java.util.List;

/**
 * Gestore dei comandi che permette di centralizzare in un unico punto la
 * chiamata a tutte le operazioni. Questo permette di tenere una traccia
 * ordinata di tutte i comandi eseguiti.
 * 
 */
public class CommandManager {

	private ArrayList<AbstractCommand> history = new ArrayList<>();
	private int indiceCorrente = -1;
	private static CommandManager instance = new CommandManager();

	private CommandManager() {

	}

	public static CommandManager getIstance() {
		return instance;
	}

	/**
	 * Metodo che implementa l'azione "indietro".
	 * 
	 */
	public boolean undo(){
		//per tornare indietro devono esserci almeno 1 comando eseguito 
		//e undo ripetuti non devono spostare l'indice più indietro di -1
		if (!history.isEmpty() && indiceCorrente > -1 && indiceCorrente <= history.size()-1) {
			
			//richiama il comando all'indice e sposta l'indice indietro di 1
			final AbstractCommand comando = history.get(indiceCorrente);
			if (comando.undoCommand()) {
				indiceCorrente--;
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Metodo che implementa l'azione "avanti".
	 * 
	 */
	public boolean redo() {
		//per andare avanti devono esserci almeno 1 comando eseguito
		//l'indice deve essere compreso tra -1 e (history.size-1)
		if (!history.isEmpty() && indiceCorrente >= -1 && indiceCorrente < history.size() -1) {
			
			//sposto l'indice in avanti
			indiceCorrente++;
			
			final AbstractCommand comando = history.get(indiceCorrente);
			if (comando.doCommand()) {
				return true;
			}else {
				//altrimenti torno l'indice indietro di -1 
				indiceCorrente--;
			}

		}
		return false;
	}

	/**
	 * Tutti i comandi devono essere richiamati all'interno di questo metodo in
	 * quanto gestisce gli smistamenti anche per l'undo/redo. Con il tipo si
	 * decide di aggiornare i pannelli riguardanti l'entrata o l'uscita.
	 * altrimenti aggiorna tutto
	 * 
	 * @param comando
	 * @param tipo
	 * @return
	 * @throws Exception 
	 */
	public boolean invocaComando(final AbstractCommand comando) {
		try {
			if (comando instanceof UndoCommand) {
				return undo();
			} else if (comando instanceof RedoCommand) {
				return redo();
			} else {
				if (comando.doCommand()) {
					history.add(comando);
					indiceCorrente = history.size() - 1;
					return true;
				}
			}
		} catch (Exception e) {
			throw new CommandException(e);
		}
		return false;
	}

	/**
	 * Restituisce l'ultimo comando eseguito del tipo passato come parametro
	 * 
	 * @param nomeClasse
	 * @return
	 */
	public AbstractCommand getLast(final Class<AbstractCommand> nomeClasse) {
		AbstractCommand ultimoCommand = null;
		if (!history.isEmpty()) {
			for (int i = history.size() - 1; i >= 0; i--) {
				if (history.get(i).getClass().equals(nomeClasse)) {
					ultimoCommand = history.get(i);
					break;
				}
			}
		}
		return ultimoCommand;
	}

	public List<AbstractCommand> getHistory() {
		return history;
	}

	/**
	 * Genera la matrice di dati da inserire nel pannello history
	 * 
	 * @return
	 */
	public Object[][] generaDati() {
		final int numeroColonne = 1;
		final ArrayList<AbstractCommand> listaComandi = (ArrayList<AbstractCommand>) getHistory();
		final Object[][] dati = new Object[listaComandi.size()][numeroColonne];
		for (int i = 0; i < listaComandi.size(); i++) {
			dati[i][0] = listaComandi.get(i);

		}
		return dati;
	}

}
