package grafica.componenti.table;

import grafica.componenti.StyleBase;

import java.awt.Color;
import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

public class TableBase extends JTable implements FocusListener {

	protected StyleBase style = new StyleBase();
	boolean isCellEditable = false;
	TableScrollPane contenitore;

	private static final long serialVersionUID = 1L;

	public TableBase() {
		init();
	}

	public TableBase(final TableModel dm) {
		super(dm);
		init();
	}

	public TableBase(final TableModel dm, final TableColumnModel cm) {
		super(dm, cm);
		init();
	}

	public TableBase(final int numRows, final int numColumns) {
		super(numRows, numColumns);
		init();
	}

	public TableBase(@SuppressWarnings("rawtypes") final Vector rowData,
			@SuppressWarnings("rawtypes") final Vector columnNames) {
		super(rowData, columnNames);
		init();
	}

	public TableBase(final Object[][] rowData, final Object[] columnNames) {
		super(rowData, columnNames);
		init();
	}

	public TableBase(final TableModel dm, final TableColumnModel cm, final ListSelectionModel sm) {
		super(dm, cm, sm);
		init();
	}

	@Override
	public boolean isCellEditable(final int row, final int column) {
		return this.isCellEditable;
	}

	public void setCellEditable(boolean isCellEditable) {
		this.isCellEditable = isCellEditable;
	}

	public void setBounds(int x, int y, int width, int height, boolean aggiornaPadre) {
		if (aggiornaPadre && contenitore != null) {
			contenitore.setBounds(x, y, width, height);
		}
		super.setBounds(x, y, width, height);
	}

	protected void init() {
		this.addFocusListener(this);
		this.settaStile();

	}

	public void generaDimensioniMinime() {
		int altezza = this.getRowCount() * this.getRowHeight();
		int larghezza = this.getColumnCount() * this.getLarghezzaMinimaColonna();
		this.setBounds(10, 10, larghezza, altezza);

	}

	private int getLarghezzaMinimaColonna() {
		int larghezzaMinima = 0;
		for (int i = 0; i < getColumnCount(); i++) {
			String nomeColonna = getColumnName(i);
			int larghezzaNome = getLarghezzaLabel(this.getGraphics(), nomeColonna);
			if (larghezzaNome > larghezzaMinima) {
				larghezzaMinima = larghezzaNome;
			}
		}
		return larghezzaMinima;
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

	public JScrollPane getContenitore() {
		return contenitore;
	}

	public void setContenitore(TableScrollPane contenitore) {
		this.contenitore = contenitore;
	}

	@Override
	public TableCellRenderer getCellRenderer(int row, int column) {
		return setStyleColumn();
	}

	public TableCellRenderer setStyleColumn() {

		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				if (isSelected) {
					//					setOpaque(true);
					//					setBackground(table.getSelectionBackground());
					//					setForeground(Color.BLUE);
				}
				if (column == 0) {
					setBackground(Color.LIGHT_GRAY);
					setForeground(Color.WHITE);
				}

				return value instanceof JLabel ? (JLabel) value : super.getTableCellRendererComponent(table, value,
						isSelected, hasFocus, row, column);
			}
		};
		return dtcr;

	}

	public int getLarghezzaLabel(Graphics g, final String label) {
		if (g == null) {
			g = this.getContenitore().getGraphics();
		}
		if (g == null) {
			g = this.getParent().getGraphics();
		}
		final FontMetrics fm = g.getFontMetrics(this.getFont());
		return fm.stringWidth(label);
	}

	public int getAltezzaLabel(Graphics g) {
		if (g == null) {
			g = this.getParent().getGraphics();
		}
		final FontMetrics fm = g.getFontMetrics(this.getFont());
		return fm.getHeight();
	}
}
