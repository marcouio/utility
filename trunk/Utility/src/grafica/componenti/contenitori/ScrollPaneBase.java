package grafica.componenti.contenitori;

import grafica.componenti.componenteBase.ComponenteBase;
import grafica.componenti.componenteBase.IComponenteBase;
import grafica.componenti.contenitori.contenitoreBase.ContainerBase;
import grafica.componenti.contenitori.contenitoreBase.ContainerBaseScrollPane;
import grafica.componenti.contenitori.contenitoreBase.IContainerBase;
import grafica.componenti.style.StyleBase;

import java.awt.Component;
import java.awt.Container;

import javax.swing.JScrollPane;

public class ScrollPaneBase extends JScrollPane implements IComponenteBase, IContainerBase {

	public ScrollPaneBase(final Container contenitore) {
		super();
		init(contenitore, this);
	}

	public ScrollPaneBase(final Component view, final int vsbPolicy, final int hsbPolicy, final Container contenitore) {
		super(view, vsbPolicy, hsbPolicy);
		init(contenitore, this);
	}

	public ScrollPaneBase(final Component view, final Container contenitore) {
		super(view);
		init(contenitore, this);
	}

	public ScrollPaneBase(final int vsbPolicy, final int hsbPolicy, final Container contenitore) {
		super(vsbPolicy, hsbPolicy);
		init(contenitore, this);
	}

	private Container contenitorePadre;
	private final ContainerBaseScrollPane containerBase = new ContainerBaseScrollPane();

	protected StyleBase style = new StyleBase("StyleBaseScrollPane");
	private final ComponenteBase componenteBase = new ComponenteBase(this);
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
		this.contenitorePadre = contenitorePadre2;
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

	@Override
	public int getLarghezza() {
		return getMaxDimensionX();
	}

	@Override
	public int getAltezza() {
		return getMaxDimensionY();
	}

}
