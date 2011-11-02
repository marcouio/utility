package grafica.componenti.textarea;

import grafica.componenti.ComponenteBase;
import grafica.componenti.ExceptionGraphics;
import grafica.componenti.IComponenteBase;
import grafica.componenti.StyleBase;

import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;

import javax.swing.JTextArea;
import javax.swing.text.Document;

public abstract class TextAreaBase extends JTextArea implements IComponenteBase {

	private static final long serialVersionUID = 1L;
	protected StyleBase style = new StyleBase();
	private Container contenitorePadre;
	private final ComponenteBase componenteBase = new ComponenteBase();

	public TextAreaBase(final Container contenitore) throws ExceptionGraphics {
		super();
		this.contenitorePadre = contenitore;
		init(contenitorePadre, this);
	}

	public TextAreaBase(final Document doc, final String text, final int rows, final int columns,
			final Container contenitore) throws ExceptionGraphics {
		super(doc, text, rows, columns);
		this.contenitorePadre = contenitore;
		init(contenitorePadre, this);
	}

	public TextAreaBase(final Document doc, final Container contenitore) throws ExceptionGraphics {
		super(doc);
		this.contenitorePadre = contenitore;
		init(contenitorePadre, this);
	}

	public TextAreaBase(final int rows, final int columns, final Container contenitore) throws ExceptionGraphics {
		super(rows, columns);
		this.contenitorePadre = contenitore;
		init(contenitorePadre, this);
	}

	public TextAreaBase(final String text, final int rows, final int columns, final Container contenitore) {
		super(text, rows, columns);
		this.contenitorePadre = contenitore;
	}

	public TextAreaBase(final String text, final Container contenitore) throws ExceptionGraphics {
		super(text);
		this.contenitorePadre = contenitore;
		init(contenitorePadre, this);
	}

	@Override
	public void init(final Container contenitorePadre2, final Component componenteFiglio) {
		componenteBase.init(contenitorePadre2, componenteFiglio);
		settaStile();
	}

	@Override
	public boolean repaintCustomizzato(final Object[] parametri) {
		return componenteBase.repaintCustomizzato(parametri);
	}

	/**
	 * Metodo facade di metodo omonimo per facilitarne l'accesso e la leggibilita'
	 * 
	 * @param componenteParagone
	 * @param distanzaOrizzantale
	 * @param distanzaVerticale
	 * @return
	 */
	public boolean posizionaADestraDi(final Component componenteParagone, final int distanzaOrizzantale,
			final int distanzaVerticale) {
		return posizionaADestraDi(componenteParagone, distanzaOrizzantale, distanzaVerticale, this);
	}

	/**
	 * Metodo facade di metodo omonimo per facilitarne l'accesso e la leggibilita'
	 * 
	 * @param componenteParagone
	 * @param distanzaOrizzantale
	 * @param distanzaVerticale
	 * @return
	 */
	public boolean posizionaASinistraDi(final Component componenteParagone, final int distanzaOrizzantale,
			final int distanzaVerticale) {
		return posizionaASinistraDi(componenteParagone, distanzaOrizzantale, distanzaVerticale, this);
	}

	/**
	 * Metodo facade di metodo omonimo per facilitarne l'accesso e la leggibilita'
	 * 
	 * @param componenteParagone
	 * @param distanzaOrizzantale
	 * @param distanzaVerticale
	 * @return
	 */
	public boolean posizionaSottoA(final Component componenteParagone, final int distanzaOrizzantale,
			final int distanzaVerticale) {
		return posizionaSottoA(componenteParagone, distanzaOrizzantale, distanzaVerticale, this);
	}

	/**
	 * Metodo facade di metodo omonimo per facilitarne l'accesso e la leggibilita'
	 * 
	 * @param componenteParagone
	 * @param distanzaOrizzantale
	 * @param distanzaVerticale
	 * @return
	 */
	public boolean posizionaSopraA(final Component componenteParagone, final int distanzaOrizzantale,
			final int distanzaVerticale) {
		return posizionaSopraA(componenteParagone, distanzaOrizzantale, distanzaVerticale, this);
	}

	@Override
	public boolean posizionaADestraDi(final Component componenteParagone, final int distanzaOrizzantale,
			final int distanzaVerticale, final Component componenteDaRiposizionare) {
		return componenteBase.posizionaADestraDi(componenteParagone, distanzaOrizzantale, distanzaVerticale,
				componenteDaRiposizionare);
	}

	@Override
	public boolean posizionaASinistraDi(final Component componenteParagone, final int distanzaOrizzontale,
			final int distanzaVerticale, final Component componenteDaRiposizionare) {
		return componenteBase.posizionaASinistraDi(componenteParagone, distanzaOrizzontale, distanzaVerticale,
				componenteDaRiposizionare);
	}

	@Override
	public boolean posizionaSottoA(final Component componenteParagone, final int distanzaOrizzantale,
			final int distanzaVerticale, final Component componenteDaRiposizionare) {
		return componenteBase.posizionaSottoA(componenteParagone, distanzaOrizzantale, distanzaVerticale,
				componenteDaRiposizionare);
	}

	@Override
	public boolean posizionaSopraA(final Component componenteParagone, final int distanzaOrizzantale,
			final int distanzaVerticale, final Component componenteDaRiposizionare) {
		componenteBase.posizionaSopraA(componenteParagone, distanzaOrizzantale, distanzaVerticale,
				componenteDaRiposizionare);
		return false;
	}

	/**
	 * Metodo facade di metodo omonimo per facilitarne l'accesso e la leggibilita'
	 * 
	 * @param g
	 * @param label
	 * @return
	 */
	public int getLarghezzaSingleStringa(final Graphics g, final String label) {
		return getLarghezzaSingleStringa(g, label, this);
	}

	/**
	 * Metodo facade di metodo omonimo per facilitarne l'accesso e la leggibilita'
	 * 
	 * @param g
	 * @return
	 */
	public int getAltezzaSingleStringa(final Graphics g) {
		return getAltezzaSingleStringa(g, this);
	}

	@Override
	public int getLarghezzaSingleStringa(final Graphics g, final String label, final Component componenteDaRiposizionare) {
		return componenteBase.getLarghezzaSingleStringa(g, label, componenteDaRiposizionare);
	}

	@Override
	public int getAltezzaSingleStringa(final Graphics g, final Component componenteDaRiposizionare) {
		return componenteBase.getAltezzaSingleStringa(g, componenteDaRiposizionare);
	}

	@Override
	public void settaStile() {
		style = settaStileOverride() != null ? settaStileOverride() : style;
		style.setPadre(this);
		this.setFont(style.getFont());
		this.setForeground(style.getForeground());
		this.setBackground(style.getBackground());
	}

	protected StyleBase settaStileOverride() {
		return new StyleBase("StyleBaseTA");
	}

	public StyleBase getStyle() {
		return style;
	}

	public void setStyle(final StyleBase style) {
		this.style = style;
	}

	public Container getContenitorePadre() {
		return contenitorePadre;
	}

	public void setContenitorePadre(final Container contenitorePadre) {
		this.contenitorePadre = contenitorePadre;
	}

}
