package com.molinari.utility.graphic.component.textfield;

import com.molinari.utility.graphic.component.alert.Alert;

public class FormatterBaseTF implements IFormatterTF {

	@Override
	public Object parsifica(String testo){
		return testo;
	}

	@Override
	public Object metodoForCatch(Exception e) {
		Alert.segnalazioneErroreGrave("Testo non inserito correttamente: " + e.getMessage());
		return e;
	}

}
