package com.molinari.utility.controller;

import com.molinari.utility.graphic.component.container.FrameBase;

public class StarterBase implements Starter {

	private ControlloreBase controllore;
	
	public StarterBase() {
		//do nothing
	}

	@Override
	public void start(FrameBase frameBase) {
		//do nothing
	}



	public ControlloreBase getControllore() {
		return controllore;
	}



	public void setControllore(ControlloreBase controllore) {
		this.controllore = controllore;
	}

}
