package com.molinari.utility.thread.template;

import com.molinari.utility.thread.requests.RichiestaThread;

public interface Producer<R, C> extends Runnable {

	boolean end();

	RichiestaThread<R, C> take() throws InterruptedException;
	
	RichiestaThread<R, C> next();

	boolean hasNext();
}
