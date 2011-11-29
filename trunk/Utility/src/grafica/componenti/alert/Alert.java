package grafica.componenti.alert;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 * Alert fornisce una facade per accedere in maniera più veloce alle
 * "message dialog" di swing. Potrebbe implementare in futuro la gestione dei
 * log relativi alle dialog
 * 
 */
public class Alert {

	public static final String TITLE_OK = "ok!";
	public static final String TITLE_ERROR = "Non ci siamo!";

	public static void info(final String messaggio, final String title) {
		JOptionPane.showMessageDialog(null, messaggio, title, JOptionPane.INFORMATION_MESSAGE);
	}

	public static void errore(final String messaggio, final String title) {
		JOptionPane.showMessageDialog(null, messaggio, title, JOptionPane.ERROR_MESSAGE, new ImageIcon("imgUtil/index.jpeg"));
	}

	public static void segnalazioneErroreGrave(final String messaggio) {
		Alert.errore(messaggio, Alert.TITLE_ERROR);
		// TODO LOG
	}

	public static void segnalazioneErroreWarning(final String messaggio) {
		Alert.errore(messaggio, Alert.TITLE_ERROR);
		// TODO LOG
	}

	public static void segnalazioneInfo(final String messaggio) {
		Alert.info(messaggio, Alert.TITLE_OK);
		// TODO LOG
	}

	public static String getMessaggioErrore(final String messaggio) {
		return "Operazione non eseguita: " + messaggio;
	}

}
