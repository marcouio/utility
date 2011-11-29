package grafica.componenti.alert;

import java.awt.Component;
import java.awt.Dialog;

import javax.swing.Icon;

public class Dialogo extends Dialog{

	private Dialogo(Dialog owner) {
		super(owner);
	}

	private static final long serialVersionUID = 1L;
	private String title;
	private String message;
	private int messageType;
	private Icon icon;
	private Component padre;
	
	public class Builder{
		
	}
}