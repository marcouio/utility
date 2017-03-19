package com.molinari.utility.xml;

import java.io.File;
import java.util.logging.Level;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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


	public static CoreXMLManager getSingleton() {
		synchronized (CoreXMLManager.class) {
			if (singleton == null) {
				singleton = new CoreXMLManager();
			}
		} // if
		return singleton;
	}

	/**
	 * @return il codice della lingua impostata come "Locale"
	 */
	public String getLanguage() {
		Node nodo = UtilXml.getNodo("lang", doc);
		Element elemento = UtilXml.getElement(nodo);
		if (elemento != null) {
			return elemento.getAttribute("locale");
		}
		return null;
	}

	/**
	 * @return true se è impostata la modalità di autoconfigurazione
	 */
	public boolean isAutoConfig() {
		Node nodo = UtilXml.getNodo("auto-config", doc);
		Element elemento = UtilXml.getElement(nodo);
		if (elemento != null) {
			return new Boolean(elemento.getAttribute("value")).booleanValue();
		}
		return false;
	}

	/**
	 * @return path del file di style in formato stringa
	 * @throws Exception 
	 */
	public String getXMLStyleFilePath() throws Exception {
		Node nodo = UtilXml.getNodo("style", doc);
		if(nodo != null){
			NodeList listaFigli = nodo.getChildNodes();
			for (int i = 0; i < listaFigli.getLength(); i++) {
				Node nodoFiglio = listaFigli.item(i);
				if (nodoFiglio.getNodeName().equals("file")) {
					Element elemento = UtilXml.getElement(nodoFiglio);
					if (elemento != null) {
						return elemento.getAttribute("url");
					}
				}
			}
		}
		String msg = "<style><file url=\"./config-style.xml\" /></style>";
		throw new Exception("E' necessario mantenere nel config-core.xml:" + msg);
	}

	/**
	 * @return il nome del file dei messaggi
	 */
	public String getFileMessaggiName() {
		Node nodo = UtilXml.getNodo("messaggi", doc);
		NodeList listaFigli = nodo.getChildNodes();
		for (int i = 0; i < listaFigli.getLength(); i++) {
			Node nodoFiglio = listaFigli.item(i);
			if (nodoFiglio.getNodeName().equals("file")) {
				Element elemento = UtilXml.getElement(nodoFiglio);
				if (elemento != null) {
					return elemento.getAttribute("nome");
				}
			}
		}
		return null;
	}

	public Document getDoc() {
		return doc;
	}
}
