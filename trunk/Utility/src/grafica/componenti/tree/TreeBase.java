package grafica.componenti.tree;

import grafica.componenti.componenteBase.ComponenteBase;
import grafica.componenti.componenteBase.IComponenteBase;
import grafica.componenti.style.StyleBase;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreeSelectionModel;

import disegno.immagini.UtilImage;

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

	public TreeBase(final Vector<ITreeObject> userObjects, final Container contenitorePadre) {
		super(userObjects);
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
		this.setStile(new StyleBase("StyleBaseTree"));
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

	public static void main(final String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				final JFrame frame = new JFrame();
				final JPanel pane = new JPanel();
				final JScrollPane panel = new JScrollPane();
				panel.setSize(200, 200);
				final TreeBase tree = new TreeBase(panel) {

					private static final long serialVersionUID = 1L;

				};

				final String path = "/home/marcouio/Immagini/";
				ImageIcon icona = new ImageIcon(path + "icon.png");
				ImageIcon icona2 = new ImageIcon(path + "icon.png");
				ImageIcon icona3 = new ImageIcon(path + "icon.png");

				icona = UtilImage.resizeImage(10, 10, icona);
				icona2 = UtilImage.resizeImage(10, 10, icona2);
				icona3 = UtilImage.resizeImage(10, 10, icona3);

				final TreeObjectFoglia foglia1 = new TreeObjectFoglia("foglia1", icona);

				final TreeObjectFoglia foglia2 = new TreeObjectFoglia("foglia2", icona2);
				final TreeObjectRamo ramo1 = new TreeObjectRamo(foglia2, "ramo1", foglia2.getIcona());
				final TreeObjectFoglia foglia3 = new TreeObjectFoglia("foglia3", icona2);
				ramo1.addFoglia(foglia3);
				ramo1.addChildrenToTree();
				final DefaultMutableTreeNode root = initTree(tree).getTreeNode();

				root.add(foglia1.getTreeNode());
				root.add(ramo1.getTreeNode());
				root.add(new DefaultMutableTreeNode("Ciao"));
				panel.setViewportView(tree);

				tree.setIconForAll(null, null, icona3);
				frame.getContentPane().add(panel);
				frame.getContentPane().add(pane);
				frame.setVisible(true);
				frame.setSize(400, 400);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				tree.expandRow(0);

				final JButton button = new JButton("aggiorna tree");
				pane.add(button);
				button.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(final ActionEvent e) {
						tree.setModel(new DefaultTreeModel(new DefaultMutableTreeNode("Ciao")));
					}
				});
			}

			private TreeObjectRamo initTree(final TreeBase tree) {
				final String path = "C:/Documents and Settings/marco.molinari/Documenti/Download/Mio/Immagini/";
				ImageIcon icona = new ImageIcon(path + "stop.jpg");
				icona = UtilImage.resizeImage(10, 10, icona);
				final TreeObjectFoglia fogliaroot = new TreeObjectFoglia("root", icona);
				final TreeObjectRamo ramoRoot = new TreeObjectRamo(fogliaroot, "ramo", fogliaroot.getIcona());
				// tree.setImmagineTreeObject(ramoRoot);
				// tree.setIconOnlyFoglie(icona);
				// tree.setIconForAll();
				final DefaultTreeModel treeModel = new DefaultTreeModel(ramoRoot.getTreeNode());
				tree.setModel(treeModel);
				tree.setEditable(false);
				tree.setRootVisible(true);
				tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

				return ramoRoot;
			}
		});
	}

	@Override
	public boolean repaintCustomizzato(final Object[] parametri) {
		 boolean repaintCustomizzato = componenteBase.repaintCustomizzato(parametri, this);
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
	public void setStile(final StyleBase styleBase) {
		componenteBase.settaStile(styleBase, this);
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
		// TODO Auto-generated method stub
		
	}

}
