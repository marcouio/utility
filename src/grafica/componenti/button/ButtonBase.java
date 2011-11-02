package grafica.componenti.button;

import grafica.componenti.ComponenteBase;
import grafica.componenti.IComponenteBase;
import grafica.componenti.StyleBase;

import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;

public abstract class ButtonBase extends JButton implements IComponenteBase {

	public ButtonBase(final Container contenitore) {
		super();
		this.contenitorePadre = contenitore;
		init(contenitorePadre, this);
	}

	public ButtonBase(final Action a, final Container contenitore) {
		super(a);
		this.contenitorePadre = contenitore;
		init(contenitorePadre, this);
	}

	public ButtonBase(final Icon icon, final Container contenitore) {
		super(icon);
		this.contenitorePadre = contenitore;
		init(contenitorePadre, this);
	}

	public ButtonBase(final String text, final Icon icon, final Container contenitore) {
		super(text, icon);
		this.contenitorePadre = contenitore;
		init(contenitorePadre, this);
	}

	public ButtonBase(final String text, final Container contenitore) {
		super(text);
		this.contenitorePadre = contenitore;
		init(contenitorePadre, this);
	}

	private static final long serialVersionUID = 1L;
	protected StyleBase style = new StyleBase();
	private final ComponenteBase componenteBase = new ComponenteBase();
	private final Container contenitorePadre;

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

	@Override
	public boolean posizionaADestraDi(final Component componenteParagone, final int distanzaOrizzantale,
			final int distanzaVerticale, final Component compDaPosizionare) {
		return componenteBase.posizionaADestraDi(componenteParagone, distanzaOrizzantale, distanzaVerticale,
				compDaPosizionare);
	}

	@Override
	public boolean posizionaASinistraDi(final Component componenteParagone, final int distanzaOrizzontale,
			final int distanzaVerticale, final Component compDaPosizionare) {
		return componenteBase.posizionaASinistraDi(componenteParagone, distanzaOrizzontale, distanzaVerticale,
				compDaPosizionare);
	}

	@Override
	public boolean posizionaSottoA(final Component componenteParagone, final int distanzaOrizzantale,
			final int distanzaVerticale, final Component compDaPosizionare) {
		return componenteBase.posizionaSottoA(componenteParagone, distanzaOrizzantale, distanzaVerticale,
				compDaPosizionare);
	}

	@Override
	public boolean posizionaSopraA(final Component componenteParagone, final int distanzaOrizzantale,
			final int distanzaVerticale, final Component compDaPosizionare) {
		return componenteBase.posizionaSopraA(componenteParagone, distanzaOrizzantale, distanzaVerticale,
				compDaPosizionare);
	}

	@Override
	public int getLarghezzaSingleStringa(final Graphics g, final String label, final Component compDaValutare) {
		return componenteBase.getLarghezzaSingleStringa(g, label, compDaValutare);
	}

	@Override
	public int getAltezzaSingleStringa(final Graphics g, final Component compDaValutare) {
		return componenteBase.getAltezzaSingleStringa(g, compDaValutare);
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
	public void settaStile() {
		style = settaStileOverride() != null ? settaStileOverride() : style;
		style.setPadre(this);
		this.setFont(style.getFont());
		this.setForeground(style.getForeground());
		this.setBackground(style.getBackground());
	}

	protected StyleBase settaStileOverride() {
		return new StyleBase("StyleBaseB");
	}

}
