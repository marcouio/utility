package com.molinari.utility.thread.template;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.molinari.utility.thread.CallableBase;
import com.molinari.utility.thread.requests.RichiestaThread;

public class Elaborator<R, C> {

	private int nThreads = 10;

	public void execute(Job<R,C> operation) {
		
		try {
			ExecutorService execProducer = Executors.newSingleThreadExecutor();
			Producer<R,C> producer = operation.getProducer();
			execProducer.submit(producer);
			
			ExecutorService execOper = Executors.newFixedThreadPool(nThreads);
			
			while(!producer.end()){
				RichiestaThread<R,C> take = producer.take();
				CallableBase<R,C> call = creaCallable(take, operation);
				
				Future<RichiestaThread<R, C>> submit = execOper.submit(call);
				
			}
			
			execProducer.shutdown();
			execOper.shutdown();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private CallableBase<R, C> creaCallable(RichiestaThread<R,C> take, Job<R, C> operation) {
			try {
				Class<CallableBase<R, C>> classe = operation.getConsumerClass();
				Constructor<CallableBase<R, C>> constructor = classe.getConstructor(RichiestaThread.class);
				return constructor.newInstance(take);
			} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
	}
}
