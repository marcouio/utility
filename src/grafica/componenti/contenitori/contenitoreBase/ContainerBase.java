package grafica.componenti.contenitori.contenitoreBase;

import grafica.componenti.componenteBase.IComponenteBase;

import java.awt.Component;
import java.awt.Container;

public class ContainerBase extends Container {

	private static final long serialVersionUID = 1L;

	public int getMaxDimensionX(final Container container) {
		int maxX = 0;
		Component[] componenti = container.getComponents();
		if (componenti != null) {
			for (Component componente : componenti) {
				int larghezzaComponente = componente.getWidth();
				if (componente instanceof IComponenteBase) {
					IComponenteBase comp = (IComponenteBase) componente;
					if (larghezzaComponente < comp.getLarghezza()) {
						larghezzaComponente = comp.getLarghezza();
					}
				}
				int x = (int) (componente.getLocation().getX() + larghezzaComponente);
				if (x > maxX) {
					maxX = x;
				}
			}
		}
		return maxX + 3;
	}

	public int getMaxDimensionY(final Container container) {
		int maxY = 0;
		Component[] componenti = container.getComponents();
		if (componenti != null) {
			for (Component componente : componenti) {
				int altezzaComponente = componente.getHeight();
				if (componente instanceof IComponenteBase) {
					IComponenteBase compLabel = ((IComponenteBase) componente);
					if (altezzaComponente < compLabel.getAltezza()) {
						altezzaComponente = compLabel.getAltezza();
					}

				}

				int y = (int) (componente.getLocation().getY() + altezzaComponente);
				if (y > maxY) {
					maxY = y;
				}
			}
		}
		return maxY + 2;
	}
}
