package grafica.componenti.contenitori;

import grafica.componenti.ComponenteBase;
import grafica.componenti.ExceptionGraphics;
import grafica.componenti.IComponenteBase;
import grafica.componenti.StyleBase;

import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.LayoutManager;

import javax.swing.JPanel;

public abstract class PannelloBase extends JPanel implements IComponenteBase, IContainerBase {

	private Container contenitorePadre;
	private final ContainerBase containerBase = new ContainerBase();
	protected StyleBase style = new StyleBasePannello();
	private static final long serialVersionUID = 1L;
	private final ComponenteBase componenteBase = new ComponenteBase();

	public PannelloBase(final Container contenitore) throws ExceptionGraphics {
		super();
		this.contenitorePadre = contenitore;
		init(contenitore, this);
	}

	public PannelloBase(final boolean isDoubleBuffered, final Container contenitore) throws ExceptionGraphics {
		super(isDoubleBuffered);
		this.contenitorePadre = contenitore;
		init(contenitore, this);
	}

	public PannelloBase(final LayoutManager layout, final boolean isDoubleBuffered, final Container contenitore)
			throws ExceptionGraphics {
		super(layout, isDoubleBuffered);
		this.contenitorePadre = contenitore;
		init(contenitore, this);
	}

	public PannelloBase(final LayoutManager layout, final Container contenitore) throws ExceptionGraphics {
		super(layout);
		this.contenitorePadre = contenitore;
		init(contenitore, this);
	}

	@Override
	public void init(final Container contenitorePadre2, final Component componenteFiglio) throws ExceptionGraphics {
		componenteBase.init(contenitorePadre2, componenteFiglio);
		this.settaStile();
	}

	@Override
	public boolean repaintCustomizzato(final Object[] parametri) throws ExceptionGraphics {
		return componenteBase.repaintCustomizzato(parametri);
	}

	@Override
	public boolean posizionaADestraDi(final Component componenteParagone, final int distanzaOrizzantale,
			final int distanzaVerticale) throws ExceptionGraphics {
		return componenteBase.aDestraDi(componenteParagone, distanzaOrizzantale, distanzaVerticale, this);
	}

	@Override
	public boolean posizionaASinistraDi(final Component componenteParagone, final int distanzaOrizzontale,
			final int distanzaVerticale) throws ExceptionGraphics {
		return componenteBase.aSinistraDi(componenteParagone, distanzaOrizzontale, distanzaVerticale, this);
	}

	@Override
	public boolean posizionaSottoA(final Component componenteParagone, final int distanzaOrizzantale,
			final int distanzaVerticale) throws ExceptionGraphics {
		return componenteBase.sottoA(componenteParagone, distanzaOrizzantale, distanzaVerticale, this);
	}

	@Override
	public boolean posizionaSopraA(final Component componenteParagone, final int distanzaOrizzantale,
			final int distanzaVerticale) throws ExceptionGraphics {
		componenteBase.sopraA(componenteParagone, distanzaOrizzantale, distanzaVerticale, this);
		return false;
	}

	@Override
	public int getLarghezzaSingleStringa(final Graphics g, final String label) throws ExceptionGraphics {
		return componenteBase.getLarghezzaSingleStringa(g, label, this);
	}

	@Override
	public int getAltezzaSingleStringa(final Graphics g) throws ExceptionGraphics {
		return componenteBase.getAltezzaSingleStringa(g, this);
	}

	@Override
	public void settaStile() throws ExceptionGraphics {
		style = settaStileOverride() != null ? settaStileOverride() : style;
		style.setPadre(this);
		this.setFont(style.getFont());
		this.setForeground(style.getForeground());
		this.setBackground(style.getBackground());

	}

	protected abstract StyleBase settaStileOverride();

	public class StyleBasePannello extends StyleBase {

	}

	public Container getContenitorePadre() {
		return contenitorePadre;
	}

	public void setContenitorePadre(final Container contenitorePadre) {
		this.contenitorePadre = contenitorePadre;
	}

	public StyleBase getStyle() {
		return style;
	}

	public void setStyle(final StyleBase style) {
		this.style = style;
	}

	public ComponenteBase getComponenteBase() {
		return componenteBase;
	}

	@Override
	public int getMaxDimensionX() {
		return containerBase.getMaxDimensionX(this);
	}

	@Override
	public int getMaxDimensionY() {
		return containerBase.getMaxDimensionY(this);
	}
}
