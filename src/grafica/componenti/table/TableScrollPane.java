package grafica.componenti.table;

import grafica.componenti.UtilComponenti;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

public class TableScrollPane extends JScrollPane {

	private static final long serialVersionUID = 1L;
	private TableBase2d tabellaFiglia;

	public TableScrollPane() {
		super();
	}

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
		this.setBounds(this.tabellaFiglia.getX(), this.tabellaFiglia.getY(), this.tabellaFiglia.getWidth(),
				this.tabellaFiglia.getHeight());
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

		final TableScrollPane pane = new TableScrollPane();
		final TableBase2d table = new TableBase2d(primo, nomiColonne, pane);
		//		table.setContenitore(pane);
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);
		table.setRowHeight(13);
		return pane;
	}

	/**
	 * 
	 * Permette di generare una tabella dentro uno scrollpane. Funziona?
	 * 
	 * @param primo
	 * @param nomiColonne
	 * @return Table
	 */
	public TableScrollPane createTableScrollPane2(final Object[][] primo, final String[] nomiColonne) {

		final TableBase2d table = new TableBase2d(primo, nomiColonne, this);
		//		table.setContenitore(pane);
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);
		table.setRowHeight(13);
		return this;
	}

	public static void main(final String[] args) throws InterruptedException, InvocationTargetException {
		SwingUtilities.invokeAndWait(new Runnable() {

			@Override
			public void run() {
				TableScrollPane pane = new TableScrollPane();
				pane = pane.createTableScrollPane2(new String[][] { { "ciao" }, { "ciao" } }, new String[] { "ciao" });
				JPanel panel = UtilComponenti.initContenitoreFrame(new FlowLayout());
				panel.add(pane);
			}
		});
	}

}
