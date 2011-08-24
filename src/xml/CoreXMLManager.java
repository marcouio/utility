package xml;

import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class CoreXMLManager {

	private static final String XMLCOREPATH = "./config-core.xml";
	private Document            doc;

	private CoreXMLManager() {
		try {
			doc = UtilXml.createDocument(new File(XMLCOREPATH));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static CoreXMLManager singleton;

	public static CoreXMLManager getSingleton() {
		if (singleton == null) {
			synchronized (CoreXMLManager.class) {
				if (singleton == null) {
					singleton = new CoreXMLManager();
				}
			} // if
		} // if
		return singleton;
	}

	public String getLanguage() {
		Node nodo = UtilXml.getNodo("lang", doc);
		Element elemento = UtilXml.getElement(nodo);
		if (elemento != null) {
			return elemento.getAttribute("locale");
		}
		return null;
	}

	public String getXMLStyleFilePath() {
		Node nodo = UtilXml.getNodo("style", doc);
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
		return null;
	}

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
}
