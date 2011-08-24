package grafica.componenti.tree;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.tree.DefaultMutableTreeNode;

public class TreeObjectRamo extends TreeObjectFoglia implements ITreeObject {

	private final ITreeObject treeObject;
	private final ArrayList<ITreeObject> foglie = new ArrayList<ITreeObject>();

	public static void main(String[] args) {
	}

	public TreeObjectRamo(ITreeObject treeObject, String nome, ImageIcon icon) {
		this.treeObject = treeObject;
		this.treeObject.setNome(nome);
		this.treeObject.setIcona(icon);
	}

	public TreeObjectRamo(ITreeObject treeObject) {
		this.treeObject = treeObject;
	}

	@Override
	public DefaultMutableTreeNode getTreeNode() {
		return treeObject.getTreeNode();
	}

	@Override
	public void setNome(String nome) {
		this.treeObject.setNome(nome);
	}

	@Override
	public String getNome() {
		return treeObject.getNome();
	}

	@Override
	public String toString() {
		return getNome();
	}

	public ArrayList<ITreeObject> getFoglie() {
		return foglie;
	}

	public void addFoglia(ITreeObject foglia) {
		this.foglie.add(foglia);
	}

	public void removeFoglia(ITreeObject foglia) {
		this.foglie.remove(foglia);
	}

	public void removeFoglia(int indexFoglia) {
		this.foglie.remove(indexFoglia);
	}

	public void addChildrenToTree() {
		for (int i = 0; i < foglie.size(); i++) {
			ITreeObject treeObject = foglie.get(i);
			DefaultMutableTreeNode nodoFiglio = treeObject.getTreeNode();
			this.getTreeNode().add(nodoFiglio);
			if (treeObject instanceof TreeObjectRamo) {
				((TreeObjectRamo) treeObject).addChildrenToTree();
			}
		}

	}

}
