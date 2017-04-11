package com.molinari.utility.graphic;

import com.molinari.utility.common.LineStackTracePrinter;

public class ExceptionGraphics extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String message = null;
	private String help = null;

	public ExceptionGraphics(final String message, final String help) {
		this.message = message;
		this.help = help;
	}

	@Override
	public String getMessage() {
		final StringBuilder sb = new StringBuilder();
		sb.append(LineStackTracePrinter.scriviLineaDellErrore(6));
		sb.append(". Motivo: ");
		if (message != null && help != null) {
			sb.append(message + " - " + help);
		} else if (help != null) {
			sb.append(help);
		} else if (message != null) {
			sb.append(message);
		} else {
			sb.append("Metodo non supportato o esistente solo perché esteso: da non chiamare!");
		}
		return sb.toString();
	}
}
