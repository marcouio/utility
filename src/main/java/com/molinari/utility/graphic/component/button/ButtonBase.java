package main.java.com.molinari.utility.graphic.component.button;

import main.java.com.molinari.utility.graphic.UtilComponenti;
import main.java.com.molinari.utility.graphic.component.base.ComponenteBaseConPadreContenitore;
import main.java.com.molinari.utility.graphic.component.base.IComponenteBase;
import main.java.com.molinari.utility.graphic.component.container.base.ContainerBase;
import main.java.com.molinari.utility.graphic.component.container.base.ContainerBaseBottone;
import main.java.com.molinari.utility.graphic.component.container.base.IContainerBase;
import main.java.com.molinari.utility.graphic.component.style.StyleBase;

import java.awt.Component;
import java.awt.Container;
import java.awt.Insets;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ButtonBase extends JButton implements IComponenteBase, IContainerBase {

	public static final double COSTANTE_ALLARGA = 1.05;

	public static void main(final String[] args) {
		final JPanel p = UtilComponenti.initContenitoreFrame(null);
		final ButtonBase b = new ButtonBase("button", p);
		System.out.println(b.getFont().getSize());
	}

	public ButtonBase(final Container contenitore) {
		super();
		makeGUI(contenitore);
		init(contenitore, this);
	}

	public ButtonBase(final Action a, final Container contenitore) {
		super(a);
		makeGUI(contenitore);
		init(contenitore, this);
	}

	public ButtonBase(final Icon icon, final Container contenitore) {
		super(icon);
		makeGUI(contenitore);
		init(contenitore, this);
	}

	public ButtonBase(final String text, final Icon icon, final Container contenitore) {
		super(text, icon);
		makeGUI(contenitore);
		init(contenitore, this);
	}

	public ButtonBase(final String text, final Container contenitore) {
		super(text);
		makeGUI(contenitore);
		init(contenitore, this);
	}

	private static final long serialVersionUID = 1L;
	private final ComponenteBaseConPadreContenitore componenteBase = new ComponenteBaseConPadreContenitore(this);
	private final ContainerBaseBottone containerBase = new ContainerBaseBottone();
	private Container contenitorePadre;

	@Override
	public void init(final Container contenitorePadre2, final Component componenteFiglio) {
		this.setContenitorePadre(contenitorePadre2);
		componenteBase.init(contenitorePadre2, componenteFiglio);
		this.applicaStile(new StyleBase("StyleBaseB"), this);
		this.setBorderPainted(false);
		this.setMargin(new Insets(0, 0, 0, 0));
		setSize(getLarghezza(), getAltezza());
	}

	@Override
	public boolean repaintCustomizzato(final Object[] parametri) {
		return componenteBase.repaintCustomizzato(parametri,this);
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
	public void applicaStile(StyleBase style, IComponenteBase padre) {
		componenteBase.applicaStile(style, padre);
	}		

	@Override
	public int getLarghezza() {
		final int margineSinitro = this.getMargin().left;
		final int margineDestro = this.getMargin().right;
		final int larghezzaTesto = componenteBase.getLarghezzaSingleStringa(getGraphics(), getText(), this, false);
		int larghImage = 0;
		if (this.getIcon() != null) {
			larghImage = getIcon().getIconWidth();
		}
		return (int) (getMaxDimensionX() + margineSinitro + margineDestro + (larghezzaTesto * COSTANTE_ALLARGA) + larghImage);
	}

	@Override
	public int getAltezza() {
		final int margineAlto = this.getMargin().top;
		final int margineBasso = this.getMargin().bottom;
		final int altezzaTesto = componenteBase.getAltezzaSingleStringa(getGraphics(), this);
		int altezzaImage = 0;
		if (this.getIcon() != null) {
			altezzaImage = getIcon().getIconHeight();
		}
		if (altezzaTesto > altezzaImage) {
			return getMaxDimensionY() + margineAlto + margineBasso + altezzaTesto;
		} else {
			return getMaxDimensionY() + margineAlto + margineBasso + altezzaImage;
		}
	}

	@Override
	public ContainerBase getContainerBase() {
		return containerBase;
	}

	@Override
	public int getMaxDimensionX() {
		return containerBase.getMaxDimensionY(this);
	}

	@Override
	public int getMaxDimensionY() {
		return containerBase.getMaxDimensionY(this);
	}

	@Override
	public Container getContenitorePadre() {
		return contenitorePadre;
	}

	public void setContenitorePadre(final Container contenitorePadre) {
		this.contenitorePadre = contenitorePadre;
	}

	@Override
	public void makeGUI(Container contenitorePadre) {
		// TODO Auto-generated method stub
		
	}

}
