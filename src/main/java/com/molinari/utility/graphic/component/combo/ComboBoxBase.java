package main.java.com.molinari.utility.graphic.component.combo;

import java.awt.Component;
import java.awt.Container;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import main.java.com.molinari.utility.graphic.component.base.ComponenteBaseConPadreContenitore;
import main.java.com.molinari.utility.graphic.component.base.IComponenteBase;
import main.java.com.molinari.utility.graphic.component.style.StyleBase;

public class ComboBoxBase extends JComboBox<Object> implements IComponenteBase {

	private static final long serialVersionUID = 1L;
	private Container contenitorePadre;
	private final ComponenteBaseConPadreContenitore componenteBase = new ComponenteBaseConPadreContenitore(this);

	public ComboBoxBase(final Container contenitorePadre) {
		makeGUI(contenitorePadre);
		init(contenitorePadre, this);
	}
	
	public ComboBoxBase(final Container contenitorePadre, final List<Object> lista) {
		this.setModel(new DefaultComboBoxModel<Object>(new Vector<>(lista)));
		makeGUI(contenitorePadre);
		init(contenitorePadre, this);
	}
	
	public ComboBoxBase(final Container contenitorePadre, final Object[] lista) {
		final DefaultComboBoxModel<Object> modelGiorni = new DefaultComboBoxModel<Object>(lista);
		this.setModel(modelGiorni);
		makeGUI(contenitorePadre);
		init(contenitorePadre, this);
	}

	@Override
	public void init(final Container contenitorePadre2, final Component componenteFiglio) {
		contenitorePadre = contenitorePadre2;
		componenteBase.init(contenitorePadre2, componenteFiglio);
		this.applicaStile(null, this);
		makeGUI(contenitorePadre2);
		setSize(getLarghezza(), getAltezza());
	}

	@Override
	public void applicaStile(StyleBase style, IComponenteBase padre) {
		componenteBase.applicaStile(style, padre);
	}

	@Override
	public boolean repaintCustomizzato(final Object[] parametri) {
		 if(componenteBase.repaintCustomizzato(parametri, this)){
			 @SuppressWarnings("unchecked")
			final DefaultComboBoxModel<Object> comboModel = ((DefaultComboBoxModel<Object>) parametri[IComponenteBase.PARAM_REPAINT_MODEL]);
			 setModel(comboModel);
			 componenteBase.ridisegna(this);
			 return true;
		 }
		return false;
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
	public boolean posizionaASinistraDi(final Component componenteParagone, final int distanzaOrizzontale, final int distanzaVerticale) {
		return componenteBase.posizionaASinistraDi(componenteParagone, distanzaOrizzontale, distanzaVerticale, this);
	}

	public boolean posizionaSottoA(final Component componenteParagone, final int distanzaOrizzantale, final int distanzaVerticale) {
		return componenteBase.posizionaSottoA(componenteParagone, distanzaOrizzantale, distanzaVerticale, this);
	}

	public boolean posizionaSopraA(final Component componenteParagone, final int distanzaOrizzantale, final int distanzaVerticale) {
		return componenteBase.posizionaSopraA(componenteParagone, distanzaOrizzantale, distanzaVerticale, this);
	}

	@Override
	public int getLarghezza() {
		final int larghezzaMax = getLarghezzaMax();
		return 25 + larghezzaMax;
	}

	private int getLarghezzaMax() {
		final int nMember = this.getModel().getSize();
		int largMax = 0;
		for (int i = 0; i < nMember; i++) {
			final Object elementAt = this.getModel().getElementAt(i);
			final int larghezzaComp = componenteBase.getLarghezzaSingleStringa(getGraphics(), elementAt.toString(), this);

			if (larghezzaComp > largMax) {
				largMax = larghezzaComp;
			}
		}
		return largMax;
	}

	@Override
	public int getAltezza() {
		return componenteBase.getAltezzaSingleStringa(getGraphics(), this);
	}

	@Override
	public Container getContenitorePadre() {
		return contenitorePadre;
	}

	public void setContenitorePadre(final Container contenitorePadre) {
		this.contenitorePadre = contenitorePadre;
	}

	@Override
	public void makeGUI(Container contenitorePadre) {
		// TODO Auto-generated method stub
		
	}

}
