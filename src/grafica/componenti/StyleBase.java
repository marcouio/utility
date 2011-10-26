package grafica.componenti;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import xml.CoreXMLManager;
import xml.UtilXml;

/**
 * Gli oggetti nella gerarchia di StyleBase sono interni ad altri oggetti swing,
 * con lo scopo di poterne configurare l'aspetto. Sia le proprietà (font,
 * foreground, background) che i relativi metodi setter e getter possono
 * apparire a prima vista una duplicazione di codice inutile dei rispettivi
 * swing. In realtà questi oggetti sono indispensabili per garantire
 * l'ereditarietà del comportamento: ad esempio non configurando sull'xml un
 * oggetto (textData) non potrei stabilire se un eventuale padre (text) è stato
 * configurato e quindi mi rimarebbero le informazioni di default. In questa
 * maniera invece l'ereditarietà viene impostata runtime
 */
public class StyleBase {

	private static final String FONT = "font";
	private static final String BACKGROUND = "background";
	private static final String FOREGROUND = "foreground";
	private static final String FONTFAMILY = "font-family";
	private static final String TYPE = "type";
	private static final String SIZE = "size";
	private static final String R = "r";
	private static final String G = "g";
	private static final String B = "b";

	private Font font;
	private Color foreground;
	private Color background;
	private Component padre;

	public static void main(final String[] args) {
		final StyleBase base = new StyleBase();
		System.out.println(base.getBackground());
	}

	public StyleBase() {
		caricaInfoStyleBase();
	}

	public StyleBase(final Font font, final Color foreground, final Color background) {
		super();
		this.font = font;
		this.foreground = foreground;
		this.background = background;
	}

	/**
	 * Scorre tutti i nodi e delega al metodo 'settaStileBase' il compito di settare lo stile per ogni nodo
	 */
	private void caricaInfoStyleBase() {
		try {
			final NodeList listaNodi = UtilXml.getNodeList(CoreXMLManager.getSingleton().getXMLStyleFilePath());
			if (listaNodi != null) {
				for (int i = 0; i < listaNodi.getLength(); i++) {
					final Node nodo = listaNodi.item(i);
					settaStileBase(nodo);
				}
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Controlla il tipo di proprieta' da settare e richiama il rispettivo metodo. Per implementare nuove caratteristiche di stile, 
	 * aggiungere altri if else con relativi controlli alle proprietà e metodi per settarli
	 * @param nodo
	 */
	private void settaStileBase(final Node nodo) {
		if (nodo.getNodeName().equalsIgnoreCase(this.getClass().getSimpleName())) {
			final NodeList figliNodoComponente = nodo.getChildNodes();
			for (int x = 0; x < figliNodoComponente.getLength(); x++) {
				final Node nodoComponente = figliNodoComponente.item(x);
				if (nodoComponente.getNodeName().equals(FONT)) {
					settaFont(nodo, this, nodoComponente);
				} else if (nodoComponente.getNodeName().equals(BACKGROUND)) {
					settaBackground(this, nodoComponente);
				} else if (nodoComponente.getNodeName().equals(FOREGROUND)) {
					settaForeground(this, nodoComponente);
				}
			}
		}
	}

	/**
	 * Metodo per settare il colore del testo
	 * @param style
	 * @param nodoComponente
	 */
	protected static void settaForeground(final StyleBase style, final Node nodoComponente) {
		final Element elemento = UtilXml.getElement(nodoComponente);
		if (elemento != null) {
			try {
				final NodeList listaFigliForeground = nodoComponente.getChildNodes();
				for (int z = 0; z < listaFigliForeground.getLength(); z++) {
					final Node figlioForeground = listaFigliForeground.item(z);
					final Element elementoForeground = UtilXml.getElement(figlioForeground);
					if (elementoForeground != null) {
						try {
							final int r = Integer.parseInt(elementoForeground.getAttribute(R));
							final int g = Integer.parseInt(elementoForeground.getAttribute(G));
							final int b = Integer.parseInt(elementoForeground.getAttribute(B));
							style.setForeground(new Color(r, g, b));
						} catch (final Exception e) {
							e.printStackTrace();
						}
					}
				}
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Metodo per settare il colore di sfondo
	 * @param style
	 * @param nodoComponente
	 */
	protected static void settaBackground(final StyleBase style, final Node nodoComponente) {

		final Element elemento = UtilXml.getElement(nodoComponente);
		if (elemento != null) {
			try {
				final NodeList listaFigliBackground = nodoComponente.getChildNodes();
				for (int z = 0; z < listaFigliBackground.getLength(); z++) {
					final Node colorBackground = listaFigliBackground.item(z);
					final Element elementoColorBackground = UtilXml.getElement(colorBackground);
					if (elementoColorBackground != null) {
						try {
							final int r = Integer.parseInt(elementoColorBackground.getAttribute(R));
							final int g = Integer.parseInt(elementoColorBackground.getAttribute(G));
							final int b = Integer.parseInt(elementoColorBackground.getAttribute(B));
							style.setBackground(new Color(r, g, b));
						} catch (final Exception e) {
							e.printStackTrace();
						}
					}
				}

			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Metodo per settare il font
	 * @param nodo
	 * @param style
	 * @param nodoComponente
	 */
	protected static void settaFont(final Node nodo, final StyleBase style, final Node nodoComponente) {
		final Element elemento = UtilXml.getElement(nodoComponente);
		if (elemento != null) {
			final String carattere = elemento.getAttribute(FONTFAMILY);
			final int tipo = Integer.parseInt(elemento.getAttribute(TYPE));
			final int size = Integer.parseInt(elemento.getAttribute(SIZE));
			if (carattere != null && size != 0) {
				style.setFont(new Font(carattere, tipo, size));
			}
		}
	}

	public void setPadre(final Component padre) {
		this.padre = padre;
	}

	public Component getPadre() {
		return padre;
	}

	public Font getFont() {
		return font;
	}

	public Color getForeground() {
		return foreground;
	}

	public Color getBackground() {
		return background;
	}

	public void setFont(final Font font) {
		this.font = font;
	}

	public void setForeground(final Color foreground) {
		this.foreground = foreground;
	}

	public void setBackground(final Color background) {
		this.background = background;
	}

}
