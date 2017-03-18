package com.molinari.utility.graphic.component.tree;

import javax.swing.ImageIcon;
import javax.swing.tree.DefaultMutableTreeNode;

public interface ITreeObject {

	public DefaultMutableTreeNode getTreeNode();

	public void setNome(String nome);

	public String getNome();

	public ImageIcon getIcona();

	public void setIcona(ImageIcon icona);
}
