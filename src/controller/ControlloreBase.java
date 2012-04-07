package controller;

import grafica.componenti.UtilComponenti;
import grafica.componenti.contenitori.FrameBase;

import java.awt.Graphics2D;
import java.io.File;
import java.util.logging.Logger;

import javax.swing.SwingUtilities;

import log.LoggerOggetto;
import messaggi.I18NManager;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import xml.CoreXMLManager;
import xml.UtilXml;

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
	 * della classe ControlloreBase. Per creare un comando bisogna estendere un AbstractCommand
	 */
	protected CommandManager commandManager;

	/**
	 * Utente loggato. Non necessariamente implementato. 
	 */
	protected static IUtente utenteLogin;

	/**
	 * Viene creata e mantenuta l'istanza di un "Graphics2D", in modo che non ci casi in cui serva e sia null 
	 */
	protected static Graphics2D applicationGraphics2d;

	/**
	 * Logger dell'applicazione
	 */
	private static Logger log;

	public void myMain(final ControlloreBase controllore, final boolean dimensiona, final String nomeApplicazione) {
		ControlloreBase.nomeApplicazione = nomeApplicazione;
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				try {
					creaFileXmlConfigurazione();
				
					FrameBase frame = UtilComponenti.initContenitoreFrameApplicazione(null, controllore);
					ControlloreBase.setApplicationframe(frame);
					controllore.setStartUtenteLogin();
					verificaPresenzaDb();
					controllore.mainOverridato(frame);
					if (dimensiona) {
						frame.setSize(frame.getLarghezza(), frame.getAltezza());
					}
				} catch (Exception e) {
					e.printStackTrace();
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
	
	public static void creaFileXmlConfigurazione() throws Exception{
		String pathFile = CoreXMLManager.XMLCOREPATH;
		File fileConf = new File(pathFile);
		Document doc = UtilXml.createDocument(fileConf);
		
		Node nodo = UtilXml.getNodo("configs", doc);
		NodeList nodeList = UtilXml.getNodeList(doc);
		if(nodo == null && nodeList == null){
			
			Element rootElement = doc.createElement("configs");
			
			doc.appendChild(rootElement);

			//Style
			Element style = doc.createElement("style");
			rootElement.appendChild(style);
			Element file = doc.createElement("file");
			style.appendChild(file);
			Attr attrUrl = doc.createAttribute("url");
			attrUrl.setValue("./config-style.xml");
			file.setAttributeNode(attrUrl);
			
			//lang
			Element lang = doc.createElement("lang");
			rootElement.appendChild(lang);
			Attr attrLocale = doc.createAttribute("locale");
			attrLocale.setValue("it");
			lang.setAttributeNode(attrLocale);
			
			//messaggi
			Element messaggi = doc.createElement("messaggi");
			rootElement.appendChild(messaggi);
			Element fileM = doc.createElement("file");
			messaggi.appendChild(fileM);
			Attr attrNome = doc.createAttribute("nome");
			attrNome.setValue("messaggi");
			fileM.setAttributeNode(attrNome);
			
			//auto-config
			Element auto_config = doc.createElement("auto-config");
			rootElement.appendChild(auto_config);
			Attr attrValue = doc.createAttribute("value");
			attrValue.setValue("true");
			auto_config.setAttributeNode(attrValue);
			
			UtilXml.writeXmlFile(doc, pathFile);
		}
		
	}
	public static void main(String[] args) throws Exception {
		ControlloreBase.creaFileXmlConfigurazione();
	}

}
