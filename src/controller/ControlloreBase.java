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

/**
 * La classe è di estrema importanza nel framework. Essa funge da classe abstract per il controller, per cui tutte le operazioni di business devono passare di qui.
 * Inoltre, contiene al proprio interno l'istanza singleton di: il frame generale dell'applicazione, il gestore dei comandi, l'utente login
 * se presente, il graphics generale dell'applicazione e del Logger. Contiene inoltre dei metodi di utilità per prendere i messaggi in lingua.
 * 
 * Per utilizzarla, bisogna esternderla e implementare i metodi necessari.
 * 
 * @author marco.molinari
 *
 */
public abstract class ControlloreBase {

	private static String nomeApplicazione = "default";
	/**
	 * Frame generale che conterra tutti gli altri contenuti
	 */
	protected static FrameBase applicationframe;

	/**
	 * Gestore dei comandi. Contiene la lista dei comandi eseguiti. Per eseguire un comando, chiamare il metodo invocaComando
	 * della classe ControlloreBase
	 */
	protected CommandManager commandManager;

	/**
	 * Utente loggato. Non necessariamente implementato. 
	 */
	protected static IUtente utenteLogin;

	/**
	 * Viene creata e mantenuta l'istanza di un "Graphics2D", cosicchè nel caso servisse ce ne sia sempre una disponibile 
	 */
	protected static Graphics2D applicationGraphics2d;

	/**
	 * 
	 */
	private static Logger log;

	public void myMain(final ControlloreBase controllore, final boolean dimensiona, final String nomeApplicazione) {
		ControlloreBase.nomeApplicazione = nomeApplicazione;
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				FrameBase frame = UtilComponenti.initContenitoreFrameApplicazione(null, controllore);
				ControlloreBase.setApplicationframe(frame);
				controllore.setStartUtenteLogin();
				verificaPresenzaDb();
				controllore.mainOverridato(frame);
				if (dimensiona) {
					frame.setSize(frame.getLarghezza(), frame.getAltezza());
				}
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

	public static Logger getLog() {
		if (log == null) {
			log = LoggerOggetto.getLog(nomeApplicazione);
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
