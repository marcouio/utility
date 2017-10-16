package com.molinari.utility.thread.template;

import java.util.concurrent.BlockingQueue;

import com.molinari.utility.thread.requests.RichiestaThread;

public abstract class ProducerBase<R, C> implements Producer<R, C> {

	private BlockingQueue<RichiestaThread<R, C>> queue;

	private boolean end;
	
	@Override
	public void run() {
		while(hasNext()){
			queue.add(next());
		}
		end = true;
	}

	@Override
	public boolean end() {
		return end && queue.isEmpty();
	}

	@Override
	public RichiestaThread<R, C> take() throws InterruptedException {
		return queue.take();
	}
}
