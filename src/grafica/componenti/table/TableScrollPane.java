package grafica.componenti.table;

import java.awt.Dimension;

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

	/**
	 * 
	 * Permette di generare una tabella dentro uno scrollpane
	 * 
	 * @param primo
	 * @param nomiColonne
	 * @return Table
	 */
	public static TableScrollPane createTableScrollPane(final Object[][] primo, final String[] nomiColonne) {

		final TableBase2d table = new TableBase2d(primo, nomiColonne);
		final TableScrollPane pane = new TableScrollPane(table);
		table.setContenitore(pane);
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);
		table.setRowHeight(13);
		return pane;
	}


}
