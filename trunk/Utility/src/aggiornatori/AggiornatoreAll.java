package aggiornatori;

import grafica.componenti.contenitori.FrameBase;
import controller.ControlloreBase;

public class AggiornatoreAll extends AggiornatoreBase {

	public AggiornatoreAll() {
		FrameBase frameGenerale = ControlloreBase.getApplicationframe();
		setParametri(new Object[] { frameGenerale });
		setComp(frameGenerale);
	}

	@Override
	public boolean aggiorna() {
		return super.aggiorna();
	}

}
