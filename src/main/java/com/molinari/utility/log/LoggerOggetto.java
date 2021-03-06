package com.molinari.utility.log;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.molinari.utility.controller.ControlloreBase;
import com.molinari.utility.io.UtilIo;

public class LoggerOggetto {

	private static Logger log;
	private static FileHandler fileLog;
	private static LoggerOggetto singleton;

	public static LoggerOggetto getSingleton() {
		if (singleton == null) {
			singleton = new LoggerOggetto();
		}
		return singleton;
	}

	private LoggerOggetto() {
	}

	/**
	 * Restituisce il logger dell'applicazione. Viene creato se chiamato per la
	 * prima volta.
	 * 
	 * @return
	 */
	public static Logger getLog(final String nomeLog) {
		if (log == null) {
			creaLog(nomeLog);
		} else {
			log = Logger.getLogger(nomeLog);
		}
		return log;
	}

	public static void setLog(final Logger log) {
		LoggerOggetto.log = log;
	}

	/**
	 * Crea un Logger
	 * 
	 * @return Logger
	 */
	private static Logger creaLog(final String nomeLog) {

		log = Logger.getLogger(nomeLog);
		try {
			UtilIo.deleteFileDaDirectory("./", "MyL");
			fileLog = new FileHandler("MyLog.txt", 500000, 1, false);
			fileLog.setFormatter(new SimpleFormatter());
		} catch (SecurityException | IOException e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
		log.addHandler(fileLog);
		log.info("Start programma!");
		return log;
	}
	
	public void setLevel(Level level){
		log.setLevel(level);
	}

	public static String getMessaggioErroreOperazione(final Exception e) {
		return "Operazione non eseguita: " + e.getMessage();
	}
}
