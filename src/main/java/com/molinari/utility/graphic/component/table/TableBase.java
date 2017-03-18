package main.java.com.molinari.utility.graphic.component.table;

import main.java.com.molinari.utility.graphic.component.base.ComponenteBaseScrollPane;
import main.java.com.molinari.utility.graphic.component.base.IComponenteBase;
import main.java.com.molinari.utility.graphic.component.style.StyleBase;
import main.java.com.molinari.utility.graphic.component.style.StyleTable;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

public class TableBase extends JTable implements FocusListener, IComponenteBase {

	
	private Container contenitorePadre;
	private final ComponenteBaseScrollPane componenteBase = new ComponenteBaseScrollPane(this);
	
	boolean isCellEditable = false;
	protected Color backgroundNotSel  = null;
	protected Color backgroundSel  = null;
	protected Color foregroundNotSel  = null;
	protected Color foregroundSel  = null;
	
	protected Color backgroundPrimaRiga = null;
	protected Color backgroundPrimaColonna = null;
	protected Color foregroundPrimaRiga = null;
	protected Color foregroundPrimaColonna = null;
	
	private static final long serialVersionUID = 1L;

	public TableBase(final Container contenitorePadre) {
		super();
		makeGUI(contenitorePadre);
		init(contenitorePadre, this);
	}

	public TableBase(final TableModel dm, final Container contenitorePadre) {
		super(dm);
		makeGUI(contenitorePadre);
		init(contenitorePadre, this);
	}

	public TableBase(final int numRows, final int numColumns, final Container contenitorePadre) {
		super(numRows, numColumns);
		makeGUI(contenitorePadre);
		init(contenitorePadre, this);
	}

	public TableBase(final String[][] movimenti, final String[] nomiColonne, final Container contenitorePadre) {
		super(movimenti, nomiColonne);
		makeGUI(contenitorePadre);
		init(contenitorePadre, this);
	}

	@Override
	public void init(final Container contenitorePadre2, final Component componenteFiglio) {
		contenitorePadre = contenitorePadre2;
		componenteBase.init(contenitorePadre2, componenteFiglio);
		this.addFocusListener(this);
		StyleTable styleTable = new StyleTable("stylebasetable");
		this.applicaStile(styleTable, this);
		setSize(this.generaDimensioniMinime());
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

		return new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected, final boolean hasFocus, final int row, final int column) {
				final Component rendered = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				if (isSelected && hasFocus) {
					rendered.setBackground(getBackgroundSel());
					rendered.setForeground(getForegroundSel());
				} else {
					rendered.setBackground(getBackgroundNotSel());
					rendered.setForeground(getForegroundNotSel());
				}
				if (column == 0) {
					if(getBackgroundPrimaColonna() != null){
						rendered.setBackground(getBackgroundPrimaColonna());
					}
					if(getForegroundPrimaColonna() != null){
						rendered.setForeground(getForegroundPrimaColonna());
					}
				}
				if (row == 0) {
					if(getBackgroundPrimaRiga() != null){
						rendered.setBackground(getBackgroundPrimaRiga());
					}
					if(getForegroundPrimaRiga() != null){
						rendered.setForeground(getForegroundPrimaRiga());
					}
				} 
				return rendered;
			}
		};

	}

	@Override
	public boolean isCellEditable(final int row, final int column) {
		return isCellEditable;
	}

	@Override
	public void applicaStile(final StyleBase styleBase, IComponenteBase comp) {
		componenteBase.applicaStile(styleBase, comp);
		
		if(styleBase != null && styleBase instanceof StyleTable){
			
			StyleTable styleTable = (StyleTable) styleBase;
			this.setBackgroundNotSel(styleTable.getBackgroundNotSel());
			this.setBackgroundPrimaColonna(styleTable.getBackgroundPrimaColonna());
			this.setBackgroundPrimaRiga(styleTable.getBackgroundPrimaRiga());
			this.setBackgroundSel(styleTable.getBackgroundSel());
			this.setForegroundNotSel(styleTable.getForegroundNotSel());
			this.setForegroundPrimaColonna(styleTable.getForegroundPrimaColonna());
			this.setForegroundPrimaRiga(styleTable.getForegroundPrimaRiga());
			this.setForegroundSel(styleTable.getForegroundSel());
			this.setCellEditable(styleTable.isCellEditable());
		}
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

	public boolean isCellEditable() {
		return isCellEditable;
	}

	public void setCellEditable(boolean isCellEditable) {
		this.isCellEditable = isCellEditable;
	}

	@Override
	public boolean repaintCustomizzato(final Object[] parametri) {
		if(componenteBase.repaintCustomizzato(parametri,this)){
			
			final AbstractTableModel tableModel = (DefaultTableModel) parametri[IComponenteBase.PARAM_REPAINT_MODEL];
			setModel(tableModel);
			componenteBase.ridisegna(this);
			return true;
		}
		return false;
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
	
	public Color getBackgroundNotSel() {
		return backgroundNotSel;
	}

	public void setBackgroundNotSel(Color backgroundNotSel) {
		this.backgroundNotSel = backgroundNotSel;
	}

	public Color getBackgroundSel() {
		return backgroundSel;
	}

	public void setBackgroundSel(Color backgroundSel) {
		this.backgroundSel = backgroundSel;
	}

	public Color getForegroundNotSel() {
		return foregroundNotSel;
	}

	public void setForegroundNotSel(Color foregroundNotSel) {
		this.foregroundNotSel = foregroundNotSel;
	}

	public Color getForegroundSel() {
		return foregroundSel;
	}

	public void setForegroundSel(Color foregroundSel) {
		this.foregroundSel = foregroundSel;
	}

	public Color getBackgroundPrimaRiga() {
		return backgroundPrimaRiga;
	}

	public void setBackgroundPrimaRiga(Color backgroundPrimaRiga) {
		this.backgroundPrimaRiga = backgroundPrimaRiga;
	}

	public Color getBackgroundPrimaColonna() {
		return backgroundPrimaColonna;
	}

	public void setBackgroundPrimaColonna(Color backgroundPrimaColonna) {
		this.backgroundPrimaColonna = backgroundPrimaColonna;
	}

	public Color getForegroundPrimaRiga() {
		return foregroundPrimaRiga;
	}

	public void setForegroundPrimaRiga(Color foregroundPrimaRiga) {
		this.foregroundPrimaRiga = foregroundPrimaRiga;
	}

	public Color getForegroundPrimaColonna() {
		return foregroundPrimaColonna;
	}

	public void setForegroundPrimaColonna(Color foregroundPrimaColonna) {
		this.foregroundPrimaColonna = foregroundPrimaColonna;
	}

	@Override
	public void makeGUI(Container contenitorePadre) {
		// TODO Auto-generated method stub
		
	}

}
