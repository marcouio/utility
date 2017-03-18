package main.java.com.molinari.utility.graphic.component.buttonpanel;

import main.java.com.molinari.utility.graphic.ExceptionGraphics;
import main.java.com.molinari.utility.graphic.component.button.ToggleBtnBase;
import main.java.com.molinari.utility.graphic.component.container.PannelloBase;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class PannelloBottoni extends PannelloBase implements ActionListener{

	private static final long	serialVersionUID	= 1L;
	private final ButtonGroup gruppoBottoni = new ButtonGroup();
	protected final ArrayList<Bottone> listaBottoni = new ArrayList<Bottone>();


	public PannelloBottoni(final Container contenitore) {
		super(contenitore);
	}

	public PannelloBottoni(final ArrayList<Bottone> bottoni, final Container contenitore) {
		super(contenitore);

		for (final Bottone toggleBtn : bottoni) {
			listaBottoni.add(toggleBtn);
			this.addBottone(toggleBtn);
		}
	}

	public void addBottone(final Bottone bottone) {
		listaBottoni.add(bottone);
		gruppoBottoni.add(bottone.getBottone());
		posiziona(bottone);
		bottone.getBottone().addActionListener(this);

	}

	private void posiziona(final Bottone bottone) {
		int last = listaBottoni.size();
		if(last>1){
			Bottone bottoneCompare = listaBottoni.get(last-2);
			bottone.posizionaADestraDi(bottoneCompare, 0, 0);
		}else{
			bottone.posizionaADestraDi(null, 0, 0);
		}
	}

	public void deselezionaBottoni() {
		for (final Bottone bottone : listaBottoni) {
			if(bottone.getPanelInterno() != null){
				bottone.getPanelInterno().setVisible(false);
			}
			if (bottone.getBottone() != null) {
				bottone.getBottone().setSelected(false);
			}
			bottone.setVisible(true);
			gruppoBottoni.clearSelection();
		}

	}

	public static void addPulsanti(final PannelloBottoni pb) throws ExceptionGraphics{
		final Bottone primo = new Bottone(pb);
		PannelloBottoniInterno internoPrimo = new PannelloBottoniInterno(primo, null, "Uno", null, "Due", null);
		primo.setPanelInterno(internoPrimo);
		primo.setSize(150, 150);

		final ToggleBtnBase bottoniPrimo = new ToggleBtnBase("Primo", new ImageIcon("/home/kiwi/Immagini/prova.png"), primo, primo);
		bottoniPrimo.setSize(150, 150);
		bottoniPrimo.settaggioBottoneStandard();
		primo.setBottone(bottoniPrimo);

		pb.addBottone(primo);
		primo.posizionaSottoA(null, 0, 0);

		final Bottone secondo = new Bottone(pb);
		PannelloBottoniInterno internoSecondo = new PannelloBottoniInterno(secondo, null, "Uno", null, "Due", null);
		secondo.setPanelInterno(internoSecondo);
		secondo.setSize(150, 150);
		final ToggleBtnBase bottoniSecondo = new ToggleBtnBase("Secondo", new ImageIcon("/home/kiwi/Immagini/prova.png"), secondo, secondo);
		bottoniSecondo.setSize(150, 150);
		bottoniSecondo.settaggioBottoneStandard();
		secondo.setBottone(bottoniSecondo);

		pb.addBottone(secondo);
		secondo.posizionaADestraDi(primo, 0, 0);

		final Bottone terzo = new Bottone(pb);
		PannelloBottoniInterno internoTerzo = new PannelloBottoniInterno(terzo, null, "Uno", null, "Due", null);
		terzo.setPanelInterno(internoTerzo);
		terzo.setSize(150, 150);
		final ToggleBtnBase bottoniTerzo = new ToggleBtnBase("Terzo", new ImageIcon("/home/kiwi/Immagini/prova.png"), terzo, terzo);
		bottoniTerzo.setSize(150, 150);
		bottoniTerzo.settaggioBottoneStandard();
		terzo.setBottone(bottoniTerzo);

		pb.addBottone(terzo);
		terzo.posizionaADestraDi(secondo, 0, 0);
	}

	public static void main(final String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				final JFrame inst = new JFrame();
				inst.setBounds(0, 0, 1000, 650);
				PannelloBottoni pb = new PannelloBottoni(inst);
				try {
					PannelloBottoni.addPulsanti(pb);
				} catch (ExceptionGraphics e) {
					e.printStackTrace();
				}
				pb.posizionaSottoA(null, 0, 0);
				pb.setSize(500, 200);
				inst.setTitle("PannelloBottoni");
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
				inst.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		});
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		deselezionaBottoni();
		Bottone b = null;
		if (e.getSource() instanceof ToggleBtnBase) {
			ToggleBtnBase togglebtn = (ToggleBtnBase) e.getSource();
			b = ((Bottone) togglebtn.getPadre());
			togglebtn.setSelected(true);
			if (b != null) {
				if(b.getPanelInterno() != null){
					togglebtn.setVisible(false);
					b.getPanelInterno().setVisible(true);
				}
			}
		}
	} 

	public ArrayList<Bottone> getListaBottoni() {
		return listaBottoni;
	}

}
