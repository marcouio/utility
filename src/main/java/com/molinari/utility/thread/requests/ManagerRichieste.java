package main.java.com.molinari.utility.thread.requests;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import main.java.com.molinari.utility.thread.CallableBase;

public class ManagerRichieste {
	
	private CopyOnWriteArrayList<RichiestaThread> richieste = new CopyOnWriteArrayList<RichiestaThread>();

	public ManagerRichieste(List<Future<CallableBase>> reqs) throws InterruptedException, ExecutionException {
		if(reqs != null){
			
			for (Future<CallableBase> req : reqs) {
				RichiestaThread reqThread = req.get().getRichiestaThread();
				add(reqThread);
			}
		}
	}

	public void add(RichiestaThread ric){
		richieste.add(ric);
	}

	public CopyOnWriteArrayList<RichiestaThread> getRichieste() {
		return richieste;
	}

	public void setRichieste(CopyOnWriteArrayList<RichiestaThread> richieste) {
		this.richieste = richieste;
	}
	
	public int getNumRichiesteScartate(){
		return makeCounter().count(RichiestaThread.STATO_SCARTATO);
	}
	
	public int getNumRichiesteEseguite(){
		return makeCounter().count(RichiestaThread.STATO_ESEGUITO);
	}
	
	public int getTotaleRichieste(){
		return getRichieste().size();
	}
	
	protected CounterRichieste makeCounter(){
		return new CounterRichieste();
	}
	
	public class CounterRichieste{
		public int count(int stato){
			
			int counter = 0;
			if(richieste != null){
				for (int i = 0; i < richieste.size(); i++) {
					RichiestaThread richiesta = richieste.get(i);
					if(richiesta.getStato() == stato){
						counter++;
					
					}
				}
			}
			
			return counter;
		}
	}
}
