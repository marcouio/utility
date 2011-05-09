package grafica.componenti;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class PannelloBottoni extends JPanel implements ActionListener {

	private static final long        serialVersionUID = 1L;
	ButtonGroup                      gruppoBottoni    = new ButtonGroup();
	private final ArrayList<Bottone> listaBottoni     = new ArrayList<Bottone>();

	public static final int          MODE_PIENO       = 0;

	// public static final int MODE_VUOTO = 1;

	public PannelloBottoni() {
		init();
		this.setBorder(null);
	}

	public PannelloBottoni(ArrayList<Bottone> bottoni) {

		init();

		for (Bottone toggleBtn : bottoni) {
			listaBottoni.add(toggleBtn);
			this.addBottone(toggleBtn);
		}

	}

	public PannelloBottoni(int mode) {
		init();

		ToggleBtn bottoni3 = new ToggleBtn("Primo", new ImageIcon(
		                "/home/kiwi/Immagini/prova.png"));
		bottoni3.settaggioBottoneStandard();
		Bottone b3 = new Bottone(bottoni3);
		ToggleBtn bottoni2 = new ToggleBtn("Secondo", new ImageIcon(
		                "/home/kiwi/Immagini/prova.png"));

		bottoni2.settaggioBottoneStandard();
		Bottone b2 = new Bottone(bottoni3);
		ToggleBtn bottoni = new ToggleBtn("Terzo", new ImageIcon(
		                "/home/kiwi/Immagini/prova.png"));
		bottoni.settaggioBottoneStandard();
		Bottone b = new Bottone(bottoni3);

		this.addBottone(b3);
		this.addBottone(b2);
		this.addBottone(b);
	}

	private void init() {
		GridLayout grid = new GridLayout(1, 0, 0, 0);
		grid.setHgap(0);
		this.setLayout(grid);
	}

	public void addBottone(Bottone bottone) {
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(1, 1, 0, 0));
		p.add(bottone);
		this.add(p);

		listaBottoni.add(bottone);
		if (bottone.getTBottone() != null) {
			gruppoBottoni.add(bottone.getTBottone());
		} else {
			gruppoBottoni.add(bottone.getJBottone());
		}

		if (bottone.getTBottone() != null) {
			bottone.getTBottone().addActionListener(this);
		} else if (bottone.getJBottone() != null) {
			bottone.getJBottone().addActionListener(this);
		}

	}

	public void deselezionaBottoni() {
		for (Bottone bottone : listaBottoni) {
			bottone.getContent().setVisible(false);
			if (bottone.getTBottone() != null)
				bottone.getTBottone().setSelected(false);
			gruppoBottoni.clearSelection();
			bottone.setLayout(new GridLayout(2, 1, 0, 0));
		}

	}

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame inst = new JFrame();
				inst.setBounds(0, 0, 1000, 650);
				inst.add(new PannelloBottoni(MODE_PIENO));
				inst.setTitle("PannelloBottoni");
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		deselezionaBottoni();
		Bottone b = null;
		if (e.getSource() instanceof ToggleBtn) {
			b = ((Bottone) ((ToggleBtn) e.getSource()).getPadre());
			((ToggleBtn) e.getSource()).setSelected(true);
		}
		if (b != null)
			b.getContent().setVisible(true);
		b.setLayout(new GridLayout(4, 1));
	}

	public ArrayList<Bottone> getListaBottoni() {
		return listaBottoni;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
}