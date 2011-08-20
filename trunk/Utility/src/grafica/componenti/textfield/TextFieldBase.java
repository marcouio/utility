package grafica.componenti.textfield;

import grafica.componenti.Alert;
import grafica.componenti.StyleBase;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

public class TextFieldBase extends JTextField implements FocusListener {

	private static final long serialVersionUID = 1L;
	protected IFormatterTF formatter;
	protected StyleBase style = new StyleBaseTF();;

	public static void main(final String[] args) {
		final TextFieldBase tfb = new TextFieldBase("dd-MM-yyyy");
		System.out.println(tfb.getBackground());
	}

	public TextFieldBase(final String testo, final IFormatterTF formatter) {
		super(testo);
		this.formatter = formatter;
		init();
	}

	public TextFieldBase(final String testo) {
		super(testo);
		this.style = new StyleBaseTF();
		init();
	}

	protected void init() {
		this.addFocusListener(this);
		this.settaStile();
	}

	protected void settaStile() {
		style.setPadre(this);
		// StyleBase.caricaInfoStyle(style);
		this.setFont(style.getFont());
		this.setForeground(style.getForeground());
		this.setBackground(style.getBackground());
	}

	public Object getTestoConvertitoInTipo() {
		final Object testoConvertito = null;
		if (formatter != null && getText() != null) {
			try {
				formatter.parsifica(getText());
			} catch (final Exception e) {
				Alert.segnalazioneErroreGrave("Testo non inserito correttamente: " + e.getMessage());
				e.printStackTrace();
			}
		}
		return testoConvertito;
	}

	@Override
	public void focusGained(final FocusEvent arg0) {
	}

	@Override
	public void focusLost(final FocusEvent arg0) {
		this.getTestoConvertitoInTipo();
	}

	public class StyleBaseTF extends StyleBase {

	}
}
