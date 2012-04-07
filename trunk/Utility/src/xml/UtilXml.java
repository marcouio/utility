package xml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

public class UtilXml {

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
	    throws ParserConfigurationException, SAXException {
		
		final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		final DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc;
		try {
			doc = dBuilder.parse(xml);
		} catch (IOException e) {
			doc = dBuilder.newDocument();
		}
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
		final Document doc = UtilXml.createDocument(new File(path));
		final Element root = doc.getDocumentElement();
		final NodeList listaNodi = (root.hasChildNodes()) ? root.getChildNodes() : null;
		return listaNodi;
	}

	public static NodeList getNodeList(final Document doc) {
		if(doc != null){
			final Element root = doc.getDocumentElement();
			if(root!=null){
				final NodeList listaNodi = (root.hasChildNodes()) ? root.getChildNodes() : null;
				return listaNodi;
			}
		}
		return null;
	}

	/**
	 * Trasforma il "Node" in "Element" se è di tipo "Element"
	 * 
	 * @param nodoComponente
	 * @return Element
	 */
	public static Element getElement(final Node nodoComponente) {
		Element elemento = null;
		if(nodoComponente != null){
			if (nodoComponente.getNodeType() == Node.ELEMENT_NODE) {
				elemento = (Element) nodoComponente;
			}
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
	
	public static Element addElement(final Document doc, 
									 final Element elementoPadre,
									 final String nomeElemento) {
		Element style = doc.createElement(nomeElemento);
		elementoPadre.appendChild(style);
		return style;
	}

	public static Node getNodo(String nodo, Document doc) {
		NodeList listaNodi = getNodeList(doc);
		if(listaNodi!=null){
			for (int i = 0; i < listaNodi.getLength(); i++) {
				Node nodoDaLista = listaNodi.item(i);
				if (nodoDaLista.getNodeName().equals(nodo)) {
					return nodoDaLista;
				}
			}
		}
		return null;
	}
	
	// This method writes a DOM document to a file
		public static StreamResult writeXmlFile2(Document doc, StreamResult stream) {
			try {
				// save the result
				Transformer xformer = TransformerFactory.newInstance().newTransformer();
				
				StreamResult outputTarget = stream;
				xformer.transform(new DOMSource(doc), outputTarget);
				return outputTarget;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

	public static void writeXmlFile(Document doc, String path) {

		FileOutputStream fos = null;
		File file = new File(path);
		try {
			fos = new FileOutputStream(file);
			// XERCES 1 or 2 additionnal classes.
			OutputFormat of = new OutputFormat("XML","ISO-8859-1",true);
			of.setIndent(1);
			of.setIndenting(true);
			of.setDoctype(null,"users.dtd");
			XMLSerializer serializer = new XMLSerializer(fos,of);
			// As a DOM Serializer
			
			serializer.asDOMSerializer();
			serializer.serialize( doc);
			fos.close();		

		} catch (Exception e1) {
			writeXmlFile2(doc, new StreamResult(file));
		}
	}
	
	public static void main(String[] args) {
		try {
			
			String pathname = "/home/kiwi/Documenti/file.xml";
			Document doc = UtilXml.createDocument(new File(pathname));
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
			
			UtilXml.writeXmlFile(doc, pathname);
		}catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static Attr addAttribute(final Document doc,
									final Element element, 
									final String attrNome,
									final String attrValue) {
		Attr attr = doc.createAttribute(attrNome);
		attr.setValue(attrValue);
		element.setAttributeNode(attr);
		return attr;
	}

}