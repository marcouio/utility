package com.molinari.utility.io;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import com.google.common.io.Files;
import com.molinari.utility.GenericException;
import com.molinari.utility.controller.ControlloreBase;

public class UtilIo {

	public static void uploadFile(String hostAddr, int port, String fileName) throws IOException {
		final byte[] buf = new byte[1000];
		int len;
		// connessione con l'host, ad una data porta
		final Socket sock = createSock(hostAddr, port);
		final FileInputStream in = createFileInputStream(fileName);
		final OutputStream out = sock.getOutputStream();
		while ((len = in.read(buf)) != -1) {
			out.write(buf, 0, len);
			out.flush();
		}
		out.close();
		sock.close();
		in.close();
	}

	public static String prepareUrl(String pathPar) {
		String path = pathPar;
		if (path != null) {
			path = adjust(path);
			return URLUTF8Encoder.unescape(URLUTF8Encoder.encode(path));
		}
		return null;
	}

	private static String adjust(String path) {
		String nome = path;
		nome = nome.replaceAll(">", "");
		nome = nome.replaceAll("<", "");
		nome = nome.replaceAll("'", " ");
		nome = nome.replaceAll("\\?", "");
		nome = nome.replaceAll("!", "");
		nome = nome.replaceAll("\"", "");
		nome = nome.replaceAll("[\\[\\]]", "");
		nome = nome.replaceAll("\\*", "");
		nome = nome.replaceAll("\\(", "");
		nome = nome.replaceAll("\\)", "");
		nome = nome.trim();
		return nome;
	}

	private static Socket createSock(String hostAddr, int port) throws IOException {
		return new Socket(hostAddr, port);
	}

	/**
	 * Unzip it
	 * 
	 * @param zipFile
	 *            input zip file
	 * @param output
	 *            zip file output folder
	 */
	public static void unZipIt(String zipFile, String outputFolder) {

		byte[] buffer = new byte[1024];

		try (FileInputStream fis = new FileInputStream(zipFile); ZipInputStream zis = new ZipInputStream(fis);) {

			// create output directory is not exists
			File folder = new File(outputFolder);
			if (!folder.exists()) {
				folder.mkdir();
			}

			// get the zip file content
			// get the zipped file list entry
			ZipEntry ze = zis.getNextEntry();

			while (ze != null) {

				String fileName = ze.getName();
				File newFile = new File(outputFolder + File.separator + fileName);

				ControlloreBase.getLog().info(() -> "file unzip : " + newFile.getAbsoluteFile());

				// create all non exists folders
				// else you will hit FileNotFoundException for compressed folder
				new File(newFile.getParent()).mkdirs();

				try (FileOutputStream fos = new FileOutputStream(newFile);) {

					int len;
					while ((len = zis.read(buffer)) > 0) {
						fos.write(buffer, 0, len);
					}

					ze = zis.getNextEntry();
				}
			}

		} catch (IOException ex) {
			ControlloreBase.getLog().log(Level.SEVERE, ex.getMessage(), ex);
		}
	}

	public static void createZipFile(final String inputFileName, final String zipFileName) throws IOException {

		try (
				// Creare gli stream d’input e output
				FileInputStream inStream = createFileInputStream(inputFileName);
				FileOutputStream fis = createFileOutputStream(zipFileName);
				ZipOutputStream outStream = new ZipOutputStream(fis);) {

			// Aggiungere un oggetto ZipEntry allo stream d’output
			outStream.putNextEntry(new ZipEntry(inputFileName));

			final byte[] buffer = new byte[1024];
			int bytesRead;

			// Ciascuna porzione di dati letti dallo stream di input
			// viene scritta nello stream di output
			while ((bytesRead = inStream.read(buffer)) > 0) {
				outStream.write(buffer, 0, bytesRead);
			}

			outStream.closeEntry();

		} catch (final IOException ex) {
			ControlloreBase.getLog().log(Level.SEVERE, ex.getMessage(), ex);
		}
	}

	private static FileOutputStream createFileOutputStream(final String zipFileName) throws FileNotFoundException {
		return new FileOutputStream(zipFileName);
	}

	private static FileInputStream createFileInputStream(final String inputFileName) throws FileNotFoundException {
		return new FileInputStream(inputFileName);
	}

	public static void deleteFileDaDirectory(final String directory) {
		final Predicate<? super File> predicate = File::isFile;
		deleteFileFromDirectory(directory, predicate);

	}

	public static void deleteFileFromDirectory(final String directory, Predicate<? super File> predicate) {
		final File dir = new File(directory);

		final File[] listFiles = dir.listFiles();
		final List<File> fileList = Arrays.asList(listFiles);
		final Stream<File> streamFiltered = fileList.stream().filter(predicate);
		streamFiltered.forEach(File::delete);

	}

	public static void deleteFileDaDirectory(final String directory, final String treCharIniziali) {

		final Predicate<? super File> predicate = t -> {
			final boolean startWith = t.getName().substring(0, 3).equals(treCharIniziali);
			return t.isFile() && startWith;
		};

		deleteFileFromDirectory(directory, predicate);

	}

	public static void scriviFileSuPiuRighe(final File file, final List<String> righe) {
		try {
			final FileWriter fileWriter = createFileWriter(file);
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

	private static FileWriter createFileWriter(final File file) throws IOException {
		return new FileWriter(file);
	}

	public static void close(Closeable... closeables) throws IOException {
		for (final Closeable closeable : closeables) {
			closeable.close();
		}
	}

	public abstract class Reader<T> {

		private final File file;

		public Reader(File file) {
			this.file = file;
		}

		public List<T> readAll() throws FileNotFoundException {
			final List<T> result = new ArrayList<>();
			final FileInputStream fis = new FileInputStream(file);
			try (Scanner scanner = new Scanner(new InputStreamReader(fis, UTF_8))) {
				while (scanner.hasNextLine()) {
					final String nextLine = scanner.nextLine();
					result.add(doSomething(nextLine));
				}
			}
			return result;
		}

		protected abstract T doSomething(String nextLine);
	}

	public static boolean check(final String estensione, final File file) {
		if (fileExist(file)) {
			return checkEstensione(estensione, file) && checkLunghezzaNome(estensione, file)
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

	protected static boolean rename(final File mp3, final String nomedopo) {
		final File file2 = new File(nomedopo);
		return mp3.renameTo(file2);
	}

	public static boolean moveFile(final File origine, final File destinazione) {
		String pathTo = prepareUrl(destinazione.getAbsolutePath());
		try {
			Files.move(origine, new File(pathTo));
			return true;
		} catch (IOException e) {
			throw new GenericException(e);
		}
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
