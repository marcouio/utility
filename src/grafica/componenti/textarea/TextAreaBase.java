package grafica.componenti.textarea;

import grafica.componenti.componenteBase.ComponenteBase;
import grafica.componenti.componenteBase.IComponenteBase;
import grafica.componenti.style.StyleBase;
import grafica.componenti.style.StyleTextArea;

import java.awt.Component;
import java.awt.Container;

import javax.swing.JTextArea;
import javax.swing.text.Document;

public class TextAreaBase extends JTextArea implements IComponenteBase {

	private static final long serialVersionUID = 1L;
	protected StyleBase style = new StyleTextArea();
	private Container contenitorePadre;
	private final ComponenteBase componenteBase = new ComponenteBase();

	public TextAreaBase(final Container contenitore) {
		super();
		init(contenitore, this);
	}

	public TextAreaBase(final Document doc, final String text, final int rows, final int columns, final Container contenitore) {
		super(doc, text, rows, columns);
		init(contenitore, this);
	}

	public TextAreaBase(final Document doc, final Container contenitore) {
		super(doc);
		init(contenitore, this);
	}

	public TextAreaBase(final int rows, final int columns, final Container contenitore) {
		super(rows, columns);
		init(contenitore, this);
	}

	public TextAreaBase(final String text, final int rows, final int columns, final Container contenitore) {
		super(text, rows, columns);
		init(contenitore, this);
	}

	public TextAreaBase(final String text, final Container contenitore) {
		super(text);
		init(contenitore, this);
	}

	@Override
	public void init(final Container contenitorePadre2, final Component componenteFiglio) {
		this.contenitorePadre = contenitorePadre2;
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
	public boolean posizionaADestraDi(final Component componenteParagone, final int distanzaOrizzantale, final int distanzaVerticale) {
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
	public boolean posizionaASinistraDi(final Component componenteParagone, final int distanzaOrizzantale, final int distanzaVerticale) {
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
	public boolean posizionaSottoA(final Component componenteParagone, final int distanzaOrizzantale, final int distanzaVerticale) {
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
	public void settaStile() {
		StyleTextArea styleTextArea = (StyleTextArea) style;
		componenteBase.settaStile(style, this);
		if (settaStileOverride() != null) {
			style = settaStileOverride();
			componenteBase.settaStile(styleTextArea, this);
		}
		this.setLineWrap(styleTextArea.isLineWrap());
		this.setWrapStyleWord(styleTextArea.isWrapStyleWord());
		this.setAutoscrolls(styleTextArea.isAutoscroll());
		this.setRows(styleTextArea.getRows());
		this.setColumns(styleTextArea.getColumns());
	}

	protected StyleBase settaStileOverride() {
		return null;
		//		return new StyleTextArea("StyleBaseTA");
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

	@Override
	public int getLarghezza() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getAltezza() {
		// TODO Auto-generated method stub
		return 0;
	}

}
