package grafica.componenti.style;

import grafica.componenti.componenteBase.IComponenteBase;

import java.awt.Color;
import java.awt.Font;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import xml.CoreXMLManager;
import xml.UtilXml;

/**
 * Gli oggetti nella gerarchia di StyleBase sono interni ad altri oggetti swing,
 * con lo scopo di poterne configurare l'aspetto tramite il file xml definito 
 * all'interno di config-core.xml. 
 * <p>
 * <b>Per il funzionamento</b>: gli oggetti "base" dovranno implementare il metodo 
 * settaStile definito in IComponenteBase che dichiarerà un metodo al proprio 
 * interno "settaStileOverride" il quale può essere ridefinito all'interno 
 * dei relativi figli. Ecco un esempio di "settaStile" e "settaStileOverride:
 *  <p>
 *<pre><code>
 *
   @Override
	public void settaStile() {
		style = settaStileOverride() != null ? settaStileOverride() : style;
		componenteBase.settaStile(style, this);
	}
	
	protected StyleBase settaStileOverride(){
		return new StyleBase("esempioStile");
	;
 * </code></pre>
 * "style" è una proprietà interna alla classe che dovrà essere di tipo StyleBase o una
 * sua specializzazione
 * 
 * <p>Infine, il parametro interno al costruttore di stylebase sarà il nome del tag dichiarato all'interno dell'xml
 * <p>ATTENZIONE: tutte le impostazioni indicate negli style sovrapporranno quelle indicati nei costruttori degli oggetti grafici base,
 * ma non quelle nei costruttori delle loro specializzazioni. Se settate le informazioni di stile non verranno settate le 
 * impostazioni di autoconfig.
 */
public class StyleBase {

	private static final String FONT = "font";
	private static final String BACKGROUND = "background";
	private static final String FOREGROUND = "foreground";
	private static final String FONTFAMILY = "font-family";
	private static final String TYPE = "type";
	private static final String SIZE = "size";
	protected static final String VALUE = "value";
	protected static final String STYLE = "style";
	protected static final String NAME = "name";

	protected static final String DIMENSION = "dimension";
	private static final String WIDTH = "width";
	private static final String HEIGHT = "height";

	private static final String R = "r";
	private static final String G = "g";
	private static final String B = "b";

	private Font font;
	private Color foreground;
	private Color background;
	private IComponenteBase padre;
	private int width;
	private int height;
	private boolean trovato;

	public static void main(final String[] args) {
		//		final StyleBase base = new StyleBase();
		//		System.out.println(base.getBackground());
	}

	public StyleBase() {
		caricaInfoStyleBase("stylebase");
	}

	public StyleBase(final String stile) {
		caricaInfoStyleBase(stile);
	}

	public StyleBase(final Font font, final Color foreground, final Color background) {
		this.font = font;
		this.foreground = foreground;
		this.background = background;
	}

	/**
	 * Scorre tutti i nodi e delega al metodo 'settaStileBase' il compito di settare lo stile per ogni nodo
	 * @param stile 
	 */
	private void caricaInfoStyleBase(final String stile) {
		try {
			final NodeList listaNodi = UtilXml.getNodeList(CoreXMLManager.getSingleton().getXMLStyleFilePath());
			if (listaNodi != null) {
				for (int i = 0; i < listaNodi.getLength(); i++) {
					final Node nodo = listaNodi.item(i);
					settaStileBase(nodo, stile);
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
	protected void settaStileBase(final Node nodo, final String stile) {
		if (nodo.getNodeName().equalsIgnoreCase(STYLE)) {
			Element elementoStyle = UtilXml.getElement(nodo);
			String nomeStile = elementoStyle.getAttribute(NAME);
			if (nomeStile != null && nomeStile.equalsIgnoreCase(stile)) {
				trovato = true;
				final NodeList figliNodoComponente = nodo.getChildNodes();
				for (int x = 0; x < figliNodoComponente.getLength(); x++) {
					final Node nodoComponente = figliNodoComponente.item(x);
					if (nodoComponente.getNodeName().equals(FONT)) {
						settaFont(nodo, this, nodoComponente);
					} else if (nodoComponente.getNodeName().equals(BACKGROUND)) {
						settaBackground(this, nodoComponente);
					} else if (nodoComponente.getNodeName().equals(FOREGROUND)) {
						settaForeground(this, nodoComponente);
					} else if (nodoComponente.getNodeName().equals(DIMENSION)) {
						settaDimension(this, nodoComponente);
					}
				}
			}
		}
	}

	/**
	 * Metodo per settare le dimensioni del componente
	 * @param styleBase
	 * @param nodoComponente
	 */
	private void settaDimension(final StyleBase styleBase, final Node nodoComponente) {
		final Element elemento = UtilXml.getElement(nodoComponente);
		if (elemento != null) {
			try {
				int height = Integer.parseInt(elemento.getAttribute(HEIGHT));
				int width = Integer.parseInt(elemento.getAttribute(WIDTH));
				styleBase.setHeight(height);
				styleBase.setWidth(width);
			} catch (Exception e) {
				styleBase.setHeight(0);
				styleBase.setWidth(0);
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
							style.setForeground(new Color(0, 0, 0));
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
							style.setBackground(new Color(0, 0, 0));
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

	public void setPadre(final IComponenteBase padre2) {
		this.padre = padre2;
	}

	public IComponenteBase getPadre() {
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

	public int getWidth() {
		return width;
	}

	public void setWidth(final int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(final int height) {
		this.height = height;
	}

	public boolean isTrovato() {
		return trovato;
	}

	public void setTrovato(boolean trovato) {
		this.trovato = trovato;
	}

}
