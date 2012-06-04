package grafica.componenti.combo;

import grafica.componenti.alert.Alert;
import grafica.componenti.componenteBase.ComponenteBaseConPadreContenitore;
import grafica.componenti.componenteBase.IComponenteBase;
import grafica.componenti.style.StyleBase;

import java.awt.Component;
import java.awt.Container;

import javax.swing.JComboBox;

public class ComboBoxBase extends JComboBox implements IComponenteBase{
	
	private static final long serialVersionUID = 1L;
	protected StyleBase style = new StyleBase();
	private Container contenitorePadre;
	private final ComponenteBaseConPadreContenitore componenteBase = new ComponenteBaseConPadreContenitore();

	public ComboBoxBase(final Container contenitorePadre) {
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
		Alert.segnalazioneErroreGrave("Non implementato");
		return 0;
	}

	@Override
	public int getAltezza() {
		Alert.segnalazioneErroreGrave("Non implementato");
		return 0;
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
