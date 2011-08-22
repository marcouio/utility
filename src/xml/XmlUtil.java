package xml;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlUtil {

	/**
	 * Carica tutte le informazioni del parametro xml in un document
	 * 
	 * @param xml
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static Document createDocument(final File xml)
	    throws ParserConfigurationException, SAXException, IOException {
		final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		final DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		final Document doc = dBuilder.parse(xml);
		return doc;
	}

	/**
	 * Restituisce la lista di nodi interni ad un document creato dall'xml
	 * passato come parametro
	 * 
	 * @param path
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static NodeList getNodeList(final String path)
	    throws ParserConfigurationException, SAXException, IOException {
		final Document doc = XmlUtil.createDocument(new File(path));
		final Element root = doc.getDocumentElement();
		final NodeList listaNodi = (root.hasChildNodes()) ? root.getChildNodes() : null;
		return listaNodi;
	}

	public static NodeList getNodeList(final Document doc) {
		final Element root = doc.getDocumentElement();
		final NodeList listaNodi = (root.hasChildNodes()) ? root.getChildNodes() : null;
		return listaNodi;
	}

	/**
	 * Trasforma il "Node" in "Element" se è di tipo "Element"
	 * 
	 * @param nodoComponente
	 * @return Element
	 */
	public static Element getElement(final Node nodoComponente) {
		Element elemento = null;
		if (nodoComponente.getNodeType() == Node.ELEMENT_NODE) {
			elemento = (Element) nodoComponente;
		}
		return elemento;
	}

	public static Node getNodo(String nodo, String path)
	    throws ParserConfigurationException, SAXException, IOException {
		NodeList listaNodi = getNodeList(path);
		for (int i = 0; i < listaNodi.getLength(); i++) {
			Node nodoDaLista = listaNodi.item(i);
			if (nodoDaLista.getNodeName().equals(nodo)) {
				return nodoDaLista;
			}
		}
		return null;
	}

	public static Node getNodo(String nodo, Document doc) {
		NodeList listaNodi = getNodeList(doc);
		for (int i = 0; i < listaNodi.getLength(); i++) {
			Node nodoDaLista = listaNodi.item(i);
			if (nodoDaLista.getNodeName().equals(nodo)) {
				return nodoDaLista;
			}
		}
		return null;
	}

	// This method writes a DOM document to a file
	public static void writeXmlFile2(Document doc, String filename) {
		try {
			// save the result
			Transformer xformer = TransformerFactory.newInstance().newTransformer();
			xformer.transform(new DOMSource(doc), new StreamResult(new File(filename)));

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
