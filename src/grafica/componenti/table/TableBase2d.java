package grafica.componenti.table;

import grafica.componenti.ComponenteBase;
import grafica.componenti.IComponenteBase;
import grafica.componenti.StyleBase;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

public abstract class TableBase2d extends JTable implements FocusListener, IComponenteBase {

	protected StyleBase style = new StyleBaseTable2d();
	boolean isCellEditable = false;
	private Container contenitorePadre;
	private final ComponenteBase componenteBase = new ComponenteBase();

	private static final long serialVersionUID = 1L;

	public TableBase2d(final Container contenitorePadre) {
		super();
		this.contenitorePadre = contenitorePadre;
		init(contenitorePadre, this);
	}

	public TableBase2d(final TableModel dm, final Container contenitorePadre) {
		super(dm);
		this.contenitorePadre = contenitorePadre;
		init(contenitorePadre, this);
	}

	public TableBase2d(final TableModel dm, final TableColumnModel cm, final Container contenitorePadre) {
		super(dm, cm);
		this.contenitorePadre = contenitorePadre;
		init(contenitorePadre, this);
	}

	public TableBase2d(final int numRows, final int numColumns, final Container contenitorePadre) {
		super(numRows, numColumns);
		this.contenitorePadre = contenitorePadre;
		init(contenitorePadre, this);
	}

	public TableBase2d(@SuppressWarnings("rawtypes") final Vector rowData,
			@SuppressWarnings("rawtypes") final Vector columnNames, final Container contenitorePadre) {
		super(rowData, columnNames);
		this.contenitorePadre = contenitorePadre;
		init(contenitorePadre, this);
	}

	public TableBase2d(final Object[][] rowData, final Object[] columnNames, final Container contenitorePadre) {
		super(rowData, columnNames);
		this.contenitorePadre = contenitorePadre;
		init(contenitorePadre, this);
	}

	public TableBase2d(final TableModel dm, final TableColumnModel cm, final ListSelectionModel sm,
			final Container contenitorePadre) {
		super(dm, cm, sm);
		this.contenitorePadre = contenitorePadre;
		init(contenitorePadre, this);
	}

	@Override
	public boolean isCellEditable(final int row, final int column) {
		return this.isCellEditable;
	}

	public void setCellEditable(final boolean isCellEditable) {
		this.isCellEditable = isCellEditable;
	}

	@Override
	public void init(final Container contenitorePadre2, final Component componenteFiglio) {
		componenteBase.init(contenitorePadre2, componenteFiglio);
		this.addFocusListener(this);
		this.settaStile();
		this.generaDimensioniMinime();

	}

	public void generaDimensioniMinime() {
		final int altezza = this.getRowCount() * this.getRowHeight();
		final int larghezza = this.getColumnCount() * this.getLarghezzaMinimaColonna();
		this.setBounds(10, 10, larghezza, altezza);

	}

	private int getLarghezzaMinimaColonna() {
		int larghezzaMinima = 0;
		for (int i = 0; i < getColumnCount(); i++) {
			final String nomeColonna = getColumnName(i);
			final int larghezzaNome = getLarghezzaSingleStringa(this.getGraphics(), nomeColonna);
			if (larghezzaNome > larghezzaMinima) {
				larghezzaMinima = larghezzaNome;
			}
		}
		return larghezzaMinima;
	}

	@Override
	public void settaStile() {
		style = settaStileOverride() != null ? settaStileOverride() : style;
		style.setPadre(this);
		this.setFont(style.getFont());
		this.setForeground(style.getForeground());
		this.setBackground(style.getBackground());
	}

	public abstract StyleBase settaStileOverride();

	@Override
	public void focusGained(final FocusEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void focusLost(final FocusEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public TableCellRenderer getCellRenderer(final int row, final int column) {
		return setStyleColumn();
	}

	@Override
	public boolean posizionaADestraDi(final Component componenteParagone, final int distanzaOrizzantale,
			final int distanzaVerticale) {
		return componenteBase.aDestraDi(componenteParagone, distanzaOrizzantale, distanzaVerticale, this);

	}

	@Override
	public boolean posizionaASinistraDi(final Component componenteParagone, final int distanzaOrizzontale,
			final int distanzaVerticale) {
		return componenteBase.aSinistraDi(componenteParagone, distanzaOrizzontale, distanzaVerticale, this);
	}

	@Override
	public boolean posizionaSottoA(final Component componenteParagone, final int distanzaOrizzantale,
			final int distanzaVerticale) {
		return componenteBase.sottoA(componenteParagone, distanzaOrizzantale, distanzaVerticale, this);
	}

	@Override
	public boolean posizionaSopraA(final Component componenteParagone, final int distanzaOrizzantale,
			final int distanzaVerticale) {
		return componenteBase.sopraA(componenteParagone, distanzaOrizzantale, distanzaVerticale, this);
	}

	/**
	 * 
	 * Permette di generare una tabella
	 * 
	 * @param primo
	 * @param nomiColonne
	 * @return Table
	 */
	public static TableBase2d createTable(final Object[][] primo, final String[] nomiColonne,
			final Container contenitorePadre) {
		final TableBase2d table = new TableBase2d(primo, nomiColonne, contenitorePadre) {

			private static final long serialVersionUID = 1L;

			@Override
			public StyleBase settaStileOverride() {
				return null;
			}
		};

		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);
		table.setRowHeight(13);
		return table;
	}

	public TableCellRenderer setStyleColumn() {

		final DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getTableCellRendererComponent(final JTable table, final Object value,
					final boolean isSelected, final boolean hasFocus, final int row, final int column) {
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

	public class StyleBaseTable2d extends StyleBase {

	}

	public Container getContenitorePadre() {
		return contenitorePadre;
	}

	public void setContenitorePadre(final Container contenitorePadre) {
		this.contenitorePadre = contenitorePadre;
	}

	public ComponenteBase getComponenteBase() {
		return componenteBase;
	}

	@Override
	public boolean repaintCustomizzato(final Object[] parametri) {
		return componenteBase.repaintCustomizzato(parametri);
	}

	@Override
	public int getLarghezzaSingleStringa(final Graphics g, final String label) {
		return componenteBase.getLarghezzaSingleStringa(g, label, this);
	}

	@Override
	public int getAltezzaSingleStringa(final Graphics g) {
		return componenteBase.getAltezzaSingleStringa(g, this);
	}
}