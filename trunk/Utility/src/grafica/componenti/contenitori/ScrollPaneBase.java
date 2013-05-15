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
		makeGUI(contenitore);
		init(contenitore, this);
	}

	public ScrollPaneBase(final Component view, final int vsbPolicy, final int hsbPolicy, final Container contenitore) {
		super(view, vsbPolicy, hsbPolicy);
		makeGUI(contenitore);
		init(contenitore, this);
	}

	public ScrollPaneBase(final Component view, final Container contenitore) {
		super(view);
		makeGUI(contenitore);
		init(contenitore, this);
	}

	public ScrollPaneBase(final int vsbPolicy, final int hsbPolicy, final Container contenitore) {
		super(vsbPolicy, hsbPolicy);
		makeGUI(contenitore);
		init(contenitore, this);
	}

	private Container contenitorePadre;
	private final ContainerBaseScrollPane containerBase = new ContainerBaseScrollPane();

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
		contenitorePadre = contenitorePadre2;
		componenteBase.init(contenitorePadre2, componenteFiglio);
		setStile(new StyleBase("StyleBaseScrollPane"));
	}

	@Override
	public boolean repaintCustomizzato(final Object[] parametri) {
		if(componenteBase.repaintCustomizzato(parametri,this)){
			if (parametri[IComponenteBase.PARAM_REPAINT_OBJ_CONTENT] == null) {
				return false;
			}
			final Component comp = (Component) parametri[IComponenteBase.PARAM_REPAINT_OBJ_CONTENT];
			setViewportView(comp);
			componenteBase.ridisegna(this);
			return true;
		}
		return false;
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
	public void setStile(final StyleBase styleBase) {
		componenteBase.settaStile(styleBase, this);
	}

	protected StyleBase settaStileOverride() {
		return null;
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

	@Override
	public void makeGUI(Container contenitorePadre) {
		// TODO Auto-generated method stub
		
	}

}
