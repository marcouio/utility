package grafica.componenti.alert;

import grafica.componenti.UtilComponenti;
import grafica.componenti.alert.builder.IBuilderDialogo;
import grafica.componenti.button.ButtonBase;
import grafica.componenti.componenteBase.ComponenteBaseDialogo;
import grafica.componenti.componenteBase.IComponenteBase;
import grafica.componenti.contenitori.FrameBase;
import grafica.componenti.contenitori.contenitoreBase.ContainerBase;
import grafica.componenti.contenitori.contenitoreBase.ContainerBaseDialogo;
import grafica.componenti.contenitori.contenitoreBase.IContainerBase;
import grafica.componenti.style.StyleBase;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;

public class DialogoBase extends JDialog implements IComponenteBase, IContainerBase, IContainerBuilderDialog {

	private final ContainerBaseDialogo containerBase = new ContainerBaseDialogo();
	private final ComponenteBaseDialogo componenteBase = new ComponenteBaseDialogo(this);
	private static final long serialVersionUID = 1L;
	Container padre;
	private int opzioneScelta = -1;

	public DialogoBase(final JFrame frame) {
		super(frame);
		padre = frame;
		makeGUI(frame);
		init(null, this);
	}

	public DialogoBase(final Dialog owner) {
		super(owner);
		padre = owner;
		makeGUI(owner);
		init(null, this);
	}

	@Override
	public IBuilderDialogo getBuilder() {
		return new BuilderDialogo(padre);
	}

	public static void main(final String[] args) {
		final FrameBase panello = UtilComponenti.initContenitoreFrameApplicazione(null, null);
		final ButtonBase bottone = new ButtonBase("premi qui di nuovo e ancora!", panello);
		bottone.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {

				final IBuilderDialogo builder = new DialogoBase(panello).getBuilder();
				builder.setMessaggio("Questo è un messaggio veramente troppo troppo lungo");
				builder.setTitolo("Titolo");
				builder.addPositiveButton();
				builder.addNegativeButton();
				builder.addCancelButton();
				final DialogoBase dialogo = builder.creaDialogo();
			}
		});
	}

	@Override
	public int getMaxDimensionX() {
		return containerBase.getMaxDimensionX(this);
	}

	@Override
	public int getMaxDimensionY() {
		return containerBase.getMaxDimensionY(this);
	}

	@Override
	public void init(final Container contenitorePadre2, final Component componenteFiglio) {
		this.setLayout(null);
		componenteBase.init(contenitorePadre2, componenteFiglio);
		this.setStile(new StyleBase("StyleBaseDialogo"));

	}

	@Override
	public boolean repaintCustomizzato(final Object[] parametri) {
		return componenteBase.repaintCustomizzato(parametri, this);
	}

	/**
	 * Metodo facade di metodo omonimo per facilitarne l'accesso e la
	 * leggibilita'
	 * 
	 * @param componenteParagone
	 * @param distanzaOrizzantale
	 * @param distanzaVerticale
	 * @return
	 */
	public boolean posizionaADestraDi(final Component componenteParagone, final int distanzaOrizzantale, final int distanzaVerticale) {
		return posizionaADestraDi(componenteParagone, distanzaOrizzantale, distanzaVerticale, this);
	}

	/**
	 * Metodo facade di metodo omonimo per facilitarne l'accesso e la
	 * leggibilita'
	 * 
	 * @param componenteParagone
	 * @param distanzaOrizzantale
	 * @param distanzaVerticale
	 * @return
	 */
	public boolean posizionaASinistraDi(final Component componenteParagone, final int distanzaOrizzantale, final int distanzaVerticale) {
		return posizionaASinistraDi(componenteParagone, distanzaOrizzantale, distanzaVerticale, this);
	}

	/**
	 * Metodo facade di metodo omonimo per facilitarne l'accesso e la
	 * leggibilita'
	 * 
	 * @param componenteParagone
	 * @param distanzaOrizzantale
	 * @param distanzaVerticale
	 * @return
	 */
	public boolean posizionaSottoA(final Component componenteParagone, final int distanzaOrizzantale, final int distanzaVerticale) {
		return posizionaSottoA(componenteParagone, distanzaOrizzantale, distanzaVerticale, this);
	}

	/**
	 * Metodo facade di metodo omonimo per facilitarne l'accesso e la
	 * leggibilita'
	 * 
	 * @param componenteParagone
	 * @param distanzaOrizzantale
	 * @param distanzaVerticale
	 * @return
	 */
	public boolean posizionaSopraA(final Component componenteParagone, final int distanzaOrizzantale, final int distanzaVerticale) {
		return posizionaSopraA(componenteParagone, distanzaOrizzantale, distanzaVerticale, this);
	}

	@Override
	public boolean posizionaADestraDi(final Component componenteParagone, final int distanzaOrizzantale, final int distanzaVerticale, final Component compDaPosizionare) {
		return componenteBase.posizionaADestraDi(componenteParagone, distanzaOrizzantale, distanzaVerticale, compDaPosizionare);
	}

	@Override
	public boolean posizionaASinistraDi(final Component componenteParagone, final int distanzaOrizzontale, final int distanzaVerticale, final Component compDaPosizionare) {
		return componenteBase.posizionaASinistraDi(componenteParagone, distanzaOrizzontale, distanzaVerticale, compDaPosizionare);
	}

	@Override
	public boolean posizionaSottoA(final Component componenteParagone, final int distanzaOrizzantale, final int distanzaVerticale, final Component compDaPosizionare) {
		return componenteBase.posizionaSottoA(componenteParagone, distanzaOrizzantale, distanzaVerticale, compDaPosizionare);
	}

	@Override
	public boolean posizionaSopraA(final Component componenteParagone, final int distanzaOrizzantale, final int distanzaVerticale, final Component compDaPosizionare) {
		return componenteBase.posizionaSopraA(componenteParagone, distanzaOrizzantale, distanzaVerticale, compDaPosizionare);
	}

	@Override
	public void setStile(final StyleBase styleBase) {
		componenteBase.settaStile(styleBase, this);
	}

	@Override
	public ContainerBase getContainerBase() {
		return containerBase;
	}

	public int getOpzioneScelta() {
		return opzioneScelta;
	}

	public void setOpzioneScelta(final int opzioneScelta) {
		this.opzioneScelta = opzioneScelta;
	}

	@Override
	public int getLarghezza() {
		return getMaxDimensionX();
	}

	@Override
	public int getAltezza() {
		return getMaxDimensionY();
	}

	@Override
	public Container getContenitorePadre() {
		// uk dialog non richiede un contenitore
		return null;
	}

	@Override
	public void makeGUI(Container contenitorePadre) {
		// TODO Auto-generated method stub
		
	}

}