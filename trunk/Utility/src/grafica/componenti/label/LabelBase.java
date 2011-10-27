package grafica.componenti.label;

import grafica.componenti.ComponenteBase;
import grafica.componenti.IComponenteBase;
import grafica.componenti.StyleBase;

import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;

import javax.swing.JLabel;

public abstract class LabelBase extends JLabel implements IComponenteBase {

	private static final long serialVersionUID = 1L;
	protected StyleBase style = new StyleBaseL();
	private Container contenitorePadre;
	private final ComponenteBase componenteBase = new ComponenteBase();

	public LabelBase(final Container contenitorePadre) {
		super();
		this.contenitorePadre = contenitorePadre;
		init(contenitorePadre, this);
	}

	public LabelBase(final String string, final Container contenitorePadre) {
		super(string);
		this.contenitorePadre = contenitorePadre;
		init(contenitorePadre, this);
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

	public class StyleBaseL extends StyleBase {

	}

	@Override
	public void init(final Container contenitorePadre2, final Component componenteFiglio) {
		componenteBase.init(contenitorePadre2, componenteFiglio);
		this.settaStile();
		int width = getLarghezzaSingleStringa(contenitorePadre2.getGraphics(), this.getText());
		int height = getAltezzaSingleStringa(contenitorePadre2.getGraphics());
		setSize(width, height);
	}

	@Override
	public boolean repaintCustomizzato(final Object[] parametri) {
		return componenteBase.repaintCustomizzato(parametri);
	}

	public StyleBase getStyle() {
		return style;
	}

	public void setStyle(final StyleBase style) {
		this.style = style;
	}

	public Container getContenitorePadre() {
		return contenitorePadre;
	}

	public void setContenitorePadre(final Container contenitorePadre) {
		this.contenitorePadre = contenitorePadre;
	}

	public ComponenteBase getComponenteBase() {
		return componenteBase;
	}

	@Override
	public boolean posizionaADestraDi(final Component componenteParagone, final int distanzaOrizzantale,
			final int distanzaVerticale) {
		return componenteBase.aDestraDi(componenteParagone, distanzaOrizzantale, distanzaVerticale, this);

	}

	@Override
	public boolean posizionaASinistraDi(final Component componenteParagone, final int distanzaOrizzontale,
			final int distanzaVerticale) {
		return componenteBase.aDestraDi(componenteParagone, distanzaOrizzontale, distanzaVerticale, this);
	}

	@Override
	public boolean posizionaSottoA(final Component componenteParagone, final int distanzaOrizzantale,
			final int distanzaVerticale) {
		return componenteBase.aDestraDi(componenteParagone, distanzaOrizzantale, distanzaVerticale, this);
	}

	@Override
	public boolean posizionaSopraA(final Component componenteParagone, final int distanzaOrizzantale,
			final int distanzaVerticale) {
		return componenteBase.aDestraDi(componenteParagone, distanzaOrizzantale, distanzaVerticale, this);
	}

	@Override
	public int getLarghezzaSingleStringa(final Graphics g, final String label) {
		return componenteBase.getLarghezzaSingleStringa(g, label, this);
	}

	@Override
	public int getAltezzaSingleStringa(final Graphics g) {
		return componenteBase.getAltezzaSingleStringa(g, this);
	}

}
