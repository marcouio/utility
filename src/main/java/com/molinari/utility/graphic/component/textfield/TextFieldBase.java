package main.java.com.molinari.utility.graphic.component.textfield;

import main.java.com.molinari.utility.graphic.UtilComponenti;
import main.java.com.molinari.utility.graphic.component.base.ComponenteBase;
import main.java.com.molinari.utility.graphic.component.base.IComponenteBase;
import main.java.com.molinari.utility.graphic.component.style.StyleBase;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class TextFieldBase extends JTextField implements FocusListener, IComponenteBase {

	private static final long serialVersionUID = 1L;
	protected IFormatterTF formatter;
	private Container contenitorePadre;
	private final ComponenteBase componenteBase = new ComponenteBase(this);

	public static void main(final String[] args) {
		final JPanel p = UtilComponenti.initContenitoreFrame(null);
		final TextFieldBase tfb = new TextFieldBase("dd-MM-yyyy", p);
		System.out.println(tfb.getBackground());
		System.out.println(tfb.getWidth());
		System.out.println(tfb.getHeight());
	}

	public TextFieldBase(final String testo, final IFormatterTF formatter, final Container contenitore) {
		super(testo);
		this.formatter = formatter;
		makeGUI(contenitore);
		init(contenitore, this);
	}

	public TextFieldBase(final String testo, final Container contenitore) {
		super(testo);
		makeGUI(contenitore);
		init(contenitore, this);
	}

	public TextFieldBase(final Container contenitore) {
		super();
		makeGUI(contenitore);
		init(contenitore, this);
	}

	@Override
	public void init(final Container contenitorePadre2, final Component componenteFiglio) {
		contenitorePadre = contenitorePadre2;
		componenteBase.init(contenitorePadre2, componenteFiglio);
		this.addFocusListener(this);
		this.applicaStile(new StyleBase("StyleBaseTF"), this);
	}

	@Override
	public void applicaStile(final StyleBase styleBase, IComponenteBase comp) {
		componenteBase.applicaStile(styleBase, comp);
	}

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
		return componenteBase.repaintCustomizzato(parametri, this);
	}

	@Override
	public void focusGained(final FocusEvent arg0) {
	}

	@Override
	public void focusLost(final FocusEvent arg0) {
		if (this.getTestoConvertitoInTipo() == null) {
		}
	}

	@Override
	public Container getContenitorePadre() {
		return contenitorePadre;
	}

	public void setContenitorePadre(final Container contenitorePadre) {
		this.contenitorePadre = contenitorePadre;
	}

	/**
	 * Metodo facade di metodo omonimo per facilitarne l'accesso e la
	 * leggibilita'
	 * 
	 * @param componenteParagone
	 * @param distanzaOrizzantale
	 * @param distanzaVerticale
	 * @return
	 */
	public boolean posizionaADestraDi(final Component componenteParagone, final int distanzaOrizzantale, final int distanzaVerticale) {
		return posizionaADestraDi(componenteParagone, distanzaOrizzantale, distanzaVerticale, this);
	}

	/**
	 * Metodo facade di metodo omonimo per facilitarne l'accesso e la
	 * leggibilita'
	 * 
	 * @param componenteParagone
	 * @param distanzaOrizzantale
	 * @param distanzaVerticale
	 * @return
	 */
	public boolean posizionaASinistraDi(final Component componenteParagone, final int distanzaOrizzantale, final int distanzaVerticale) {
		return posizionaASinistraDi(componenteParagone, distanzaOrizzantale, distanzaVerticale, this);
	}

	/**
	 * Metodo facade di metodo omonimo per facilitarne l'accesso e la
	 * leggibilita'
	 * 
	 * @param componenteParagone
	 * @param distanzaOrizzantale
	 * @param distanzaVerticale
	 * @return
	 */
	public boolean posizionaSottoA(final Component componenteParagone, final int distanzaOrizzantale, final int distanzaVerticale) {
		return posizionaSottoA(componenteParagone, distanzaOrizzantale, distanzaVerticale, this);
	}

	/**
	 * Metodo facade di metodo omonimo per facilitarne l'accesso e la
	 * leggibilita'
	 * 
	 * @param componenteParagone
	 * @param distanzaOrizzantale
	 * @param distanzaVerticale
	 * @return
	 */
	public boolean posizionaSopraA(final Component componenteParagone, final int distanzaOrizzantale, final int distanzaVerticale) {
		return posizionaSopraA(componenteParagone, distanzaOrizzantale, distanzaVerticale, this);
	}

	@Override
	public boolean posizionaADestraDi(final Component componenteParagone, final int distanzaOrizzantale, final int distanzaVerticale, final Component componenteDaRiposizionare) {
		return componenteBase.posizionaADestraDi(componenteParagone, distanzaOrizzantale, distanzaVerticale, componenteDaRiposizionare);
	}

	@Override
	public boolean posizionaASinistraDi(final Component componenteParagone, final int distanzaOrizzontale, final int distanzaVerticale, final Component componenteDaRiposizionare) {
		return componenteBase.posizionaASinistraDi(componenteParagone, distanzaOrizzontale, distanzaVerticale, componenteDaRiposizionare);
	}

	@Override
	public boolean posizionaSottoA(final Component componenteParagone, final int distanzaOrizzantale, final int distanzaVerticale, final Component componenteDaRiposizionare) {
		return componenteBase.posizionaSottoA(componenteParagone, distanzaOrizzantale, distanzaVerticale, componenteDaRiposizionare);
	}

	@Override
	public boolean posizionaSopraA(final Component componenteParagone, final int distanzaOrizzantale, final int distanzaVerticale, final Component componenteDaRiposizionare) {
		componenteBase.posizionaSopraA(componenteParagone, distanzaOrizzantale, distanzaVerticale, componenteDaRiposizionare);
		return false;
	}

	@Override
	public int getLarghezza() {
		return componenteBase.getLarghezzaSingleStringa(getGraphics(), this.getText(), this);
	}

	@Override
	public int getAltezza() {
		return componenteBase.getAltezzaSingleStringa(getGraphics(), this);
	}

	@Override
	public void makeGUI(Container contenitorePadre) {
		// TODO Auto-generated method stub
		
	}
}
