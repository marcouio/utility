package com.molinari.utility.controller;

import java.util.Comparator;

import com.molinari.utility.graphic.component.container.FrameBase;
import com.molinari.utility.servicesloader.Extensible;
import com.molinari.utility.servicesloader.LoaderLevel;

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
	public LoaderLevel getLevel() {
		return LoaderLevel.BASE;
	}

	@Override
	public Comparator<Extensible<Starter>> getComparator() {
		return new ComparatorExtendibile<>();
	}

}
