package grafica.componenti.checkbox;

import grafica.componenti.componenteBase.ComponenteBaseConPadreContenitore;
import grafica.componenti.componenteBase.IComponenteBase;
import grafica.componenti.style.StyleBase;

import java.awt.Component;
import java.awt.Container;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JCheckBox;

public class CheckBoxBase extends JCheckBox implements IComponenteBase {

	private static final long						serialVersionUID	= 1L;
	private Container								contenitorePadre;
	private final ComponenteBaseConPadreContenitore	componenteBase		= new ComponenteBaseConPadreContenitore(this);
	
	public CheckBoxBase(final Container contenitorePadre) {
		super();
		makeGUI(contenitorePadre);
		init(contenitorePadre, this);
	}

	public CheckBoxBase(Action a, final Container contenitorePadre) {
		super(a);
		makeGUI(contenitorePadre);
		init(contenitorePadre, this);
	}

	public CheckBoxBase(Icon icon, boolean selected, final Container contenitorePadre) {
		super(icon, selected);
		makeGUI(contenitorePadre);
		init(contenitorePadre, this);
	}

	public CheckBoxBase(Icon icon, final Container contenitorePadre) {
		super(icon);
		makeGUI(contenitorePadre);
		init(contenitorePadre, this);
	}

	public CheckBoxBase(String text, boolean selected, final Container contenitorePadre) {
		super(text, selected);
		makeGUI(contenitorePadre);
		init(contenitorePadre, this);
	}

	public CheckBoxBase(String text, Icon icon, boolean selected, final Container contenitorePadre) {
		super(text, icon, selected);
		makeGUI(contenitorePadre);
		init(contenitorePadre, this);
	}

	public CheckBoxBase(String text, Icon icon, final Container contenitorePadre) {
		super(text, icon);
		makeGUI(contenitorePadre);
		init(contenitorePadre, this);
	}

	public CheckBoxBase(String text,final Container contenitorePadre) {
		super(text);
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
		return componenteBase.getLarghezzaSingleStringa(getGraphics(), getText(), this);
	}

	@Override
	public int getAltezza() {
		return componenteBase.getAltezzaSingleStringa(getGraphics(), this);
	}

	@Override
	public void makeGUI(Container contenitorePadre) {
		// TODO Auto-generated method stub

	}

}
