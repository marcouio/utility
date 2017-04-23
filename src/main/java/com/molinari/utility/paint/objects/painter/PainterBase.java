package com.molinari.utility.paint.objects.painter;

import java.awt.Graphics;

import com.molinari.utility.paint.objects.IFormaGeometrica;

public class PainterBase<T extends IFormaGeometrica> implements IPainter<T> {

	private T oggettoGrafico;

	public PainterBase(final T oggettoGrafico) {
		this.oggettoGrafico = oggettoGrafico;
	}

	@Override
	public void paint(final Graphics g) {
		if (oggettoGrafico == null) {
			return;
		}
	}

	public T getOggettoGrafico() {
		return oggettoGrafico;
	}

}
