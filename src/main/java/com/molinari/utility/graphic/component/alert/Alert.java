package com.molinari.utility.graphic.component.alert;

import java.util.logging.Level;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.molinari.utility.controller.ControlloreBase;

/**
 * Alert fornisce una facade per accedere in maniera più veloce alle
 * "message dialog" di swing. Potrebbe implementare in futuro la gestione dei
 * log relativi alle dialog
 * 
 */
public class Alert {

	private Alert() {
		//do nothing
	}
	
	public static final String TITLE_OK = "ok!";
	public static final String TITLE_ERROR = "Non ci siamo!";

	public static void info(final String messaggio, final String title) {
		JOptionPane.showMessageDialog(null, messaggio, title, JOptionPane.INFORMATION_MESSAGE);
	}

	public static void errore(final String messaggio, final String title) {
		JOptionPane.showMessageDialog(null, messaggio, title, JOptionPane.ERROR_MESSAGE, new ImageIcon("imgUtil/index.jpeg"));
	}
	
	public static void segnalazioneEccezione(Exception e, String msg){
		String messaggio = msg != null ? msg : e.getMessage();
		Alert.errore("Errore: " + messaggio +". Controlla i log per maggiori info" , Alert.TITLE_ERROR);
		ControlloreBase.getLog().log(Level.SEVERE, messaggio, e);
	}

	public static void segnalazioneErroreGrave(final String messaggio) {
		Alert.errore(messaggio, Alert.TITLE_ERROR);
		ControlloreBase.getLog().severe(messaggio);
	}

	public static void segnalazioneErroreWarning(final String messaggio) {
		Alert.errore(messaggio, Alert.TITLE_ERROR);
		ControlloreBase.getLog().warning(messaggio);
	}

	public static void segnalazioneInfo(final String messaggio) {
		Alert.info(messaggio, Alert.TITLE_OK);
		ControlloreBase.getLog().info(messaggio);
	}

	public static String getMessaggioErrore(final String messaggio) {
		return "Operazione non eseguita: " + messaggio;
	}

}
