package grafica.componenti.table;

import javax.swing.JScrollPane;

public class TableScrollPane extends JScrollPane {

	private static final long serialVersionUID = 1L;

	public TableScrollPane(final TableBase tableBase) {
		super(tableBase);
		this.setBounds(tableBase.getX(), tableBase.getY(), tableBase.getWidth(), tableBase.getHeight());
	}
}
