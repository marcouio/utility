package thread;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class ManagerThread {

	ArrayList<Thread>					listaThread			= new ArrayList<Thread>();
	public static final int				NUMBER_OF_THREAD	= 10;
	private ArrayList<? extends Object>	listaRichieste;
	private Class<? extends RunnerBase>	classeRunnable;

	private Runnable					runnable;

	public ManagerThread(ArrayList<? extends Object> listaRichieste, Class<? extends RunnerBase> classe) {
		this.listaRichieste = listaRichieste;
		this.classeRunnable = classe;
	}

	public void eseguiProcesso() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, InterruptedException {

		if (listaRichieste != null && listaRichieste.size() > 0) {

			while (listaThread.size() <= NUMBER_OF_THREAD) {
				final Object parametro = (Object) listaRichieste.get(0);

				final Thread thread = cercaThread(parametro);

				if (thread != null) {
					listaRichieste.remove(0);
				}
				else {
					break;
				}
			}
			synchronized (listaThread) {
				for (int i = 0; i<listaThread.size(); i++) {
					Thread thread = listaThread.get(i);
					thread.start();
				}
			}

		}
	}

	private Thread cercaThread(Object parametro) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		synchronized (listaThread) {
			if (listaThread.size() < NUMBER_OF_THREAD) {
				Thread thread = creaThread(parametro);
				listaThread.add(thread);
				return thread;
			}
		}

		return null;
	}

	private Thread creaThread(Object parametro) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		if (classeRunnable != null) {

			Constructor<? extends RunnerBase> costruttoreRunnable = classeRunnable.getConstructor(new Class[] { ManagerThread.class, Object.class });
			RunnerBase run = costruttoreRunnable.newInstance(new Object[] { this, parametro });
			Thread thread = new Thread(run);
			
			run.setThreadPadre(thread);
			
			return thread;
		}

		return null;
	}

	public void threadEnd(Thread EndedThread) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, InterruptedException {
		listaThread.remove(EndedThread);

		synchronized (listaRichieste) {

			if (listaRichieste != null && listaRichieste.size() > 0) {
				final Object parametro = (Object) listaRichieste.get(0);
				Thread thread = cercaThread(parametro);
				if (thread != null) {
					if (listaRichieste.size() != 0) {
						listaRichieste.remove(0);
						thread.start();
					}
				}
			}
//			else{
//				for (int i = 0; i < listaThread.size(); i++) {
//					Thread thread = listaThread.get(i);
//					thread.join();
//				}
//			}
		}
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

	public Runnable getRunnable() {
		return runnable;
	}

	public void setRunnable(Runnable runnable) {
		this.runnable = runnable;
	}

}
