package grafica.componenti.contenitori;

import java.awt.Component;
import java.awt.Container;

public class ContainerBase extends Container {

	private static final long serialVersionUID = 1L;

	/**
	 * Chiamare questo metodo al posto dell'add permette di ridimensionare il Container ogni
	 * volta che viene chiamato
	 * @param comp
	 * @return
	 */
	public Component addCustom(final Component comp, final Container container) {
		Component component = container.add(comp);
		int width = getMaxDimensionX(container);
		int height = getMaxDimensionY(container);
		container.setSize(width, height);
		container.setPreferredSize(container.getSize());
		return component;
	}

	public int getMaxDimensionX(final Container container) {
		int maxX = 0;
		Component[] componenti = container.getComponents();
		for (Component componente : componenti) {
			int x = (int) (componente.getLocation().getX() + componente.getWidth());
			if (x > maxX) {
				maxX = x;
			}
		}
		return maxX;
	}

	public int getMaxDimensionY(final Container container) {
		int maxY = 0;
		Component[] componenti = container.getComponents();
		for (Component componente : componenti) {
			int y = (int) (componente.getLocation().getY() + componente.getHeight());
			if (y > maxY) {
				maxY = y;
			}
		}
		return maxY;
	}
}
