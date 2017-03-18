package com.molinari.utility.aggiornatori;

import com.molinari.utility.controller.ControlloreBase;
import com.molinari.utility.graphic.component.container.FrameBase;

public class AggiornatoreAll extends AggiornatoreBase {

	public AggiornatoreAll() {
		FrameBase frameGenerale = ControlloreBase.getApplicationframe();
		setParametri(null);
		setComp(frameGenerale);
	}

	@Override
	public boolean aggiorna() {
		return super.aggiorna();
	}

}
