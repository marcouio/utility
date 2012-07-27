package grafica.componenti.combo;

import grafica.componenti.componenteBase.ComponenteBaseConPadreContenitore;
import grafica.componenti.componenteBase.IComponenteBase;
import grafica.componenti.style.StyleBase;

import java.awt.Component;
import java.awt.Container;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

public class ComboBoxBase extends JComboBox<Object> implements IComponenteBase{
	
	private static final long serialVersionUID = 1L;
	protected StyleBase style = new StyleBase();
	private Container contenitorePadre;
	private final ComponenteBaseConPadreContenitore componenteBase = new ComponenteBaseConPadreContenitore(this);

	public ComboBoxBase(final Container contenitorePadre, Object[] lista) {
		DefaultComboBoxModel<Object> modelGiorni = new DefaultComboBoxModel<Object>(lista);
		this.setModel(modelGiorni);
		init(contenitorePadre, this);
	}
	
	@Override
	public void init(Container contenitorePadre2, Component componenteFiglio) {
			this.contenitorePadre = contenitorePadre2;
			componenteBase.init(contenitorePadre2, componenteFiglio);
			this.settaStile();
			setSize(getLarghezza(), getAltezza());
	}
	
	@Override
	public void settaStile() {
		componenteBase.settaStile(style, this);
		if (settaStileOverride() != null) {
			style = settaStileOverride();
			componenteBase.settaStile(style, this);
		}
	}

	protected StyleBase settaStileOverride() {
		return null;
	}

	@Override
	public boolean repaintCustomizzato(Object[] parametri) {
		return componenteBase.repaintCustomizzato(parametri);
	}

	@Override
	public boolean posizionaADestraDi(Component componenteParagone, int distanzaOrizzantale, int distanzaVerticale,Component compDaPosizionare) {
		return componenteBase.posizionaADestraDi(componenteParagone, distanzaOrizzantale, distanzaVerticale, compDaPosizionare);
	}

	@Override
	public boolean posizionaASinistraDi(Component componenteParagone,int distanzaOrizzontale, int distanzaVerticale,Component compDaPosizionare) {
		return componenteBase.posizionaASinistraDi(componenteParagone, distanzaOrizzontale, distanzaVerticale, compDaPosizionare);
	}

	@Override
	public boolean posizionaSottoA(Component componenteParagone,int distanzaOrizzantale, int distanzaVerticale, Component compDaPosizionare) {
		return componenteBase.posizionaSottoA(componenteParagone, distanzaOrizzantale, distanzaVerticale, compDaPosizionare);
	}

	@Override
	public boolean posizionaSopraA(Component componenteParagone,int distanzaOrizzantale, int distanzaVerticale,Component compDaPosizionare) {
		return componenteBase.posizionaSopraA(componenteParagone, distanzaOrizzantale, distanzaVerticale, compDaPosizionare);
	}

	@Override
	public int getLarghezza() {
		int larghezzaMax = getLarghezzaMax();
		return 25 + larghezzaMax;
	}

	private int getLarghezzaMax() {
		int nMember = this.getModel().getSize();
		int largMax = 0;
		for (int i = 0; i < nMember; i++) {
			Object elementAt = this.getModel().getElementAt(i);
			int larghezzaComp = componenteBase.getLarghezzaSingleStringa(getGraphics(), elementAt.toString(), this);
			
			if(larghezzaComp > largMax){
				largMax = larghezzaComp;
			}
		}
		return largMax;
	}

	@Override
	public int getAltezza() {
		return componenteBase.getAltezzaSingleStringa(getGraphics(), this);
	}
	
	public StyleBase getStyle() {
		return style;
	}

	public void setStyle(final StyleBase style) {
		this.style = style;
	}

	public Container getContenitorePadre() {
		return contenitorePadre;
	}

	public void setContenitorePadre(final Container contenitorePadre) {
		this.contenitorePadre = contenitorePadre;
	}

}
