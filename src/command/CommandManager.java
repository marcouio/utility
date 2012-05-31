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
	public boolean undo() {
		//per tornare indietro devono esserci almeno 1 comando eseguito 
		//e undo ripetuti non devono spostare l'indice più indietro di -1
		if (history.size() > 0 && indiceCorrente > -1 && indiceCorrente <= history.size()-1) {
			
			//richiama il comando all'indice e sposta l'indice indietro di 1
			final AbstractCommand comando = history.get(indiceCorrente);
			if (comando.undoCommand()) {
				indiceCorrente--;
				return true;
			}
		}
		return false;
	}
	
	public static int undoForTest(ArrayList<String> history,int indiceCorrente, boolean trueTest) {
		//per tornare indietro devono esserci almeno 1 comando eseguito 
		//e undo ripetuti non devono spostare l'indice più indietro di -1
		if (history.size() > 0 && indiceCorrente > -1 && indiceCorrente <= history.size()-1) {
			
			//richiama il comando all'indice e sposta l'indice indietro di 1
			final String comando = history.get(indiceCorrente);
			if (trueTest) {
				System.out.println("undo per il comando: " +comando);
				indiceCorrente--;
			}
		}
		return indiceCorrente;
	}

	/**
	 * Metodo che implementa l'azione "avanti".
	 * 
	 */
	public boolean redo() {
		//per andare avanti devono esserci almeno 1 comando eseguito
		//l'indice deve essere compreso tra -1 e (history.size-1)
		if (history.size() > 0 && indiceCorrente >= -1 && indiceCorrente < history.size() -1) {
			
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
	public static int redoForTest(ArrayList<String> history,int indiceCorrente, boolean trueTest) {
		// per andare avanti devono esserci almeno 1 comando eseguito
		// l'indice deve essere compreso tra -1 e (history.size-1)
		if (history.size() > 0 && indiceCorrente >= -1 && indiceCorrente < history.size()-1) {

			// sposto l'indice in avanti
			indiceCorrente++;

			final String comando = history.get(indiceCorrente);
			
			if (trueTest) {
				System.out.println("redo per il comando: "+comando);
			} else {
				// altrimenti torno l'indice indietro di -1
				indiceCorrente--;
			}
		}
		return indiceCorrente;
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
	
	public static void main(String[] args) {
		int indiceCorrente = 0;
		ArrayList<String> history = new ArrayList<String>();
		history.add("1° comando");
		history.add("2° comando");
		history.add("3° comando");
		history.add("4° comando");
		
		for (int i = 0; i < 6; i++) {
			boolean trueTest = true;
			indiceCorrente = CommandManager.undoForTest(history, indiceCorrente, trueTest);
		}
		
		for (int i = 0; i < 5; i++) {
			boolean trueTest = true;
			indiceCorrente = CommandManager.redoForTest(history, indiceCorrente, trueTest);
		}
		
		for (int i = 0; i < 6; i++) {
			boolean trueTest = true;
			indiceCorrente = CommandManager.undoForTest(history, indiceCorrente, trueTest);
		}
		for (int i = 0; i < 6; i++) {
			boolean trueTest = true;
			indiceCorrente = CommandManager.redoForTest(history, indiceCorrente, trueTest);
		}
		
		for (int i = 0; i < 6; i++) {
			boolean trueTest = true;
			indiceCorrente = CommandManager.undoForTest(history, indiceCorrente, trueTest);
		}
	}

}
