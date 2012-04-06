package disegno.oggetti;

import java.awt.Graphics;

import disegno.oggetti.painter.IPainter;


public abstract class FormaGeometrica implements IFormaGeometrica {

	protected IPainter painter;
	private String nome;

	public FormaGeometrica() {
	}

	public FormaGeometrica(final IPainter painter) {
		this.painter = painter;
	}

	public FormaGeometrica(final String nome, final IPainter painter) {
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