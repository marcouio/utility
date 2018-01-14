package com.molinari.utility.io;

import java.io.File;
import java.util.Comparator;
import java.util.logging.Level;

import com.molinari.utility.controller.ControlloreBase;
import com.molinari.utility.servicesloader.Extensible;

public class FileOperationBase implements FileOperation {

	public FileOperationBase() {
		//do nothing
	}
	
	@Override
	public void execute(String pathFile, File f) {
		ControlloreBase.getLog().log(Level.INFO, () -> "Executing operation for file: " +f.getName());
	}

	/**
	 * se necessario un controllo sulla validita' del file eseguire l'override
	 * del metodo.
	 * 
	 * @param f
	 * @return
	 */
	@Override
	public boolean checkFile(final File f) {
		ControlloreBase.getLog().log(Level.INFO, () -> "Sto eseguendo il check per il file: " +f.getName());
		boolean res = f.isFile() && f.canRead();
		if(res) {
			ControlloreBase.getLog().log(Level.INFO, () -> "Il file " + f.getName() + " è di tipo file ed è leggibile");
		}
		return res;
	}
	
	@Override
	public boolean checkDirectory(final File f) {
		boolean directory = f.isDirectory();
		if(directory) {
			ControlloreBase.getLog().log(Level.INFO, () -> "Il file " + f.getName() + " è una directory");
		}
		return directory;
	}

	@Override
	public com.molinari.utility.servicesloader.LoaderLevel getLevel() {
		return com.molinari.utility.servicesloader.LoaderLevel.BASE;
	}

	@Override
	public Comparator<Extensible<FileOperation>> getComparator() {
		return new ComparatorExtendibile<>();
	}

	@Override
	public void executeOnDirectory(File f) {
		ControlloreBase.getLog().log(Level.INFO, () -> "Executing operation for directory: " +f.getName());
		
	}

	@Override
	public String getOperation() {
		return "";
	}

	@Override
	public void after() {
		ControlloreBase.getLog().info("Termination phase of visiting files");		
	}

	@Override
	public void before(String startingPathFile) {
		ControlloreBase.getLog().info("Starting phase of visiting files");
		ControlloreBase.getLog().info(() -> "Starting path file is: " + startingPathFile);
	}

	@Override
	public FileOperation createInstance(Object... args) {
		return new FileOperationBase();
	}

}
