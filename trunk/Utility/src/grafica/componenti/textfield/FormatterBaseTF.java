package grafica.componenti.textfield;

import grafica.componenti.alert.Alert;

public class FormatterBaseTF implements IFormatterTF {

	@Override
	public Object parsifica(String testo)
	    throws Exception {
		return testo;
	}

	@Override
	public Object metodoForCatch(Exception e) {
		Alert.segnalazioneErroreGrave("Testo non inserito correttamente: " + e.getMessage());
		return e;
	}

}
