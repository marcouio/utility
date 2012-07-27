package grafica.componenti.table;

import grafica.componenti.table.table.TableBase;

import javax.swing.JScrollPane;

public class TableScrollPane extends JScrollPane {

	private static final long serialVersionUID = 1L;
	private TableBase tabellaFiglia;

	public TableScrollPane() {
		super();
	}

	public TableScrollPane(final TableBase tableBase) {
		super(tableBase);
		this.tabellaFiglia = tableBase;
		this.setBounds(tableBase.getX(), tableBase.getY(), tableBase.getWidth(), tableBase.getHeight());
	}

	public TableBase getTabellaFiglia() {
		return tabellaFiglia;
	}

	public void setTabellaFiglia(final TableBase tabellaFiglia) {
		this.tabellaFiglia = tabellaFiglia;
		this.setBounds(this.tabellaFiglia.getX(), this.tabellaFiglia.getY(), this.tabellaFiglia.getWidth(),
				this.tabellaFiglia.getHeight());
	}

}
