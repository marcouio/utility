package com.molinari.utility.thread.requests;

import java.util.concurrent.Callable;
import java.util.logging.Level;

import com.molinari.utility.GenericException;
import com.molinari.utility.controller.ControlloreBase;
import com.molinari.utility.thread.ContainerDb;
import com.molinari.utility.thread.MultiThreadException;


public abstract class BaseCallable<R, C> implements Callable<RichiestaThread<R, C>>{

	RichiestaThread<R, C> richiestaThread;
	private final ContainerDb db;
	private long numberOfCall;

	public BaseCallable(RichiestaThread<R, C> richiestaThread) {
		this.richiestaThread = richiestaThread;
		db = new ContainerDb();
	}
	
	@Override
	public RichiestaThread<R, C> call() throws Exception {
		final RichiestaThread<R, C> current = getRichiestaThread();

		try{
			
			init();

			C runEsteso = runEsteso();
			current.setCallBack(runEsteso);
			
			terminate();
			
		}catch (final Exception e) {
			gestisciEccezione(e);
		}
		
		finally{
			getDb().chiudi();
		}
		return current;
	}

	private void terminate() {
		final RichiestaThread<R, C> current = getRichiestaThread();
		current.setStato(RichiestaThread.STATO_ESEGUITO);
	}

	protected void gestisciEccezione(Exception e) {
		getRichiestaThread().setStato(RichiestaThread.STATO_SCARTATO);
		
		if(e instanceof MultiThreadException){
			final boolean irrecuperabile = ((MultiThreadException)e).isIrrecuperabile();
			if(irrecuperabile){
				throw new GenericException(e);
			}else{
			    ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
			}
		}
	}

	protected void init()  {
		getRichiestaThread().setStato(RichiestaThread.STATO_IN_ESECUZIONE);
	}
	
	public abstract C runEsteso() throws MultiThreadException;

	public ContainerDb getDb() {
		return db;
	}

	public long getNumberOfCall() {
		return numberOfCall;
	}

	public void setNumberOfCall(long numberOfCall) {
		this.numberOfCall = numberOfCall;
	}

	public RichiestaThread<R, C> getRichiestaThread() {
		return richiestaThread;
	}

	public void setRichiestaThread(RichiestaThread<R, C> richiestaThread) {
		this.richiestaThread = richiestaThread;
	}
}

