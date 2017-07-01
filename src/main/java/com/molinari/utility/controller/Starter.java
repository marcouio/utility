package com.molinari.utility.controller;

import com.molinari.utility.graphic.component.container.FrameBase;
import com.molinari.utility.servicesloader.Extensible;

public interface Starter extends Extensible<Starter>{
	
	void start(FrameBase frameBase);
	
	ControlloreBase getControllore();
	
	void setControllore(ControlloreBase controllore);
}
