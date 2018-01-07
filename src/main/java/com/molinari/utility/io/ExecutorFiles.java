package com.molinari.utility.io;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public interface ExecutorFiles {

	boolean start(String startingPathFile) throws ParserConfigurationException, SAXException;

	void after();

	void before(String startingPathFile);

}