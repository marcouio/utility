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
	
	/* (non-Javadoc)
	 * @see com.molinari.utility.io.FileVisitors#scorriEdEseguiSuTuttiIFile(java.lang.String)
	 */
	@Override
	public boolean runOnFiles(String pathFilePar) throws ParserConfigurationException, SAXException {
		String pathFile = pathFilePar;
		final boolean ok = true;
		if (!pathFile.substring(pathFile.length() - 1, pathFile.length()).equals(UtilIo.slash())) {
			pathFile += UtilIo.slash();
		}
		final File dir = new File(pathFile);
		final String[] files = dir.list();

		for (final String file : files) {
			final File f = new File(pathFile + file);

			if (getOperation().checkFile(f)) {
				try {
					getOperation().execute(pathFile, f);
				} catch (final Exception e) {
					ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
				}
			} else if (getOperation().checkDirectory(f)) {
				runOnFiles(f.getAbsolutePath());
			}
		}
		return ok;
	}

	public FileOperation getOperation() {
		if(operation == null) {
			operation = createFileOperation();
		}
		return operation;
	}

	private FileOperation createFileOperation() {
		ServiceLoaderBase<FileOperation> slb = new ServiceLoaderBase<>();
		return slb.load(FileOperation.class);
	}

	@Override
	public LoaderLevel getLevel() {
		return LoaderLevel.BASE;
	}

	@Override
	public Comparator<Extensible<FilesVisitor>> getComparator() {
		return new ComparatorExtendibile<>(); 
	}
}
