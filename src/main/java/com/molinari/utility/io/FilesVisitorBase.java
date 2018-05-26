package com.molinari.utility.io;

import java.io.File;
import java.util.Comparator;
import java.util.logging.Level;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.molinari.utility.controller.ControlloreBase;
import com.molinari.utility.servicesloader.Extensible;
import com.molinari.utility.servicesloader.LoaderLevel;
import com.molinari.utility.servicesloader.ServiceLoaderBase;

public class FilesVisitorBase implements FilesVisitor {

	private FileOperation operation;
	
	
	public FilesVisitorBase(FileOperation operation) {
		super();
		
		this.operation = operation;
	}
	
	public FilesVisitorBase() {
		// do nothing
	}

	/* (non-Javadoc)
	 * @see com.molinari.utility.io.FileVisitors#scorriEdEseguiSuTuttiIFile(java.lang.String)
	 */
	@Override
	public boolean runOnFiles(String pathFilePar) throws ParserConfigurationException, SAXException {
		final boolean ok = true;
		final String pathFile = normalizePath(pathFilePar);
		
		final File dir = new File(pathFile);
		final String[] files = dir.list();

//		List<String> lst = Arrays.asList(files);
//		lst.parallelStream().forEach((file -> eseguiSuFile(pathFile, file)));
		
		for (final String file : files) {
			eseguiSuFile(pathFile, file);
		}
		return ok;
	}

	public void eseguiSuFile(String pathFile, final String file) {
		final File f = new File(pathFile + file);

		try {
			if (getOperation().checkFile(f)) {
				operationExecute(pathFile, f);
			} else if (getOperation().checkDirectory(f)) {
				getOperation().executeOnDirectory(f);
				runOnFiles(f.getAbsolutePath());
			}
		} catch (ParserConfigurationException | SAXException e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
	}

	public <T extends ReturnFileOperation> void operationExecute(String pathFile, final File f) {
		try {
			T retExec = getOperation().execute(pathFile, f);
			getOperation().writeReport(retExec, f);
		} catch (final Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, "Error on execute operation on file: " + e.getMessage(), e);
		}
	}

	public String normalizePath(String pathFile) {
		if (!pathFile.substring(pathFile.length() - 1, pathFile.length()).equals(UtilIo.slash())) {
			pathFile += UtilIo.slash();
		}
		return pathFile;
	}

	@Override
	public FileOperation getOperation() {
		return operation;
	}

	private FileOperation createFileOperation() {
		ServiceLoaderBase<FileOperation> slb = new ServiceLoaderBase<>();
		return slb.load(FileOperation.class).createInstance();
	}

	@Override
	public LoaderLevel getLevel() {
		return LoaderLevel.BASE;
	}

	@Override
	public Comparator<Extensible<FilesVisitor>> getComparator() {
		return new ComparatorExtendibile<>(); 
	}

	@Override
	public void setOperation(FileOperation op) {
		this.operation = op;
	}

	@Override
	public FilesVisitor createInstance(Object... args) {
		return new FilesVisitorBase((FileOperation) args[0]);
	}
}
