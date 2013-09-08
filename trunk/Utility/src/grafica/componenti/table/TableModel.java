package grafica.componenti.table;

import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.table.AbstractTableModel;

public abstract class TableModel extends AbstractTableModel{

	private static final long serialVersionUID = 1L;
	private Object[][] matrice;
	private Riga nomiColonne;
	private ArrayList<Riga> righe;

	public Riga getNomiColonne() {
		return nomiColonne;
	}

	public ArrayList<Riga> getRighe() {
		return righe;
	}

	@Override
	public String getColumnName(final int column) {
		return nomiColonne.getValore(column);
	}

	public void setNomiColonne(Riga riga){
		checkColonne();
		nomiColonne = riga;
	}
	
	public void addColumn(final String column){
		checkColonne();
		nomiColonne.add(column);
	}

	private void checkColonne(){
		if(nomiColonne == null){
			nomiColonne = new Riga();
		}	
	}
	private void checkRighe(){
		if(righe == null){
			righe = new ArrayList<Riga>();
		}	
	}

	public void addRiga(final String[] riga){
		checkRighe();
		righe.add(new Riga(riga));
	}

	public void addRiga(final ArrayList<String> riga){
		checkRighe();
		righe.add(new Riga(riga));
	}

	public void addRiga(final Riga riga){
		checkRighe();
		righe.add(riga);
	}

	public TableModel(final Object parametro) throws Exception {
		preBuild(parametro);
		build(parametro);
	}

	protected abstract void preBuild(Object parametro) throws Exception;

	protected void build(final Object parametro) throws Exception{

		checkMetodi();
		int lunghezza = getNomiColonne().getLunghezza();
		matrice = new String[getRighe().size()][lunghezza];
		
		for (int x = 0; x < lunghezza; x++) {
			for (int i = 0; i < getRighe().size() ; i++) {
				try {
					final Riga riga = getRighe().get(i);
					matrice[i][x] = riga.getValore(x);

				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void checkMetodi() throws Exception {
		if(getNomiColonne() == null) throw new Exception("getNomiColonne() torna null, riempire la proprietà nomiColonne all'interno del metodo preBuild()");
		if(getRighe() == null) throw new Exception("getRighe() torna null, riempire la proprietà righe all'interno del metodo preBuild()");
	}


	public Object[][] getMatrice() {
		return matrice;
	}

	public void setCellaMatrice(final int i, final int x, final Object valore) {
		matrice[i][x] = valore;
	}

	@Override
	public int getRowCount() {
		return matrice.length;
	}

	@Override
	public int getColumnCount() {
		return matrice[0].length;
	}

	@Override
	public Object getValueAt(final int rowIndex, final int columnIndex) {
		return matrice[rowIndex][columnIndex];
	}

	public class Riga {
		ArrayList<String> celle = new ArrayList<String>();

		public Riga() {

		}

		public Riga(final String[] celle) {
			this.celle = new ArrayList<String>(Arrays.asList(celle));
		}
		public Riga(final ArrayList<String> celle){
			this.celle = celle;
		}

		public int getLunghezza(){
			return celle.size();
		}

		public void add(final String cella){
			this.celle.add(cella);
		}

		public String getValore(final int index){
			if(index >= celle.size()){
				System.err.println("Stai provando ad accedere alla riga con un indice più grande della lunghezza della riga");
			}else{
				return celle.get(index);
			}
			return null;
		}

		public void add(final String cella, final int index){
			if(index >= celle.size()){
				System.err.println("Stai provando ad inserire la stringa in un indice più grande della lunghezza della riga");
			}else{
				this.celle.add(index, cella);
			}
		}

		public void set(final String cella, final int index){
			if(index >= celle.size()){
				System.err.println("Stai provando a settare la stringa in un indice più grande della lunghezza della riga");
			}else{
				this.celle.set(index, cella);
			}
		}

		public ArrayList<String> getListaCelle() {
			return celle;
		}
	}
}
