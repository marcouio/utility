package grafica.componenti.style;

import grafica.componenti.componenteBase.IComponenteBase;

import java.awt.Color;
import java.awt.Font;
import java.io.File;

import org.w3c.dom.Document;
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

	public static final String FONT = "font";
	public static final String BACKGROUND = "background";
	public static final String FOREGROUND = "foreground";
	public static final String FONTFAMILY = "font-family";
	public static final String TYPE = "type";
	public static final String SIZE = "size";
	public static final String VALUE = "value";
	public static final String STYLE = "style";
	public static final String NAME = "name";

	public static final String DIMENSION = "dimension";
	public static final String WIDTH = "width";
	public static final String HEIGHT = "height";

	public static final String R = "r";
	public static final String G = "g";
	public static final String B = "b";
	public static final String	STYLES	= "styles";

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
	protected void caricaInfoStyleBase(final String stile) {
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
		Color color = catchColor(nodoComponente);
		style.setForeground(color);
	}
	
	protected static Color catchColor(final Node nodoComponente){
		final Element elemento = UtilXml.getElement(nodoComponente);
		if (elemento != null) {
			try {
				final NodeList listaFigliBackground = nodoComponente.getChildNodes();
				for (int z = 0; z < listaFigliBackground.getLength(); z++) {
					final Node colorBackground = listaFigliBackground.item(z);
					final Element elementoColorBackground = UtilXml.getElement(colorBackground);
					if (elementoColorBackground != null) {
						
						final int r = Integer.parseInt(elementoColorBackground.getAttribute(R));
						final int g = Integer.parseInt(elementoColorBackground.getAttribute(G));
						final int b = Integer.parseInt(elementoColorBackground.getAttribute(B));
						return new Color(r, g, b);
						
					}
				}

			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
		return new Color(0, 0, 0);
	}

	/**
	 * Metodo per settare il colore di sfondo
	 * @param style
	 * @param nodoComponente
	 */
	protected static void settaBackground(final StyleBase style, final Node nodoComponente) {
		Color color = catchColor(nodoComponente);
		style.setBackground(color);
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
	
	public static void creaFileXmlStyle() throws Exception{

		String pathFile = CoreXMLManager.getSingleton().getXMLStyleFilePath();

		File fileConf = new File(pathFile);
		Document doc = UtilXml.createDocument(fileConf);
		Node nodo = UtilXml.getNodo(StyleBase.STYLES, doc);
		NodeList nodeList = UtilXml.getNodeList(doc);
		if(nodo == null && nodeList == null){
			Element rootElement = UtilXml.addRootElement(doc, StyleBase.STYLES);

			makeStyleBase(doc, rootElement, "stylebase");
			
			Element styleElement = makeStyleBase(doc, rootElement, "stylebasetable");
			
			addColorSetting(doc, styleElement, "255", "255", "255", StyleTable.FOREGROUND_NOTSEL);
			addColorSetting(doc, styleElement, "255", "0", "0", StyleTable.FOREGROUND_SEL);
//			addColorSetting(doc, styleElement, "100", "100", "100", StyleTable.FOREGROUND_PRIMACOLONNA);
//			addColorSetting(doc, styleElement, "100", "100", "100", StyleTable.FOREGROUND_PRIMARIGA);
			addColorSetting(doc, styleElement, "192", "192", "192", StyleTable.BACKGROUND_NOTSEL);
			addColorSetting(doc, styleElement, "100", "100", "100", StyleTable.BACKGROUND_SEL);
//			addColorSetting(doc, styleElement, "100", "100", "100", StyleTable.BACKGROUND_PRIMACOLONNA);
//			addColorSetting(doc, styleElement, "100", "100", "100", StyleTable.BACKGROUND_PRIMARIGA);
			
			UtilXml.writeXmlFile(doc, pathFile);
		}
	}
	
	protected static void addColorSetting(Document doc, Element elStyle, String r, String g, String b, String nomeElemento){
		Element elemento = UtilXml.addElement(doc, elStyle, nomeElemento);
		Element elementoColor = UtilXml.addElement(doc, elemento, "color");
		UtilXml.addAttribute(doc, elementoColor, StyleBase.R, r);
		UtilXml.addAttribute(doc, elementoColor, StyleBase.G, g);
		UtilXml.addAttribute(doc, elementoColor, StyleBase.B, b);
	}

	private static Element makeStyleBase(Document doc, Element rootElement, String name) {
		//style
		Element styleElement = UtilXml.addElement(doc, rootElement, StyleBase.STYLE);
		UtilXml.addAttribute(doc, styleElement, StyleBase.NAME, name);

		//font
		Element fontElement = UtilXml.addElement(doc, styleElement, StyleBase.FONT);
		UtilXml.addAttribute(doc, fontElement, StyleBase.FONTFAMILY, "Arial");
		UtilXml.addAttribute(doc, fontElement, StyleBase.TYPE, "0");
		UtilXml.addAttribute(doc, fontElement, StyleBase.SIZE, "15");

		//foreground
		Element foregroundElement = UtilXml.addElement(doc, styleElement, StyleBase.FOREGROUND);
		Element colorForeElement = UtilXml.addElement(doc, foregroundElement, "color");
		UtilXml.addAttribute(doc, colorForeElement, StyleBase.R, "100");
		UtilXml.addAttribute(doc, colorForeElement, StyleBase.G, "100");
		UtilXml.addAttribute(doc, colorForeElement, StyleBase.B, "100");

		//background
		Element backgroundElement = UtilXml.addElement(doc, styleElement, StyleBase.BACKGROUND);
		Element colorBackElement = UtilXml.addElement(doc, backgroundElement, "color");
		UtilXml.addAttribute(doc, colorBackElement, StyleBase.R, "255");
		UtilXml.addAttribute(doc, colorBackElement, StyleBase.G, "255");
		UtilXml.addAttribute(doc, colorBackElement, StyleBase.B, "255");

		//dimensionarea
		Element dimensionAreaElement = UtilXml.addElement(doc, styleElement, StyleTextArea.DIMENSION_AREA);
		UtilXml.addAttribute(doc, dimensionAreaElement, StyleTextArea.ROWS, "60");
		UtilXml.addAttribute(doc, dimensionAreaElement, StyleTextArea.COLUMNS, "60");

		//dimension
		Element dimensionElement = UtilXml.addElement(doc, styleElement, StyleBase.DIMENSION);
		UtilXml.addAttribute(doc, dimensionElement, StyleBase.WIDTH, "101");
		UtilXml.addAttribute(doc, dimensionElement, StyleBase.HEIGHT, "131");
		return styleElement;
	}

}
