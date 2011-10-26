package grafica.componenti;

import java.awt.Component;
import java.awt.Container;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Window;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.RootPaneContainer;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

public class ComponenteBase extends Component implements IComponenteBase {

	private static final long serialVersionUID = 1L;

	public void aggiungiAlContenitore(final Container contenitorePadre2, final Component componenteFiglio) {
		if (contenitorePadre2 instanceof Window) {
			((RootPaneContainer) contenitorePadre2).getContentPane().add(componenteFiglio);
		} else if (contenitorePadre2 instanceof JScrollPane) {
			((JScrollPane) contenitorePadre2).setViewportView(componenteFiglio);
		} else {
			contenitorePadre2.add(componenteFiglio);
		}
	}

	@Override
	public void init(final Container contenitorePadre2, final Component componenteFiglio) {
		aggiungiAlContenitore(contenitorePadre2, componenteFiglio);
		componenteFiglio.setLocation(0, 0);
	}

	@Override
	public boolean repaintCustomizzato(final Object[] parametri) {
		Object objForRepaint = parametri[IComponenteBase.PARAM_REPAINT_OBJ_REPAINT];
		if (modelIsNull(objForRepaint, parametri[IComponenteBase.PARAM_REPAINT_MODEL])) {
			return false;
		}
		if (objForRepaint == null) {
			return false;
		}
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

	private boolean modelIsNull(final Object objForRepaint, final Object model) {
		if (objForRepaint instanceof JTree || objForRepaint instanceof JComboBox || objForRepaint instanceof JTable) {
			if (model == null) {
				return false;
			}
		}
		return true;
	}

	public boolean aDestraDi(final Component componenteParagone, final int distanzaOrizzantale,
			final int distanzaVerticale, final Component compDaPosizionare) {
		Point location = componenteParagone.getLocation();
		int nuovaX = (int) (location.getX() + componenteParagone.getWidth() + distanzaOrizzantale);
		int nuovaY = (int) (location.getY() + distanzaVerticale);
		compDaPosizionare.setLocation(nuovaX, nuovaY);
		return true;
	}

	public boolean aSinistraDi(final Component componenteParagone, final int distanzaOrizzontale,
			final int distanzaVerticale, final Component compDaPosizionare) {
		Point location = componenteParagone.getLocation();
		int nuovaX = (int) (location.getX() - compDaPosizionare.getWidth() - distanzaOrizzontale);
		int nuovaY = (int) (location.getY() + distanzaVerticale);
		compDaPosizionare.setLocation(nuovaX, nuovaY);
		return true;
	}

	public boolean sottoA(final Component componenteParagone, final int distanzaOrizzantale,
			final int distanzaVerticale, final Component compDaPosizionare) {
		Point location = componenteParagone.getLocation();
		int nuovaX = (int) (location.getX() + distanzaOrizzantale);
		int nuovaY = (int) (location.getY() + componenteParagone.getHeight() + distanzaVerticale);
		compDaPosizionare.setLocation(nuovaX, nuovaY);
		return true;
	}

	public boolean sopraA(final Component componenteParagone, final int distanzaOrizzantale,
			final int distanzaVerticale, final Component compDaPosizionare) {
		Point location = componenteParagone.getLocation();
		int nuovaX = (int) (location.getX() + distanzaOrizzantale);
		int nuovaY = (int) (location.getY() - compDaPosizionare.getHeight() - distanzaVerticale);
		compDaPosizionare.setLocation(nuovaX, nuovaY);
		return true;
	}

	@Override
	public boolean posizionaADestraDi(final Component componenteParagone, final int distanzaOrizzantale,
			final int distanzaVerticale) throws ExceptionGraphics {
		throw new ExceptionGraphics("Metodo esistente solo perché estende l'interfaccia IComponente",
				"Richiamare stesso metodo ma del componente da posizionare");
	}

	@Override
	public boolean posizionaASinistraDi(final Component componenteParagone, final int distanzaOrizzontale,
			final int distanzaVerticale) throws ExceptionGraphics {
		throw new ExceptionGraphics("Metodo esistente solo perché estende l'interfaccia IComponente",
				"Richiamare stesso metodo ma del componente da posizionare");
	}

	@Override
	public boolean posizionaSottoA(final Component componenteParagone, final int distanzaOrizzantale,
			final int distanzaVerticale) throws ExceptionGraphics {
		throw new ExceptionGraphics("Metodo esistente solo perché estende l'interfaccia IComponente",
				"Richiamare stesso metodo ma del componente da posizionare");
	}

	@Override
	public boolean posizionaSopraA(final Component componenteParagone, final int distanzaOrizzantale,
			final int distanzaVerticale) throws ExceptionGraphics {
		throw new ExceptionGraphics("Metodo esistente solo perché estende l'interfaccia IComponente",
				"Richiamare stesso metodo ma del componente da posizionare");
	}

	public int getLarghezzaSingleStringa(Graphics g, final String label, final Component compDaPosizionare) {
		if (g == null) {
			g = this.getParent().getGraphics();
		}
		final FontMetrics fm = g.getFontMetrics(compDaPosizionare.getFont());
		return fm.stringWidth(label) + 3;
	}

	public int getAltezzaSingleStringa(Graphics g, final Component compDaPosizionare) {
		if (g == null) {
			g = this.getParent().getGraphics();
		}
		final FontMetrics fm = g.getFontMetrics(compDaPosizionare.getFont());
		return fm.getHeight() + 3;
	}

	@Override
	public int getLarghezzaSingleStringa(final Graphics g, final String label) throws ExceptionGraphics {
		throw new ExceptionGraphics("Metodo esistente solo perché estende l'interfaccia IComponente",
				"Richiamare stesso metodo ma del componente corretto");
	}

	@Override
	public int getAltezzaSingleStringa(final Graphics g) throws ExceptionGraphics {
		throw new ExceptionGraphics("Metodo esistente solo perché estende l'interfaccia IComponente",
				"Richiamare stesso metodo ma del componente corretto");
	}

	@Override
	public void settaStile() throws ExceptionGraphics {
		throw new ExceptionGraphics("Metodo esistente solo perché estende l'interfaccia IComponente",
				"Richiamare stesso metodo ma del componente corretto");
	}
}