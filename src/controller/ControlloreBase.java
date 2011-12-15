package controller;

import grafica.componenti.UtilComponenti;
import grafica.componenti.contenitori.FrameBase;

import java.awt.Graphics2D;
import java.util.logging.Logger;

import javax.swing.SwingUtilities;

import log.LoggerOggetto;
import messaggi.I18NManager;

import command.AbstractCommand;
import command.CommandManager;

public abstract class ControlloreBase {

	protected static FrameBase applicationframe;
	protected CommandManager commandManager;
	protected static IUtente utenteLogin;
	protected static Graphics2D applicationGraphics2d;
	private static Logger log;

	public void myMain(final ControlloreBase controllore) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				FrameBase frame = UtilComponenti.initContenitoreFrameApplicazione(null, controllore);
				ControlloreBase.setApplicationframe(frame);
				controllore.setStartUtenteLogin();
				verificaPresenzaDb();
				controllore.mainOverridato(frame);
			}
		});
	}

	/**
	 * La parte di codice necessaria a far partire l'applicazione va inserita qui.
	 * @param frame
	 */
	public abstract void mainOverridato(FrameBase frame);

	public static FrameBase getApplicationframe() {
		return applicationframe;
	}

	public static void setApplicationframe(final FrameBase applicationframe) {
		ControlloreBase.applicationframe = applicationframe;
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

	public static boolean invocaComando(final AbstractCommand comando) {
		return CommandManager.getIstance().invocaComando(comando);
	}

	public void quit() {
		if (applicationframe != null) {
			applicationframe.setVisible(false);
			applicationframe.dispose();
		}
		System.exit(0);
	}

	public static void setLog(final Logger log) {
		ControlloreBase.log = log;
	}

	public static Logger getLog(final String nomeLog) {
		if (log == null) {
			log = LoggerOggetto.getLog(nomeLog);
		}
		return log;
	}

	/**
	 * Controlla se esiste sul db l'utente guest, altrimenti lo crea.
	 * Se non serve, lascialo vuoto
	 */
	public abstract boolean setStartUtenteLogin();

	/**
	 * Verifica presenza del db. Se non necessario lasciarlo vuoto
	 */
	public abstract boolean verificaPresenzaDb();

}
