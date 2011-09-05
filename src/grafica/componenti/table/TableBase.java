package grafica.componenti.table;

import grafica.componenti.StyleBase;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

public class TableBase extends JTable implements FocusListener {

	protected StyleBase style = new StyleBase();
	boolean isCellEditable = false;

	private static final long serialVersionUID = 1L;

	public TableBase() {
	}

	public TableBase(final TableModel dm) {
		super(dm);
	}

	public TableBase(final TableModel dm, final TableColumnModel cm) {
		super(dm, cm);
	}

	public TableBase(final int numRows, final int numColumns) {
		super(numRows, numColumns);
	}

	public TableBase(@SuppressWarnings("rawtypes") final Vector rowData,
			@SuppressWarnings("rawtypes") final Vector columnNames) {
		super(rowData, columnNames);
	}

	public TableBase(final Object[][] rowData, final Object[] columnNames) {
		super(rowData, columnNames);
	}

	public TableBase(final TableModel dm, final TableColumnModel cm, final ListSelectionModel sm) {
		super(dm, cm, sm);
	}

	@Override
	public boolean isCellEditable(final int row, final int column) {
		return this.isCellEditable;
	}

	public void setCellEditable(boolean isCellEditable) {
		this.isCellEditable = isCellEditable;
	}

	protected void init() {
		this.addFocusListener(this);
		this.settaStile();
	}

	protected void settaStile() {
		style.setPadre(this);
		this.setFont(style.getFont());
		this.setForeground(style.getForeground());
		this.setBackground(style.getBackground());
	}

	@Override
	public void focusGained(FocusEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void focusLost(FocusEvent arg0) {
		// TODO Auto-generated method stub

	}
}
