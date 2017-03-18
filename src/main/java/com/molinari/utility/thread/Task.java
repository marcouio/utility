package main.java.com.molinari.utility.thread;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class Task {

	public abstract ArrayList<?> getRichieste();
	
	public abstract Class<? extends RunnerBase> getRunnbleClass();
	
	
	public void eseguiTask() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, InterruptedException{
	
//		CopyOnWriteArrayList<?> richieste = getRichieste();
//		Class<? extends RunnerBase> runnbleClass = getRunnbleClass();
//		
//		ManagerThread manager = new ManagerThread(richieste, runnbleClass);
//		manager.eseguiProcesso();
	}
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, InterruptedException {
		TaskProva task = new TaskProva();
		task.eseguiTask();
	}
}
 