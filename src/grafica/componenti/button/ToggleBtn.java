package grafica.componenti.button;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 * La classe estende JToggleButton per modificarne il funzionamento di default.
 * L'utilità sta nella flessibilità con cui si possono gestire rollover,
 * distanze, colori e icone
 * 
 * @author marco.molinari
 * 
 */
public class ToggleBtn extends JToggleButton {
	private static final long serialVersionUID = 1L;
	String testo;
	ImageIcon imageIcon;

	MyIcon icona;
	private JPanel padre;

	/**
	 * La distanza dell'immagine dal bordo ovest
	 * 
	 * @return int
	 */
	private int distanzaBordoImageX = 10;

	/**
	 * la coordinata x di partenza del testo
	 * 
	 * @return int
	 */
	private int xPartenzaTesto = 38;

	/**
	 * Colore del testo con il pulsante selezionato
	 */
	private Color colorForegroundSelected = Color.GRAY;

	/**
	 * Colore background con il pulsante selezionato
	 */
	private Color colorBackgroundSelected = new Color(167, 243, 239);

	/**
	 * dovrebbe settare il colore del testo nel rollover ma non viene preso! E'
	 * inutile settarlo!!!!
	 */
	private Color colorForegroundIconRollover = Color.GREEN;

	/**
	 * Colore background del rollover
	 */
	private Color colorIconRollover = new Color(252, 228, 179);

	public MyIcon getMyIcon() {
		return icona != null ? icona : new MyIcon();
	}

	public ToggleBtn(final String text, final ImageIcon icon, final int xDistanzaBordoImmagine, final int xPartenzaTesto) {
		this(text, icon);
		this.distanzaBordoImageX = xDistanzaBordoImmagine != -1 ? xDistanzaBordoImmagine : distanzaBordoImageX;
		this.xPartenzaTesto = xPartenzaTesto != -1 ? xPartenzaTesto : this.xPartenzaTesto;
	}

	public ToggleBtn(final String text) {
		super("");
		setxPartenzaTesto(10);
		testo = text;
	}

	public ToggleBtn(final String text, final ImageIcon icon, final JPanel padre) {
		this(text, icon);
		this.setPadre(padre);
	}

	public ToggleBtn(final String text, final ImageIcon icon) {
		super("", icon);
		testo = text;
		imageIcon = icon;
	}

	public ToggleBtn(final ImageIcon icon) {
		super(icon);
		imageIcon = icon;
	}

	@Override
	public void paintComponent(final Graphics g) {
		// setto l'icona a null per non farla disegnare dal super.paintcomponent
		this.setIcon(new ImageIcon());

		// sono costretto a fare il super del paintComponent per via del
		// rollover che altrimenti dovrei gestire io
		super.paintComponent(g);

		// invece di far disegnare il testo al paintcomponent ho settato il text
		// a "" nel costruttore e il testo lo disegno qui così ho un
		// maggiore controllo
		g.drawString(testo != null ? testo : "", getLarghezzaImmagine(imageIcon) + distanzaBordoImageX
				+ calcolaTextGap(imageIcon), (getHeight() + g.getFontMetrics().getAscent()) / 2 - 1);

		// reimposto l'icona a quella passata e la disegno per il funzionamento
		// default
		this.setIcon(imageIcon);
		if(imageIcon != null){
			getIcon().paintIcon(this, g, distanzaBordoImageX, getHeight() / 2 - imageIcon.getIconHeight() / 2);
		}

		if (this.isSelected()) {
			// effetto pulsante premuto ridefinito
			disegnaBottone(g, colorForegroundSelected, colorBackgroundSelected);
		}
	}// end paint

	/**
	 * metodo di disegno interno alla classe del bottone
	 * 
	 * @param g
	 * @param foreground
	 * @param selected
	 */
	private void disegnaBottone(final Graphics g, final Color foreground, final Color selected) {
		final int w = getWidth();
		final int h = getHeight();
		g.setColor(selected); // background color
		// disegna il background del bottone
		g.fillRoundRect(1, 1, w - 1, h - 1, 7, 7);
		g.setColor(Color.WHITE);
		// disegna il bordo
		g.drawRoundRect(1, 1, w - 2, h - 2, 7, 7);
		g.setColor(foreground); // foreground color
		if (imageIcon != null) {
			g.drawImage(imageIcon.getImage(), distanzaBordoImageX, getHeight() / 2 - imageIcon.getIconHeight() / 2,
					null);
		}
		g.drawString(testo != null ? testo : "", getLarghezzaImmagine(imageIcon) + distanzaBordoImageX
				+ calcolaTextGap(imageIcon), (h + g.getFontMetrics().getAscent()) / 2 - 1);
	}

	private int getLarghezzaImmagine(final Icon i) {
		if (i != null) {
			return i.getIconWidth();
		} else {
			return 0;
		}

	}

	// TODO metodo da verificare
	/**
	 * Setta la proprietà "xPartenzaTesto" sulla base delle impostazioni iniziali della proprietà, 
	 * sulla larghezza dell'immagine e la distanza dal bordo ovest dell'immagine
	 * 
	 * @param i
	 * @return
	 */
	private int calcolaTextGap(final Icon i) {
		if (i != null) {
			if (xPartenzaTesto > (i.getIconWidth() - distanzaBordoImageX)) {
				return xPartenzaTesto - (i.getIconWidth());
			} else {
				return 0;
			}
		} else {
			return xPartenzaTesto;
		}
	}

	/**
	 * Alcune scelte grafiche di settaggio.
	 */
	public void settaggioBottoneStandard() {

		// final Border bordo =
		// BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.GRAY,
		// Color.LIGHT_GRAY);
		// Border bordo = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
		// Color.RED, Color.GREEN);
		this.setBorder(null);
		this.setRolloverEnabled(true);
		this.setBackground(Color.WHITE);
		this.setHorizontalAlignment(SwingConstants.LEFT); // allinea il
															// contenuto a
															// sinitra
		if (this instanceof ToggleBtn) {
			final Icon icona1 = (this).getMyIcon();
			this.setRolloverIcon(icona1);
		}
		this.setRolloverEnabled(true);
	}

	public void setPadre(final JPanel padre) {
		this.padre = padre;
	}

	public JPanel getPadre() {
		return padre;
	}

	public String getTesto() {
		return testo;
	}

	public void setTesto(final String s) {
		this.testo = s;
	}

	public ImageIcon getImageIcon() {
		return imageIcon;
	}

	public void setImageIcon(final ImageIcon i) {
		this.imageIcon = i;
	}

	public MyIcon getIcona() {
		return icona;
	}

	public void setIcona(final MyIcon icona) {
		this.icona = icona;
	}

	/**
	 * Restituisce la distanza dell'immagine dal bordo ovest
	 * 
	 * @return int
	 */
	public int getXDistanzaBordoImage() {
		return distanzaBordoImageX;
	}

	/**
	 * Setta la distanza dell'immagine dal bordo ovest
	 * 
	 * @param distanzaBordoImageX
	 */
	public void setXDistanzaBordoImage(final int distanzaBordoImageX) {
		this.distanzaBordoImageX = distanzaBordoImageX;
	}

	/**
	 * Prende la coordinata x di partenza del testo
	 * 
	 * @return int
	 */
	public int getxPartenzaTesto() {
		return xPartenzaTesto;
	}

	/**
	 * Setta la coordinata x di partenza del testo
	 * 
	 * @return
	 */
	public void setxPartenzaTesto(final int xPartenzaTesto) {
		this.xPartenzaTesto = xPartenzaTesto;
	}

	/**
	 * Restituisce il colore del testo con il pulsante è selezionato
	 * 
	 * @return
	 */
	public Color getColorForegroundSelected() {
		return colorForegroundSelected;
	}

	public void setColorForegroundSelected(final Color colorForeground) {
		this.colorForegroundSelected = colorForeground;
	}

	public Color getColorBackgroundSelected() {
		return colorBackgroundSelected;
	}

	public void setColorBackgroundSelected(final Color colorBackground) {
		this.colorBackgroundSelected = colorBackground;
	}

	/**
	 * Non serve ad un cazzo! il testo non cambia perché il rollover è gestito
	 * dal super e questo lascia il testo iniziale, per controllarlo dovrei
	 * gestire il rollover
	 * 
	 * @return
	 */
	public Color getColorForegroundIcon() {
		return colorForegroundIconRollover;
	}

	/**
	 * Non serve ad un cazzo! il testo non cambia perché il rollover è gestito
	 * dal super e questo lascia il testo iniziale, per controllarlo dovrei
	 * gestire il rollover
	 * 
	 * @return
	 */
	public void setColorForegroundIcon(final Color colorForegroundIcon) {
		this.colorForegroundIconRollover = colorForegroundIcon;
	}

	/**
	 * Restituisce il colore del background del rollover
	 * 
	 * @return colorIconRollover
	 */
	public Color getColorBackgroundIcon() {
		return colorIconRollover;
	}

	/**
	 * Setta il colore del background del rollover
	 * 
	 * @param colorBackgroundIcon
	 */
	public void setColorBackgroundIcon(final Color colorBackgroundIcon) {
		this.colorIconRollover = colorBackgroundIcon;
	}

	/**
	 * ﻿ * Estende la classe Icon per settarla come Icon di default al rollover
	 * di ﻿ * ToggleBtn ﻿
	 */
	private class MyIcon implements Icon {

		public MyIcon() {
			super();
		}

		@Override
		public int getIconHeight() {
			return getHeight();
		}

		@Override
		public int getIconWidth() {
			return getWidth();
		}

		@Override
		public void paintIcon(final Component c, final Graphics g, final int x, final int y) {
			if (g != null) {
				disegnaBottone(g, colorForegroundIconRollover, colorIconRollover);
			}
		}
	}

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

				ToggleBtn tb = new ToggleBtn("Ciao", new ImageIcon(
						"C:\\Documents and Settings\\marco.molinari\\Documenti\\Download\\Mio\\Immaginiplay.jpg"));
				tb.setBounds(10, 10, 200, 100);
				ToggleBtn tb2 = new ToggleBtn("Ciao2", new ImageIcon(
						"C:\\Documents and Settings\\marco.molinari\\Documenti\\Download\\Mio\\Immaginiplay.jpg"));
				tb2.setBounds(10, 140, 200, 100);
				JFrame frame = new JFrame();
				frame.getContentPane().add(tb);
				frame.getContentPane().add(tb2);
				frame.setLayout(null);
				frame.setVisible(true);
				frame.setSize(600, 600);
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		});
	}
}