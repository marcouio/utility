package com.molinari.utility.controller;

import com.molinari.utility.graphic.component.container.FrameBase;

public interface Starter {
	
	void start(FrameBase frameBase);
	
	ControlloreBase getControllore();
	
	void setControllore(ControlloreBase controllore);
}
