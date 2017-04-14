package com.molinari.utility.thread;

import java.util.concurrent.Callable;
import java.util.logging.Level;

import com.molinari.utility.GenericException;
import com.molinari.utility.controller.ControlloreBase;
import com.molinari.utility.thread.requests.RichiestaThread;


public abstract class CallableBase implements Callable<Object>{

	RichiestaThread richiestaThread;
	private final ManagerThread	manager;
	private final ContainerDb db;
	private long numberOfCall;

	public CallableBase(ManagerThread manager, RichiestaThread richiestaThread) {
		this.manager = manager;
		this.richiestaThread = richiestaThread;
		db = new ContainerDb();
	}
	
	@Override
	public Object call() throws Exception {
		final RichiestaThread current = getRichiestaThread();

		try{
			
			init();

			current.setCallBack(runEsteso());
			
			terminate();
			
		}catch (final Exception e) {
			gestisciEccezione(e);
		}
		
		finally{
			getDb().chiudi();
		}
		return this;
	}

	private void terminate() {
		final RichiestaThread current = getRichiestaThread();
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
		final ManagerThread man = getManager();
		man.incCounterCallable();
		setNumberOfCall(man.getCounterCallable());
		
		getRichiestaThread().setStato(RichiestaThread.STATO_IN_ESECUZIONE);
	}
	
	public abstract Object runEsteso() throws MultiThreadException;

	/**
	 * @return the manager
	 */
	public ManagerThread getManager() {
		return manager;
	}

	public ContainerDb getDb() {
		return db;
	}

	public long getNumberOfCall() {
		return numberOfCall;
	}

	public void setNumberOfCall(long numberOfCall) {
		this.numberOfCall = numberOfCall;
	}

	public RichiestaThread getRichiestaThread() {
		return richiestaThread;
	}

	public void setRichiestaThread(RichiestaThread richiestaThread) {
		this.richiestaThread = richiestaThread;
	}
}

