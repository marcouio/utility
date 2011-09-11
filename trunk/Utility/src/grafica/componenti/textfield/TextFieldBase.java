package grafica.componenti.textfield;

import grafica.componenti.StyleBase;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

public abstract class TextFieldBase extends JTextField implements FocusListener {

	private static final long serialVersionUID = 1L;
	protected IFormatterTF    formatter;
	protected StyleBase       style            = new StyleBaseTF();

	public static void main(final String[] args) {
		//		final TextFieldBase tfb = new TextFieldBase("dd-MM-yyyy");
		//		System.out.println(tfb.getBackground());
	}

	public TextFieldBase(final String testo, final IFormatterTF formatter) {
		super(testo);
		this.formatter = formatter;
		init();
	}

	public TextFieldBase(final String testo) {
		super(testo);
		init();
	}

	protected void init() {
		this.addFocusListener(this);
		this.settaStile();
	}

	protected void settaStile() {
		style=settaStileOverride()!=null?settaStileOverride():style;
		style.setPadre(this);
		this.setFont(style.getFont());
		this.setForeground(style.getForeground());
		this.setBackground(style.getBackground());
	}

	protected abstract StyleBase settaStileOverride();

	public Object getTestoConvertitoInTipo() {
		final Object testoConvertito = null;
		if (formatter != null && getText() != null) {
			try {
				formatter.parsifica(getText());
			} catch (final Exception e) {
				formatter.metodoForCatch(e);
			}
		}
		return testoConvertito;
	}

	@Override
	public void focusGained(final FocusEvent arg0) {}

	@Override
	public void focusLost(final FocusEvent arg0) {
		if (this.getTestoConvertitoInTipo() == null) {
			;
		}
	}

	public class StyleBaseTF extends StyleBase {

	}
}