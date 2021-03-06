package com.molinari.utility.graphic.complexcomponent;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.GregorianCalendar;

import com.molinari.utility.aggiornatori.IAggiornatore;
import com.molinari.utility.database.UtilDb;
import com.molinari.utility.graphic.component.combo.ComboBoxBase;
import com.molinari.utility.graphic.component.container.PannelloBase;
import com.molinari.utility.listener.AscoltatoreBase;

public class Calendario extends PannelloBase {

	private static final long serialVersionUID = 1L;
	private final ComboBoxBase comboGiorni;
	private final ComboBoxBase comboMesi;
	private final ComboBoxBase comboAnni;
	private ComboBoxBase comboOre;
	private ComboBoxBase comboMinuti;
	private final boolean time;
	private Date thisDate = new Date();

	public Calendario(final Container contenitore, final boolean time){
		this(contenitore, time, null);
	}

	public Calendario(final Container contenitore, final boolean time, final Dimension dimension) {
		super(contenitore);
		this.time = time;


		final String[] giorni = creaListaNumerica(31, 1);
		comboGiorni = new ComboBoxBase(this, giorni);
		if(dimension != null){
			comboGiorni.setSize(dimension);
		}

		final String[] mesi = new String[]{"01","02","03","04","05","06","07","08","09","10","11","12"};
		comboMesi = new ComboBoxBase(this, mesi);
		if(dimension != null){
			comboMesi.setSize(dimension);
		}
		comboMesi.posizionaADestraDi(comboGiorni, 0, 0, comboMesi);

		final String[] anni = creaListaAnni(new GregorianCalendar(),10);
		comboAnni = new ComboBoxBase(this, anni);
		if(dimension != null){
			comboAnni.setSize((int)(dimension.getWidth()*1.5), (int)dimension.getHeight());
		}
		comboAnni.posizionaADestraDi(comboMesi, 0, 0, comboAnni);

		if(time){

			final String[] ore = creaListaNumerica(24, 1);
			comboOre = new ComboBoxBase(this, ore);
			if(dimension != null){
				comboOre.setSize(dimension);
			}
			comboOre.posizionaADestraDi(comboAnni, 20, 0, comboOre);

			final String[] minuti = creaListaNumerica(60, 0);
			comboMinuti = new ComboBoxBase(this, minuti);
			if(dimension != null){
				comboMinuti.setSize(dimension);
			}
			comboMinuti.posizionaADestraDi(comboOre, 0, 0, comboMinuti);
		}
		addAscoltatore();
		this.setSize(this.getLarghezza(), this.getAltezza());
	}

	private void addAscoltatore() {
		AscoltatoreBase ascoltatore = new AscoltatoreCalendario(null, null, this);
		comboGiorni.addActionListener(ascoltatore);
		comboMesi.addActionListener(ascoltatore); 
		comboAnni.addActionListener(ascoltatore); 
		if(time){
			comboOre.addActionListener(ascoltatore);  
			comboMinuti.addActionListener(ascoltatore);
		}
	}

	private String[] creaListaAnni(final GregorianCalendar gC, final int lunghezza) {
		String[] anni = new String[lunghezza];
		int anno = gC.get(GregorianCalendar.YEAR);
		for(int i = 0; i < lunghezza; i++){
			anni[i] = Integer.toString(anno++);
		}
		return anni;
	}

	private String[] creaListaNumerica(final int numGiorni, final int partiDa) {
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

	public class AscoltatoreCalendario extends AscoltatoreBase{

		public AscoltatoreCalendario(final IAggiornatore aggiornatore,final Object[] parametri, Calendario calendario) {
			super(aggiornatore, parametri, calendario);
		}
		
		private boolean checkData(final boolean timestamp){
			
			boolean timeStampok = true;
			if (timestamp) {
				timeStampok = getComboOre().getSelectedItem() != null && 
						getComboMinuti().getSelectedItem() != null;
			}
			return getComboAnni().getSelectedItem() != null && 
					getComboMesi().getSelectedItem() != null &&
					getComboGiorni().getSelectedItem() != null && 
					timeStampok;

		}

		@Override
		protected void actionPerformedOverride(final ActionEvent e) {
			final String anniSel = (String)getComboAnni().getSelectedItem();
			final String mesiSel = (String)getComboMesi().getSelectedItem();
			final String giorniSel = (String)getComboGiorni().getSelectedItem();
			String oreSel = "00";
			String minutiSel = "00";

			if(time){
				oreSel = (String)getComboOre().getSelectedItem();
				minutiSel = (String)getComboMinuti().getSelectedItem();
			}

			String dataString = null;
			if(checkData(time)){
				dataString = anniSel + "/" + mesiSel + "/" + giorniSel +", " + oreSel + ":"+ minutiSel;
			}

			if(dataString != null){ 
				thisDate = UtilDb.stringToDate(dataString, "yyyy/MM/dd, HH:mm");
			}
		}

	}

	public String getStringDate(final String format){
		return UtilDb.dataToString(thisDate, format);
	}

	public Date getDate(){
		return thisDate;
	}
}
