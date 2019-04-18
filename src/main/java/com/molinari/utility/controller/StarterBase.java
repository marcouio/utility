package com.molinari.utility.controller;

import com.molinari.utility.graphic.component.container.FrameBase;
import com.molinari.utility.servicesloader.LoaderLevel;

public class StarterBase implements Starter {

	public StarterBase() {
		//do nothing
	}

	@Override
	public void start(FrameBase frameBase) {
		//do nothing
	}

	@Override
	public LoaderLevel getLevel() {
		return LoaderLevel.BASE;
	}
	
	@Override
	public Starter createInstance(Object... args) {
		return new StarterBase();
	}

}
