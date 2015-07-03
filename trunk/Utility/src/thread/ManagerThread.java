package thread;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.omg.CORBA.Environment;

import thread.richieste.ManagerRichieste;
import thread.richieste.RichiestaThread;

/**
 * Il manager si occupa di ricevere le richieste da eseguire e di gestire il
 * pool di thread che si occuperà di eseguirle. Al manager va passato anche il
 * class del runnable che va instanziato
 * 
 */
public class ManagerThread {

	public int							numberOfThread	= 10;

	private CopyOnWriteArrayList<RichiestaThread>	listaRichieste;
	private Class<?>					classeRunnable;
	private long counterCallable = 0;
	private Task						task;
	private ExecutorService			esecutore;
	
	public ManagerThread(List<RichiestaThread> richieste, Class<?> classe) {
		this.listaRichieste = new CopyOnWriteArrayList(richieste);
		this.classeRunnable = classe;
	}

	public <T> void eseguiProcesso() throws Exception {

		try {
			esecutore = Executors.newFixedThreadPool(numberOfThread);
			
			if (listaRichieste != null && listaRichieste.size() > 0) {

				ArrayList<Callable<CallableBase>> listRunnable = creaListRunnableToSumbit();

				getTask().preExecute(listaRichieste);

				List<Future<CallableBase>> future = esecutore.invokeAll(listRunnable);
				
				ManagerRichieste managerRichieste = new ManagerRichieste(future);
				task.postExecute(managerRichieste);

				esecutore.shutdown();

			}

			boolean terminato = bloccaSeNonTerminato();

			if (terminato) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
				String msg = "Task terminato alle ore: " + dateFormat.format(new Date());
				System.out.println(msg);
			}

		} catch (Exception e) {
		        System.out.println(e.getMessage());
		}
	}

	private ArrayList<Callable<CallableBase>> creaListRunnableToSumbit() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		if (listaRichieste != null && listaRichieste.size() > 0) {

			final ArrayList<Callable<CallableBase>> listaRunner = new ArrayList<Callable<CallableBase>>();

			for (int i = 0; i < listaRichieste.size(); i++) {

				final RichiestaThread parametro = listaRichieste.get(i);

				final Callable<CallableBase> runner = creaRunnable(parametro);
				if (runner != null) {
					listaRunner.add(runner);
				}
			}
			return listaRunner;
		}
		return null;
	}
	
	protected boolean bloccaSeNonTerminato() {
		boolean terminato = false;

		while (!isProcessiTerminati()) {
		}
		terminato = true;
		System.out.println("Task terminato: " + terminato);
		return terminato;
	}

	public boolean isProcessiTerminati() {
		return esecutore.isTerminated();
	}

	protected Callable<CallableBase> creaRunnable(RichiestaThread parametro) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		if (classeRunnable != null) {

			Constructor<Callable<CallableBase>> costruttoreRunnable = (Constructor<Callable<CallableBase>>) classeRunnable.getConstructor(new Class[] { ManagerThread.class, RichiestaThread.class });
			Callable<CallableBase> run = (Callable<CallableBase>) costruttoreRunnable.newInstance(new Object[] { this, parametro });

			return run;
		}
		return null;
	}

	/**
	 * @return the task
	 */
	public Task getTask() {
		return task;
	}

	/**
	 * @param task
	 *            the task to set
	 */
	public void setTask(Task task) {
		this.task = task;
	}

	public ExecutorService getEsecutore() {
		return esecutore;
	}

	public int getNumberOfThread() {
		return numberOfThread;
	}

	public void setNumberOfThread(int numberOfThread) {
		this.numberOfThread = numberOfThread;
	}

	public long getCounterCallable() {
		return counterCallable;
	}
	
	public void incCounterCallable(){
		counterCallable++;
	}
}
