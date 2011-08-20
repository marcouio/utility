package command;

import java.util.ArrayList;
import java.util.List;

/**
 * Gestore dei comandi che permette di centralizzare in un unico punto la
 * chiamata a tutte le operazioni. Questo permette di tenere una traccia
 * ordinata di tutte i comandi eseguiti.
 * 
 */
public class CommandManager {

	private ArrayList<AbstractCommand> history = new ArrayList<AbstractCommand>();
	private int indiceCorrente;
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
	public boolean undo() {
		if (history.size() > 0 && indiceCorrente > -1) {
			if (indiceCorrente > (history.size() - 1)) {
				indiceCorrente = history.size() - 1;
			}

			final AbstractCommand comando = history.get(indiceCorrente);
			if (comando.undoCommand()) {
				System.out.println("operazione undo su comando all'indice: " + indiceCorrente);
				indiceCorrente--;
				System.out.println("indice spostato a: " + indiceCorrente);
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

		if (history.size() > 0 && indiceCorrente >= -1 && indiceCorrente < history.size()) {
			indiceCorrente++;
			if (indiceCorrente < history.size()) {
				final AbstractCommand comando = history.get(indiceCorrente);
				if (comando.doCommand()) {
					System.out.println("operazione undo su comando all'indice: " + indiceCorrente);
					System.out.println("indice spostato a: " + indiceCorrente);
					return true;
				}
			} else {
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
	 */
	public boolean invocaComando(final AbstractCommand comando) {
		if (comando instanceof UndoCommand) {
			if (undo()) {
				return true;
			}
		} else if (comando instanceof RedoCommand) {
			if (redo()) {
				return true;
			}
		} else {
			if (comando.doCommand()) {
				history.add(comando);
				indiceCorrente = history.size() - 1;
				return true;
			}
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
		if (history.size() > 0) {
			for (int i = history.size() - 1; i <= 0; i--) {
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

	// TODO da spostare...

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
