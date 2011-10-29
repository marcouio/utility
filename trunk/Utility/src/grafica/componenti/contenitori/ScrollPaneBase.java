package grafica.componenti.contenitori;

import grafica.componenti.ComponenteBase;
import grafica.componenti.IComponenteBase;
import grafica.componenti.StyleBase;

import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;

import javax.swing.JScrollPane;

public abstract class ScrollPaneBase extends JScrollPane implements IComponenteBase,
		IContainerBase {
	

	public ScrollPaneBase(final Container contenitore) {
		super();
		this.contenitorePadre = contenitore;
		init(contenitore, this);
	}

	public ScrollPaneBase(Component view, int vsbPolicy, int hsbPolicy, final Container contenitore) {
		super(view, vsbPolicy, hsbPolicy);
		this.contenitorePadre = contenitore;
		init(contenitore, this);
	}

	public ScrollPaneBase(Component view, final Container contenitore) {
		super(view);
		this.contenitorePadre = contenitore;
		init(contenitore, this);
	}

	public ScrollPaneBase(int vsbPolicy, int hsbPolicy, final Container contenitore) {
		super(vsbPolicy, hsbPolicy);
		this.contenitorePadre = contenitore;
		init(contenitore, this);
	}

	public class StyleBaseScrollPane extends StyleBase {

	}

	private Container contenitorePadre;
	private final ContainerBase containerBase = new ContainerBase();

	protected StyleBase style = new StyleBaseScrollPane();
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
	public void init(Container contenitorePadre2, Component componenteFiglio) {
		componenteBase.init(contenitorePadre2, componenteFiglio);
		settaStile();
	}

	@Override
	public boolean repaintCustomizzato(Object[] parametri) {
		return componenteBase.repaintCustomizzato(parametri);
	}

	@Override
	public boolean posizionaADestraDi(Component componenteParagone,
			int distanzaOrizzantale, int distanzaVerticale,
			Component compDaPosizionare) {
		return componenteBase.posizionaADestraDi(componenteParagone, distanzaOrizzantale, distanzaVerticale, compDaPosizionare);
	}

	@Override
	public boolean posizionaASinistraDi(Component componenteParagone,
			int distanzaOrizzontale, int distanzaVerticale,
			Component compDaPosizionare) {
		return componenteBase.posizionaASinistraDi(componenteParagone, distanzaOrizzontale, distanzaVerticale, compDaPosizionare);
	}

	@Override
	public boolean posizionaSottoA(Component componenteParagone,
			int distanzaOrizzantale, int distanzaVerticale,
			Component compDaPosizionare) {
		return componenteBase.posizionaSottoA(componenteParagone, distanzaOrizzantale, distanzaVerticale, compDaPosizionare);
	}

	@Override
	public boolean posizionaSopraA(Component componenteParagone,
			int distanzaOrizzantale, int distanzaVerticale,
			Component compDaPosizionare) {
		return componenteBase.posizionaSopraA(componenteParagone, distanzaOrizzantale, distanzaVerticale, compDaPosizionare);
	}

	@Override
	public int getLarghezzaSingleStringa(Graphics g, String label,
			Component compDaValutare) {
		return componenteBase.getLarghezzaSingleStringa(g, label, compDaValutare);
	}

	@Override
	public int getAltezzaSingleStringa(Graphics g, Component compDaValutare) {
		return componenteBase.getAltezzaSingleStringa(g, compDaValutare);
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
	
	public Container getContenitorePadre() {
		return contenitorePadre;
	}

	public void setContenitorePadre(Container contenitorePadre) {
		this.contenitorePadre = contenitorePadre;
	}

	public ContainerBase getContainerBase() {
		return containerBase;
	}

	public ComponenteBase getComponenteBase() {
		return componenteBase;
	}


}
