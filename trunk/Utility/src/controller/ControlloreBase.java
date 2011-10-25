package controller;

import grafica.componenti.IFrame;

import command.CommandManager;

public abstract class ControlloreBase {

	protected IFrame frame;
	protected CommandManager commandManager;
	protected static IUtente utenteLogin;

	public IFrame getFrame() {
		return frame;
	}

	public void setFrame(IFrame frame) {
		this.frame = frame;
	}

	public void setCommandManager(CommandManager commandManager) {
		this.commandManager = commandManager;
	}

	public IUtente getUtenteLogin() {
		return utenteLogin;
	}

	public void setUtenteLogin(IUtente utenteLogin) {
		ControlloreBase.utenteLogin = utenteLogin;
	}

	public CommandManager getCommandManager() {
		if (commandManager == null) {
			commandManager = CommandManager.getIstance();
		}
		return commandManager;
	}

	/**
	 * Controlla se esiste sul db l'utente guest, altrimenti lo crea
	 */
	public abstract void setStartUtenteLogin();

	/**
	 * Verifica presenza del db
	 */
	public abstract void verificaPresenzaDb();

}
