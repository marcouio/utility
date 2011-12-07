package grafica.componenti.contenitori;

import grafica.componenti.alert.DialogoBase;
import grafica.componenti.componenteBase.IComponenteBase;
import grafica.componenti.label.LabelBase;

import java.awt.Component;
import java.awt.Container;

import javax.swing.JPanel;

public class ContainerBase extends Container {

	private static final long serialVersionUID = 1L;

	/**
	 * Chiamare questo metodo al posto dell'add permette di ridimensionare il Container ogni
	 * volta che viene chiamato. Al momento non funziona perché il settaggio delle dimensioni dei campi
	 * che sta aggiungendo avvengono dopo
	 * @param comp
	 * @return
	 */
	//	public Component addCustom(final Component comp, final Container container) {
	//		Component component = container.add(comp);
	//		int width = getMaxDimensionX(container);
	//		int height = getMaxDimensionY(container);
	//		container.setSize(width, height);
	//		container.setPreferredSize(container.getSize());
	//		return component;
	//	}

	public int getMaxDimensionX(final Container container) {
		int maxX = 0;
		Component[] componenti = null;
		if (container instanceof PannelloBase || container instanceof JPanel) {
			componenti = container.getComponents();
		} else if (container instanceof DialogoBase || container instanceof FrameBase) {
			componenti = ((DialogoBase) container).getContentPane().getComponents();
		}
		if (componenti != null) {
			for (Component componente : componenti) {
				int larghezzaComponente = componente.getWidth();
				if (componente instanceof LabelBase) {
					LabelBase compLabel = ((LabelBase) componente);
					larghezzaComponente = compLabel.getLarghezzaSingleStringa(getGraphics(), compLabel.getText(), componente);
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
		Component[] componenti = null;
		if (container instanceof JPanel) {
			componenti = container.getComponents();
		} else if (container instanceof DialogoBase || container instanceof FrameBase) {
			componenti = ((DialogoBase) container).getContentPane().getComponents();
		}
		if (componenti != null) {
			for (Component componente : componenti) {
				int altezzaComponente = componente.getHeight();
				if (componente instanceof IComponenteBase) {
					IComponenteBase compLabel = ((IComponenteBase) componente);
					altezzaComponente = compLabel.getAltezzaSingleStringa(getGraphics(), componente);
				}

				int y = (int) (componente.getLocation().getY() + altezzaComponente);
				if (y > maxY) {
					maxY = y;
				}
			}
		}
		return maxY + 3;
	}
}
