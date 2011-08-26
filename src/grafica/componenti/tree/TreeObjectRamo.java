package grafica.componenti.tree;

import grafica.componenti.Alert;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.tree.DefaultMutableTreeNode;

public class TreeObjectRamo extends TreeObjectFoglia {

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
	public void setNome(String nome) {
		this.treeObject.setNome(nome);
	}

	@Override
	public String getNome() {
		return treeObject.getNome();
	}

	@Override
	public ImageIcon getIcona() {
		return treeObject.getIcona();
	}

	@Override
	public void setIcona(ImageIcon icona) {
		treeObject.setIcona(icona);
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

	/**
	 * Il metodo non fa nulla oppure ha un comportamento standard, comunque va esteso per avere un comportamento specifico
	 */
	@Override
	public void eseguiAzioneListener() {
		Alert.info(getNome() + " Ramo", "Ramo");
	}

}
