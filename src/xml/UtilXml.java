package xml;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
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
			final NodeList listaNodi = (root.hasChildNodes()) ? root.getChildNodes() : null;
			return listaNodi;
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
	public static void writeXmlFile(Document doc, String path) {

		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(path);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		// XERCES 1 or 2 additionnal classes.
		OutputFormat of = new OutputFormat("XML","ISO-8859-1",true);
		of.setIndent(1);
		of.setIndenting(true);
		of.setDoctype(null,"users.dtd");
		XMLSerializer serializer = new XMLSerializer(fos,of);
		// As a DOM Serializer
		try {
			serializer.asDOMSerializer();
			serializer.serialize( doc);
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		try {
			Document doc = UtilXml.createDocument(new File("/home/kiwi2/Scrivania/file.xml"));
			Element rootElement = doc.createElement("config");
			doc.appendChild(rootElement);
			
			Element lang = doc.createElement("lang");
			rootElement.appendChild(lang);
			
			Attr attrLocale = doc.createAttribute("locale");
			attrLocale.setValue("it");
			lang.setAttributeNode(attrLocale);
			
			Element utente = doc.createElement("utenteDefault");
			rootElement.appendChild(utente);
			
			Attr attrUser = doc.createAttribute("user");
			attrUser.setValue("user");
			lang.setAttributeNode(attrUser);
			
			Attr attrPass = doc.createAttribute("pass");
			attrPass.setValue("pass");
			lang.setAttributeNode(attrPass);
			UtilXml.writeXmlFile(doc, "/home/kiwi2/Scrivania/file.xml");
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}