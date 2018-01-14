package com.molinari.utility.io;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.molinari.utility.servicesloader.Extensible;

public interface ExecutorFiles extends Extensible<ExecutorFiles>{

	boolean start(String startingPathFile) throws ParserConfigurationException, SAXException;

}