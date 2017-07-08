package com.molinari.utility.math;

import org.apache.commons.math3.util.MathUtils;

import com.google.common.primitives.Doubles;
import com.google.common.primitives.Ints;

public class UtilMath {

	/**
	 * Restituisce un indice casuale da un valore massimo. L'indice è generato
	 * per array e liste quindi se l'indice generato è uguale al parametro
	 * passato lo riduci di uno.
	 * 
	 * @param size
	 * @return int indice
	 */
	public int getIndiceRandom(final int size) {
		final Long indexGenerato = Math.round(Math.random() * size);
		Integer index = indexGenerato.intValue();
		if (index == size) {
			index--;
		}
		return index;
	}
	
	public static double getPercentage(double value, int percentage){
		return value / 100 * percentage;
	}

	public static boolean checkDouble(final String doble) {
		return Doubles.tryParse(doble) != null;
	}

	/**
	 * Prende l'intero del double e lo sottrae al double stesso per ottenere i
	 * decimali. Quindi trasformo i decimali in long moltiplicando per
	 * 100(verificando che la moltiplicazione non dia 0). In questo modo posso
	 * utilizzare il metodo Math.round che permette l'arrotondamento. Divido
	 * nuovamente per 100 e riottengo i decimali arrotondati a due cifre. A
	 * questo punto posso aggiungerli nuovamente agli interi
	 *
	 * @param d
	 * @return un double arrotondato a due cifre
	 */
	public static double arrotondaDecimaliDouble(final double d) {
		double decimaleArrotondato = 0;
		final String stringaDouble = Double.toString(d);
		final String interi = stringaDouble.substring(0, stringaDouble.indexOf('.'));
		final double parteIntera = Double.parseDouble(interi);
		final double parteDecimali = d - parteIntera;
		boolean equalzero = MathUtils.equals(parteDecimali * 100, 0);
		if (!equalzero) {
			final double decimaliDaArrotondare = parteDecimali * 100;
			final String arrotondato = Long.toString(Math.round(decimaliDaArrotondare));
			decimaleArrotondato = Double.parseDouble(arrotondato) / 100;
		}
		return parteIntera + decimaleArrotondato;
	}

	/**
	 * Arrotonda un double con numero di decimali passati come parametro
	 * 
	 * @param d
	 * @param decimali
	 * @return
	 */
	public static double arrotondaDecimaliDouble(final double d, final int decimali) {
		
		final StringBuilder sb = new StringBuilder("1");
				
		for (int i = 0; i < decimali; i++) {
			sb.append("0");
		}
		final int moltiplicatoreInt = Integer.parseInt(sb.toString());
		final Double moltiplicato = d * moltiplicatoreInt;
		final String arrotondato = Long.toString(Math.round(moltiplicato));
		return Double.parseDouble(arrotondato) / moltiplicatoreInt;

	}

	public static boolean isNumber(String valore) {
		return Ints.tryParse(valore) != null || Doubles.tryParse(valore) != null;
	}
	
	public static boolean doubleEquals(double first, double second){
		return Double.compare(first, second) == 0;
	}

}
