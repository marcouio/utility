package aggiornatori;

import grafica.componenti.componenteBase.IComponenteBase;

public class AggiornatoreBase implements IAggiornatore {

	protected Object[] parametri;
	protected IComponenteBase comp;

	public AggiornatoreBase() {
	}

	@Override
	public boolean aggiorna() {
		if (comp != null) {
			comp.getContenitorePadre().invalidate();
			if (comp.repaintCustomizzato(parametri)) {
				return true;
			}
		}
		return false;
	}

	public Object[] getParametri() {
		return parametri;
	}

	public void setParametri(final Object[] parametri) {
		this.parametri = parametri;
	}

	public IComponenteBase getComp() {
		return comp;
	}

	public void setComp(final IComponenteBase comp) {
		this.comp = comp;
	}

}
