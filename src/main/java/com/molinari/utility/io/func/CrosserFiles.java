package com.molinari.utility.io.func;

import java.io.File;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.logging.Level;

import com.molinari.utility.controller.ControlloreBase;

public class CrosserFiles {
	
	public void execute(String pathFile, Consumer<File> exec) {
		executeOnDir(pathFile, exec);
	}
	
	public void executeOnDir(String pathFile, Consumer<File> exec) {
		
		boolean checkPath = checkPath(pathFile);
		if(!checkPath) {
			throw new IllegalArgumentException("Path parameter is not valid");
		}

		File[] files = new File(pathFile).listFiles();
		executeOnFilesArrays(exec, files);
	}

	public void executeOnFilesArrays(Consumer<File> exec, File[] file) {
		Arrays.stream(file).filter(File::isDirectory).forEach(getDirectoryConsumer(exec));
		Arrays.stream(file).filter(File::isFile).forEach(exec);
	}

	public Consumer<File> getDirectoryConsumer(Consumer<File> exec) {
		return f -> {
			executeOnFilesArrays(exec, f.listFiles());
		};
	}

	public boolean checkPath(String pathFile) {
		if(pathFile != null) {
		File dir = new File(pathFile);
			if(dir.exists() && dir.isDirectory()) {
				return true;
			}
		}
		return false;
	}

	public boolean checkFolder(Predicate<File> checkDirectory, File f) {
		boolean test = checkDirectory.test(f);
		if(test) {
			ControlloreBase.getLog().log(Level.INFO, () -> "Check on folder is ok");
		}
		return test;
	}
	
	public boolean checkFile(Predicate<File> checkFile, File f) {
		
		ControlloreBase.getLog().log(Level.INFO, () -> "Sto eseguendo il check per il file: " +f.getName());
		boolean test = checkFile.test(f);
		if(test) {
			ControlloreBase.getLog().log(Level.INFO, () -> "Check on file is ok");
		}
		return test;
	}
}
