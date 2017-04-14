package com.molinari.utility.text;

import java.awt.GraphicsEnvironment;

public class UtilText {

	public String[] listSystemFontsName() {
		final GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		return ge.getAvailableFontFamilyNames();
	}

	/**
	 * Dato un campo, ne valuta la lunghezza. Se e' piu' corto della dimensione
	 * inserita come parametro aggiunge campi vuoti, altrimenti tronca
	 * aggiungendo uno spazio finale.
	 * 
	 * @param campo
	 * @param dimensione
	 * @return String
	 */
	public static String creaStringStessaDimensione(String field, final int dimensione) {
		String campo = field;
		if (campo.length() < dimensione) {
			for (int i = campo.length(); i < dimensione + 1; i++) {
				final StringBuilder sb = new StringBuilder();
				sb.append(campo);
				sb.append(" ");
				campo = sb.toString();
			}
		} else {
			campo = campo.substring(0, dimensione);
			final StringBuilder sb = new StringBuilder();
			sb.append(campo);
			sb.append(" ");
			campo = sb.toString();
		}
		return campo;
	}

	public static String slash() {
		String slash = "";
		final String os = System.getProperty("os.name");
		if (os.startsWith("Win")) {
			slash = "\\";
		} else {
			slash = "/";
		}
		return slash;
	}

	public static boolean noNull(final String[] lista) {
		for (final String string : lista) {
			if (string == null || "".equals(string)) {
				return false;
			}
		}
		return true;
	}

}
