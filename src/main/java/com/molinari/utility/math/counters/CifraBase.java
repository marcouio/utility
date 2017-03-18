package com.molinari.utility.math.counters;

public class CifraBase implements Cifra{

	private Cifra cifraInferiore;
	private Cifra cifraSuperiore;
	
	private String valore;
	
	public CifraBase(String valore) {
		this.valore = valore;
	}
	
	@Override
	public Cifra getCifraInferiore() {
		return cifraInferiore;
	}

	@Override
	public Cifra getCifraSuperiore() {
		return cifraSuperiore;
	}

	@Override
	public String getValore() {
		return valore;
	}

	public void setCifraInferiore(Cifra cifraInferiore) {
		this.cifraInferiore = cifraInferiore;
	}

	public void setCifraSuperiore(Cifra cifraSuperiore) {
		this.cifraSuperiore = cifraSuperiore;
	}

	public void setValore(String valore) {
		this.valore = valore;
	}

}
