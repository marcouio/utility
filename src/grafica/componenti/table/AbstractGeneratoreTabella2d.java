package grafica.componenti.table;

import grafica.componenti.UtilComponenti;
import grafica.componenti.style.StyleBase;

import java.awt.FlowLayout;

import javax.swing.JPanel;
import javax.swing.table.AbstractTableModel;

/**
 * Fornisce il codice comune per le tabelle e stabilisce i metodi da
 * implementare per le classi figlie
 * 
 * @author marco.molinari
 * 
 */
public abstract class AbstractGeneratoreTabella2d extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private Object[][] matrice;

	public AbstractGeneratoreTabella2d() {
		init();
	}

	private void init() {
		final String[] nomiColonne = costruisciArrayNomiColonna();
		final String[] nomiRighe = costruisciArrayNomeRighe();

		matrice = new String[nomiRighe.length][nomiColonne.length];
		inserisciNomiRigheToMatrice(matrice, nomiRighe);

		for (int i = 0; i < nomiRighe.length; i++) {
			for (int x = 1; x < nomiColonne.length; x++) {
				try {
					matrice[i][x] = setCellaMatricePerRicorsione(i, x);
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void inserisciNomiRigheToMatrice(final Object[][] matrice2, final String[] nomiRighe) {
		for (int i = 0; i < nomiRighe.length; i++) {
			matrice[i][0] = nomiRighe[i];
		}

	}

	/**
	 * Da implementare in ogni sottoclasse, deve contenere un array di string
	 * con i nomi delle righe
	 * 
	 * @return String[]
	 */
	protected abstract String[] costruisciArrayNomeRighe();

	/**
	 * Da implementare in ogni sottoclasse, deve contenere un array di string
	 * con i nomi delle colonne
	 * 
	 * @return String[]
	 */
	protected abstract String[] costruisciArrayNomiColonna();

	/**
	 * 
	 * @param i
	 * @param x
	 * @return
	 */
	public abstract Object setCellaMatricePerRicorsione(int i, int x);

	public Object[][] getMatrice() {
		return matrice;
	}

	public void setCellaMatrice(final int i, final int x, final Object valore) {
		matrice[i][x] = valore;
	}

	public static void main(final String[] args) {
		final AbstractGeneratoreTabella2d gen = new AbstractGeneratoreTabella2d() {

			private static final long serialVersionUID = 1L;

			@Override
			public Object setCellaMatricePerRicorsione(final int i, final int x) {
				return "Ciao";
			}

			@Override
			protected String[] costruisciArrayNomiColonna() {
				final String[] nomiColonne = { "Mesi", "Fisse", "Variabili" };
				return nomiColonne;
			}

			@Override
			protected String[] costruisciArrayNomeRighe() {
				final String[] nomiRighe = new String[3];
				for (int i = 0; i < nomiRighe.length; i++) {
					nomiRighe[i] = "nomeRiga" + i;
				}
				return nomiRighe;
			}
		};
		gen.setCellaMatrice(2, 2, "bobobbo");
		//		final TableBase2d table = AbstractGeneratoreTabella2d.createTable(gen.matrice, gen.costruisciArrayNomiColonna());
		final TableScrollPane pane = new TableScrollPane();
		final TableBase2d table = new TableBase2d(gen, pane) {
			private static final long serialVersionUID = 1L;

			@Override
			public StyleBase settaStileOverride() {
				return null;
			}
		};
		table.setStyleColumn();
		table.setBounds(140, 140, 400, 100);
		table.setOpaque(true); //content panes must be opaque
		final JPanel panel = UtilComponenti.initContenitoreFrame(new FlowLayout());
		panel.add(pane);
	}

	@Override
	public int getRowCount() {
		return costruisciArrayNomeRighe().length;
	}

	@Override
	public int getColumnCount() {
		return costruisciArrayNomiColonna().length;
	}

	@Override
	public Object getValueAt(final int rowIndex, final int columnIndex) {
		return matrice[rowIndex][columnIndex];
	}

}