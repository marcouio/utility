package grafica.componenti.table.table;

import grafica.componenti.componenteBase.ComponenteBaseScrollPane;
import grafica.componenti.componenteBase.IComponenteBase;
import grafica.componenti.style.StyleBase;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

public class TableBase extends JTable implements FocusListener, IComponenteBase {

	protected StyleBase style = new StyleBase();
	boolean isCellEditable = false;
	private Container contenitorePadre;
	private final ComponenteBaseScrollPane componenteBase = new ComponenteBaseScrollPane(this);
	protected Color coloreBackground;
	private static final long serialVersionUID = 1L;

	public TableBase(final Container contenitorePadre) {
		super();
		init(contenitorePadre, this);
	}

	public TableBase(final TableModel dm, final Container contenitorePadre) {
		super(dm);
		init(contenitorePadre, this);
	}

	public TableBase(final int numRows, final int numColumns, final Container contenitorePadre) {
		super(numRows, numColumns);
		init(contenitorePadre, this);
	}

	public TableBase(final String[][] movimenti, final String[] nomiColonne, final Container contenitorePadre) {
		super(movimenti, nomiColonne);
		init(contenitorePadre, this);
	}

	@Override
	public void init(final Container contenitorePadre2, final Component componenteFiglio) {
		contenitorePadre = contenitorePadre2;
		componenteBase.init(contenitorePadre2, componenteFiglio);
		this.addFocusListener(this);
		this.settaStile();
		setSize(this.generaDimensioniMinime());
		System.out.println("altezza: " + this.getHeight() + ". larghezza: " + this.getWidth());
	}

	public Dimension generaDimensioniMinime() {
		final int altezza = this.getRowCount() * this.getRowHeight();
		final int larghezza = this.getColumnCount() * this.getLarghezzaMinimaColonna();
		return new Dimension(larghezza, altezza);
	}

	private int getLarghezzaMinimaColonna() {
		int larghezzaMinima = 0;
		for (int i = 0; i < getColumnCount(); i++) {
			final String nomeColonna = getColumnName(i);
			final int larghezzaNome = componenteBase.getLarghezzaSingleStringa(this.getGraphics(), nomeColonna, this);
			if (larghezzaNome > larghezzaMinima) {
				larghezzaMinima = larghezzaNome;
			}
		}
		return larghezzaMinima;
	}

	@Override
	public TableCellRenderer getCellRenderer(final int row, final int column) {
		return setStyleColumn();
	}

	// TODO
	public TableCellRenderer setStyleColumn() {

		final DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected, final boolean hasFocus, final int row, final int column) {
				final Component rendered = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				if (isSelected && hasFocus) {
					rendered.setBackground(Color.WHITE);
					rendered.setForeground(Color.RED);
				} else {
					rendered.setBackground(Color.RED);
					rendered.setForeground(Color.WHITE);
				}
				if (column == 1) {
					if (coloreBackground != null) {
						rendered.setBackground(coloreBackground);
						rendered.setForeground(Color.WHITE);
					}
				} else if (column == 0 && row == 10) {
					rendered.setBackground(coloreBackground);
					rendered.setForeground(Color.GREEN);
				}
				return rendered;
			}
		};
		return dtcr;

	}

	@Override
	public boolean isCellEditable(final int row, final int column) {
		return isCellEditable;
	}

	@Override
	public void settaStile() {
		componenteBase.settaStile(style, this);
		if (settaStileOverride() != null) {
			style = settaStileOverride();
			componenteBase.settaStile(style, this);
		}
	}

	public StyleBase settaStileOverride() {
		return new StyleBase("StyleBaseTable2d");
	}

	@Override
	public void focusGained(final FocusEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void focusLost(final FocusEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public Container getContenitorePadre() {
		return contenitorePadre;
	}

	public void setContenitorePadre(final Container contenitorePadre) {
		this.contenitorePadre = contenitorePadre;
	}

	@Override
	public boolean repaintCustomizzato(final Object[] parametri) {
		return componenteBase.repaintCustomizzato(parametri);
	}

	/**
	 * Metodo facade di metodo omonimo per facilitarne l'accesso e la
	 * leggibilita'
	 * 
	 * @param componenteParagone
	 * @param distanzaOrizzantale
	 * @param distanzaVerticale
	 * @return
	 */
	public boolean posizionaADestraDi(final Component componenteParagone, final int distanzaOrizzantale, final int distanzaVerticale) {
		return posizionaADestraDi(componenteParagone, distanzaOrizzantale, distanzaVerticale, this);
	}

	/**
	 * Metodo facade di metodo omonimo per facilitarne l'accesso e la
	 * leggibilita'
	 * 
	 * @param componenteParagone
	 * @param distanzaOrizzantale
	 * @param distanzaVerticale
	 * @return
	 */
	public boolean posizionaASinistraDi(final Component componenteParagone, final int distanzaOrizzantale, final int distanzaVerticale) {
		return posizionaASinistraDi(componenteParagone, distanzaOrizzantale, distanzaVerticale, this);
	}

	/**
	 * Metodo facade di metodo omonimo per facilitarne l'accesso e la
	 * leggibilita'
	 * 
	 * @param componenteParagone
	 * @param distanzaOrizzantale
	 * @param distanzaVerticale
	 * @return
	 */
	public boolean posizionaSottoA(final Component componenteParagone, final int distanzaOrizzantale, final int distanzaVerticale) {
		return posizionaSottoA(componenteParagone, distanzaOrizzantale, distanzaVerticale, this);
	}

	/**
	 * Metodo facade di metodo omonimo per facilitarne l'accesso e la
	 * leggibilita'
	 * 
	 * @param componenteParagone
	 * @param distanzaOrizzantale
	 * @param distanzaVerticale
	 * @return
	 */
	public boolean posizionaSopraA(final Component componenteParagone, final int distanzaOrizzantale, final int distanzaVerticale) {
		return posizionaSopraA(componenteParagone, distanzaOrizzantale, distanzaVerticale, this);
	}

	@Override
	public boolean posizionaADestraDi(final Component componenteParagone, final int distanzaOrizzantale, final int distanzaVerticale, final Component componenteDaRiposizionare) {
		return componenteBase.posizionaADestraDi(componenteParagone, distanzaOrizzantale, distanzaVerticale, componenteDaRiposizionare);
	}

	@Override
	public boolean posizionaASinistraDi(final Component componenteParagone, final int distanzaOrizzontale, final int distanzaVerticale, final Component componenteDaRiposizionare) {
		return componenteBase.posizionaASinistraDi(componenteParagone, distanzaOrizzontale, distanzaVerticale, componenteDaRiposizionare);
	}

	@Override
	public boolean posizionaSottoA(final Component componenteParagone, final int distanzaOrizzantale, final int distanzaVerticale, final Component componenteDaRiposizionare) {
		return componenteBase.posizionaSottoA(componenteParagone, distanzaOrizzantale, distanzaVerticale, componenteDaRiposizionare);
	}

	@Override
	public boolean posizionaSopraA(final Component componenteParagone, final int distanzaOrizzantale, final int distanzaVerticale, final Component componenteDaRiposizionare) {
		componenteBase.posizionaSopraA(componenteParagone, distanzaOrizzantale, distanzaVerticale, componenteDaRiposizionare);
		return false;
	}

	@Override
	public int getLarghezza() {
		return (int) generaDimensioniMinime().getWidth();
	}

	@Override
	public int getAltezza() {
		return (int) generaDimensioniMinime().getHeight();
	}

	public Color getColoreBackground() {
		return coloreBackground;
	}

	public void setColoreBackground(final Color coloreBackground) {
		this.coloreBackground = coloreBackground;
	}
}
