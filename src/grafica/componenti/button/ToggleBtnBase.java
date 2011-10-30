package grafica.componenti.button;

import grafica.componenti.ComponenteBase;
import grafica.componenti.IComponenteBase;
import grafica.componenti.StyleBase;
import grafica.componenti.ToggleBtn;

import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public abstract class ToggleBtnBase extends ToggleBtn implements IComponenteBase {

	public class StyleBaseB extends StyleBase {

	}
	

	public ToggleBtnBase(ImageIcon icon, final Container contenitore) {
		super(icon);
		this.contenitorePadre = contenitore;
		init(contenitorePadre, this);
	}

	public ToggleBtnBase(String text, ImageIcon icon,
			int xDistanzaBordoImmagine, int xPartenzaTesto, final Container contenitore) {
		super(text, icon, xDistanzaBordoImmagine, xPartenzaTesto);
		this.contenitorePadre = contenitore;
		init(contenitorePadre, this);
	}

	public ToggleBtnBase(String text, ImageIcon icon, JPanel padre, final Container contenitore) {
		super(text, icon, padre);
		this.contenitorePadre = contenitore;
		init(contenitorePadre, this);
	}

	public ToggleBtnBase(String text, ImageIcon icon, final Container contenitore) {
		super(text, icon);
		this.contenitorePadre = contenitore;
		init(contenitorePadre, this);
	}

	public ToggleBtnBase(String text,final Container contenitore) {
		super(text);
		this.contenitorePadre = contenitore;
		init(contenitorePadre, this);
	}

	private static final long serialVersionUID = 1L;
	protected StyleBase style = new StyleBaseB();
	private final ComponenteBase componenteBase = new ComponenteBase();
	private Container contenitorePadre;

	@Override
	public void init(Container contenitorePadre2, Component componenteFiglio){
		componenteBase.init(contenitorePadre2, componenteFiglio);
		int width = getLarghezzaSingleStringa(contenitorePadre2.getGraphics(), this.getText(), this);
		int height = getAltezzaSingleStringa(contenitorePadre2.getGraphics(), this);
		setSize(width, height);
		this.settaStile();
	}

	@Override
	public boolean repaintCustomizzato(Object[] parametri){
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

	protected abstract StyleBase settaStileOverride();

}
