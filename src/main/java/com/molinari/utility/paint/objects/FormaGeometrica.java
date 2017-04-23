package com.molinari.utility.paint.objects;

import java.awt.Graphics;

import com.molinari.utility.paint.objects.painter.IPainter;


public abstract class FormaGeometrica implements IFormaGeometrica {

	protected IPainter<? extends IFormaGeometrica> painter;
	private String nome;

	public FormaGeometrica() {
	}

	public FormaGeometrica(final IPainter<? extends IFormaGeometrica> painter) {
		this.painter = painter;
	}

	public FormaGeometrica(final String nome, final IPainter<? extends IFormaGeometrica> painter) {
		this.nome = nome;
		this.painter = painter;
	}

	@Override
	public String getNome() {
		return this.nome;
	}

	@Override
	public void setNome(final String nome) {
		this.nome = nome;
	}

	public IPainter getPainter() {
		return painter;
	}

	public void setPainter(final IPainter painter) {
		this.painter = painter;
	}

	@Override
	public void draw(final Graphics g) {
		painter.paint(g);
	}
}