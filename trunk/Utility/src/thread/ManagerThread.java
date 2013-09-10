package thread;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ManagerThread {

	ArrayList<Runnable>					listaRunnable			= new ArrayList<Runnable>();
	public static final int				NUMBER_OF_THREAD	= 10;
	private ArrayList<? extends Object>	listaRichieste;
	private Class<? extends RunnerBase>	classeRunnable;

	public ManagerThread(ArrayList<? extends Object> listaRichieste, Class<? extends RunnerBase> classe) {
		this.listaRichieste = listaRichieste;
		this.classeRunnable = classe;
	}

	public void eseguiProcesso() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, InterruptedException {

		if (listaRichieste != null && listaRichieste.size() > 0) {

			while (listaRunnable.size() <= NUMBER_OF_THREAD && listaRichieste.size() > 0) {
				final Object parametro = (Object) listaRichieste.get(0);

				final Runnable runner = cercaRunnable(parametro);

				if (runner != null) {
					listaRunnable.add(runner);
					listaRichieste.remove(0);
				}
			}
			
			final ExecutorService executor = Executors.newFixedThreadPool(NUMBER_OF_THREAD);

			synchronized (listaRunnable) {
				for (int i = 0; i<listaRunnable.size(); i++) {
					Runnable runner = listaRunnable.get(i);
					executor.execute(runner);
				}
			}
				
			executor.shutdown();

			
			while (!executor.isTerminated()) {
	        }
	        System.out.println("Finished all threads");
		}
	}

	private Runnable cercaRunnable(Object parametro) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		synchronized (listaRunnable) {
			return creaRunnable(parametro);
		}
	}

	private Runnable creaRunnable(Object parametro) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		if (classeRunnable != null) {

			Constructor<? extends RunnerBase> costruttoreRunnable = classeRunnable.getConstructor(new Class[] { ManagerThread.class, Object.class });
			RunnerBase run = costruttoreRunnable.newInstance(new Object[] { this, parametro });
			return run;
		}

		return null;
	}

	public static void main(String[] args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, InterruptedException {

		ArrayList<Object> lista = new ArrayList<Object>();
		lista.add("thr1");
		lista.add("thr2");
		lista.add("thr3");
		lista.add("thr4");
		lista.add("thr5");
		lista.add("thr6");
		lista.add("thr7");
		lista.add("thr8");
		lista.add("thr9");
		lista.add("thr10");
		lista.add("thr11");
		lista.add("thr12");
		lista.add("thr13");
		lista.add("thr14");
		lista.add("thr15");
		lista.add("thr16");
		lista.add("thr17");
		lista.add("thr18");
		lista.add("thr19");
		lista.add("thr20");

		ManagerThread managerThread = new ManagerThread(lista, RunnerCiao.class);
		managerThread.eseguiProcesso();

		// GregorianCalendar secondoStart = new GregorianCalendar();
		// System.out.println("start single: " +
		// dateFormat.format(secondoStart.getTime()));
		// System.out.println();
		//
		// RunProva target = new RunProva();
		// target.setParametro("UNico");
		// Thread thread = new Thread(target);
		// thread.start();
		//
		// GregorianCalendar secondoFine = new GregorianCalendar();
		// System.out.println("fine single: " +
		// dateFormat.format(secondoFine.getTime()));
		//
		// System.out.println(secondoFine.getTimeInMillis() -
		// secondoStart.getTimeInMillis());
	}

}
