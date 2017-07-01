package com.molinari.utility.controller;

import java.util.Comparator;

import com.molinari.utility.graphic.component.container.FrameBase;
import com.molinari.utility.servicesloader.Extensible;

public class StarterBase implements Starter {

	private ControlloreBase controllore;
	
	public StarterBase() {
		//do nothing
	}

	@Override
	public void start(FrameBase frameBase) {
		//do nothing
	}



	@Override
	public ControlloreBase getControllore() {
		return controllore;
	}



	@Override
	public void setControllore(ControlloreBase controllore) {
		this.controllore = controllore;
	}

	@Override
	public int getLevel() {
		return 0;
	}

	@Override
	public Comparator<Extensible<Starter>> getComparator() {
		return new ComparatorExtendibile<>();
	}

}
