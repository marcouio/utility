package main.java.com.molinari.utility.graphic.component.textarea;

import main.java.com.molinari.utility.graphic.component.base.ComponenteBase;
import main.java.com.molinari.utility.graphic.component.base.IComponenteBase;
import main.java.com.molinari.utility.graphic.component.style.StyleBase;
import main.java.com.molinari.utility.graphic.component.style.StyleTextArea;

import java.awt.Component;
import java.awt.Container;

import javax.swing.JTextArea;
import javax.swing.text.Document;

public class TextAreaBase extends JTextArea implements IComponenteBase {

	private static final long serialVersionUID = 1L;
	private Container contenitorePadre;
	private final ComponenteBase componenteBase = new ComponenteBase(this);

	public TextAreaBase(final Container contenitore) {
		super();
		makeGUI(contenitore);
		init(contenitore, this);
	}

	public TextAreaBase(final Document doc, final String text, final int rows, final int columns, final Container contenitore) {
		super(doc, text, rows, columns);
		makeGUI(contenitore);
		init(contenitore, this);
	}

	public TextAreaBase(final Document doc, final Container contenitore) {
		super(doc);
		makeGUI(contenitore);
		init(contenitore, this);
	}

	public TextAreaBase(final int rows, final int columns, final Container contenitore) {
		super(rows, columns);
		makeGUI(contenitore);
		init(contenitore, this);
	}

	public TextAreaBase(final String text, final int rows, final int columns, final Container contenitore) {
		super(text, rows, columns);
		makeGUI(contenitore);
		init(contenitore, this);
	}

	public TextAreaBase(final String text, final Container contenitore) {
		super(text);
		init(contenitore, this);
	}

	@Override
	public void init(final Container contenitorePadre2, final Component componenteFiglio) {
		contenitorePadre = contenitorePadre2;
		componenteBase.init(contenitorePadre2, componenteFiglio);
		applicaStile(new StyleTextArea(), this);
		this.setLineWrap(true);
		this.setWrapStyleWord(true);
		this.setAutoscrolls(true);
	}

	@Override
	public boolean repaintCustomizzato(final Object[] parametri) {
		return componenteBase.repaintCustomizzato(parametri, this);
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
	public void applicaStile(final StyleBase styleBase, IComponenteBase comp) {
		componenteBase.applicaStile(styleBase, comp);

		if (styleBase != null && styleBase instanceof StyleTextArea) {

			final StyleTextArea styleTextArea = (StyleTextArea) styleBase;
			this.setLineWrap(styleTextArea.isLineWrap());
			this.setWrapStyleWord(styleTextArea.isWrapStyleWord());
			this.setAutoscrolls(styleTextArea.isAutoscroll());
			this.setRows(styleTextArea.getRows());
			this.setColumns(styleTextArea.getColumns());
		}

	}

	@Override
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

	@Override
	public void makeGUI(Container contenitorePadre) {
		// TODO Auto-generated method stub
		
	}

}
