package main.java.com.molinari.utility.graphic.component.style;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import main.java.com.molinari.utility.xml.UtilXml;

public class StyleTextArea extends StyleBase {

	public static final String LINE_WRAP = "lineWrap";
	public static final String WRAP_STYLE_WORD = "wrapStyleWord";
	public static final String AUTOSCROLL = "autoscroll";

	public static final String ROWS = "rows";
	public static final String COLUMNS = "columns";
	public static final String DIMENSION_AREA = "dimensionarea";

	private int rows;
	private int columns;

	private boolean lineWrap = false;
	private boolean wrapStyleWord = false;
	private boolean autoscroll = false;

//	public static void main(final String[] args) {
//		SwingUtilities.invokeLater(new Runnable() {
//
//			@Override
//			public void run() {
//				JPanel p = UtilComponenti.initContenitoreFrame(null);
//				TextAreaBase tab = new TextAreaBase("Coao", p) {
//					private static final long	serialVersionUID	= 1L;
//				};
//			}
//		});
//	}

	public StyleTextArea() {
		super();
	}

	public StyleTextArea(final String stile) {
		super(stile);
	}

	@Override
	protected void settaStileBase(final Node nodo, final String stile) {
		super.settaStileBase(nodo, stile);
		if (nodo.getNodeName().equalsIgnoreCase(STYLE)) {
			Element elementoStyle = UtilXml.getElement(nodo);
			String nomeStile = elementoStyle.getAttribute(NAME);
			if (nomeStile != null && nomeStile.equalsIgnoreCase(stile)) {
				final NodeList figliNodoComponente = nodo.getChildNodes();
				for (int x = 0; x < figliNodoComponente.getLength(); x++) {
					final Node nodoComponente = figliNodoComponente.item(x);
					if (nodoComponente.getNodeName().equals(LINE_WRAP)) {
						settaLineWrap(nodo, this, nodoComponente);
					} else if (nodoComponente.getNodeName().equals(WRAP_STYLE_WORD)) {
						settaWrapStyleWord(nodo, this, nodoComponente);
					} else if (nodoComponente.getNodeName().equals(AUTOSCROLL)) {
						settaAutoscroll(nodo, this, nodoComponente);
					} else if (nodoComponente.getNodeName().equals(DIMENSION_AREA)) {
						settaDimensioniTextArea(nodo, this, nodoComponente);
					}
				}
			}
		}
	}

	private void settaDimensioniTextArea(final Node nodo, final StyleTextArea styleTextArea, final Node nodoComponente) {
		final Element elemento = UtilXml.getElement(nodoComponente);
		if (elemento != null) {
			int rows, columns = 0;
			if (elemento.getAttribute(ROWS) != null) {
				rows = Integer.parseInt(elemento.getAttribute(ROWS));
				styleTextArea.setRows(rows);
			}
			if (elemento.getAttribute(COLUMNS) != null) {
				columns = Integer.parseInt(elemento.getAttribute(COLUMNS));
				styleTextArea.setColumns(columns);
			}
		}
	}

	private void settaAutoscroll(final Node nodo, final StyleTextArea styleTextArea, final Node nodoComponente) {
		final Element elemento = UtilXml.getElement(nodoComponente);
		if (elemento != null) {
			final boolean autoscroll = new Boolean(elemento.getAttribute(VALUE)).booleanValue();
			styleTextArea.setAutoscroll(autoscroll);
		}
	}

	/**
	 * Sets the style of wrapping used if the text area is wrapping
	 * lines.  If set to true the lines will be wrapped at word
	 * boundaries (whitespace) if they are too long
	 * to fit within the allocated width.  If set to false,
	 * the lines will be wrapped at character boundaries.
	 * By default this property is false.
	 * 
	 * @param nodo
	 * @param styleTextArea
	 * @param nodoComponente
	 */
	private void settaWrapStyleWord(final Node nodo, final StyleTextArea styleTextArea, final Node nodoComponente) {
		final Element elemento = UtilXml.getElement(nodoComponente);
		if (elemento != null) {
			final boolean wrap = new Boolean(elemento.getAttribute(VALUE)).booleanValue();
			styleTextArea.setWrapStyleWord(wrap);
		}
	}

	/**
	 * Sets the line-wrapping policy of the text area.  If set
	 * to true the lines will be wrapped if they are too long
	 * to fit within the allocated width.  If set to false,
	 * the lines will always be unwrapped.  A <code>PropertyChange</code>
	 * event ("lineWrap") is fired when the policy is changed.
	 * By default this property is false.
	 * 
	 * @param nodo
	 * @param styleTextArea
	 * @param nodoComponente
	 */
	private void settaLineWrap(final Node nodo, final StyleTextArea styleTextArea, final Node nodoComponente) {
		final Element elemento = UtilXml.getElement(nodoComponente);
		if (elemento != null) {
			final boolean wrap = new Boolean(elemento.getAttribute(VALUE)).booleanValue();
			styleTextArea.setLineWrap(wrap);
		}
	}

	public boolean isLineWrap() {
		return lineWrap;
	}

	public void setLineWrap(final boolean wrap) {
		this.lineWrap = wrap;
	}

	public boolean isWrapStyleWord() {
		return wrapStyleWord;
	}

	public void setWrapStyleWord(final boolean wrapStyleWord) {
		this.wrapStyleWord = wrapStyleWord;
	}

	public boolean isAutoscroll() {
		return autoscroll;
	}

	public void setAutoscroll(final boolean autoscroll) {
		this.autoscroll = autoscroll;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(final int rows) {
		this.rows = rows;
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(final int columns) {
		this.columns = columns;
	}
}
