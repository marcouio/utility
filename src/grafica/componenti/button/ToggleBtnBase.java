package grafica.componenti.button;

import grafica.componenti.componenteBase.ComponenteBase;
import grafica.componenti.componenteBase.IComponenteBase;
import grafica.componenti.style.StyleBase;

import java.awt.Component;
import java.awt.Container;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ToggleBtnBase extends ToggleBtn implements IComponenteBase {

	public ToggleBtnBase(final ImageIcon icon, final Container contenitore) {
		super(icon);
		init(contenitore, this);
	}

	public ToggleBtnBase(final String text, final ImageIcon icon, final int xDistanzaBordoImmagine, final int xPartenzaTesto, final Container contenitore) {
		super(text, icon, xDistanzaBordoImmagine, xPartenzaTesto);
		init(contenitore, this);
	}

	public ToggleBtnBase(final String text, final ImageIcon icon, final JPanel padre, final Container contenitore) {
		super(text, icon, padre);
		init(contenitore, this);
	}

	public ToggleBtnBase(final String text, final ImageIcon icon, final Container contenitore) {
		super(text, icon);
		init(contenitore, this);
	}

	public ToggleBtnBase(final String text, final Container contenitore) {
		super(text);
		init(contenitore, this);
	}

	private static final long serialVersionUID = 1L;
	protected StyleBase style = new StyleBase();
	private final ComponenteBase componenteBase = new ComponenteBase();
	private Container contenitorePadre;

	@Override
	public void init(final Container contenitorePadre2, final Component componenteFiglio) {
		this.setContenitorePadre(contenitorePadre2);
		componenteBase.init(contenitorePadre2, componenteFiglio);
		this.settaStile();
		setSize(getLarghezza(), getAltezza());
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
	public void settaStile() {
		componenteBase.settaStile(style, this);
		if (settaStileOverride() != null) {
			style = settaStileOverride();
			componenteBase.settaStile(style, this);
		}
	}

	protected StyleBase settaStileOverride() {
		return new StyleBase("StyleBaseToggle");
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

	public Container getContenitorePadre() {
		return contenitorePadre;
	}

	public void setContenitorePadre(Container contenitorePadre) {
		this.contenitorePadre = contenitorePadre;
	}

}