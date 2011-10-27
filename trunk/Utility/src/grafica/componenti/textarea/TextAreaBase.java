package grafica.componenti.textarea;

import grafica.componenti.ComponenteBase;
import grafica.componenti.ExceptionGraphics;
import grafica.componenti.IComponenteBase;
import grafica.componenti.StyleBase;

import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;

import javax.swing.JTextArea;
import javax.swing.text.Document;

public abstract class TextAreaBase extends JTextArea implements IComponenteBase {

	public class StyleBaseTA extends StyleBase {

	}

	private static final long serialVersionUID = 1L;
	protected StyleBase style = new StyleBaseTA();
	private Container contenitorePadre;
	private final ComponenteBase componenteBase = new ComponenteBase();

	public TextAreaBase(final Container contenitore) throws ExceptionGraphics {
		super();
		this.contenitorePadre = contenitore;
		init(contenitorePadre, this);
	}

	public TextAreaBase(final Document doc, final String text, final int rows, final int columns, final Container contenitore) throws ExceptionGraphics {
		super(doc, text, rows, columns);
		this.contenitorePadre = contenitore;
		init(contenitorePadre, this);
	}

	public TextAreaBase(final Document doc, final Container contenitore) throws ExceptionGraphics {
		super(doc);
		this.contenitorePadre = contenitore;
		init(contenitorePadre, this);
	}

	public TextAreaBase(final int rows, final int columns, final Container contenitore) throws ExceptionGraphics {
		super(rows, columns);
		this.contenitorePadre = contenitore;
		init(contenitorePadre, this);
	}

	public TextAreaBase(final String text, final int rows, final int columns, final Container contenitore) {
		super(text, rows, columns);
		this.contenitorePadre = contenitore;
	}

	public TextAreaBase(final String text, final Container contenitore) throws ExceptionGraphics {
		super(text);
		this.contenitorePadre = contenitore;
		init(contenitorePadre, this);
	}


	@Override
	public void init(final Container contenitorePadre2, final Component componenteFiglio)
	throws ExceptionGraphics {
		componenteBase.init(contenitorePadre2, componenteFiglio);
		settaStile();
	}

	@Override
	public boolean repaintCustomizzato(final Object[] parametri)
	throws ExceptionGraphics {
		return componenteBase.repaintCustomizzato(parametri);
	}

	@Override
	public boolean posizionaADestraDi(final Component componenteParagone,
			final int distanzaOrizzantale, final int distanzaVerticale)
	throws ExceptionGraphics {
		return componenteBase.aDestraDi(componenteParagone, distanzaOrizzantale, distanzaVerticale, this);
	}

	@Override
	public boolean posizionaASinistraDi(final Component componenteParagone,
			final int distanzaOrizzontale, final int distanzaVerticale)
	throws ExceptionGraphics {
		return componenteBase.aSinistraDi(componenteParagone, distanzaOrizzontale, distanzaVerticale, this);
	}

	@Override
	public boolean posizionaSottoA(final Component componenteParagone,
			final int distanzaOrizzantale, final int distanzaVerticale)
	throws ExceptionGraphics {
		return componenteBase.sottoA(componenteParagone, distanzaOrizzantale, distanzaVerticale, this);
	}

	@Override
	public boolean posizionaSopraA(final Component componenteParagone,
			final int distanzaOrizzantale, final int distanzaVerticale)
	throws ExceptionGraphics {
		return componenteBase.sopraA(componenteParagone, distanzaOrizzantale, distanzaVerticale, this);
	}

	@Override
	public int getLarghezzaSingleStringa(final Graphics g, final String label)
	throws ExceptionGraphics {
		return componenteBase.getLarghezzaSingleStringa(g, label, this);
	}

	@Override
	public int getAltezzaSingleStringa(final Graphics g) throws ExceptionGraphics {
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

}
