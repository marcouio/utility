package com.molinari.utility.thread;

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
	}
	
	public abstract void runEsteso();

	public Object getParametro() {
		return parametro;
	}

}
