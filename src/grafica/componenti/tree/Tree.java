package grafica.componenti.tree;

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
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreeSelectionModel;

import disegno.immagini.UtilImage;

public class Tree extends JTree {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				JFrame frame = new JFrame();
				JPanel panel = new JPanel(new GridLayout(0, 1));
				panel.setSize(400, 400);
				Tree tree = new Tree();

				String path = "/home/marcouio/Immagini/linux-serverLinux-TUX06.jpg";
				ImageIcon icona = new ImageIcon(path);
				icona = UtilImage.resizeImage(10, 10, icona);
				TreeObjectFoglia foglia1 = new TreeObjectFoglia("foglia1", icona);
				tree.setRenderer(foglia1);

				TreeObjectFoglia foglia2 = new TreeObjectFoglia("foglia2", icona);
				TreeObjectRamo ramo1 = new TreeObjectRamo(foglia2, "ramo1", foglia2.getIcona());
				TreeObjectFoglia foglia3 = new TreeObjectFoglia("foglia3", icona);
				ramo1.addFoglia(foglia3);
				ramo1.addChildrenToTree();

				DefaultMutableTreeNode root = initTree(tree).getTreeNode();

				root.add(foglia1.getTreeNode());
				root.add(ramo1.getTreeNode());
				panel.add(tree);
				frame.getContentPane().add(panel);
				frame.setVisible(true);
				frame.setSize(400,400);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}

			private TreeObjectRamo initTree(Tree tree) {
				String path = "/home/marcouio/Immagini/linux-serverLinux-TUX06.jpg";
				ImageIcon icona = new ImageIcon(path);
				icona = UtilImage.resizeImage(10, 10, icona);
				TreeObjectFoglia fogliaroot = new TreeObjectFoglia("root", icona);
				TreeObjectRamo ramoRoot = new TreeObjectRamo(fogliaroot, "ramo", fogliaroot.getIcona());
				tree.setRenderer(ramoRoot);
				DefaultTreeModel treeModel = new DefaultTreeModel(ramoRoot.getTreeNode());
				tree.setModel(treeModel);
				tree.setEditable(true);
				tree.setRootVisible(true);
				tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

				return ramoRoot;
			}
		});
	}

	private static final long serialVersionUID = 1L;

	public Tree() {
		super();
		init();
	}

	public Tree(Object[] userObjects) {
		super(userObjects);
		init();
	}

	public Tree(TreeModel model) {
		super(model);
		init();
	}

	public Tree(TreeNode treeNode) {
		super(treeNode);
		init();
	}

	public Tree(Hashtable<String, ITreeObject> mappaObjects) {
		super(mappaObjects);
		init();
	}

	public Tree(Vector<ITreeObject> userObjects) {
		super(userObjects);
		init();
	}

	public Tree(TreeNode treeNode, boolean arg) {
		super(treeNode, arg);
		init();
	}

	private void init() {

	}

	public void setRenderer(ITreeObject ramoTree) {
		// 	l'immagine dell'oggetto
		final ImageIcon icon = ramoTree.getIcona();

		if (icon != null) {
			final DefaultTreeCellRenderer dtcr = new DefaultTreeCellRenderer() {
				private static final long serialVersionUID = 1L;

				@Override
				public Component getTreeCellRendererComponent(final JTree tree, final Object value, final boolean sel,
						final boolean expanded, final boolean leaf, final int row, final boolean hasFocus) {

					final JLabel label = (JLabel) super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,
							row, hasFocus);
					//					if (((DefaultMutableTreeNode) value).isRoot()) {
					label.setIcon(icon);
					//					}
					return label;
				}
			};
			this.setCellRenderer(dtcr);
		}

	}
}
