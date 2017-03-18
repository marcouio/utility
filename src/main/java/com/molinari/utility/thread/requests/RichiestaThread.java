package main.java.com.molinari.utility.thread.requests;

public class RichiestaThread {

	public static final int STATO_DA_ESEGUIRE = 0;
	public static final int STATO_IN_ESECUZIONE = 1;
	public static final int STATO_ESEGUITO = 2;
	public static final int STATO_SCARTATO = 3;
	
	private int stato = STATO_DA_ESEGUIRE;
	private Object richiesta;
	private Object callBack;
	
	public int getStato() {
		return stato;
	}
	public void setStato(int stato) {
		this.stato = stato;
	}
	public Object getRichiesta() {
		return richiesta;
	}
	public void setRichiesta(Object richiesta) {
		this.richiesta = richiesta;
	}
	public Object getCallBack() {
		return callBack;
	}
	public void setCallBack(Object callBack) {
		this.callBack = callBack;
	}
	
	public boolean isDaEseguire(){
		return getStato() == STATO_DA_ESEGUIRE;
	}
}
