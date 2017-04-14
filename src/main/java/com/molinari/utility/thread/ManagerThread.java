package com.molinari.utility.thread;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;

import com.molinari.utility.GenericException;
import com.molinari.utility.controller.ControlloreBase;
import com.molinari.utility.thread.requests.RichiestaThread;

public class ManagerThread {

	private final List<Runnable>					listaRunnable			= new ArrayList<>();
	
	private int							numberOfThread	= 10;

	private final CopyOnWriteArrayList<RichiestaThread>	listaRichieste;
	private final Class<?>					classeRunnable;
	private long counterCallable = 0;
	private Task						task;
	private ExecutorService			esecutore;

	public ManagerThread(CopyOnWriteArrayList<RichiestaThread> listaRichieste, Class<? extends RunnerBase> classe) {
		this.listaRichieste = listaRichieste;
		this.classeRunnable = classe;
	}

	public void eseguiProcesso() {

		if (listaRichieste != null && !listaRichieste.isEmpty()) {

			while (listaRunnable.size() <= numberOfThread && !listaRichieste.isEmpty()) {
				final Object parametro = listaRichieste.get(0);

				final Runnable runner = cercaRunnable(parametro);

				if (runner != null) {
					listaRunnable.add(runner);
					listaRichieste.remove(0);
				}
			}
			
			final ExecutorService executor = Executors.newFixedThreadPool(numberOfThread);

			synchronized (listaRunnable) {
				for (int i = 0; i<listaRunnable.size(); i++) {
					final Runnable runner = listaRunnable.get(i);
					executor.execute(runner);
				}
			}
				
			executor.shutdown();

			
			while (!executor.isTerminated()) {
	        }
			ControlloreBase.getLog().info("Finished all threads");
		}
	}

	private Runnable cercaRunnable(Object parametro) {
		synchronized (listaRunnable) {
			return creaRunnable(parametro);
		}
	}

	private Runnable creaRunnable(Object parametro) {
		try {
			if (classeRunnable != null) {

				final Constructor<? extends RunnerBase> costruttoreRunnable = (Constructor<? extends RunnerBase>) classeRunnable.getConstructor(new Class[] { ManagerThread.class, Object.class });
				return costruttoreRunnable.newInstance(new Object[] { this, parametro });
			}

			return null;
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
			throw new GenericException();
		}
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
