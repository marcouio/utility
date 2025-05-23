package com.molinari.utility.graphic.component.base;

import java.awt.Component;
import java.awt.Container;

import com.molinari.utility.graphic.component.style.StyleBase;

public interface IComponenteBase {

	public static int PARAM_REPAINT_MODEL = 0;
	public static int PARAM_REPAINT_OBJ_CONTENT = 1;

	/**
	 * Esegue alcune operazioni preliminari sugli oggetti grafici come l'add sui
	 * contenitori. Viene implementato nella classe ComponenteBase e richiamato
	 * all'interno degli oggetti grafici base. Questo metodo deve essere
	 * l'ultimo metodo del costruttore perché se necessario sovrascrive alcune
	 * informazioni di setting assegnate dal framework in una prima fase.
	 * 
	 * @param contenitorePadre2
	 * @param componenteFiglio
	 * @
	 */
	public void init(final Container contenitorePadre2, final Component componenteFiglio);

	/**
	 * Metodo implementato all'interno della classe ComponenteBase o di una sua
	 * estensione, ridisegna l'oggetto che lo richiama in base al suo tipo.
	 * 
	 * @param <b>parametri[0]</b>, il suo model;
	 *        <p>
	 *        <b>parametri[1]</b>,l'oggetto contenuto da sostituire. I parametri
	 *        non sono sempre nullabili
	 * @return <b>true</b> se l'aggiornamento è andato a buon fine
	 */
	public boolean repaintCustomizzato(Object[] parametri);

	/**
	 * Metodo di posizionamento utilizzabile solo con layout a null. Permette di
	 * posizionare un oggetto rispetto ad un altro. Implementato all'interno di
	 * ComponenteBase o di una sua estensione e utilizzato all'interno di
	 * oggetti grafici base
	 * 
	 * @param componenteParagone
	 * @param distanzaOrizzantale
	 * @param distanzaVerticale
	 * @param compDaPosizionare
	 * @return
	 */
	public boolean posizionaADestraDi(final Component componenteParagone, final int distanzaOrizzantale, final int distanzaVerticale, final Component compDaPosizionare);

	/**
	 * Metodo di posizionamento utilizzabile solo con layout a null. Permette di
	 * posizionare un oggetto rispetto ad un altro. Implementato all'interno di
	 * ComponenteBase o di una sua estensione e utilizzato all'interno di
	 * oggetti grafici base
	 * 
	 * @param componenteParagone
	 * @param distanzaOrizzontale
	 * @param distanzaVerticale
	 * @param compDaPosizionare
	 * @return
	 */
	public boolean posizionaASinistraDi(final Component componenteParagone, final int distanzaOrizzontale, final int distanzaVerticale, final Component compDaPosizionare);

	/**
	 * Metodo di posizionamento utilizzabile solo con layout a null. Permette di
	 * posizionare un oggetto rispetto ad un altro. Implementato all'interno di
	 * ComponenteBase o di una sua estensione e utilizzato all'interno di
	 * oggetti grafici base
	 * 
	 * @param componenteParagone
	 * @param distanzaOrizzantale
	 * @param distanzaVerticale
	 * @param compDaPosizionare
	 * @return
	 */
	public boolean posizionaSottoA(final Component componenteParagone, final int distanzaOrizzantale, final int distanzaVerticale, final Component compDaPosizionare);

	/**
	 * Metodo di posizionamento utilizzabile solo con layout a null. Permette di
	 * posizionare un oggetto rispetto ad un altro. Implementato all'interno di
	 * ComponenteBase o di una sua estensione e utilizzato all'interno di
	 * oggetti grafici base
	 * 
	 * @param componenteParagone
	 * @param distanzaOrizzantale
	 * @param distanzaVerticale
	 * @param compDaPosizionare
	 * @return
	 */
	public boolean posizionaSopraA(final Component componenteParagone, final int distanzaOrizzantale, final int distanzaVerticale, final Component compDaPosizionare);

	/**
	 * Ogni oggetto di base deve implementare tale metodo per settare il proprio
	 * stile dall'xml. In più all'interno della classe deve essere creato un
	 * metodo (settaStileOverride)astratto da richiamare all'interno di questo
	 * metodo per prevedere l'estensione dello stile. Vedi esempio LabelBase.
	 * ATTENZIONE!!! il metodo va richimato solo alla
	 * fine del metodo 'init', qualsiasi altro utilizzo non è sicuro.
	 * 
	 */
	public void applicaStile(final StyleBase style, final IComponenteBase padre);

	public int getLarghezza();

	public int getAltezza();

	public Container getContenitorePadre();
	
	/**
	 * Questo metodo è invocato all'interno del costruttore dei singoli componenti perché avviene prima dell'inizializzazione del parent. I componenti costruiti ed aggiunti qui dentro devono
	 * avere le dimensioni impostate in modo da impostare le dimensioni successivamente del contenitore padre
	 * @param contenitorePadre
	 */
	public void makeGUI(final Container contenitorePadre);

	public void setSize(Component componentToSize, boolean isPercent, int width, int height);

	default void makeGUI(final Container contenitorePadre, final Object... parametro) {
		makeGUI(contenitorePadre);
	};
	
}
