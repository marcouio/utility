package thread;

import java.util.concurrent.Callable;

import thread.richieste.RichiestaThread;


public abstract class CallableBase implements Callable<Object>{

	RichiestaThread richiestaThread;
	private ManagerThread	manager;
	private ContainerDb db;
	private long numberOfCall;

	public CallableBase(ManagerThread manager, RichiestaThread richiestaThread) throws Exception {
		this.manager = manager;
		this.richiestaThread = richiestaThread;
		db = new ContainerDb();
	}
	
	public Object call() throws Exception {
		RichiestaThread current = getRichiestaThread();

		try{
			
			init();

			current.setCallBack(runEsteso());
			
			terminate();
			
		}catch (Exception e) {
			gestisciEccezione(e);
		}
		
		finally{
			getDb().chiudi();
		}
		return this;
	}

	private void terminate() {
		RichiestaThread current = getRichiestaThread();
		current.setStato(RichiestaThread.STATO_ESEGUITO);
	}

	protected void gestisciEccezione(Exception e) throws Exception {
		getRichiestaThread().setStato(RichiestaThread.STATO_SCARTATO);
		
		if(e instanceof MultiThreadException){
			boolean irrecuperabile = ((MultiThreadException)e).isIrrecuperabile();
			if(irrecuperabile){
				throw new Exception(e);
			}else{
			    e.printStackTrace();
			}
		}
	}

	protected void init()  {
		ManagerThread man = getManager();
		Task task = man.getTask();

		if (task != null) {
			
		}
		
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

