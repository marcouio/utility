package controller;

import java.awt.Graphics2D;

import javax.swing.JFrame;

import messaggi.I18NManager;

import command.CommandManager;

public abstract class ControlloreBase {

	protected JFrame applicationframe;
	protected CommandManager commandManager;
	protected static IUtente utenteLogin;
	protected static Graphics2D applicationGraphics2d;

	public JFrame getApplicationframe() {
		return applicationframe;
	}

	public void setApplicationframe(final JFrame applicationframe) {
		this.applicationframe = applicationframe;
	}

	public static Graphics2D getApplicationGraphics2d() {
		return applicationGraphics2d;
	}

	public static void setApplicationGraphics2d(final Graphics2D applicationGraphics2d) {
		ControlloreBase.applicationGraphics2d = applicationGraphics2d;
	}

	public void setCommandManager(final CommandManager commandManager) {
		this.commandManager = commandManager;
	}
	
	public String getMessaggio(final String chiave) {
		return I18NManager.getSingleton().getMessaggio(chiave);
	}

	public IUtente getUtenteLogin() {
		return utenteLogin;
	}

	public void setUtenteLogin(final IUtente utenteLogin) {
		ControlloreBase.utenteLogin = utenteLogin;
	}

	public CommandManager getCommandManager() {
		if (commandManager == null) {
			commandManager = CommandManager.getIstance();
		}
		return commandManager;
	}

	/**
	 * Controlla se esiste sul db l'utente guest, altrimenti lo crea.
	 * Se non serve, lascialo vuoto
	 */
	public abstract void setStartUtenteLogin();

	/**
	 * Verifica presenza del db. Se non necessari lasciarlo vuoto
	 */
	public abstract void verificaPresenzaDb();

}
