package com.molinari.utility.io.func;

import java.io.File;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.stream.Stream;

import com.molinari.utility.controller.ControlloreBase;

public class CrosserFiles {
	
	private boolean parallel;
	
	public CrosserFiles() {
		
	}
	
	public void execute(String pathFile, Consumer<File> exec) {
		executeOnDir(pathFile, exec);
	}
	
	public void executeOnDir(String pathFile, Consumer<File> exec) {
		
		boolean checkPath = checkPath(pathFile);
		if(!checkPath) {
			throw new IllegalArgumentException("Path parameter is not valid");
		}

		File[] file = new File(pathFile).listFiles();
		
		Arrays.stream(file).parallel().forEach(getDirectoryConsumer(exec));
	}

	public Consumer<File> getDirectoryConsumer(Consumer<File> exec) {
		return f -> { 
			File[] listFiles = f.listFiles();
			if(listFiles != null) { 
				toStream(listFiles).filter(File::isDirectory).forEach(getDirectoryConsumer(exec));
				toStream(listFiles).filter(File::isFile).forEach(exec);
				return;
			}
			exec.accept(f);
		};
	}
	
	private <T> Stream<T> toStream(T[] arrayObj){
		return parallel ? Arrays.stream(arrayObj).parallel() :  Arrays.stream(arrayObj);
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

	public boolean isParallel() {
		return parallel;
	}

	public void setParallel(boolean parallel) {
		this.parallel = parallel;
	}
}
