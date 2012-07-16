package grafica.componenti.table;

import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.table.AbstractTableModel;

public abstract class TableModel extends AbstractTableModel{

	private static final long serialVersionUID = 1L;
	private Object[][] matrice;

	public ArrayList<Riga> righe = new ArrayList<Riga>();
	
	public ArrayList<Riga> getRighe() {
		return righe;
	}

	public void setRighe(final ArrayList<Riga> righe) {
		this.righe = righe;
	}
	
	public void addRiga(final String[] riga){
		righe.add(new Riga(riga));
	}
	
	public void addRiga(final ArrayList<String> riga){
		righe.add(new Riga(riga));
	}

	public TableModel(Object parametro) {
		preBuild(parametro);
		build(parametro);
	}
	
	protected abstract void preBuild(Object parametro);

	protected void build(Object parametro){
		
		int lunghezza = getRighe().get(0).getLunghezza();
		matrice = new String[getRighe().size()][lunghezza];

		for (int i = 1; i <= getRighe().size(); i++) {
			for (int x = 1; x <= lunghezza; x++) {
				try {
					final Riga riga = getRighe().get(i);
					matrice[i][x] = riga.getValore(x);
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
		}
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
	public Object getValueAt(int rowIndex, int columnIndex) {
		return matrice[rowIndex][columnIndex];
	}
	
	public class Riga {
		ArrayList<String> celle = new ArrayList<String>();
		
		public Riga(String[] celle) {
			this.celle = (ArrayList<String>) Arrays.asList(celle);
		}
		public Riga(final ArrayList<String> celle){
			this.celle = celle;
		}
		
		public int getLunghezza(){
			return celle.size();
		}
		
		public void add(String cella){
			this.celle.add(cella);
		}
		
		public String getValore(int index){
			if(index >= celle.size()){
				System.err.println("Stai provando ad accedere alla riga con un indice più grande della lunghezza della riga");
			}else{
				return celle.get(index);
			}
			return null;
		}
		
		public void add(String cella, int index){
			if(index >= celle.size()){
				System.err.println("Stai provando ad inserire la stringa in un indice più grande della lunghezza della riga");
			}else{
				this.celle.add(index, cella);
			}
		}
		
		public void set(String cella, int index){
			if(index >= celle.size()){
				System.err.println("Stai provando a settare la stringa in un indice più grande della lunghezza della riga");
			}else{
				this.celle.set(index, cella);
			}
		}
	}
}
