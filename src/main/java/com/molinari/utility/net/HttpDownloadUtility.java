package com.molinari.utility.net;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import com.molinari.utility.io.UtilIo;

/**
 * A utility that downloads a file from a URL.
 * 
 * @author www.codejava.net
 *
 */
public class HttpDownloadUtility {

	public static void downloadFile(String fileURL, String savePath) throws IOException {
		URL url = new URL(fileURL);
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		httpConn.setInstanceFollowRedirects(true);
		try {
			int responseCode = httpConn.getResponseCode();
			if (responseCode != HttpURLConnection.HTTP_OK) {
				throw new IOException("Risposta HTTP " + responseCode + " per " + fileURL);
			}

			File targetFile = new File(savePath).getAbsoluteFile();
			File parent = targetFile.getParentFile();
			if (parent != null && !parent.exists() && !parent.mkdirs()) {
				throw new IOException("Impossibile creare la cartella " + parent.getAbsolutePath());
			}

			Path tempFile = Files.createTempFile(parent.toPath(), targetFile.getName(), ".part");
			try {
				try (InputStream inputStream = httpConn.getInputStream()) {
					Files.copy(inputStream, tempFile, StandardCopyOption.REPLACE_EXISTING);
				}
				Files.move(tempFile, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				Files.deleteIfExists(tempFile);
				throw e;
			}
		} finally {
			httpConn.disconnect();
		}
	}
	

	public static ArrayList<String> readFully(InputStream inputStream) throws IOException {
		InputStream in = inputStream;
		InputStreamReader is = new InputStreamReader(in);
		ArrayList<String> righe = new ArrayList<String>();
		BufferedReader br = new BufferedReader(is);
		String read = br.readLine();

		while (read != null) {
			righe.add(read);
			read = br.readLine();

		}

		return righe;
	}
}