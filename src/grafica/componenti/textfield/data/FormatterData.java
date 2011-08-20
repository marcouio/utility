package grafica.componenti.textfield.data;

import grafica.componenti.textfield.IFormatterTF;

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

}
