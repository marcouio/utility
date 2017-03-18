package main.java.com.molinari.utility.graphic.component.tree;

import javax.swing.ImageIcon;
import javax.swing.tree.DefaultMutableTreeNode;

import main.java.com.molinari.utility.graphic.component.alert.Alert;

public class TreeObjectFoglia implements ITreeObject {

	DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(this);
	private String nome;
	private ImageIcon icona;

	public TreeObjectFoglia() {

	}

	public TreeObjectFoglia(String nome, ImageIcon icona) {
		this.nome = nome;
		this.icona = icona;
	}

	public TreeObjectFoglia(String nome) {
		this.nome = nome;
	}

	@Override
	public DefaultMutableTreeNode getTreeNode() {
		return this.treeNode;
	}

	@Override
	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String getNome() {
		return nome;
	}

	@Override
	public ImageIcon getIcona() {
		return icona;
	}

	@Override
	public void setIcona(ImageIcon icona) {
		this.icona = icona;
	}

	@Override
	public String toString() {
		return getNome();
	}

	/**
	 * Il metodo non fa nulla oppure ha un comportamento standard, comunque va esteso per avere un comportamento specifico
	 */
	public void eseguiAzioneListener() {
		Alert.info(getNome() + " Foglia", "Foglia");
	}
}
