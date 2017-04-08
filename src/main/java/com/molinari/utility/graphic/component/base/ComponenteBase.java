package com.molinari.utility.graphic.component.base;

import java.awt.Component;
import java.awt.Container;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.text.JTextComponent;

import com.molinari.utility.controller.ControlloreBase;
import com.molinari.utility.graphic.component.style.StyleBase;
import com.molinari.utility.xml.CoreXMLManager;

/**
 * La classe è per uso privato all'interno degli oggetti grafici base. Non va
 * chiamata direttamente ne la classe, ne nessun suo metodo
 * 
 * @author marco.molinari
 * 
 */
public class ComponenteBase extends Component implements IComponenteBase {

	private static final long serialVersionUID = 1L;
	public static final int HEIGHT_STRING_DEFAULT = 30;
	public static final int WIDTH_STRING_DEFAULT = 100;
	public static final int HEIGHT_DEFAULT = 100;
	public static final int WIDTH_DEFAULT = 100;
	public static final int WIDTH_STRING_MIN = 5;
	public static final int HEIGHT_STRING_MIN = 5;
	private IComponenteBase padre;
	protected StyleBase style = new StyleBase();

	public ComponenteBase(final IComponenteBase padre) {
		this.setPadre(padre);
	}

	/**
	 * Se parametri non sono null aggiunge il component nel container
	 * 
	 * @param contenitorePadre2
	 * @param componenteFiglio
	 */
	public void aggiungiAlContenitore(final Container contenitorePadre2, final Component componenteFiglio) {
		contenitorePadre2.add(componenteFiglio);
		if (contenitorePadre2.getComponentCount() == 1) {
			componenteFiglio.setLocation(contenitorePadre2.getX(), contenitorePadre2.getY());
		}
	}

	@Override
	public void init(final Container contenitorePadre2, final Component componenteFiglio) {
		aggiungiAlContenitore(contenitorePadre2, componenteFiglio);
		componenteFiglio.setLocation(0, 0);
		if (!(componenteFiglio instanceof Container)) {
			componenteFiglio.setSize(WIDTH_STRING_DEFAULT, HEIGHT_STRING_DEFAULT);
		}
	}
	
	@Override
	public boolean repaintCustomizzato(final Object[] parametri) {
		return false;
	}
	
	public void ridisegna(IComponenteBase componente){
		 ((Component) componente).invalidate();
		 ((Component) componente).validate();
		 ((Component) componente).repaint();
		 componente.getContenitorePadre().invalidate();
		 componente.getContenitorePadre().revalidate();
		 componente.getContenitorePadre().repaint();

	}

	public boolean repaintCustomizzato(final Object[] parametri, IComponenteBase componente) {
		if (!checkPreliminariForRepaint(parametri, componente)) {
			return false;
		}
		return true;
	}

	private boolean checkPreliminariForRepaint(final Object[] parametri, IComponenteBase componente) {
		if (parametri == null || parametri.length == 0) {
			return false;
		}
		if (parametri.length > 1 && modelIsNull(componente, parametri[IComponenteBase.PARAM_REPAINT_MODEL])) {
			return false;
		}
		return true;
	}

	private boolean modelIsNull(final Object objForRepaint, final Object model) {
		if ((objForRepaint instanceof JTree || objForRepaint instanceof JComboBox || objForRepaint instanceof JTable) && model == null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean posizionaADestraDi(final Component componenteParagone, final int distanzaOrizzantale, final int distanzaVerticale, final Component compDaPosizionare) {
		int nuovaX = distanzaOrizzantale;
		int nuovaY = distanzaVerticale;

		if (componenteParagone != null) {
			final Point location = componenteParagone.getLocation();
			nuovaX = (int) (location.getX() + componenteParagone.getWidth() + distanzaOrizzantale);
			nuovaY = (int) (location.getY() + distanzaVerticale);
		}

		compDaPosizionare.setLocation(nuovaX, nuovaY);
		return true;
	}

	@Override
	public boolean posizionaASinistraDi(final Component componenteParagone, final int distanzaOrizzontale, final int distanzaVerticale, final Component compDaPosizionare) {

		int nuovaX = -distanzaOrizzontale;
		int nuovaY = distanzaVerticale;

		if (componenteParagone != null) {
			final Point location = componenteParagone.getLocation();
			nuovaX = (int) (location.getX() - compDaPosizionare.getWidth() - distanzaOrizzontale);
			nuovaY = (int) (location.getY() + distanzaVerticale);
		}
		compDaPosizionare.setLocation(nuovaX, nuovaY);
		return true;
	}

	@Override
	public boolean posizionaSottoA(final Component componenteParagone, final int distanzaOrizzantale, final int distanzaVerticale, final Component compDaPosizionare) {
		int nuovaX = distanzaOrizzantale;
		int nuovaY = distanzaVerticale;

		if (componenteParagone != null) {
			final Point location = componenteParagone.getLocation();
			nuovaX = (int) (location.getX() + distanzaOrizzantale);
			nuovaY = (int) (location.getY() + componenteParagone.getHeight() + distanzaVerticale);
		}

		compDaPosizionare.setLocation(nuovaX, nuovaY);
		return true;
	}

	@Override
	public boolean posizionaSopraA(final Component componenteParagone, final int distanzaOrizzantale, final int distanzaVerticale, final Component compDaPosizionare) {
		int nuovaX = distanzaOrizzantale;
		int nuovaY = -distanzaVerticale;

		if (componenteParagone != null) {
			final Point location = componenteParagone.getLocation();
			nuovaX = (int) (location.getX() + distanzaOrizzantale);
			nuovaY = (int) (location.getY() - compDaPosizionare.getHeight() - distanzaVerticale);
		}

		compDaPosizionare.setLocation(nuovaX, nuovaY);
		return true;
	}

	public int getLarghezzaSingleStringa(Graphics graphics, final String label, final Component compDaPosizionare, final boolean setDefault) {
		Graphics g = graphics;
		int larghezza = 0;
		g = trovaUnGraphicsValido(g, compDaPosizionare);
		if (g != null && compDaPosizionare.getFont() != null) {
			final FontMetrics fm = g.getFontMetrics(compDaPosizionare.getFont());
			larghezza = fm.stringWidth(label);
		}
		if (setDefault) {
			return larghezza > ComponenteBase.WIDTH_STRING_MIN ? larghezza + 3 : ComponenteBase.WIDTH_STRING_DEFAULT;
		}
		return larghezza;
	}

	/**
	 * Se la larghezza è maggiore del minimo, restituisce la larghezza + 3
	 * altrimenti setta il default
	 * 
	 * @param g
	 * @param label
	 * @param compDaPosizionare
	 * @return
	 */
	public int getLarghezzaSingleStringa(final Graphics g, final String label, final Component compDaPosizionare) {
		return getLarghezzaSingleStringa(g, label, compDaPosizionare, true);
	}

	public int getAltezzaSingleStringa(Graphics graphics, final Component compDaPosizionare) {
		Graphics g = graphics;
		
		int altezza = 0;
		g = trovaUnGraphicsValido(g, compDaPosizionare);
		if (g != null && compDaPosizionare.getFont() != null) {
			final FontMetrics fm = g.getFontMetrics(compDaPosizionare.getFont());
			altezza = fm.getHeight();
		}
		return altezza > ComponenteBase.HEIGHT_STRING_MIN ? altezza + 3 : ComponenteBase.HEIGHT_STRING_DEFAULT;
	}

	public Graphics trovaUnGraphicsValido(Graphics graphics, final Component compDaPosizionare) {
		Graphics g = graphics;
		if (g == null) {
			if (compDaPosizionare.getParent() != null) {
				g = compDaPosizionare.getParent().getGraphics();
			}
			if (g == null) {
				g = ControlloreBase.getApplicationGraphics2d();
			}
		}
		return g;
	}

	@Override
	public void applicaStile(final StyleBase style, final IComponenteBase padre) {
		if (style != null) {
			this.style = style;
		}
		this.style.setPadre(padre);
		final Component padreComponent = (Component) padre;
		padreComponent.setFont(this.style.getFont());
		padreComponent.setForeground(this.style.getForeground());
		if(this.style.getBackground() != null){
			padreComponent.setBackground(this.style.getBackground());
		}
		padreComponent.setSize(this.style.getWidth(), this.style.getHeight());
		if (CoreXMLManager.getSingleton().isAutoConfig() && ihaveToSetDimension(this.style, padreComponent)) {
			final int width = ((IComponenteBase) padreComponent).getLarghezza();
			final int height = ((IComponenteBase) padreComponent).getAltezza();
			padreComponent.setSize(width, height);
		}
	}

	public boolean ihaveToSetDimension(final StyleBase style, final Component padreComponent) {
		boolean dimensionNull = style.getWidth() == 0 && style.getHeight() == 0;
		if (padreComponent instanceof JTextComponent && dimensionNull) {
			return padreComponent.getWidth() != 0 && padreComponent.getHeight() != 0;
		}
		return false;
	}

	@Override
	public int getLarghezza() {
		return WIDTH_DEFAULT;
	}

	@Override
	public int getAltezza() {
		return HEIGHT_DEFAULT;
	}

	@Override
	public Container getContenitorePadre() {
		if (getPadre() instanceof Container) {
			return (Container) getPadre();
		}
		return null;

	}

	@Override
	public void makeGUI(Container contenitorePadre) {
		//do nothing
	}

	public IComponenteBase getPadre() {
		return padre;
	}

	public void setPadre(IComponenteBase padre) {
		this.padre = padre;
	}
}
