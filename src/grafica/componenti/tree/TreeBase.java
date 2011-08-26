package grafica.componenti.tree;

import grafica.componenti.StyleBase;

import java.awt.Component;
import java.awt.GridLayout;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
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

public class TreeBase extends JTree implements TreeSelectionListener {
	
	protected StyleBase style = new StyleBaseTree();
	private static final long serialVersionUID = 1L;
	private DefaultTreeCellRenderer treeCellRenderer = new DefaultTreeCellRenderer();

	public TreeBase() {
		super();
		init();
	}

	public TreeBase(Object[] userObjects) {
		super(userObjects);
		init();
	}

	public TreeBase(TreeModel model) {
		super(model);
		init();
	}

	public TreeBase(TreeNode treeNode) {
		super(treeNode);
		init();
	}

	public TreeBase(Hashtable<String, ITreeObject> mappaObjects) {
		super(mappaObjects);
		init();
	}

	public TreeBase(Vector<ITreeObject> userObjects) {
		super(userObjects);
		init();
	}

	public TreeBase(TreeNode treeNode, boolean arg) {
		super(treeNode, arg);
		init();
	}

	private void init() {
		this.setEditable(true);
		this.setRootVisible(true);
		this.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		this.addTreeSelectionListener(this);
		this.setCellRenderer(treeCellRenderer);
	}
	
	public void espandiTutto() {
		int row = this.getRowCount();
		for (int i = 0; i < row; i++) {
			this.expandRow(i);
		}
	}

	public void setIconAllNode(ImageIcon icon) {
		treeCellRenderer.setIcon(icon);
	}

	public void setIconForAll(final ImageIcon iconFoglia, final ImageIcon iconRoot, final ImageIcon iconRamo) {

		treeCellRenderer = new DefaultTreeCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getTreeCellRendererComponent(final JTree tree, final Object value, final boolean sel,
					final boolean expanded, final boolean leaf, final int row, final boolean hasFocus) {

				final JLabel label = (JLabel) super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row,
						hasFocus);
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
				if (iconFoglia != null && node.isLeaf()) {
					label.setIcon(iconFoglia);
				} else if (node.isRoot()) {
					label.setIcon(iconRoot);
				} else if (iconRamo != null && node.getChildCount() > 0) {
					label.setIcon(iconRamo);
				} else if (node.getUserObject() instanceof ITreeObject) {
					ITreeObject treeObject = (ITreeObject) node.getUserObject();
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
			public Component getTreeCellRendererComponent(final JTree tree, final Object value, final boolean sel,
					final boolean expanded, final boolean leaf, final int row, final boolean hasFocus) {

				final JLabel label = (JLabel) super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row,
						hasFocus);
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
				if (node.getUserObject() instanceof ITreeObject) {
					ITreeObject treeObject = (ITreeObject) node.getUserObject();
					if (treeObject.getIcona() != null) {
						label.setIcon(treeObject.getIcona());
					}
				}
				return label;
			}
		};
		this.setCellRenderer(treeCellRenderer);
	}

	public void setIconOnlyForRoot(ITreeObject ramoRoot) {
		// 	l'immagine dell'oggetto che avranno tutti
		final ImageIcon icon = ramoRoot.getIcona();

		if (icon != null) {
			final DefaultTreeCellRenderer dtcr = new DefaultTreeCellRenderer() {
				private static final long serialVersionUID = 1L;

				@Override
				public Component getTreeCellRendererComponent(final JTree tree, final Object value, final boolean sel,
						final boolean expanded, final boolean leaf, final int row, final boolean hasFocus) {

					final JLabel label = (JLabel) super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,
							row, hasFocus);
					if (((DefaultMutableTreeNode) value).isRoot()) {
						label.setIcon(icon);
					}
					return label;
				}
			};
			this.setCellRenderer(dtcr);
		}
	}

	public void setIconOnlyFoglie(ImageIcon leafIcon) {
		if (leafIcon != null) {
			treeCellRenderer.setLeafIcon(leafIcon);
		}
	}

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		//Returns the last path element of the selection.
		//This method is useful only when the selection model allows a single selection.
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) this.getLastSelectedPathComponent();

		((DefaultTreeModel) treeModel).nodeChanged(node);
		this.getParent().getParent().repaint();

		if (node == null) {
			//Nothing is selected.	
			return;
		}

		Object nodeInfo = node.getUserObject();
		if (node.isLeaf() && nodeInfo instanceof TreeObjectFoglia) {
			((TreeObjectFoglia) nodeInfo).eseguiAzioneListener();
		} else if (nodeInfo instanceof TreeObjectFoglia) {
			((TreeObjectRamo) nodeInfo).eseguiAzioneListener();
		}
	}
	
	public class StyleBaseTree extends StyleBase {

	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				JFrame frame = new JFrame();
				JPanel panel = new JPanel(new GridLayout(0, 1));
				panel.setSize(400, 400);
				TreeBase tree = new TreeBase();

				String path = "C:/Documents and Settings/marco.molinari/Documenti/Download/Mio/Immagini/";
				ImageIcon icona = new ImageIcon(path + "play.jpg");
				ImageIcon icona2 = new ImageIcon(path + "playrosso.jpg");
				ImageIcon icona3 = new ImageIcon(path + "stop.jpg");

				icona = UtilImage.resizeImage(10, 10, icona);
				icona2 = UtilImage.resizeImage(10, 10, icona2);
				icona3 = UtilImage.resizeImage(10, 10, icona3);

				TreeObjectFoglia foglia1 = new TreeObjectFoglia("foglia1", icona);

				TreeObjectFoglia foglia2 = new TreeObjectFoglia("foglia2", icona2);
				TreeObjectRamo ramo1 = new TreeObjectRamo(foglia2, "ramo1", foglia2.getIcona());
				TreeObjectFoglia foglia3 = new TreeObjectFoglia("foglia3", icona2);
				ramo1.addFoglia(foglia3);
				ramo1.addChildrenToTree();

				DefaultMutableTreeNode root = initTree(tree).getTreeNode();

				root.add(foglia1.getTreeNode());
				root.add(ramo1.getTreeNode());
				root.add(new DefaultMutableTreeNode("Ciao"));
				panel.add(tree);
				//				tree.setIconForAll(null, null, icona3);
				frame.getContentPane().add(panel);
				frame.setVisible(true);
				frame.setSize(400, 400);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				tree.expandRow(0);
			}

			private TreeObjectRamo initTree(TreeBase tree) {
				String path = "C:/Documents and Settings/marco.molinari/Documenti/Download/Mio/Immagini/";
				ImageIcon icona = new ImageIcon(path + "stop.jpg");
				icona = UtilImage.resizeImage(10, 10, icona);
				TreeObjectFoglia fogliaroot = new TreeObjectFoglia("root", icona);
				TreeObjectRamo ramoRoot = new TreeObjectRamo(fogliaroot, "ramo", fogliaroot.getIcona());
				//				tree.setImmagineTreeObject(ramoRoot);
				//				tree.setIconOnlyFoglie(icona);
				//				tree.setIconForAll();
				DefaultTreeModel treeModel = new DefaultTreeModel(ramoRoot.getTreeNode());
				tree.setModel(treeModel);
				tree.setEditable(false);
				tree.setRootVisible(true);
				tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

				return ramoRoot;
			}
		});
	}


}
