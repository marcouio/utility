package com.molinari.utility.thread.requests;

public class RichiestaThread<R,C> {

	public static final int STATO_DA_ESEGUIRE = 0;
	public static final int STATO_IN_ESECUZIONE = 1;
	public static final int STATO_ESEGUITO = 2;
	public static final int STATO_SCARTATO = 3;
	
	private int stato = STATO_DA_ESEGUIRE;
	private R richiesta;
	private C callBack;
	
	public int getStato() {
		return stato;
	}
	public void setStato(int stato) {
		this.stato = stato;
	}
	public R getRichiesta() {
		return richiesta;
	}
	public void setRichiesta(R richiesta) {
		this.richiesta = richiesta;
	}
	public C getCallBack() {
		return callBack;
	}
	public void setCallBack(C callBack) {
		this.callBack = callBack;
	}
	
	public boolean isDaEseguire(){
		return getStato() == STATO_DA_ESEGUIRE;
	}
}
