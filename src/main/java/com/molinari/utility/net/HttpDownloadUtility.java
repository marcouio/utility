package com.molinari.utility.net;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.molinari.utility.io.UtilIo;

/**
 * A utility that downloads a file from a URL.
 * 
 * @author www.codejava.net
 *
 */
public class HttpDownloadUtility {

	public static void downloadFile(String fileURL, String saveDir) throws IOException {
		URL url = new URL(fileURL);
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		int responseCode = httpConn.getResponseCode();

		// always check HTTP response code first
		if (responseCode == HttpURLConnection.HTTP_OK) {
			String contentType = httpConn.getContentType();
			int contentLength = httpConn.getContentLength();

			System.out.println("Content-Type = " + contentType);
			System.out.println("Content-Length = " + contentLength);

			// opens input stream from the HTTP connection
			InputStream inputStream = httpConn.getInputStream();
			File targetFile = new File(saveDir);
		    FileOutputStream fos = new FileOutputStream(targetFile);
		    fos.write(inputStream.available());
			fos.close();
			
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