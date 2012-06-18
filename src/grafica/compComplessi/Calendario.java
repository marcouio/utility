package grafica.compComplessi;

import grafica.componenti.UtilComponenti;
import grafica.componenti.combo.ComboBoxBase;
import grafica.componenti.contenitori.PannelloBase;
import grafica.componenti.label.Label;

import java.awt.Color;
import java.awt.Container;
import java.util.GregorianCalendar;

import javax.swing.JPanel;

public class Calendario extends PannelloBase {

	private static final long serialVersionUID = 1L;
	private ComboBoxBase comboGiorni;
	private ComboBoxBase comboMesi;
	private ComboBoxBase comboAnni;
	private ComboBoxBase comboOre;
	private ComboBoxBase comboMinuti;

	public Calendario(Container contenitore, boolean time) {
		super(contenitore);

		final String[] giorni = creaListaNumerica(31, 1);
		comboGiorni = new ComboBoxBase(this, giorni);
		
		final String[] mesi = new String[]{"01","02","03","04","05","06","07","08","09","10","11","12"};
		comboMesi = new ComboBoxBase(this, mesi);
		comboMesi.posizionaADestraDi(comboGiorni, 0, 0, comboMesi);
		
		final String[] anni = creaListaAnni(new GregorianCalendar(),10);
		comboAnni = new ComboBoxBase(this, anni);
		comboAnni.posizionaADestraDi(comboMesi, 0, 0, comboAnni);
		
		if(time){
			
			Label labelTrattino = new Label(" - ", this);
			labelTrattino.posizionaADestraDi(comboAnni, 0, 0, labelTrattino);
			
			final String[] ore = creaListaNumerica(24, 1);
			comboOre = new ComboBoxBase(this, ore);
			comboOre.posizionaADestraDi(labelTrattino, 0, 0, comboOre);
			
			final String[] minuti = creaListaNumerica(60, 0);
			comboMinuti = new ComboBoxBase(this, minuti);
			comboMinuti.posizionaADestraDi(comboOre, 0, 0, comboMinuti);
		}
		
		this.setSize(this.getLarghezza(), this.getAltezza());
	}

	private String[] creaListaAnni(GregorianCalendar gC, int lunghezza) {
		String[] anni = new String[lunghezza];
		int anno = gC.get(GregorianCalendar.YEAR);
		for(int i = 0; i < lunghezza; i++){
			anni[i] = Integer.toString(anno++);
		}
		return anni;
	}

	private String[] creaListaNumerica(int numGiorni, int partiDa) {
		String[] giorni = new String[numGiorni];
		for(int i = 0; i<numGiorni; i++){
			if(i<9){
				giorni[i] = "0"+(i + partiDa);
			}else{
				giorni[i] = Integer.toString(i + partiDa);
			}
		}
		
		return giorni;
	}
	
	public ComboBoxBase getComboGiorni() {
		return comboGiorni;
	}

	public ComboBoxBase getComboMesi() {
		return comboMesi;
	}

	public ComboBoxBase getComboAnni() {
		return comboAnni;
	}

	public ComboBoxBase getComboOre() {
		return comboOre;
	}

	public ComboBoxBase getComboMinuti() {
		return comboMinuti;
	}

	
	public static void main(String[] args) {
		JPanel p = UtilComponenti.initContenitoreFrame(null);
		Calendario cal = new Calendario(p, false);
		
	}

}
