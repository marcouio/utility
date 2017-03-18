package main.java.com.molinari.utility.paint.objects;

public interface IFormaGeometricaComplessa extends IFormaGeometrica {

	public void add(final IFormaGeometrica oggettoGrafico);

	public void remove(final IFormaGeometrica oggettoGrafico);

	public IFormaGeometrica getChild(final int index);
}
