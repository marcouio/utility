package grafica.componenti.contenitori;

import grafica.componenti.ComponenteBase;
import grafica.componenti.IComponenteBase;
import grafica.componenti.IFrame;
import grafica.componenti.StyleBase;

import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;

import javax.swing.JFrame;

public abstract class FrameBase extends JFrame implements IFrame, IComponenteBase {

	private Container contenitorePadre;
	private final ComponenteBase componenteBase = new ComponenteBase();
	protected StyleBase style = new StyleBaseFrame();
	private static final long serialVersionUID = 1L;

	public FrameBase() {
		this.getContentPane().setLayout(null);
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
	public void init(final Container contenitorePadre2, final Component componenteFiglio) {
		componenteBase.init(contenitorePadre2, componenteFiglio);
		this.settaStile();
	}

	public class StyleBaseFrame extends StyleBase {

	}

	@Override
	public boolean repaintCustomizzato(final Object[] parametri) {
		return componenteBase.repaintCustomizzato(parametri);
	}

	@Override
	public boolean posizionaADestraDi(final Component componenteParagone, final int distanzaOrizzantale,
			final int distanzaVerticale) {
		return componenteBase.aDestraDi(componenteParagone, distanzaOrizzantale, distanzaVerticale, this);

	}

	@Override
	public boolean posizionaASinistraDi(final Component componenteParagone, final int distanzaOrizzontale,
			final int distanzaVerticale) {
		return componenteBase.aSinistraDi(componenteParagone, distanzaOrizzontale, distanzaVerticale, this);
	}

	@Override
	public boolean posizionaSottoA(final Component componenteParagone, final int distanzaOrizzantale,
			final int distanzaVerticale) {
		return componenteBase.sottoA(componenteParagone, distanzaOrizzantale, distanzaVerticale, this);
	}

	@Override
	public boolean posizionaSopraA(final Component componenteParagone, final int distanzaOrizzantale,
			final int distanzaVerticale) {
		return componenteBase.sopraA(componenteParagone, distanzaOrizzantale, distanzaVerticale, this);
	}

	@Override
	public int getLarghezzaSingleStringa(final Graphics g, final String label) {
		return componenteBase.getLarghezzaSingleStringa(g, label, this);
	}

	@Override
	public int getAltezzaSingleStringa(final Graphics g) {
		return componenteBase.getAltezzaSingleStringa(g, this);
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
