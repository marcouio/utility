package grafica.componenti.contenitori.contenitoreBase;

import java.awt.Container;

import javax.swing.JFrame;

public class ContainerBaseFrame extends ContainerBase {

	private static final long serialVersionUID = 1L;

	@Override
	public int getMaxDimensionX(final Container container) {
		JFrame frame = (JFrame) container;
		Container cont = frame.getContentPane();
		return super.getMaxDimensionX(cont);
	}

	@Override
	public int getMaxDimensionY(final Container container) {
		JFrame frame = (JFrame) container;
		Container cont = frame.getContentPane();
		return super.getMaxDimensionY(cont);
	}

}
