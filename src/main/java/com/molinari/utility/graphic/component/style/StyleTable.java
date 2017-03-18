package main.java.com.molinari.utility.graphic.component.style;

import java.awt.Color;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import main.java.com.molinari.utility.xml.UtilXml;

public class StyleTable extends StyleBase {
	
	boolean isCellEditable = false;
	protected Color backgroundNotSel;
	protected Color backgroundSel;
	protected Color foregroundNotSel;
	protected Color foregroundSel;
	
	protected Color backgroundPrimaRiga;
	protected Color backgroundPrimaColonna;
	protected Color foregroundPrimaRiga;
	protected Color foregroundPrimaColonna;

	public static final String BACKGROUND_NOTSEL = "backgroundNotSel";
	public static final String BACKGROUND_SEL = "backgroundSel";
	public static final String FOREGROUND_SEL = "foregroundSel";
	public static final String FOREGROUND_NOTSEL = "foregroundNotSel";
	
	public static final String FOREGROUND_PRIMARIGA = "foregroundPrimaRiga";
	public static final String FOREGROUND_PRIMACOLONNA = "foregroundPrimaColonna";
	public static final String BACKGROUND_PRIMARIGA = "backgroundPrimaRiga";
	public static final String BACKGROUND_PRIMACOLONNA = "backgroundPrimaColonna";
	
	public static final String IS_CELL_EDITABLE = "isCellEditable";
	
	public StyleTable() {
		super();
	}

	public StyleTable(final String stile) {
		super(stile);
	}
	
	@Override
	protected void settaStileBase(Node nodo, String stile) {
		super.settaStileBase(nodo, stile);
		if (nodo.getNodeName().equalsIgnoreCase(STYLE)) {
			Element elementoStyle = UtilXml.getElement(nodo);
			String nomeStile = elementoStyle.getAttribute(NAME);
			if (nomeStile != null && nomeStile.equalsIgnoreCase(stile)) {
				final NodeList figliNodoComponente = nodo.getChildNodes();
				for (int x = 0; x < figliNodoComponente.getLength(); x++) {
					final Node nodoComponente = figliNodoComponente.item(x);
					if (nodoComponente.getNodeName().equals(BACKGROUND_NOTSEL)) {
						this.setBackgroundNotSel(catchColor(nodoComponente));
					} else if (nodoComponente.getNodeName().equals(BACKGROUND_SEL)) {
						this.setBackgroundSel(catchColor(nodoComponente));
					} else if (nodoComponente.getNodeName().equals(BACKGROUND_PRIMARIGA)) {
						this.setBackgroundPrimaRiga(catchColor(nodoComponente));
					} else if (nodoComponente.getNodeName().equals(BACKGROUND_PRIMACOLONNA)) {
						this.setBackgroundPrimaColonna(catchColor(nodoComponente));
					}else if (nodoComponente.getNodeName().equals(FOREGROUND_NOTSEL)) {
						this.setForegroundNotSel(catchColor(nodoComponente));
					} else if (nodoComponente.getNodeName().equals(FOREGROUND_SEL)) {
						this.setForegroundSel(catchColor(nodoComponente));
					} else if (nodoComponente.getNodeName().equals(FOREGROUND_PRIMARIGA)) {
						this.setForegroundPrimaRiga(catchColor(nodoComponente));
					} else if (nodoComponente.getNodeName().equals(FOREGROUND_PRIMACOLONNA)) {
						this.setForegroundPrimaColonna(catchColor(nodoComponente));
					}
				}
			}
		}
	}

	public Color getBackgroundNotSel() {
		return backgroundNotSel;
	}

	public void setBackgroundNotSel(Color backgroundNotSel) {
		this.backgroundNotSel = backgroundNotSel;
	}

	public boolean isCellEditable() {
		return isCellEditable;
	}

	public void setCellEditable(boolean isCellEditable) {
		this.isCellEditable = isCellEditable;
	}

	public Color getBackgroundSel() {
		return backgroundSel;
	}

	public void setBackgroundSel(Color backgroundSel) {
		this.backgroundSel = backgroundSel;
	}

	public Color getForegroundNotSel() {
		return foregroundNotSel;
	}

	public void setForegroundNotSel(Color foregroundNotSel) {
		this.foregroundNotSel = foregroundNotSel;
	}

	public Color getForegroundSel() {
		return foregroundSel;
	}

	public void setForegroundSel(Color foregroundSel) {
		this.foregroundSel = foregroundSel;
	}

	public Color getBackgroundPrimaRiga() {
		return backgroundPrimaRiga;
	}

	public void setBackgroundPrimaRiga(Color backgroundPrimaRiga) {
		this.backgroundPrimaRiga = backgroundPrimaRiga;
	}

	public Color getBackgroundPrimaColonna() {
		return backgroundPrimaColonna;
	}

	public void setBackgroundPrimaColonna(Color backgroundPrimaColonna) {
		this.backgroundPrimaColonna = backgroundPrimaColonna;
	}

	public Color getForegroundPrimaRiga() {
		return foregroundPrimaRiga;
	}

	public void setForegroundPrimaRiga(Color foregroundPrimaRiga) {
		this.foregroundPrimaRiga = foregroundPrimaRiga;
	}

	public Color getForegroundPrimaColonna() {
		return foregroundPrimaColonna;
	}

	public void setForegroundPrimaColonna(Color foregroundPrimaColonna) {
		this.foregroundPrimaColonna = foregroundPrimaColonna;
	}
}
