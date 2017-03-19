package com.molinari.utility.io;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.molinari.utility.controller.ControlloreBase;

public class UtilIo {

	public static void uploadFile(String hostAddr, int port, String fileName) throws IOException { 
		byte[] buf = new byte[1000]; 
		int len; 
		// connessione con l'host, ad una data porta 
		Socket sock = new Socket(hostAddr, port); 
		FileInputStream in = createFileInputStream(fileName); 
		OutputStream out = sock.getOutputStream(); 
		while ( ( len = in.read(buf) ) != -1 ) { 
			out.write(buf, 0, len); 
			out.flush(); 
		} 
		out.close(); 
		sock.close(); 
		in.close();
	}

	public void createZipFile(final String inputFileName,
			final String zipFileName) {

		try {

			// Creare gli stream d’input e output
			FileInputStream inStream = createFileInputStream(inputFileName);
			ZipOutputStream outStream = new ZipOutputStream(createFileOutputStream(zipFileName));

			// Aggiungere un oggetto ZipEntry allo stream d’output
			outStream.putNextEntry(new ZipEntry(inputFileName));

			byte[] buffer = new byte[1024];
			int bytesRead;

			// Ciascuna porzione di dati letti dallo stream di input
			// viene scritta nello stream di output
			while ((bytesRead = inStream.read(buffer)) > 0) {
				outStream.write(buffer, 0, bytesRead);
			}

			outStream.closeEntry();

			outStream.close();
			inStream.close();

		} catch (IOException ex) {
			ControlloreBase.getLog().log(Level.SEVERE, ex.getMessage(), ex);
		}
	}

	private FileOutputStream createFileOutputStream(final String zipFileName) throws FileNotFoundException {
		return new FileOutputStream(zipFileName);
	}

	private static FileInputStream createFileInputStream(final String inputFileName) throws FileNotFoundException {
		return new FileInputStream(inputFileName);
	}

	public static void deleteFileDaDirectory(final String directory) {
		Predicate<? super File> predicate = File::isFile;
		deleteFileFromDirectory(directory, predicate);
		
	}
	
	public static void deleteFileFromDirectory(final String directory, Predicate<? super File> predicate){
		final File dir = new File(directory);
		
		File[] listFiles = dir.listFiles();
		List<File> fileList = Arrays.asList(listFiles);
		Stream<File> streamFiltered = fileList.stream().filter(predicate);
		streamFiltered.forEach(File::delete);
			
	}

	public static void deleteFileDaDirectory(final String directory, final String treCharIniziali) {
		
		Predicate<? super File> predicate = t -> {
			boolean startWith = t.getName().substring(0, 3).equals(treCharIniziali);
			return t.isFile() && startWith;
		};
		
		deleteFileFromDirectory(directory, predicate);
		
	}

	public static void scriviFileSuPiuRighe(final File file, final List<String> righe) {
		try {
			FileWriter fileWriter = createFileWriter(file);
			final BufferedWriter out = new BufferedWriter(fileWriter);
			for (final String type : righe) {
				out.write(type);
				out.newLine();
			}
			close(out, fileWriter);
		} catch (final IOException e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
	}

	private static FileWriter createFileWriter(final File file)
			throws IOException {
		return new FileWriter(file);
	}
	
	public static void close(Closeable ...closeables) throws IOException {
		for (Closeable closeable : closeables) {
			closeable.close();
		}
		
	}

	public static boolean check(final String estensione, final File file) {
		if(fileExist(file)){ 
				return checkEstensione(estensione, file)
				&& checkLunghezzaNome(estensione, file)
				&& checkAssenzaParentesi(file);
		}
		return false;
	}

	private static boolean fileExist(final File file) {
		return file.exists() && file.isFile();
	}

	public static boolean checkEstensione(final String estensione, final File file) {
		return file.getName().endsWith(estensione);
	}

	public static boolean checkLunghezzaNome(final String estensione, final File file) {
		final String nomeFile = file.getName();
		return nomeFile.length() > estensione.length();
	}

	public static boolean checkAssenzaParentesi(final File file) {
		return !(file.getName().contains("(") || file.getName().contains(")"));
	}

	protected static boolean rename(final File mp3, final String nomedopo)
			throws IOException {
		final File file2 = new File(nomedopo);
		return mp3.renameTo(file2);
	}

	public boolean moveFile(final File origine, final File destinazione) {
		return origine.renameTo(destinazione);
	}

	public static String slash() {
		String slash;
		final String os = System.getProperty("os.name");
		if (os.startsWith("Win")) {
			slash = "\\";
		} else {
			slash = "/";
		}
		return slash;
	}
}
