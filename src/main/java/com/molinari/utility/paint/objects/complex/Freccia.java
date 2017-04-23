package com.molinari.utility.paint.objects.complex;

import java.awt.Point;

import com.molinari.utility.paint.FormaCompostaBase;
import com.molinari.utility.paint.objects.Segmento;
import com.molinari.utility.paint.objects.punte.PuntaBase;

public class Freccia extends FormaCompostaBase {

	public static final int INDEX_SEGNEMNTO = 0;
	public static final int INDEX_PUNTA = 1;
	
	public Freccia(Segmento segmento, PuntaBase punta) {
		add(segmento);
		add(punta);
	}

	@Override
	public Point getPuntoCentrale() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void settaDistanzaDaMouse(Point puntatoreMouse) {
		// TODO Auto-generated method stub
		
	}

}
