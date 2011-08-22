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
			doc = XmlUtil.createDocument(new File(XMLCOREPATH));
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
		Node nodo = XmlUtil.getNodo("lang", doc);
		Element elemento = XmlUtil.getElement(nodo);
		if (elemento != null) {
			return elemento.getAttribute("locale");
		}
		return null;
	}

	public String getXMLStyleFilePath() {
		Node nodo = XmlUtil.getNodo("style", doc);
		NodeList listaFigli = nodo.getChildNodes();
		for (int i = 0; i < listaFigli.getLength(); i++) {
			Node nodoFiglio = listaFigli.item(i);
			if (nodoFiglio.getNodeName().equals("file")) {
				Element elemento = XmlUtil.getElement(nodoFiglio);
				if (elemento != null) {
					return elemento.getAttribute("url");
				}
			}
		}
		return null;
	}

	public String getFileMessaggiName() {
		Node nodo = XmlUtil.getNodo("messaggi", doc);
		NodeList listaFigli = nodo.getChildNodes();
		for (int i = 0; i < listaFigli.getLength(); i++) {
			Node nodoFiglio = listaFigli.item(i);
			if (nodoFiglio.getNodeName().equals("file")) {
				Element elemento = XmlUtil.getElement(nodoFiglio);
				if (elemento != null) {
					return elemento.getAttribute("nome");
				}
			}
		}
		return null;
	}
}
