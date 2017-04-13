package com.molinari.utility.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;

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

import com.molinari.utility.controller.ControlloreBase;
import com.molinari.utility.io.UtilIo;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

public class UtilXml {
	
	
	private UtilXml() {
		//do nothing
	}

	/**
	 * Carica tutte le informazioni del parametro xml in un document
	 * 
	 * @param xml
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static Document createDocument(final File xml){
		Document doc = null;
		DocumentBuilder dBuilder = null;
		try {
			final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(xml);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
			if(dBuilder != null){
				doc = dBuilder.newDocument();
			}
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
	public static NodeList getNodeList(final String path) {
		final Document doc = UtilXml.createDocument(new File(path));
		if(doc != null){
			final Element root = doc.getDocumentElement();
			return root.hasChildNodes() ? root.getChildNodes() : null;
		}
		return null;
	}
		

	public static NodeList getNodeList(final Document doc) {
		if(doc != null){
			final Element root = doc.getDocumentElement();
			if(root!=null){
				return root.hasChildNodes() ? root.getChildNodes() : null;
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
		if (nodoComponente != null && nodoComponente.getNodeType() == Node.ELEMENT_NODE) {
			elemento = (Element) nodoComponente;
		}
		return elemento;
	}

	public static Node getNodo(String nodo, String path) {
		final NodeList listaNodi = getNodeList(path);
		if(listaNodi != null){
			for (int i = 0; i < listaNodi.getLength(); i++) {
				final Node nodoDaLista = listaNodi.item(i);
				if (nodoDaLista.getNodeName().equals(nodo)) {
					return nodoDaLista;
				}
			}
		}
		return null;
	}
	
	public static Element addElement(final Document doc, 
									 final Element elementoPadre,
									 final String nomeElemento) {
		final Element style = doc.createElement(nomeElemento);
		elementoPadre.appendChild(style);
		return style;
	}

	public static Node getNodo(String nodo, Document doc) {
		final NodeList listaNodi = getNodeList(doc);
		if(listaNodi!=null){
			for (int i = 0; i < listaNodi.getLength(); i++) {
				final Node nodoDaLista = listaNodi.item(i);
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
				final Transformer xformer = TransformerFactory.newInstance().newTransformer();
				
				final StreamResult outputTarget = stream;
				xformer.transform(new DOMSource(doc), outputTarget);
				return outputTarget;
			} catch (final Exception e) {
				ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
			}
			return null;
		}

	public static void writeXmlFile(Document doc, String path) throws IOException {

		FileOutputStream fos = null;
		final File file = new File(path);
		try {
			fos = new FileOutputStream(file);
			// XERCES 1 or 2 additionnal classes.
			final OutputFormat of = new OutputFormat("XML","ISO-8859-1",true);
			of.setIndent(1);
			of.setIndenting(true);
			of.setDoctype(null,"users.dtd");
			final XMLSerializer serializer = new XMLSerializer(fos,of);
			// As a DOM Serializer
			
			serializer.asDOMSerializer();
			serializer.serialize( doc);
			fos.close();		

		} catch (final Exception e1) {
			ControlloreBase.getLog().log(Level.SEVERE, e1.getMessage(), e1);
			writeXmlFile2(doc, new StreamResult(file));
		}finally {
			UtilIo.close(fos);
		}
	}

	public static Attr addAttribute(final Document doc,
									final Element element, 
									final String attrNome,
									final String attrValue) {
		final Attr attr = doc.createAttribute(attrNome);
		attr.setValue(attrValue);
		element.setAttributeNode(attr);
		return attr;
	}

	public static Element addRootElement(Document doc, String rootNome) {
		final Element root = doc.createElement(rootNome);
		doc.appendChild(root);
		return root;
	}

}