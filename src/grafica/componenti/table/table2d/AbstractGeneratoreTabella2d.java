package grafica.componenti.table.table2d;

import java.lang.reflect.InvocationTargetException;

import grafica.componenti.UtilComponenti;
import grafica.componenti.table.TableScrollPane;
import grafica.componenti.table.table.TableBase;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
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

		matrice = new String[nomiRighe.length+1][nomiColonne.length+1];
		inserisciNomiRigheToMatrice(matrice, nomiRighe);
		inserisciNomiColonneToMatrice(matrice, nomiColonne);

		for (int i = 1; i <= nomiRighe.length; i++) {
			for (int x = 1; x <= nomiColonne.length; x++) {
				try {
					matrice[i][x] = setCellaMatricePerRicorsione(i, x);
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void inserisciNomiColonneToMatrice(final Object[][] matrice, final String[] nomiColonne) {
		for (int i = 0; i < nomiColonne.length; i++) {
			matrice[0][i+1] = nomiColonne[i];
		}
		
	}

	private void inserisciNomiRigheToMatrice(final Object[][] matrice, final String[] nomiRighe) {
		for (int i = 0; i < nomiRighe.length; i++) {
			matrice[i+1][0] = nomiRighe[i];
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
	
	public static void main(String[] args) throws InvocationTargetException, InterruptedException {
		SwingUtilities.invokeAndWait(new Runnable() {

			@Override
			public void run() {
				JPanel panel = UtilComponenti.initContenitoreFrame(null);
				TableBase table = new TableBase(panel);
				panel.add(table);
				table.setLocation(10, 10);
				table.setSize(200, 300);
				table.setModel(new AbstractGeneratoreTabella2d() {
					
					@Override
					public Object setCellaMatricePerRicorsione(int i, int x) {
						return "ciao" + i + x;
					}
					
					@Override
					protected String[] costruisciArrayNomiColonna() {
						return new String[]{"colonna 1", "colonna 2", "colonna 3"};
					}
					
					@Override
					protected String[] costruisciArrayNomeRighe() {
						return new String[]{"riga 1","riga 2","riga 3", "riga 4"};
					}
				});
			}
		});
		
	}

}