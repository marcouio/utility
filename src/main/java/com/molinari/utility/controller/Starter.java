package com.molinari.utility.controller;

import com.molinari.utility.graphic.PercentageDimension;
import com.molinari.utility.graphic.component.container.FrameBase;
import com.molinari.utility.servicesloader.Extensible;

public interface Starter extends Extensible<Starter>{
	
	PercentageDimension getPercentageDimension();
	void start(FrameBase frameBase);
	
}
