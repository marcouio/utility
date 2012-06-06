package grafica.componenti.alert.builder;

import grafica.componenti.alert.DialogoBase;

import java.awt.Dimension;

import javax.swing.Icon;

public interface IBuilderDialogo {

	public void setTitolo(final String titolo);

	public void setMessaggio(final String messaggio);

	public void setMessageType(final int tipoMessaggio);

	public void setIcon(final Icon icona);

	public void setDimensioni(final Dimension dimensioni);

	public DialogoBase creaDialogo();
	
	public void addNegativeButton();

	public void addPositiveButton();
	
	public void addCancelButton();

}
