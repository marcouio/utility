package test.pannellobottoni;

import java.awt.GridLayout;

import javax.swing.AbstractButton;
import javax.swing.JPanel;

public class Bottone extends JPanel {

	private static final long serialVersionUID = 1L;
	private AbstractButton    bottone;
	private PannelloBottoni   contenuto;
	private boolean           isEspanso;

	public static final int   RIEMPITO         = 0;

	public Bottone() {
		init();
	}

	public Bottone(AbstractButton bottone) {
		init();
		this.bottone = bottone;
		this.add(bottone);
	}

	public Bottone(PannelloBottoni contenuto) {
		init();
		this.contenuto = contenuto;
		this.add(contenuto);
	}

	public Bottone(PannelloBottoni contenuto, AbstractButton bottone) {
		init();
		this.contenuto = contenuto;
		this.bottone = bottone;
	}

	private void init() {
		this.setLayout(new GridLayout(2, 1));
		if (contenuto != null) {
			this.contenuto.setLayout(new GridLayout(1, 0));
		}
	}

	public void espandi() {
		setEspanso(true);
		this.setLayout(new GridLayout(4, 1));
		if (contenuto != null) {
			contenuto.setVisible(true);
		}
		revalidate();
		repaint();
	}

	public void contrai() {
		setEspanso(false);
		if (contenuto != null) {
			contenuto.setVisible(false);
		}
	}

	protected AbstractButton getBottone() {
		return bottone;
	}

	protected void setBottone(AbstractButton bottone) {
		this.bottone = bottone;
	}

	protected PannelloBottoni getContenuto() {
		return contenuto;
	}

	protected void setContenuto(PannelloBottoni contenuto) {
		this.contenuto = contenuto;
		add(contenuto);
		this.contenuto.setVisible(false);
	}

	public void setEspanso(boolean isEspanso) {
		this.isEspanso = isEspanso;
	}

	public boolean isEspanso() {
		return isEspanso;
	}

}