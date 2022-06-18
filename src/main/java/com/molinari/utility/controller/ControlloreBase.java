package com.molinari.utility.controller;

import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.SwingUtilities;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.molinari.utility.commands.AbstractCommand;
import com.molinari.utility.commands.CommandManager;
import com.molinari.utility.graphic.PercentageDimension;
import com.molinari.utility.graphic.UtilComponenti;
import com.molinari.utility.graphic.component.container.FrameBase;
import com.molinari.utility.graphic.component.style.StyleBase;
import com.molinari.utility.log.LoggerOggetto;
import com.molinari.utility.messages.I18NManager;
import com.molinari.utility.servicesloader.ServiceLoaderStarter;
import com.molinari.utility.xml.CoreXMLManager;
import com.molinari.utility.xml.UtilXml;

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
public class ControlloreBase {

	private String connectionClassName = "";

	private String nomeApplicazione = "default";

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
	private Logger log;

	private static ControlloreBase singleton;
	
	private Starter starter;
	
	public static ControlloreBase getSingleton() {
		synchronized (ControlloreBase.class) {
			if (singleton == null) {
				singleton = new ControlloreBase();
			}
		} // if101
		return singleton;
	}
	
	public void myMain(final ControlloreBase controllore, final String nomeApplicazione) {
		this.nomeApplicazione = nomeApplicazione;
		SwingUtilities.invokeLater(() -> {
			try {
				setStarter(createStarterInstance(this));
				creaFileXmlConfigurazione();
				creaFileXmlStyle();
				init();
				PercentageDimension percentageDimension = getStarter() != null ? getStarter().getPercentageDimension() : null;
				FrameBase frame = UtilComponenti.initContenitoreFrameApplicazione(null, controllore, percentageDimension);
				ControlloreBase.setApplicationframe(frame);
				controllore.mainOverridato(frame);
			} catch (Exception e) {
				ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
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
	public void mainOverridato(FrameBase frame){
		getStarter().getPercentageDimension();
		getStarter().start(frame);
	}

	public Starter createStarterInstance(ControlloreBase controlloreBase) {
		try {
			ServiceLoaderStarter<Starter> slb = new ServiceLoaderStarter<>();
			Starter load = slb.load(Starter.class);
			return load;
		} catch (Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
		return null;
	}

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

	public static boolean invocaComando(final AbstractCommand comando) {
		return CommandManager.getIstance().invocaComando(comando);
	}

	public void quit() {
		if (applicationframe != null) {
			applicationframe.setVisible(false);
			applicationframe.dispose();
		}
	}

	public void setLog(final Logger log) {
		this.log = log;
	}
	
	public static Logger getLog() {
		return ControlloreBase.getSingleton().getLogObj();
	}

	public Logger getLogObj() {
		if (log == null) {
			log = LoggerOggetto.getLog(nomeApplicazione);
		}
		return log;
	}

	public void creaFileXmlStyle() throws IOException {
		StyleBase.creaFileXmlStyle();
	}

	public static void creaFileXmlConfigurazione() throws IOException {
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
			Element autoconfig = UtilXml.addElement(doc, rootElement, "auto-config");
			UtilXml.addAttribute(doc, autoconfig, "value", "true");

			UtilXml.writeXmlFile(doc, pathFile);
		}

	}
	
	public static void main(final String[] args) {
		ControlloreBase.getSingleton().myMain(ControlloreBase.getSingleton(), "myApplication");
	}

	public void init(){
		setConnectionClassName(getConnectionClassName()); 
	}

	public String getConnectionClassName(){
		return connectionClassName;
	}

	public void setConnectionClassName(String connectionClassName) {
		this.connectionClassName = connectionClassName;
	}

	public Starter getStarter() {
		return starter;
	}

	public void setStarter(Starter starter) {
		this.starter = starter;
	}

}
