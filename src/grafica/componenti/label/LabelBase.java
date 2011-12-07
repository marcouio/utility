package grafica.componenti.label;

import grafica.componenti.componenteBase.ComponenteBase;
import grafica.componenti.componenteBase.IComponenteBase;
import grafica.componenti.style.StyleBase;

import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.JLabel;

public class LabelBase extends JLabel implements IComponenteBase {

	private static final long serialVersionUID = 1L;
	protected StyleBase style = new StyleBase();
	private Container contenitorePadre;
	private final ComponenteBase componenteBase = new ComponenteBase();

	public LabelBase(final Icon image, final int horizontalAlignment, final Container contenitorePadre) {
		super(image, horizontalAlignment);
		this.contenitorePadre = contenitorePadre;
		init(contenitorePadre, this);
	}

	public LabelBase(final Icon image, final Container contenitorePadre) {
		super(image);
		this.contenitorePadre = contenitorePadre;
		init(contenitorePadre, this);
	}

	public LabelBase(final String text, final Icon icon, final int horizontalAlignment, final Container contenitorePadre) {
		super(text, icon, horizontalAlignment);
		this.contenitorePadre = contenitorePadre;
		init(contenitorePadre, this);
	}

	public LabelBase(final String text, final int horizontalAlignment, final Container contenitorePadre) {
		super(text, horizontalAlignment);
		this.contenitorePadre = contenitorePadre;
		init(contenitorePadre, this);
	}

	public LabelBase(final Container contenitorePadre) {
		super();
		this.contenitorePadre = contenitorePadre;
		init(contenitorePadre, this);
	}

	public LabelBase(final String string, final Container contenitorePadre) {
		super(string);
		this.contenitorePadre = contenitorePadre;
		init(contenitorePadre, this);
	}

	@Override
	public void settaStile() {
		componenteBase.settaStile(style, this);
		if (settaStileOverride() != null) {
			style = settaStileOverride();
			componenteBase.settaStile(style, this);
		}
	}

	protected StyleBase settaStileOverride() {
		return new StyleBase("StyleBaseL");
	}

	@Override
	public void init(final Container contenitorePadre2, final Component componenteFiglio) {
		componenteBase.init(contenitorePadre2, componenteFiglio);
		this.settaStile();
		int width = getLarghezzaSingleStringa(contenitorePadre2.getGraphics(), this.getText(), this);
		int height = getAltezzaSingleStringa(contenitorePadre2.getGraphics(), this);
		setSize(width, height);
	}

	@Override
	public boolean repaintCustomizzato(final Object[] parametri) {
		return componenteBase.repaintCustomizzato(parametri);
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

}
