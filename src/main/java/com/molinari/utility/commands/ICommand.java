package main.java.com.molinari.utility.commands;

public interface ICommand {

	/**
	 * Metodo implementato dai singoli comandi per eseguire la specifica
	 * operazione
	 * 
	 * @return true se il comando è andato a buon fine
	 * @throws Exception 
	 */
	public boolean execute() throws Exception;

	/**
	 * Metodo implementato dai singoli comandi per eseguire l'undo della
	 * specifica operazione
	 * 
	 * @return true se il comando è andato a buon fine
	 * @throws Exception 
	 */
	public boolean unExecute() throws Exception;

	/**
	 * Metodo implementato all'interno di AbstractCommand, richiama l'unExecute
	 * del comando
	 * 
	 * @return
	 * @throws Exception 
	 */
	public boolean undoCommand() throws Exception;

	/**
	 * Metodo implementato all'interno di AbstractCommand, richiama l'execute
	 * del comando
	 * 
	 * @return
	 * @throws Exception 
	 */
	public boolean doCommand() throws Exception;

	/**
	 * Metodo implementato all'interno dei singoli comandi, scrive il log
	 * generato dall'esecuzione del comando
	 * 
	 * @param isComandoEseguito
	 */
	public void scriviLogExecute(boolean isComandoEseguito);

	/**
	 * Metodo implementato all'interno dei singoli comandi, scrive il log
	 * generato dall'esecuzione del comando
	 * 
	 * @param isComandoEseguito
	 */
	public void scriviLogUnExecute(boolean isComandoEseguito);
}
