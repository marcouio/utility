package com.molinari.utility.graphic.component.alert;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;

import javax.swing.JDialog;
import javax.swing.JFrame;

import com.molinari.utility.graphic.component.alert.builder.IBuilderDialogo;
import com.molinari.utility.graphic.component.base.ComponenteBaseDialogo;
import com.molinari.utility.graphic.component.base.IComponenteBase;
import com.molinari.utility.graphic.component.container.base.ContainerBase;
import com.molinari.utility.graphic.component.container.base.ContainerBaseDialogo;
import com.molinari.utility.graphic.component.container.base.IContainerBase;
import com.molinari.utility.graphic.component.style.StyleBase;

public class DialogoBase extends JDialog implements IComponenteBase, IContainerBase, IContainerBuilderDialog {

	private final ContainerBaseDialogo containerBase = new ContainerBaseDialogo();
	private final ComponenteBaseDialogo componenteBase = new ComponenteBaseDialogo(this);
	private static final long serialVersionUID = 1L;
	Container padre;
	private int opzioneScelta = -1;

	public DialogoBase() {
		makeGUI(null);
		init(null, this);
	}
	
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
		this.applicaStile(new StyleBase("StyleBaseDialogo"), this);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

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
	public void applicaStile(StyleBase style, IComponenteBase padre) {
		componenteBase.applicaStile(style, padre);
		
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
		//do nothing
	}
	public void setSize(boolean isPercent, int width, int height) {
		componenteBase.setSize(this, isPercent, width, height);
	}

	@Override
	public void setSize(Component componentToSize, boolean isPercent, int width, int height) {
		componenteBase.setSize(componentToSize, isPercent, width, height);
	}
}