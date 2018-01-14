package com.molinari.utility.io;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.molinari.utility.servicesloader.Extensible;

public interface FilesVisitor extends Extensible<FilesVisitor>{

	/**
	 * Questo metodo e' solo una parte di codice ricorrente che scorre tutte le
	 * cartelle in profondita' per tutti i file che raggiunge eseguo un metodo
	 * che va implementato a secondo dell'uso che se ne vuole fare
	 * 
	 * @param pathFile
	 * @return
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	boolean runOnFiles(String pathFilePar) throws ParserConfigurationException, SAXException;

	void setOperation(FileOperation op);
	
	FileOperation getOperation();
}