package com.molinari.utility.paint;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import com.molinari.utility.paint.objects.FormaGeometrica;
import com.molinari.utility.paint.objects.IFormaGeometrica;

public class FormaCompostaBase extends FormaGeometrica implements IFormaComposta {

	private List<IFormaGeometrica> forme = new ArrayList<>();
	
	@Override
	public Point getPuntoCentrale() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void ridimensiona(Point mouse) {
		for (IFormaGeometrica forma : forme) {
			boolean inRegion = forma.isInRegion(mouse);
			if(inRegion){
				forma.ridimensiona(mouse);
			}
		}
	}

	@Override
	public boolean isInRegion(Point mouse) {
		for (IFormaGeometrica forma : forme) {
			boolean inRegion = forma.isInRegion(mouse);
			if(inRegion){
				return true;
			}
		}
		return false;
	}

	@Override
	public void settaDistanzaDaMouse(Point puntatoreMouse) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add(IFormaGeometrica formaGeometrica) {
		forme.add(formaGeometrica);
	}

	@Override
	public void remove(IFormaGeometrica formaGeometrica) {
		forme.remove(formaGeometrica);
	}

	@Override
	public void remove(int index) {
		forme.remove(index);
	}

	@Override
	public IFormaGeometrica get(int index) {
		return forme.get(index);
	}
	
}
