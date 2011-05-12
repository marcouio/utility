package testo;

public class TextUtil {

	/**
	 * Dato un campo, ne valuta la lunghezza. Se e' piu' corto della dimensione
	 * inserita come parametro aggiunge campi vuoti, altrimenti tronca
	 * aggiungendo uno spazio finale.
	 * 
	 * @param campo
	 * @param dimensione
	 * @return String
	 */
	public static String creaStringStessaDimensione(String campo, int dimensione) {
		if (campo.length() < dimensione) {
			for (int i = campo.length(); i < dimensione + 1; i++) {
				campo = campo + " ";
			}
		} else {
			campo = campo.substring(0, dimensione);
			campo = campo + " ";
		}
		return campo;
	}
}