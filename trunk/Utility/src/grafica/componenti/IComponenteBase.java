package grafica.componenti;

import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;

public interface IComponenteBase {

	public static int PARAM_REPAINT_OBJ_REPAINT = 0;
	public static int PARAM_REPAINT_MODEL = 1;
	public static int PARAM_REPAINT_OBJ_CONTENT = 2;

	public void init(final Container contenitorePadre2, final Component componenteFiglio) throws ExceptionGraphics;

	/**
	 * Metodo implementato all'interno della classe ComponenteBase, ridisegna l'oggetto in base al suo tipo.
	 * @param parametri: parametri[0],l'oggetto da ridisegnare; parametri[1], il suo model; parametri[2],l'oggetto contenuto da sostituire.
	 * 			I parametri non sono sempre nullabili
	 * @return
	 */
	public boolean repaintCustomizzato(Object[] parametri) throws ExceptionGraphics;

	public boolean posizionaADestraDi(final Component componenteParagone, final int distanzaOrizzantale,
			final int distanzaVerticale) throws ExceptionGraphics;

	public boolean posizionaASinistraDi(final Component componenteParagone, final int distanzaOrizzontale,
			final int distanzaVerticale) throws ExceptionGraphics;

	public boolean posizionaSottoA(final Component componenteParagone, final int distanzaOrizzantale,
			final int distanzaVerticale) throws ExceptionGraphics;

	public boolean posizionaSopraA(final Component componenteParagone, final int distanzaOrizzantale,
			final int distanzaVerticale) throws ExceptionGraphics;

	public int getLarghezzaSingleStringa(final Graphics g, final String label) throws ExceptionGraphics;

	public int getAltezzaSingleStringa(final Graphics g) throws ExceptionGraphics;

	/**
	 * Ogni oggetto di base deve settare il proprio stile. In più all'interno della classe deve essere creato un metodo (settaStileOverride)
	 * astratto da richiamare all'interno di questo metodo per prevedere l'estensione dello stile. Vedi esempio LabelBase.
	 * ATTENZIONE!!! il metodo dovrebbe essere privato e va richimato solo all'interno del metodo 'init'
	 * @throws ExceptionGraphics 
	 */
	public void settaStile() throws ExceptionGraphics;
}
