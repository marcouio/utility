package com.molinari.utility.graphic.component.textfield.date;

import com.molinari.utility.graphic.component.alert.Alert;
import com.molinari.utility.graphic.component.textfield.IFormatterTF;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatterData extends SimpleDateFormat implements IFormatterTF {

	private static final long serialVersionUID = 1L;

	public FormatterData(final String format) {
		super(format);
	}

	@Override
	public Date parsifica(final String testo) throws Exception {
		return super.parse(testo);
	}

	@Override
	public Object metodoForCatch(final Exception e) {
		if (e instanceof NumberFormatException) {
			Alert.segnalazioneErroreGrave("Inserire la data con valori numerici e con il formato suggerito: AAAA/MM/GG");
		} else if (e instanceof IllegalArgumentException) {
			Alert.segnalazioneErroreGrave(Alert.getMessaggioErrore("Non hai inserito una data, " + e.getMessage()));
		} else if (e instanceof StringIndexOutOfBoundsException) {
			Alert.segnalazioneErroreGrave(Alert.getMessaggioErrore("Numero di caratteri errato per una data, " + e.getMessage()));
		} else {
			Alert.segnalazioneErroreGrave(Alert.getMessaggioErrore("Data non inserita correttamente: " + e.getMessage()));
		}
		return e;
	}
}
