package disegno.oggetti.poligoni;

import java.awt.Point;
import java.util.ArrayList;

import disegno.oggetti.IFormaGeometrica2D;

public interface IPoligono extends IFormaGeometrica2D {

	public ArrayList<Lato> getListaLati();

	public ArrayList<Lato> setMouseSuiLati(Point mouse);
}
