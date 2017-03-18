package main.java.com.molinari.utility.graphic.component.label;

import main.java.com.molinari.utility.graphic.component.base.ComponenteBaseConPadreContenitore;
import main.java.com.molinari.utility.graphic.component.base.IComponenteBase;
import main.java.com.molinari.utility.graphic.component.container.base.ContainerBase;
import main.java.com.molinari.utility.graphic.component.container.base.IContainerBase;
import main.java.com.molinari.utility.graphic.component.style.StyleBase;

import java.awt.Component;
import java.awt.Container;

import javax.swing.Icon;
import javax.swing.JLabel;

public class LabelBase extends JLabel implements IComponenteBase, IContainerBase {

	private static final long serialVersionUID = 1L;
	private Container contenitorePadre;
	private final ContainerBase containerBase = new ContainerBase();
	private final ComponenteBaseConPadreContenitore componenteBase = new ComponenteBaseConPadreContenitore(this);

	public LabelBase(final Icon image, final int horizontalAlignment, final Container contenitorePadre) {
		super(image, horizontalAlignment);
		makeGUI(contenitorePadre);
		init(contenitorePadre, this);
	}

	public LabelBase(final Icon image, final Container contenitorePadre) {
		super(image);
		makeGUI(contenitorePadre);
		init(contenitorePadre, this);
	}

	public LabelBase(final String text, final Icon icon, final int horizontalAlignment, final Container contenitorePadre) {
		super(text, icon, horizontalAlignment);
		makeGUI(contenitorePadre);
		init(contenitorePadre, this);
	}

	public LabelBase(final String text, final int horizontalAlignment, final Container contenitorePadre) {
		super(text, horizontalAlignment);
		makeGUI(contenitorePadre);
		init(contenitorePadre, this);
	}

	public LabelBase(final Container contenitorePadre) {
		super();
		makeGUI(contenitorePadre);
		init(contenitorePadre, this);
	}

	public LabelBase(final String string, final Container contenitorePadre) {
		super(string);
		makeGUI(contenitorePadre);
		init(contenitorePadre, this);
	}
	
	@Override
	public void applicaStile(StyleBase style, IComponenteBase padre) {
		componenteBase.applicaStile(style, padre);
	}
	@Override
	public void init(final Container contenitorePadre2, final Component componenteFiglio) {
		contenitorePadre = contenitorePadre2;
		componenteBase.init(contenitorePadre2, componenteFiglio);
		this.applicaStile(new StyleBase("StyleBaseL"), this);
		setSize(getLarghezza(), getAltezza());
	}

	@Override
	public boolean repaintCustomizzato(final Object[] parametri) {
		return componenteBase.repaintCustomizzato(parametri, this);
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
		final int larghezzaTesto = componenteBase.getLarghezzaSingleStringa(getGraphics(), getText(), this);
		return getMaxDimensionX() + larghezzaTesto;
	}

	@Override
	public int getAltezza() {
		final int altezzaTesto = componenteBase.getAltezzaSingleStringa(getGraphics(), this);
		return getMaxDimensionY() + altezzaTesto;
	}

	@Override
	public ContainerBase getContainerBase() {
		return containerBase;
	}

	@Override
	public int getMaxDimensionX() {
		return containerBase.getMaxDimensionX(this);
	}

	@Override
	public int getMaxDimensionY() {
		return containerBase.getMaxDimensionY(this);
	}

	@Override
	public void makeGUI(Container contenitorePadre) {
		// TODO Auto-generated method stub
		
	}

}
