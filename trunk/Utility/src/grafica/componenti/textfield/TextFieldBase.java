package grafica.componenti.textfield;

import grafica.componenti.ComponenteBase;
import grafica.componenti.IComponenteBase;
import grafica.componenti.StyleBase;

import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

public abstract class TextFieldBase extends JTextField implements FocusListener, IComponenteBase {

	private static final long serialVersionUID = 1L;
	protected IFormatterTF formatter;
	protected StyleBase style = new StyleBaseTF();
	private Container contenitorePadre;
	private final ComponenteBase componenteBase = new ComponenteBase();

	public static void main(final String[] args) {
		//		final TextFieldBase tfb = new TextFieldBase("dd-MM-yyyy");
		//		System.out.println(tfb.getBackground());
	}

	public TextFieldBase(final String testo, final IFormatterTF formatter, final Container contenitore) {
		super(testo);
		this.formatter = formatter;
		this.contenitorePadre = contenitore;
		init(contenitorePadre, this);
	}

	public TextFieldBase(final String testo, final Container contenitore) {
		super(testo);
		this.contenitorePadre = contenitore;
		init(contenitorePadre, this);
	}

	public TextFieldBase(final Container contenitore) {
		super();
		this.contenitorePadre = contenitore;
		init(contenitorePadre, this);
	}

	@Override
	public void init(final Container contenitorePadre2, final Component componenteFiglio) {
		componenteBase.init(contenitorePadre2, componenteFiglio);
		this.addFocusListener(this);
		this.settaStile();
		int width = getLarghezzaSingleStringa(contenitorePadre2.getGraphics(), this.getText());
		int height = getAltezzaSingleStringa(contenitorePadre2.getGraphics());
		setSize(width, height);
	}

	@Override
	public void settaStile() {
		style = settaStileOverride() != null ? settaStileOverride() : style;
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
	public boolean repaintCustomizzato(final Object[] parametri) {
		return componenteBase.repaintCustomizzato(parametri);
	}

	@Override
	public void focusGained(final FocusEvent arg0) {
	}

	@Override
	public void focusLost(final FocusEvent arg0) {
		if (this.getTestoConvertitoInTipo() == null) {
			;
		}
	}

	public class StyleBaseTF extends StyleBase {

	}

	public Container getContenitorePadre() {
		return contenitorePadre;
	}

	public void setContenitorePadre(final Container contenitorePadre) {
		this.contenitorePadre = contenitorePadre;
	}

	public ComponenteBase getComponenteBase() {
		return componenteBase;
	}

	@Override
	public boolean posizionaADestraDi(final Component componenteParagone, final int distanzaOrizzantale,
			final int distanzaVerticale) {
		return componenteBase.aDestraDi(componenteParagone, distanzaOrizzantale, distanzaVerticale, this);

	}

	@Override
	public boolean posizionaASinistraDi(final Component componenteParagone, final int distanzaOrizzontale,
			final int distanzaVerticale) {
		return componenteBase.aSinistraDi(componenteParagone, distanzaOrizzontale, distanzaVerticale, this);
	}

	@Override
	public boolean posizionaSottoA(final Component componenteParagone, final int distanzaOrizzantale,
			final int distanzaVerticale) {
		return componenteBase.sottoA(componenteParagone, distanzaOrizzantale, distanzaVerticale, this);
	}

	@Override
	public boolean posizionaSopraA(final Component componenteParagone, final int distanzaOrizzantale,
			final int distanzaVerticale) {
		return componenteBase.sopraA(componenteParagone, distanzaOrizzantale, distanzaVerticale, this);
	}

	@Override
	public int getLarghezzaSingleStringa(final Graphics g, final String label) {
		return componenteBase.getLarghezzaSingleStringa(g, label, this);
	}

	@Override
	public int getAltezzaSingleStringa(final Graphics g) {
		return componenteBase.getAltezzaSingleStringa(g, this);
	}
}
