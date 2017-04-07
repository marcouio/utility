package com.molinari.utility.aggiornatori;

import java.util.logging.Level;

import com.molinari.utility.controller.ControlloreBase;

public class AggiornatoreManager {

	public static final String TIPO_AGGIORNA_ALL = "all";
	private static final String TIPO_AGGIORNA_NULLA = "null";

	private static AggiornatoreManager singleton;

	public static AggiornatoreManager getSingleton() {
		if (singleton == null) {
			singleton = new AggiornatoreManager();
		}
		return singleton;
	}

	private AggiornatoreManager() {
	}

	/**
	 * Crea l'aggiornatore a partire dalla classe in formato stringa
	 * 
	 * @param classe
	 * @return
	 */
	public IAggiornatore creaAggiornatoreByClasse(final String classe) {
		IAggiornatore aggiornatore = null;
		try {
			aggiornatore = classe != null ? 
					(IAggiornatore) Class.forName(classe).newInstance() : 
					new AggiornatoreBase();
		} catch (final Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
			aggiornatore = new AggiornatoreBase();
		}
		return aggiornatore;
	}

	/**
	 * Crea l'aggiornatore a partire dal tipo passato come parametro. i parametri sono delle costanti
	 * definite all'interno di questo manager
	 * @param tipoAggiornamento
	 * @return
	 */
	public IAggiornatore creaAggiornatoreByTipo(String tipoAggiornamento) {
		IAggiornatore aggiornatore = null;
		String tipoAggiornamentoLoc = tipoAggiornamento != null ? tipoAggiornamento : TIPO_AGGIORNA_NULLA;

		if (TIPO_AGGIORNA_ALL.equalsIgnoreCase(tipoAggiornamentoLoc)) {
			aggiornatore = new AggiornatoreAll();
		} else if (TIPO_AGGIORNA_NULLA.equalsIgnoreCase(tipoAggiornamentoLoc)) {
			aggiornatore = new AggiornatoreNull();
		}
		return aggiornatore;
	}

	public class AggiornatoreNull implements IAggiornatore {

		@Override
		public boolean aggiorna() {
			return false;
		}

	}
}
