package com.molinari.utility.controller;

import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics2D;
import java.io.File;
import java.sql.Connection;
import java.util.logging.Logger;

import javax.swing.SwingUtilities;

import com.molinari.utility.commands.AbstractCommand;
import com.molinari.utility.commands.CommandManager;
import com.molinari.utility.database.ConnectionPool;
import com.molinari.utility.graphic.UtilComponenti;
import com.molinari.utility.graphic.component.container.FrameBase;
import com.molinari.utility.graphic.component.container.PannelloBase;
import com.molinari.utility.graphic.component.style.StyleBase;
import com.molinari.utility.log.LoggerOggetto;
import com.molinari.utility.messages.I18NManager;
import com.molinari.utility.xml.CoreXMLManager;
import com.molinari.utility.xml.UtilXml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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

	public static String connectionClassName = "";

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
	protected Object utenteLogin;

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
					creaFileXmlStyle();
					init();
					FrameBase frame = UtilComponenti.initContenitoreFrameApplicazione(null, controllore);
					ControlloreBase.setApplicationframe(frame);
					controllore.mainOverridato(frame);
					if (dimensiona) {
						Container content = frame.getContentPane();
						Component[] components = content.getComponents();
						for (Component component : components) {
							if(component instanceof PannelloBase){
								PannelloBase pannello = (PannelloBase) component;
								pannello.setSize(pannello.getLarghezza(), pannello.getAltezza());
							}
						}
						int larghezza = frame.getLarghezza();
						int altezza = frame.getAltezza();
						frame.setSize(larghezza, altezza);
						getLog().info("Frame, larghezza: " + frame.getSize().getWidth());
						getLog().info("Frame, altezza: " + frame.getSize().getHeight());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public String getMessaggio(final String chiave, final String[] params) {
		return I18NManager.getSingleton().getMessaggio(chiave, params);
	}

	public String getMessaggio(final String chiave) {
		return I18NManager.getSingleton().getMessaggio(chiave);
	}

	/**
	 * La parte di codice necessaria a far partire l'applicazione va inserita qui.
	 * @param frame
	 * @throws Exception 
	 */
	public abstract void mainOverridato(FrameBase frame) throws Exception;

	public static FrameBase getApplicationframe() {
		return applicationframe;
	}

	public static Connection getConnection() throws Exception{
		return ConnectionPool.getSingleton().getConnection();
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

	public Object getUtenteLogin() {
		return utenteLogin;
	}

	public void setUtenteLogin(final Object utenteLogin) {
		this.utenteLogin = utenteLogin;
	}

	public CommandManager getCommandManager() {
		if (commandManager == null) {
			commandManager = CommandManager.getIstance();
		}
		return commandManager;
	}

	public static boolean invocaComando(final AbstractCommand comando) throws Exception {
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

	public void creaFileXmlStyle() throws Exception{
		StyleBase.creaFileXmlStyle();
	}

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
			Element style = UtilXml.addElement(doc, rootElement, "style");
			Element file =  UtilXml.addElement(doc,style,"file"); 
			UtilXml.addAttribute(doc, file, "url", "./config-style.xml");

			//lang
			Element lang = UtilXml.addElement(doc, rootElement, "lang");
			UtilXml.addAttribute(doc, lang, "locale", "it");

			//messaggi
			Element messaggi = UtilXml.addElement(doc, rootElement, "messaggi");
			Element fileM = UtilXml.addElement(doc, messaggi, "file");
			UtilXml.addAttribute(doc, fileM, "nome", "messaggi");

			//auto-config
			Element auto_config = UtilXml.addElement(doc, rootElement, "auto-config");
			UtilXml.addAttribute(doc, auto_config, "value", "true");

			UtilXml.writeXmlFile(doc, pathFile);
		}

	}

	public static void main(final String[] args) throws Exception {
		ControlloreBase.creaFileXmlConfigurazione();
	}

	public void init(){
		connectionClassName = getConnectionClassName(); 
	}

	public abstract String getConnectionClassName();

}
