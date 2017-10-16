package com.molinari.utility.thread.template;

public interface Job<R, C> {

	Producer<R, C> getProducer();

	Class getConsumerClass();

}
