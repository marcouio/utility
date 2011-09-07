package grafica.componenti.table;

import javax.swing.JScrollPane;

public class TableScrollPane extends JScrollPane {

	private static final long serialVersionUID = 1L;
	private TableBase tabellaFiglia;

	public TableScrollPane(final TableBase tableBase) {
		super(tableBase);
		this.tabellaFiglia = tableBase;
		this.setBounds(tableBase.getX(), tableBase.getY(), tableBase.getWidth(), tableBase.getHeight());
	}

	public TableBase getTabellaFiglia() {
		return tabellaFiglia;
	}

	public void setTabellaFiglia(TableBase tabellaFiglia) {
		this.tabellaFiglia = tabellaFiglia;
	}
}
