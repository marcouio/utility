package com.molinari.utility.xml;

import java.io.File;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Level;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.molinari.utility.GenericException;
import com.molinari.utility.controller.ControlloreBase;

public class CoreXMLManager {

	public static final String XMLCOREPATH = "./config-core.xml";
	public static final String XMLSTYLEPATH = "./config-style.xml";
	private Document doc;
	private static CoreXMLManager singleton;

	private CoreXMLManager() {
		try {
			doc = UtilXml.createDocument(new File(XMLCOREPATH));
		} catch (Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
	}
	
	public Node getNode(String nodeName){
		return UtilXml.getNodo(nodeName, doc);
	}
	
	public Element getElement(Node node){
		return UtilXml.getElement(node);
	}
	
	// This method writes a DOM document to a file
		public static void writeXmlFile(Document doc) {
			try {
				final Transformer transformer = TransformerFactory.newInstance().newTransformer();
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");

				// initialize StreamResult with File object to save to file
				final StreamResult result = new StreamResult(new StringWriter());
				final DOMSource source = new DOMSource(doc);
				transformer.transform(source, result);

			} catch (final Exception e) {
				ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
			}

		}

		// This method writes a DOM document to a file
		public static void writeXmlFile2(Document doc, String filename) {
			try {
				// save the result
				final Transformer xformer = TransformerFactory.newInstance().newTransformer();
				xformer.transform(new DOMSource(doc), new StreamResult(new File(filename)));

			} catch (final Exception e) {
				ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
			}
		}

	public static CoreXMLManager getSingleton() {
		synchronized (CoreXMLManager.class) {
			if (singleton == null) {
				singleton = new CoreXMLManager();
			}
		} // if
		return singleton;
	}
	
	private <T> T getElementValueByNode(String nodeName, Function<String, T> mapFunction, T defaultValue) {
		Node nodo = UtilXml.getNodo(nodeName, doc);
		Element elemento = UtilXml.getElement(nodo);
		if (elemento != null) {
			return mapFunction.apply(elemento.getAttribute("value"));
		}
		return defaultValue;
	}

	/**
	 * @return il codice della lingua impostata come "Locale"
	 */
	public String getLanguage() {
		return getElementValueByNode("lang", e -> e, "en");
	}
	
	public Level getLogLevel() {
		return getElementValueByNode("loglevel", e -> Level.parse(e), Level.INFO);
	}

	/**
	 * @return true se è impostata la modalità di autoconfigurazione
	 */
	public String getDatabaseUrl() {
		return getElementValueByNode("databaseurl", e -> e, "../database.sqlite");
	}
	
	/**
	 * @return true se è impostata la modalità di autoconfigurazione
	 */
	public boolean isAutoConfig() {
		return getElementValueByNode("lang", e -> Boolean.parseBoolean(e), false);
	}

	/**
	 * @return path del file di style in formato stringa
	 * @throws Exception 
	 */
	public String getXMLStyleFilePath() {
		Node nodo = UtilXml.getNodo("style", doc);
		if(nodo != null){
			NodeList listaFigli = nodo.getChildNodes();
			for (int i = 0; i < listaFigli.getLength(); i++) {
				Node nodoFiglio = listaFigli.item(i);
				if ("file".equals(nodoFiglio.getNodeName())) {
					Element elemento = UtilXml.getElement(nodoFiglio);
					if (elemento != null) {
						return elemento.getAttribute("url");
					}
				}
			}
		}
		String msg = "<style><file url=\"./config-style.xml\" /></style>";
		throw new GenericException("E' necessario mantenere nel config-core.xml:" + msg);
	}

	/**
	 * @return il nome del file dei messaggi
	 */
	public List<String> getFileMessaggiName() {
		List<String> files = new ArrayList<>();
		Node nodo = UtilXml.getNodo("messaggi", doc);
		NodeList listaFigli = nodo.getChildNodes();
		for (int i = 0; i < listaFigli.getLength(); i++) {
			Node nodoFiglio = listaFigli.item(i);
			if ("file".equals(nodoFiglio.getNodeName())) {
				Element elemento = UtilXml.getElement(nodoFiglio);
				if (elemento != null) {
					files.add(elemento.getAttribute("nome"));
				}
			}
		}
		return files;
	}

	public Document getDoc() {
		return doc;
	}

	public String getDateFormat() {
		return getElementValueByNode("dateformat", d -> d, "yyyy/MM/dd");
	}
}
