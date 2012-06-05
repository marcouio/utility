package grafica.compComplessi;

import grafica.componenti.UtilComponenti;
import grafica.componenti.combo.ComboBoxBase;
import grafica.componenti.contenitori.PannelloBase;

import java.awt.Color;
import java.awt.Container;
import java.util.GregorianCalendar;

import javax.swing.JPanel;

public class Calendario extends PannelloBase {

	private static final long serialVersionUID = 1L;

	public Calendario(Container contenitore) {
		super(contenitore);

		final String[] giorni = creaListaGiorni(31);
		final ComboBoxBase comboGiorni = new ComboBoxBase(this, giorni);
		
		final String[] mesi = new String[]{"01","02","03","04","05","06","07","08","09","10","11","12"};
		final ComboBoxBase comboMesi = new ComboBoxBase(this, mesi);
		comboMesi.posizionaADestraDi(comboGiorni, 0, 0, comboMesi);
		
		final String[] anni = creaListaAnni(new GregorianCalendar(),10);
		final ComboBoxBase comboAnni = new ComboBoxBase(this, anni);
		comboAnni.posizionaADestraDi(comboMesi, 0, 0, comboAnni);
		
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

	private String[] creaListaGiorni(int numGiorni) {
		String[] giorni = new String[numGiorni];
		for(int i = 0; i<numGiorni; i++){
			if(i<9){
				giorni[i] = "0"+(i + 1);
			}else{
				giorni[i] = Integer.toString(i + 1);
			}
		}
		
		return giorni;
	}
	
	public static void main(String[] args) {
		JPanel p = UtilComponenti.initContenitoreFrame(null);
		Calendario cal = new Calendario(p);
		
	}

}
