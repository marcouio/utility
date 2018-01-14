package com.molinari.utility.io;

import java.io.File;
import java.util.Comparator;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.molinari.utility.servicesloader.Extensible;
import com.molinari.utility.servicesloader.LoaderLevel;
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
	
	private FileOperation operation;
	
	public ExecutorFilesBase() {
		//default do nothing
	}
	
	public ExecutorFilesBase(FileOperation operation) {
		super();
		this.operation = operation;
	}

	/* (non-Javadoc)
	 * @see com.molinari.utility.io.ExecutorFiles#start(java.lang.String)
	 */
	@Override
	public boolean start(String startingPathFile) throws ParserConfigurationException, SAXException {
		
		checkPathIsDir(startingPathFile);
		
		getOperation().before(startingPathFile);

		boolean result = getFileVisitors().runOnFiles(startingPathFile);
		
		getOperation().after();
		
		return result;
	}

	public void checkPathIsDir(String startingPathFile) {
		File dir = new File(startingPathFile);
		if(!dir.isDirectory()) {
			throw new IllegalArgumentException("startingPathFile parameter has to be the absolute path of a directory");
		}
	}
	
	public FilesVisitor getFileVisitors() {
		if(fileVisitors == null) {
			fileVisitors = createFileVisitors(getOperation());
		}
		return fileVisitors;
	}

	private FilesVisitor createFileVisitors(FileOperation operationPar) {
		ServiceLoaderBase<FilesVisitor> slb = new ServiceLoaderBase<>();
		return slb.load(FilesVisitor.class).createInstance(operationPar);
	}

	public FileOperation getOperation() {
		return operation;
	}

	public void setOperation(FileOperation operation) {
		this.operation = operation;
	}

	@Override
	public LoaderLevel getLevel() {
		return LoaderLevel.BASE;
	}

	@Override
	public Comparator<Extensible<ExecutorFiles>> getComparator() {
		return new ComparatorExtendibile<>();
	}

	@Override
	public ExecutorFiles createInstance(Object... args) {
		return new ExecutorFilesBase((FileOperation) args[0]);
	}
}
