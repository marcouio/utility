package aggiornatori;

import grafica.componenti.IComponenteBase;

public class AggiornatoreBase implements IAggiornatore {
	
	private Object[] parametri;
	private IComponenteBase comp;
	
	public AggiornatoreBase() {
	}

	@Override
	public boolean aggiorna() {
		if(comp!=null){
			if(comp.repaintCustomizzato(parametri)){
				return true;
			}
		}
		return false;
	}

	public Object[] getParametri() {
		return parametri;
	}

	public void setParametri(Object[] parametri) {
		this.parametri = parametri;
	}

	public IComponenteBase getComp() {
		return comp;
	}

	public void setComp(IComponenteBase comp) {
		this.comp = comp;
	}

}
