package grafica.componenti.pannelloBottoni;

import grafica.componenti.button.ToggleBtn;

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

/**
 * PannelloBottoni è un pannello/ascoltatore che al proprio interno può
 * contenere n oggetti Bottone. La classe risulta poco affidabile e poco
 * generalizzabile, va ripensata
 * 
 */
public class PannelloBottoni extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	ButtonGroup gruppoBottoni = new ButtonGroup();
	protected final ArrayList<Bottone> listaBottoni = new ArrayList<Bottone>();

	public static final int MODE_PIENO = 0;

	// public static final int MODE_VUOTO = 1;

	public PannelloBottoni() {
		init();
		this.setBorder(null);
	}

	public PannelloBottoni(final ArrayList<Bottone> bottoni) {

		init();

		for (final Bottone toggleBtn : bottoni) {
			listaBottoni.add(toggleBtn);
			this.addBottone(toggleBtn);
		}

	}

	public PannelloBottoni(final int mode) {
		init();

		final ToggleBtn bottoni3 = new ToggleBtn("Primo", new ImageIcon("/home/kiwi/Immagini/prova.png"));
		bottoni3.settaggioBottoneStandard();
		final Bottone b3 = new Bottone(bottoni3);
		final ToggleBtn bottoni2 = new ToggleBtn("Secondo", new ImageIcon("/home/kiwi/Immagini/prova.png"));

		bottoni2.settaggioBottoneStandard();
		final Bottone b2 = new Bottone(bottoni3);
		final ToggleBtn bottoni = new ToggleBtn("Terzo", new ImageIcon("/home/kiwi/Immagini/prova.png"));
		bottoni.settaggioBottoneStandard();
		final Bottone b = new Bottone(bottoni3);

		this.addBottone(b3);
		this.addBottone(b2);
		this.addBottone(b);
	}

	private void init() {
		final GridLayout grid = new GridLayout(1, 0, 0, 0);
		grid.setHgap(0);
		this.setLayout(grid);
	}

	public void addBottone(final Bottone bottone) {
		final JPanel p = new JPanel();
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
		for (final Bottone bottone : listaBottoni) {
			bottone.getContent().setVisible(false);
			if (bottone.getTBottone() != null) {
				bottone.getTBottone().setSelected(false);
			}
			gruppoBottoni.clearSelection();
			bottone.setLayout(new GridLayout(2, 1, 0, 0));
		}

	}

	public static void main(final String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				final JFrame inst = new JFrame();
				inst.setBounds(0, 0, 1000, 650);
				inst.add(new PannelloBottoni(MODE_PIENO));
				inst.setTitle("PannelloBottoni");
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		deselezionaBottoni();
		Bottone b = null;
		if (e.getSource() instanceof ToggleBtn) {
			b = ((Bottone) ((ToggleBtn) e.getSource()).getPadre());
			((ToggleBtn) e.getSource()).setSelected(true);
		}
		if (b != null) {
			b.getContent().setVisible(true);
		}
		b.setLayout(new GridLayout(4, 1));
	}

	public ArrayList<Bottone> getListaBottoni() {
		return listaBottoni;
	}

	@Override
	protected void paintComponent(final Graphics g) {
		super.paintComponent(g);
	}
}