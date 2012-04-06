package disegno.oggetti;

import grafica.componenti.contenitori.PannelloBase;

import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import disegno.oggetti.painter.IPainter;

public class Quadrato extends Rettangolo {

	public Quadrato(final String nome, final IPainter painter) {
		super(nome, painter);
	}

	public Quadrato(final String nome) {
		super(nome);
	}

	public Quadrato() {
		super();
	}

	public int getLato() {
		return getWidth();
	}

	public static void main(final String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				JFrame f = new JFrame();
				f.setVisible(true);
				f.getContentPane().add(new PannelloBase(f) {
					@Override
					protected void paintComponent(final Graphics g) {
						super.paintComponent(g);
						Rettangolo rettangolo = new Rettangolo("ret");
						Quadrato quadrato = new Quadrato();
						quadrato.setSize(50, 50);
						rettangolo.setSize(100, 50);
						rettangolo = quadrato;
						rettangolo.setSize(50, 20);
						rettangolo.draw(g);
						System.out.println(rettangolo.getArea());
					}
				});
				f.setBounds(0, 0, 600, 500);
				f.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(final WindowEvent event) {
						System.exit(0);
					}
				});
			}
		});
		
	}
}
