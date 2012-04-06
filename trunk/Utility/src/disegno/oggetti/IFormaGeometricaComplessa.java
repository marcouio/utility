package disegno.oggetti;

public interface IFormaGeometricaComplessa extends IFormaGeometrica {

	public void add(final IFormaGeometrica oggettoGrafico);

	public void remove(final IFormaGeometrica oggettoGrafico);

	public IFormaGeometrica getChild(final int index);
}
