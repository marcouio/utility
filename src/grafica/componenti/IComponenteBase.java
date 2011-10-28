package grafica.componenti;

import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;

public interface IComponenteBase {

	public static int PARAM_REPAINT_OBJ_REPAINT = 0;
	public static int PARAM_REPAINT_MODEL = 1;
	public static int PARAM_REPAINT_OBJ_CONTENT = 2;

	/**
	 * Esegue alcune operazioni preliminari sugli oggetti grafici come l'add sui contenitori. Viene implementato nella classe 
	 * ComponenteBase e richiamato all'interno degli oggetti grafici base. Questo metodo deve essere l'ultimo metodo del costruttore
	 * perché se necessario sovrascrive alcune informazioni di setting assegnate dal framework in una prima fase.
	 * 
	 * @param contenitorePadre2
	 * @param componenteFiglio
	 * @throws ExceptionGraphics
	 */
	public void init(final Container contenitorePadre2, final Component componenteFiglio);

	/**
	 * Metodo implementato all'interno della classe ComponenteBase, ridisegna l'oggetto che lo richiama in base al suo tipo.
	 * @param parametri: parametri[0],l'oggetto da ridisegnare; parametri[1], il suo model; parametri[2],l'oggetto contenuto da sostituire.
	 * 			I parametri non sono sempre nullabili
	 * @return
	 */
	public boolean repaintCustomizzato(Object[] parametri);

	/**
	 * Metodo di posizionamento utilizzabile solo con layout a null. Permette di posizionare un oggetto rispetto ad un altro.
	 * Implementato all'interno di ComponenteBase e utilizzato all'interno di oggetti grafici base 
	 * 
	 * @param componenteParagone
	 * @param distanzaOrizzantale
	 * @param distanzaVerticale
	 * @param compDaPosizionare
	 * @return
	 */
	public boolean posizionaADestraDi(final Component componenteParagone, final int distanzaOrizzantale,
			final int distanzaVerticale, final Component compDaPosizionare);

	/**
	 * Metodo di posizionamento utilizzabile solo con layout a null. Permette di posizionare un oggetto rispetto ad un altro.
	 * Implementato all'interno di ComponenteBase e utilizzato all'interno di oggetti grafici base
	 * 
	 * @param componenteParagone
	 * @param distanzaOrizzontale
	 * @param distanzaVerticale
	 * @param compDaPosizionare
	 * @return
	 */
	public boolean posizionaASinistraDi(final Component componenteParagone, final int distanzaOrizzontale,
			final int distanzaVerticale, final Component compDaPosizionare);

	/**
	 * Metodo di posizionamento utilizzabile solo con layout a null. Permette di posizionare un oggetto rispetto ad un altro.
	 * Implementato all'interno di ComponenteBase e utilizzato all'interno di oggetti grafici base
	 * 
	 * @param componenteParagone
	 * @param distanzaOrizzantale
	 * @param distanzaVerticale
	 * @param compDaPosizionare
	 * @return
	 */
	public boolean posizionaSottoA(final Component componenteParagone, final int distanzaOrizzantale,
			final int distanzaVerticale, final Component compDaPosizionare);

	/**
	 * Metodo di posizionamento utilizzabile solo con layout a null. Permette di posizionare un oggetto rispetto ad un altro.
	 * Implementato all'interno di ComponenteBase e utilizzato all'interno di oggetti grafici base
	 * 
	 * @param componenteParagone
	 * @param distanzaOrizzantale
	 * @param distanzaVerticale
	 * @param compDaPosizionare
	 * @return
	 */
	public boolean posizionaSopraA(final Component componenteParagone, final int distanzaOrizzantale,
			final int distanzaVerticale, final Component compDaPosizionare);

	/**
	 * Calcola la larghezza di un campo sulla base del testo che lo contiene. 
	 * Implementato all'interno di ComponenteBase e utilizzato all'interno di oggetti grafici base
	 * 
	 * @param g
	 * @param label
	 * @param compDaValutare
	 * @return
	 */
	public int getLarghezzaSingleStringa(final Graphics g, final String label, final Component compDaValutare);

	/**
	 * Calcola la larghezza di un campo sulla base del testo che lo contiene. 
	 * Implementato all'interno di ComponenteBase e utilizzato all'interno di oggetti grafici base
	 * 
	 * @param g
	 * @param compDaValutare
	 * @return
	 */
	public int getAltezzaSingleStringa(final Graphics g, final Component compDaValutare);

	/**
	 * Ogni oggetto di base deve implementare tale metodo per settare il proprio stile. In più all'interno della classe 
	 * deve essere creato un metodo (settaStileOverride)astratto da richiamare all'interno di questo metodo per 
	 * prevedere l'estensione dello stile. Vedi esempio LabelBase. ATTENZIONE!!! il metodo dovrebbe essere privato e va 
	 * richimato solo alla fine del metodo 'init', qualsiasi altro utilizzo non è sicuro.
	 * 
	 */
	public void settaStile();
}
