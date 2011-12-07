package grafica.componenti.alert;

import grafica.componenti.IComponenteBase;
import grafica.componenti.UtilComponenti;
import grafica.componenti.button.ButtonBase;
import grafica.componenti.componenteBase.ComponenteBase;
import grafica.componenti.contenitori.ContainerBase;
import grafica.componenti.contenitori.FrameBase;
import grafica.componenti.contenitori.IContainerBase;
import grafica.componenti.label.Label;
import grafica.componenti.style.StyleBase;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;

public class DialogoBase extends JDialog implements IComponenteBase, IContainerBase {

	private final ContainerBase containerBase = new ContainerBase();
	private final ComponenteBase componenteBase = new ComponenteBase();
	protected StyleBase style = new StyleBase();
	private static final long serialVersionUID = 1L;
	private Container padre;
	private int opzioneScelta = -1;

	private DialogoBase(final JFrame frame) {
		super(frame);
		init(null, this);
		this.padre = frame;
	}

	private DialogoBase(final Dialog owner) {
		super(owner);
		init(null, this);
		this.padre = owner;
	}

	public StyleBase getStyle() {
		return style;
	}

	public void setStyle(final StyleBase style) {
		this.style = style;
	}

	public class Builder extends AbstractBuilderBase {

		public Builder(final Container padre) {
			if (padre instanceof JFrame) {
				dialogo = new DialogoBase((JFrame) padre);
			} else if (padre instanceof Dialog) {
				dialogo = new DialogoBase((Dialog) padre);
			}
			dialogo.padre = padre;
		}

		@Override
		public DialogoBase creaDialogo() {
			super.creaDialogo();

			dialogo.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialogo.setVisible(true);

			return dialogo;
		}

		@Override
		protected Object creaDialogButtonBar(final int[] listaBottoni2, final Object labelIcona, final Object labelMessaggio) {
			ButtonBase buttonOld = null;
			for (int i = 0; i < listaBottoni2.length; i++) {
				int buttonPresente = listaBottoni2[i];
				if (buttonPresente != -1) {
					ButtonBase button = chiamaMetodoPerCreazioneButton(buttonPresente);
					if (i == 0) {
						if (labelIcona != null) {
							button.posizionaSottoA((Label) labelIcona, 0, 20);
						} else if (labelMessaggio != null) {
							button.posizionaSottoA((Label) labelMessaggio, 0, 20);
						}
					} else {
						button.posizionaADestraDi(buttonOld, 10, 0);
					}
					buttonOld = button;
					System.out.println(button.getLocation());
				}
			}
			return null;
		}

		@Override
		protected Object creaDialogLocation(final Dimension dimensioni2) {
			int y = ((padre.getY() + padre.getHeight()) / 2) - (dialogo.getHeight() / 2);
			int x = ((padre.getX() + padre.getWidth()) / 2) - (dialogo.getWidth() / 2);
			Point puntoLocation = new Point(x, y);
			dialogo.setLocation(puntoLocation);
			return puntoLocation;
		}

		@Override
		protected Object creaDialogoDimensioni(final Dimension dimensioni2) {
			if (dimensioni2 != null) {
				dialogo.setSize(dimensioni2);
			} else {
				dialogo.setSize(dialogo.getMaxDimensionX(), dialogo.getMaxDimensionY());
			}
			return dimensioni2;
		}

		@Override
		protected Object creaDialogoIcona(final Icon icon2) {
			return new Label(icon2, dialogo);
		}

		@Override
		protected Object creaDialogoMessaggio(final String message2, final Object iconaLabel) {
			Label labelMessaggio = new Label(message2, dialogo);
			if (iconaLabel != null) {
				labelMessaggio.posizionaSottoA((Component) iconaLabel, 10, 0);
			}
			return labelMessaggio;
		}

		@Override
		protected Object creaDialogoTitolo(final String titolo2) {
			dialogo.setTitle(titolo2);
			return titolo2;
		}
	}

	public Builder getBuilder() {
		return new Builder(padre);
	}

	public static void main(final String[] args) {
		final FrameBase panello = UtilComponenti.initContenitoreFrameApplicazione(null, null);
		ButtonBase bottone = new ButtonBase("premi qui!", panello);
		bottone.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {

				Builder builder = new DialogoBase(panello).getBuilder();
				builder.setMessaggio("Questo è un messaggio veramente lungo");
				builder.setTitolo("Titolo");
				builder.addPositiveButton();
				builder.addNegativeButton();
				builder.addCancelButton();
				DialogoBase dialogo = builder.creaDialogo();
			}
		});
	}

	@Override
	public int getMaxDimensionX() {
		return containerBase.getMaxDimensionX(this.getContentPane());
	}

	@Override
	public int getMaxDimensionY() {
		return containerBase.getMaxDimensionY(this.getContentPane());
	}

	@Override
	public void init(final Container contenitorePadre2, final Component componenteFiglio) {
		this.setLayout(null);
		componenteBase.init(contenitorePadre2, componenteFiglio);
		this.settaStile();

	}

	@Override
	public boolean repaintCustomizzato(final Object[] parametri) {
		return componenteBase.repaintCustomizzato(parametri);
	}

	/**
	 * Metodo facade di metodo omonimo per facilitarne l'accesso e la leggibilita'
	 * 
	 * @param componenteParagone
	 * @param distanzaOrizzantale
	 * @param distanzaVerticale
	 * @return
	 */
	public boolean posizionaADestraDi(final Component componenteParagone, final int distanzaOrizzantale, final int distanzaVerticale) {
		return posizionaADestraDi(componenteParagone, distanzaOrizzantale, distanzaVerticale, this);
	}

	/**
	 * Metodo facade di metodo omonimo per facilitarne l'accesso e la leggibilita'
	 * 
	 * @param componenteParagone
	 * @param distanzaOrizzantale
	 * @param distanzaVerticale
	 * @return
	 */
	public boolean posizionaASinistraDi(final Component componenteParagone, final int distanzaOrizzantale, final int distanzaVerticale) {
		return posizionaASinistraDi(componenteParagone, distanzaOrizzantale, distanzaVerticale, this);
	}

	/**
	 * Metodo facade di metodo omonimo per facilitarne l'accesso e la leggibilita'
	 * 
	 * @param componenteParagone
	 * @param distanzaOrizzantale
	 * @param distanzaVerticale
	 * @return
	 */
	public boolean posizionaSottoA(final Component componenteParagone, final int distanzaOrizzantale, final int distanzaVerticale) {
		return posizionaSottoA(componenteParagone, distanzaOrizzantale, distanzaVerticale, this);
	}

	/**
	 * Metodo facade di metodo omonimo per facilitarne l'accesso e la leggibilita'
	 * 
	 * @param componenteParagone
	 * @param distanzaOrizzantale
	 * @param distanzaVerticale
	 * @return
	 */
	public boolean posizionaSopraA(final Component componenteParagone, final int distanzaOrizzantale, final int distanzaVerticale) {
		return posizionaSopraA(componenteParagone, distanzaOrizzantale, distanzaVerticale, this);
	}

	@Override
	public boolean posizionaADestraDi(final Component componenteParagone, final int distanzaOrizzantale, final int distanzaVerticale, final Component compDaPosizionare) {
		return componenteBase.posizionaADestraDi(componenteParagone, distanzaOrizzantale, distanzaVerticale, compDaPosizionare);
	}

	@Override
	public boolean posizionaASinistraDi(final Component componenteParagone, final int distanzaOrizzontale, final int distanzaVerticale, final Component compDaPosizionare) {
		return componenteBase.posizionaASinistraDi(componenteParagone, distanzaOrizzontale, distanzaVerticale, compDaPosizionare);
	}

	@Override
	public boolean posizionaSottoA(final Component componenteParagone, final int distanzaOrizzantale, final int distanzaVerticale, final Component compDaPosizionare) {
		return componenteBase.posizionaSottoA(componenteParagone, distanzaOrizzantale, distanzaVerticale, compDaPosizionare);
	}

	@Override
	public boolean posizionaSopraA(final Component componenteParagone, final int distanzaOrizzantale, final int distanzaVerticale, final Component compDaPosizionare) {
		return componenteBase.posizionaSopraA(componenteParagone, distanzaOrizzantale, distanzaVerticale, compDaPosizionare);
	}

	/**
	 * Metodo facade di metodo omonimo per facilitarne l'accesso e la leggibilita'
	 * 
	 * @param g
	 * @param label
	 * @return
	 */
	public int getLarghezzaSingleStringa(final Graphics g, final String label) {
		return getLarghezzaSingleStringa(g, label, this);
	}

	/**
	 * Metodo facade di metodo omonimo per facilitarne l'accesso e la leggibilita'
	 * 
	 * @param g
	 * @return
	 */
	public int getAltezzaSingleStringa(final Graphics g) {
		return getAltezzaSingleStringa(g, this);
	}

	@Override
	public int getLarghezzaSingleStringa(final Graphics g, final String label, final Component componenteDaRiposizionare) {
		return componenteBase.getLarghezzaSingleStringa(g, label, componenteDaRiposizionare);
	}

	@Override
	public int getAltezzaSingleStringa(final Graphics g, final Component componenteDaRiposizionare) {
		return componenteBase.getAltezzaSingleStringa(g, componenteDaRiposizionare);
	}

	@Override
	public void settaStile() {
		componenteBase.settaStile(style, this);
		if (settaStileOverride() != null) {
			style = settaStileOverride();
			componenteBase.settaStile(style, this);
		}
	}

	protected StyleBase settaStileOverride() {
		return new StyleBase("StyleBaseDialogo");
	}

	@Override
	public ContainerBase getContainerBase() {
		return containerBase;
	}

	public int getOpzioneScelta() {
		return opzioneScelta;
	}

	public void setOpzioneScelta(final int opzioneScelta) {
		this.opzioneScelta = opzioneScelta;
	}

}