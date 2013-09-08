package thread;

import java.lang.reflect.InvocationTargetException;


public abstract class RunnerBase implements Runnable{

	private Object parametro;
	ManagerThread manager;
	Thread threadPadre;
	
	public RunnerBase(ManagerThread manager, Object parametro) {
		this.manager = manager;
		this.parametro = parametro;
	}	
	
	@Override
	public void run() {
		runEsteso();
		notifyEnd();
	}
	
	public abstract void runEsteso();

	private void notifyEnd() {
		try {
			manager.threadEnd(getThreadPadre());
		}
		catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	public Thread getThreadPadre() {
		return threadPadre;
	}

	public void setThreadPadre(Thread threadPadre) {
		this.threadPadre = threadPadre;
	}

	public Object getParametro() {
		return parametro;
	}

}
