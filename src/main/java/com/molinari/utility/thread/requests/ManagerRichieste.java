package com.molinari.utility.thread.requests;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.molinari.utility.thread.CallableBase;

public class ManagerRichieste {
	
	private List<RichiestaThread> richieste = new CopyOnWriteArrayList<>();

	public ManagerRichieste(List<Future<CallableBase>> reqs) throws InterruptedException, ExecutionException {
		if(reqs != null){
			
			for (final Future<CallableBase> req : reqs) {
				final RichiestaThread reqThread = req.get().getRichiestaThread();
				add(reqThread);
			}
		}
	}

	public void add(RichiestaThread ric){
		richieste.add(ric);
	}

	public List<RichiestaThread> getRichieste() {
		return richieste;
	}

	public void setRichieste(List<RichiestaThread> richieste) {
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
					final RichiestaThread richiesta = richieste.get(i);
					if(richiesta.getStato() == stato){
						counter++;
					
					}
				}
			}
			
			return counter;
		}
	}
}
