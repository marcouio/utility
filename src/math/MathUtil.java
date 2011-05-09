package math;

public class MathUtil {

	/**
	 * Restituisce un indice casuale da un valore massimo. L'indice è generato
	 * per array e liste quindi se l'indice generato è uguale al parametro
	 * passato lo riduci di uno.
	 * 
	 * @param size
	 * @return int indice
	 */
	public int getIndiceRandom(int size) {
		Long indexGenerato = Math.round(Math.random() * size);
		Integer index = indexGenerato.intValue();
		if (index == size) {
			index--;
		}
		return index;
	}

	public static boolean checkDouble(String Doble) {
		boolean ok = true;
		try {
			arrotondaDecimaliDouble(Double.parseDouble(Doble), 2);
		} catch (Exception e) {
			ok = false;
		}
		return ok;
	}

	public static double arrotondaDecimaliDouble(double d, int decimali) {
		String moltiplicatore = "1";
		for (int i = 0; i < decimali; i++) {
			moltiplicatore += "0";
		}
		int moltiplicatoreInt = Integer.parseInt(moltiplicatore);
		Double moltiplicato = d * moltiplicatoreInt;
		String arrotondato = Long.toString(Math.round(moltiplicato));
		return Double.parseDouble(arrotondato) / moltiplicatoreInt;

	}

	public static void main(String[] args) {
		double arro2 = arrotondaDecimaliDouble(34.444, 4);
		System.out.println(arro2);
	}

}
