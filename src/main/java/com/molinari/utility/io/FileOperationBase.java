package com.molinari.utility.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;

import com.molinari.utility.controller.ControlloreBase;
import com.molinari.utility.io.csv.BeanOperationFile;
import com.molinari.utility.io.csv.WriterCSV;
import com.molinari.utility.servicesloader.Extensible;

public class FileOperationBase implements FileOperation {

	private String startingPath;
	
	WriterCSV<BeanOperationFile> writer;
	
	public FileOperationBase() {
		//do nothing
	}
	
	@Override
	public <T> T execute(String pathFile, File f) {
		ControlloreBase.getLog().log(Level.INFO, () -> "Executing operation for file: " +f.getName());
		return null;
	}
	
	@Override
	public <T extends ReturnFileOperation> void writeReport(T objReturn, File f) {
		try {
			if(writer == null) { 
				writer = new WriterCSV<>(BeanOperationFile.class, startingPath + File.separator + "report.csv");
				BeanOperationFile bof = createBeanHeader();
				writeBean(bof);
			}
			BeanOperationFile bof = createBean(objReturn, f);
			writeBean(bof);
		} catch (IOException e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
	}

	public <T extends ReturnFileOperation> BeanOperationFile createBean(T objReturn, File f) {
		BeanOperationFile bof = new BeanOperationFile();
		bof.setInput(f.getAbsolutePath());
		bof.setOutput(objReturn.getResponse());
		if(!objReturn.getErrors().isEmpty()) {
			bof.setError(objReturn.getErrors().get(0));
		}
		return bof;
	}

	public void writeBean(BeanOperationFile bof) throws IOException {
		List<BeanOperationFile> lst = new ArrayList<>();
		lst.add(bof);
		writer.write(lst);
	}

	public BeanOperationFile createBeanHeader() {
		BeanOperationFile bof = new BeanOperationFile();
		bof.setInput("INPUT");
		bof.setOutput("OUTPUT");
		bof.setNotes("NOTES");
		bof.setError("ERROR");
		return bof;
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
		this.startingPath = startingPathFile;
	}

	@Override
	public FileOperation createInstance(Object... args) {
		return new FileOperationBase();
	}

}
