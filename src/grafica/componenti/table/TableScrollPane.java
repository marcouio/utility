package grafica.componenti.table;

import javax.swing.JScrollPane;

public class TableScrollPane extends JScrollPane {

	private static final long serialVersionUID = 1L;
	private TableBase2d tabellaFiglia;

	public TableScrollPane(final TableBase2d tableBase) {
		super(tableBase);
		this.tabellaFiglia = tableBase;
		this.setBounds(tableBase.getX(), tableBase.getY(), tableBase.getWidth(), tableBase.getHeight());
	}

	public TableBase2d getTabellaFiglia() {
		return tabellaFiglia;
	}

	public void setTabellaFiglia(final TableBase2d tabellaFiglia) {
		this.tabellaFiglia = tabellaFiglia;
	}
}
