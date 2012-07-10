package grafica.componenti.componenteBase;

import grafica.componenti.ExceptionGraphics;
import grafica.componenti.style.StyleBase;

import java.awt.Component;
import java.awt.Container;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import xml.CoreXMLManager;
import controller.ControlloreBase;

/**
 * La classe è per uso privato all'interno degli oggetti grafici base. Non va chiamata direttamente ne la classe,
 * ne nessun suo metodo
 * 
 * @author marco.molinari
 *
 */
public class ComponenteBase extends Component implements IComponenteBase {

	private static final long serialVersionUID = 1L;
	public static final int HEIGHT_STRING_DEFAULT = 30;
	public static final int WIDTH_STRING_DEFAULT = 100;
	public static final int WIDTH_STRING_MIN = 5;
	public static final int HEIGHT_STRING_MIN = 5;
	
	/**
	 * Se parametri non sono null aggiunge il component nel container
	 * @param contenitorePadre2
	 * @param componenteFiglio
	 */
	public void aggiungiAlContenitore(final Container contenitorePadre2, final Component componenteFiglio) {
		contenitorePadre2.add(componenteFiglio);
		if (contenitorePadre2.getComponentCount() == 1) {
			componenteFiglio.setLocation(contenitorePadre2.getX() + 10, contenitorePadre2.getY() + 10);
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
		if (!checkPreliminariForRepaint(parametri)) {
			return false;
		}
		Object objForRepaint = parametri[IComponenteBase.PARAM_REPAINT_OBJ_REPAINT];
		//JTREE
		if (objForRepaint instanceof JTree) {
			TreeModel treeModel = (DefaultTreeModel) parametri[IComponenteBase.PARAM_REPAINT_MODEL];
			((JTree) objForRepaint).setModel(treeModel);
			//JSCROLLPANE
		} else if (objForRepaint instanceof JScrollPane) {
			if (parametri[IComponenteBase.PARAM_REPAINT_OBJ_CONTENT] == null) {
				return false;
			}
			Component comp = (Component) parametri[IComponenteBase.PARAM_REPAINT_OBJ_CONTENT];
			((JScrollPane) objForRepaint).setViewportView(comp);
			//JCOMBOBOX
		} else if (objForRepaint instanceof JComboBox) {
			DefaultComboBoxModel comboModel = (DefaultComboBoxModel) parametri[IComponenteBase.PARAM_REPAINT_MODEL];
			((JComboBox) objForRepaint).setModel(comboModel);
			//JTABLE
		} else if (objForRepaint instanceof JTable) {
			AbstractTableModel tableModel = (DefaultTableModel) parametri[IComponenteBase.PARAM_REPAINT_MODEL];
			((JTable) objForRepaint).setModel(tableModel);
		}
		((Component) objForRepaint).invalidate();
		((Component) objForRepaint).validate();
		((Component) objForRepaint).repaint();

		return true;
	}

	private boolean checkPreliminariForRepaint(final Object[] parametri) {
		if (parametri == null || parametri.length == 0) {
			return false;
		}
		if (parametri[IComponenteBase.PARAM_REPAINT_OBJ_REPAINT] == null) {
			return false;
		}
		if (parametri.length > 1 && modelIsNull(parametri[IComponenteBase.PARAM_REPAINT_OBJ_REPAINT], parametri[IComponenteBase.PARAM_REPAINT_MODEL])) {
			return false;
		}
		return true;
	}

	private boolean modelIsNull(final Object objForRepaint, final Object model) {
		if (objForRepaint instanceof JTree || objForRepaint instanceof JComboBox || objForRepaint instanceof JTable) {
			if (model == null) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean posizionaADestraDi(final Component componenteParagone, final int distanzaOrizzantale, final int distanzaVerticale, final Component compDaPosizionare) {
		int nuovaX = 0;
		int nuovaY = 0;
		if(componenteParagone != null){
			Point location = componenteParagone.getLocation();
			nuovaX = (int) (location.getX() + componenteParagone.getWidth() + distanzaOrizzantale);
			nuovaY = (int) (location.getY() + distanzaVerticale);
		}else{
			nuovaX = distanzaOrizzantale;
			nuovaY = distanzaVerticale;
		}
		compDaPosizionare.setLocation(nuovaX, nuovaY);
		return true;
	}

	@Override
	public boolean posizionaASinistraDi(final Component componenteParagone, final int distanzaOrizzontale, final int distanzaVerticale, final Component compDaPosizionare) {
		int nuovaX = 0;
		int nuovaY = 0;
		if(componenteParagone != null){
			Point location = componenteParagone.getLocation();
			nuovaX = (int) (location.getX() - compDaPosizionare.getWidth() - distanzaOrizzontale);
			nuovaY = (int) (location.getY() + distanzaVerticale);
		}else{
			nuovaX = distanzaOrizzontale;
			nuovaY = distanzaVerticale;
		}
		compDaPosizionare.setLocation(nuovaX, nuovaY);
		return true;
	}

	@Override
	public boolean posizionaSottoA(final Component componenteParagone, final int distanzaOrizzantale, final int distanzaVerticale, final Component compDaPosizionare) {
		int nuovaX = 0;
		int nuovaY = 0;
		if(componenteParagone != null){
			Point location = componenteParagone.getLocation();
			nuovaX = (int) (location.getX() + distanzaOrizzantale);
			nuovaY = (int) (location.getY() + componenteParagone.getHeight() + distanzaVerticale);
			compDaPosizionare.setLocation(nuovaX, nuovaY);
		}else{
			nuovaX = distanzaOrizzantale;
			nuovaY = distanzaVerticale;
		}
		return true;
	}

	@Override
	public boolean posizionaSopraA(final Component componenteParagone, final int distanzaOrizzantale, final int distanzaVerticale, final Component compDaPosizionare) {
		int nuovaX = 0;
		int nuovaY = 0;
		if(componenteParagone != null){
			Point location = componenteParagone.getLocation();
			nuovaX = (int) (location.getX() + distanzaOrizzantale);
			nuovaY = (int) (location.getY() - compDaPosizionare.getHeight() - distanzaVerticale);
			compDaPosizionare.setLocation(nuovaX, nuovaY);
		}else{
			nuovaX = distanzaOrizzantale;
			nuovaY = distanzaVerticale;
		}
		
		return true;
	}

	public int getLarghezzaSingleStringa(Graphics g, final String label, final Component compDaPosizionare) {
		int larghezza = 0;
		g = trovaUnGraphicsValido(g, compDaPosizionare);
		if (g != null && compDaPosizionare.getFont() != null) {
			final FontMetrics fm = g.getFontMetrics(compDaPosizionare.getFont());
			larghezza = fm.stringWidth(label);
		}
		return larghezza > ComponenteBase.WIDTH_STRING_MIN ? larghezza + 3 : ComponenteBase.WIDTH_STRING_DEFAULT;
	}

	public int getAltezzaSingleStringa(Graphics g, final Component compDaPosizionare) {
		int altezza = 0;
		g = trovaUnGraphicsValido(g, compDaPosizionare);
		if (g != null && compDaPosizionare.getFont() != null) {
			final FontMetrics fm = g.getFontMetrics(compDaPosizionare.getFont());
			altezza = fm.getHeight();
		}
		return altezza > ComponenteBase.HEIGHT_STRING_MIN ? altezza + 3 : ComponenteBase.HEIGHT_STRING_DEFAULT;
	}

	public Graphics trovaUnGraphicsValido(Graphics g, final Component compDaPosizionare) {
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
	public void settaStile() {
		try {
			throw new ExceptionGraphics("Metodo esistente solo perché estende l'interfaccia IComponente", "Richiamare stesso metodo ma del componente corretto");
		} catch (ExceptionGraphics e) {
			e.printStackTrace();
		}
	}

	public void settaStile(final StyleBase style, final IComponenteBase padre) {
		style.setPadre(padre);
		Component padreComponent = ((Component) padre);
		padreComponent.setFont(style.getFont());
		padreComponent.setForeground(style.getForeground());
		padreComponent.setBackground(style.getBackground());
		padreComponent.setSize(style.getWidth(), style.getHeight());
		if (CoreXMLManager.getSingleton().isAutoConfig()) {
			if (ihaveToSetDimension(style, padreComponent)) {
				int width = ((IComponenteBase) padreComponent).getLarghezza();
				int height = ((IComponenteBase) padreComponent).getAltezza();
				padreComponent.setSize(width, height);
			}
		}
	}

	public boolean ihaveToSetDimension(final StyleBase style, final Component padreComponent) {
		if (padreComponent instanceof JTextComponent) {
			if (style.getWidth() == 0 && style.getHeight() == 0) {
				if ((padreComponent.getWidth() != 0 && padreComponent.getHeight() != 0)) {
					return false;
				} else {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public int getLarghezza() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getAltezza() {
		// TODO Auto-generated method stub
		return 0;
	}
}
