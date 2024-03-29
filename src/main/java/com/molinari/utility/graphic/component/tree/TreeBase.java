package com.molinari.utility.graphic.component.tree;

import java.awt.Component;
import java.awt.Container;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreeSelectionModel;

import com.molinari.utility.graphic.component.base.ComponenteBase;
import com.molinari.utility.graphic.component.base.IComponenteBase;
import com.molinari.utility.graphic.component.style.StyleBase;

/**
 * La classe è un oggetto grafico base che estende il JTree di Swing. Oltre alle
 * funzionalita' degli oggetti grafici base del framework, fornisce una serie di
 * metodi di utilità e implementazioni per facilitare l'utilizzo dei 'tree'.
 * 
 * @author marco.molinari
 */
public class TreeBase extends JTree implements TreeSelectionListener, IComponenteBase {

	private Container contenitorePadre;
	private final ComponenteBase componenteBase = new ComponenteBase(this);
	private static final long serialVersionUID = 1L;
	private DefaultTreeCellRenderer treeCellRenderer = new DefaultTreeCellRenderer();

	public TreeBase(final Container contenitorePadre) {
		super();
		makeGUI(contenitorePadre);
		init(contenitorePadre, this);
	}

	public TreeBase(final Object[] userObjects, final Container contenitorePadre) {
		super(userObjects);
		makeGUI(contenitorePadre);
		init(contenitorePadre, this);
	}

	public TreeBase(final TreeModel model, final Container contenitorePadre) {
		super(model);
		makeGUI(contenitorePadre);
		init(contenitorePadre, this);
	}

	public TreeBase(final TreeNode treeNode, final Container contenitorePadre) {
		super(treeNode);
		makeGUI(contenitorePadre);
		init(contenitorePadre, this);
	}

	public TreeBase(final Hashtable<String, ITreeObject> mappaObjects, final Container contenitorePadre) {
		super(mappaObjects);
		makeGUI(contenitorePadre);
		init(contenitorePadre, this);
	}

	public TreeBase(final List<ITreeObject> userObjects, final Container contenitorePadre) {
		super(new Vector<ITreeObject>(userObjects));
		makeGUI(contenitorePadre);
		init(contenitorePadre, this);
	}

	public TreeBase(final TreeNode treeNode, final boolean arg, final Container contenitorePadre) {
		super(treeNode, arg);
		makeGUI(contenitorePadre);
		init(contenitorePadre, this);
	}

	@Override
	public void init(final Container contenitorePadre2, final Component componenteFiglio) {
		contenitorePadre = contenitorePadre2;
		componenteBase.init(contenitorePadre2, componenteFiglio);
		this.setContenitorePadre(contenitorePadre2);
		this.setEditable(true);
		this.setRootVisible(true);
		this.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		this.addTreeSelectionListener(this);
		this.setCellRenderer(treeCellRenderer);
		this.applicaStile(new StyleBase("StyleBaseTree"), this);
		setSize(getLarghezza(), getAltezza());
	}

	public void espandiTutto() {
		final int row = this.getRowCount();
		for (int i = 0; i < row; i++) {
			this.expandRow(i);
		}
	}

	public void setIconAllNode(final ImageIcon icon) {
		treeCellRenderer.setIcon(icon);
	}

	public void setIconForAll(final ImageIcon iconFoglia, final ImageIcon iconRoot, final ImageIcon iconRamo) {

		treeCellRenderer = new DefaultTreeCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getTreeCellRendererComponent(final JTree tree, final Object value, final boolean sel, final boolean expanded, final boolean leaf, final int row, final boolean hasFocus) {

				final JLabel label = (JLabel) super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
				final DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
				if (iconFoglia != null && node.isLeaf()) {
					label.setIcon(iconFoglia);
				} else if (node.isRoot()) {
					label.setIcon(iconRoot);
				} else if (iconRamo != null && node.getChildCount() > 0) {
					label.setIcon(iconRamo);
				} else if (node.getUserObject() instanceof ITreeObject) {
					final ITreeObject treeObject = (ITreeObject) node.getUserObject();
					if (treeObject.getIcona() != null) {
						label.setIcon(treeObject.getIcona());
					}
				}
				return label;
			}
		};
		this.setCellRenderer(treeCellRenderer);
	}

	public void setIconForAll() {

		treeCellRenderer = new DefaultTreeCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getTreeCellRendererComponent(final JTree tree, final Object value, final boolean sel, final boolean expanded, final boolean leaf, final int row, final boolean hasFocus) {

				final JLabel label = (JLabel) super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
				final DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
				if (node.getUserObject() instanceof ITreeObject) {
					final ITreeObject treeObject = (ITreeObject) node.getUserObject();
					if (treeObject.getIcona() != null) {
						label.setIcon(treeObject.getIcona());
					}
				}
				return label;
			}
		};
		this.setCellRenderer(treeCellRenderer);
	}

	public void setIconOnlyForRoot(final ITreeObject ramoRoot) {
		// l'immagine dell'oggetto che avranno tutti
		final ImageIcon icon = ramoRoot.getIcona();

		if (icon != null) {
			final DefaultTreeCellRenderer dtcr = new DefaultTreeCellRenderer() {
				private static final long serialVersionUID = 1L;

				@Override
				public Component getTreeCellRendererComponent(final JTree tree, final Object value, final boolean sel, final boolean expanded, final boolean leaf, final int row, final boolean hasFocus) {

					final JLabel label = (JLabel) super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
					if (((DefaultMutableTreeNode) value).isRoot()) {
						label.setIcon(icon);
					}
					return label;
				}
			};
			this.setCellRenderer(dtcr);
		}
	}

	public void setIconOnlyFoglie(final ImageIcon leafIcon) {
		if (leafIcon != null) {
			treeCellRenderer.setLeafIcon(leafIcon);
		}
	}

	public boolean repaintFattoDaMe() {
		return true;

	}

	@Override
	public void valueChanged(final TreeSelectionEvent e) {
		// Returns the last path element of the selection.
		// This method is useful only when the selection model allows a single
		// selection.
		final DefaultMutableTreeNode node = (DefaultMutableTreeNode) this.getLastSelectedPathComponent();

		((DefaultTreeModel) treeModel).nodeChanged(node);
		this.getParent().getParent().repaint();

		if (node == null) {
			// Nothing is selected.
			return;
		}

		final Object nodeInfo = node.getUserObject();
		if (node.isLeaf() && nodeInfo instanceof TreeObjectFoglia) {
			((TreeObjectFoglia) nodeInfo).eseguiAzioneListener();
		} else if (nodeInfo instanceof TreeObjectFoglia) {
			((TreeObjectRamo) nodeInfo).eseguiAzioneListener();
		}
	}

	@Override
	public Container getContenitorePadre() {
		return contenitorePadre;
	}

	public void setContenitorePadre(final Container contenitorePadre) {
		this.contenitorePadre = contenitorePadre;
	}

	@Override
	public boolean repaintCustomizzato(final Object[] parametri) {
		 final boolean repaintCustomizzato = componenteBase.repaintCustomizzato(parametri, this);
		 if (repaintCustomizzato) {
			 final TreeModel treeModel = (TreeModel) parametri[IComponenteBase.PARAM_REPAINT_MODEL];
			 setModel(treeModel);
			 componenteBase.ridisegna(this);
			 return true;
		}
		return false;
	}

	/**
	 * Metodo facade di metodo omonimo per facilitarne l'accesso e la
	 * leggibilita'
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
	 * Metodo facade di metodo omonimo per facilitarne l'accesso e la
	 * leggibilita'
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
	 * Metodo facade di metodo omonimo per facilitarne l'accesso e la
	 * leggibilita'
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
	 * Metodo facade di metodo omonimo per facilitarne l'accesso e la
	 * leggibilita'
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
	public boolean posizionaADestraDi(final Component componenteParagone, final int distanzaOrizzantale, final int distanzaVerticale, final Component componenteDaRiposizionare) {
		return componenteBase.posizionaADestraDi(componenteParagone, distanzaOrizzantale, distanzaVerticale, componenteDaRiposizionare);
	}

	@Override
	public boolean posizionaASinistraDi(final Component componenteParagone, final int distanzaOrizzontale, final int distanzaVerticale, final Component componenteDaRiposizionare) {
		return componenteBase.posizionaASinistraDi(componenteParagone, distanzaOrizzontale, distanzaVerticale, componenteDaRiposizionare);
	}

	@Override
	public boolean posizionaSottoA(final Component componenteParagone, final int distanzaOrizzantale, final int distanzaVerticale, final Component componenteDaRiposizionare) {
		return componenteBase.posizionaSottoA(componenteParagone, distanzaOrizzantale, distanzaVerticale, componenteDaRiposizionare);
	}

	@Override
	public boolean posizionaSopraA(final Component componenteParagone, final int distanzaOrizzantale, final int distanzaVerticale, final Component componenteDaRiposizionare) {
		componenteBase.posizionaSopraA(componenteParagone, distanzaOrizzantale, distanzaVerticale, componenteDaRiposizionare);
		return false;
	}

	@Override
	public void applicaStile(final StyleBase styleBase, IComponenteBase comp) {
		componenteBase.applicaStile(styleBase, comp);
	}

	@Override
	public int getLarghezza() {
		return componenteBase.getLarghezza();
	}

	@Override
	public int getAltezza() {
		return componenteBase.getAltezza();
	}

	@Override
	public void makeGUI(Container contenitorePadre) {
		//do nothing
		
	}

	public void setSize(boolean isPercent, int width, int height) {
		componenteBase.setSize(this, isPercent, width, height);
	}

	@Override
	public void setSize(Component componentToSize, boolean isPercent, int width, int height) {
		componenteBase.setSize(componentToSize, isPercent, width, height);
	}
}
