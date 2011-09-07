package grafica.componenti.table;

import grafica.componenti.UtilComponenti;

import java.awt.Dimension;

import javax.swing.JPanel;

/**
 * Fornisce il codice comune per le tabelle e stabilisce i metodi da
 * implementare per le classi figlie
 * 
 * @author marco.molinari
 * 
 */
public abstract class AbstractGeneratoreTabella {

	private Object[][] matrice;

	public AbstractGeneratoreTabella() {
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

	private void inserisciNomiRigheToMatrice(Object[][] matrice2, String[] nomiRighe) {
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
	 * Permette di generare una tabella
	 * 
	 * @param primo
	 * @param nomiColonne
	 * @return Table
	 */
	public static TableBase createTable(final Object[][] primo, final String[] nomiColonne) {
		TableBase table = new TableBase(primo, nomiColonne);

		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);
		table.setRowHeight(13);
		return table;
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

		TableBase table = new TableBase(primo, nomiColonne);
		TableScrollPane pane = new TableScrollPane(table);
		table.setContenitore(pane);
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);
		table.setRowHeight(13);
		return pane;
	}

	public void setCellaMatrice(int i, int x, Object valore) {
		matrice[i][x] = valore;
	}

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

	public static void main(String[] args) {
		AbstractGeneratoreTabella gen = new AbstractGeneratoreTabella() {

			@Override
			public Object setCellaMatricePerRicorsione(int i, int x) {
				return "Ciao";
			}

			@Override
			protected String[] costruisciArrayNomiColonna() {
				final String[] nomiColonne = { "Mesi", "Fisse", "Variabili" };
				return nomiColonne;
			}

			@Override
			protected String[] costruisciArrayNomeRighe() {
				String[] nomiRighe = new String[3];
				for (int i = 0; i < nomiRighe.length; i++) {
					nomiRighe[i] = "nomeRiga" + i;
				}
				return nomiRighe;
			}
		};
		gen.setCellaMatrice(2, 2, "bobobbo");
		TableBase table = AbstractGeneratoreTabella.createTable(gen.matrice, gen.costruisciArrayNomiColonna());
		table.setStyleColumn();
		table.setBounds(140, 140, 400, 100);
		TableScrollPane pane = new TableScrollPane(table);
		table.setOpaque(true); //content panes must be opaque
		JPanel panel = UtilComponenti.initContenitoreFrame(null);
		panel.add(pane);
	}
}
