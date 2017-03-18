package com.molinari.utility.xml;

import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class FileXml {

	private File file;
	private Document doc;

	public FileXml(final String path) throws Exception {
		file = new File(path);
		doc = UtilXml.createDocument(file);

	}

	public void addRootElement(final String nomeRoot) {
		UtilXml.addRootElement(doc, nomeRoot);
	}

	public void addElement(final String elementoPadre, final String nomeElemento) {
		final Element elementById = getDoc().getElementById(elementoPadre);
		UtilXml.addElement(getDoc(), elementById, nomeElemento);
	}

	public void addAttribute(final String elementoPadre, final String nomeElemento, final String valueElemento) {
		final Element elementById = getDoc().getElementById(elementoPadre);
		UtilXml.addAttribute(getDoc(), elementById, nomeElemento, valueElemento);
	}

	public File getFile() {
		return file;
	}

	public void setFile(final File file) {
		this.file = file;
	}

	public Document getDoc() {
		return doc;
	}

	public void setDoc(final Document doc) {
		this.doc = doc;
	}

}
