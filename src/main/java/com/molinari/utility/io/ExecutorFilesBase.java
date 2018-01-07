package com.molinari.utility.io;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.molinari.utility.controller.ControlloreBase;
import com.molinari.utility.servicesloader.ServiceLoaderBase;

/**
 * Questa classe serve per eseguire la stessa operazione su piu file. Scorre
 * tutti i file, anche andando in profondita ed esegue l'operazione implementata
 * nelle classi figlie.
 * 
 * @author marco.molinari
 * 
 */
public class ExecutorFilesBase implements ExecutorFiles {
	
	private FilesVisitor fileVisitors;
	
	/* (non-Javadoc)
	 * @see com.molinari.utility.io.ExecutorFiles#start(java.lang.String)
	 */
	@Override
	public boolean start(String startingPathFile) throws ParserConfigurationException, SAXException {
		
		before(startingPathFile);
		
		boolean result = getFileVisitors().runOnFiles(startingPathFile);
		
		after();
		
		return result;
	}
	
	public FilesVisitor getFileVisitors() {
		if(fileVisitors == null) {
			fileVisitors = createFileVisitors();
		}
		return fileVisitors;
	}

	private FilesVisitor createFileVisitors() {
		ServiceLoaderBase<FilesVisitor> slb = new ServiceLoaderBase<>();
		return slb.load(FilesVisitor.class);
	}

	/* (non-Javadoc)
	 * @see com.molinari.utility.io.ExecutorFiles#after()
	 */
	@Override
	public void after() {
		ControlloreBase.getLog().info("Termination phase of visiting files");
	}

	/* (non-Javadoc)
	 * @see com.molinari.utility.io.ExecutorFiles#before(java.lang.String)
	 */
	@Override
	public void before(String startingPathFile) {
		ControlloreBase.getLog().info("Starting phase of visiting files");
		ControlloreBase.getLog().info(() -> "Starting path file is: " + startingPathFile);
	}
	
}
