package grafica.compComplessi;

import java.awt.Container;

import javax.swing.DefaultComboBoxModel;

import grafica.componenti.combo.ComboBoxBase;
import grafica.componenti.contenitori.PannelloBase;

public class Calendario extends PannelloBase {

	public Calendario(Container contenitore) {
		super(contenitore);
		
		ComboBoxBase giorno = new ComboBoxBase(this);
//		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>(items);
		
	}

}
