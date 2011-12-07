package grafica.componenti.contenitori;

import grafica.componenti.ComponenteBase;
import grafica.componenti.IComponenteBase;
import grafica.componenti.style.StyleBase;

import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;

import javax.swing.JScrollPane;

public class ScrollPaneBase extends JScrollPane implements IComponenteBase, IContainerBase {

	public ScrollPaneBase(final Container contenitore) {
		super();
		this.contenitorePadre = contenitore;
		init(contenitore, this);
	}

	public ScrollPaneBase(final Component view, final int vsbPolicy, final int hsbPolicy, final Container contenitore) {
		super(view, vsbPolicy, hsbPolicy);
		this.contenitorePadre = contenitore;
		init(contenitore, this);
	}

	public ScrollPaneBase(final Component view, final Container contenitore) {
		super(view);
		this.contenitorePadre = contenitore;
		init(contenitore, this);
	}

	public ScrollPaneBase(final int vsbPolicy, final int hsbPolicy, final Container contenitore) {
		super(vsbPolicy, hsbPolicy);
		this.contenitorePadre = contenitore;
		init(contenitore, this);
	}

	private Container contenitorePadre;
	private final ContainerBase containerBase = new ContainerBase();

	protected StyleBase style = new StyleBase("StyleBaseScrollPane");
	private final ComponenteBase componenteBase = new ComponenteBase();
	private static final long serialVersionUID = 1L;

	@Override
	public int getMaxDimensionX() {
		return containerBase.getMaxDimensionX(this);
	}

	@Override
	public int getMaxDimensionY() {
		return containerBase.getMaxDimensionY(this);
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

	@Override
	public boolean posizionaADestraDi(final Component componenteParagone, final int distanzaOrizzantale, final int distanzaVerticale, final Component compDaPosizionare) {
		return componenteBase.posizionaADestraDi(componenteParagone, distanzaOrizzantale, distanzaVerticale, compDaPosizionare);
	}

	@Override
	public boolean posizionaASinistraDi(final Component componenteParagone, final int distanzaOrizzontale, final int distanzaVerticale, final Component compDaPosizionare) {
		return componenteBase.posizionaASinistraDi(componenteParagone, distanzaOrizzontale, distanzaVerticale, compDaPosizionare);
	}

	@Override
	public boolean posizionaSottoA(final Component componenteParagone, final int distanzaOrizzantale, final int distanzaVerticale, final Component compDaPosizionare) {
		return componenteBase.posizionaSottoA(componenteParagone, distanzaOrizzantale, distanzaVerticale, compDaPosizionare);
	}

	@Override
	public boolean posizionaSopraA(final Component componenteParagone, final int distanzaOrizzantale, final int distanzaVerticale, final Component compDaPosizionare) {
		return componenteBase.posizionaSopraA(componenteParagone, distanzaOrizzantale, distanzaVerticale, compDaPosizionare);
	}

	@Override
	public int getLarghezzaSingleStringa(final Graphics g, final String label, final Component compDaValutare) {
		return componenteBase.getLarghezzaSingleStringa(g, label, compDaValutare);
	}

	@Override
	public int getAltezzaSingleStringa(final Graphics g, final Component compDaValutare) {
		return componenteBase.getAltezzaSingleStringa(g, compDaValutare);
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
		return null;
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

	public Container getContenitorePadre() {
		return contenitorePadre;
	}

	public void setContenitorePadre(final Container contenitorePadre) {
		this.contenitorePadre = contenitorePadre;
	}

	@Override
	public ContainerBase getContainerBase() {
		return containerBase;
	}

}
