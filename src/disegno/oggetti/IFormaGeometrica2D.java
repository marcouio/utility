package disegno.oggetti;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;

public interface IFormaGeometrica2D extends IFormaGeometrica {

	public int getWidth();

	public int getHeight();

	public void setWidth(final int width);

	public void setHeight(final int height);

	public void setSize(final int width, final int height);

	public Dimension getSize();

	public int getX();

	public int getY();

	public void setX(final int x);

	public void setY(final int y);

	public void setLocation(final int x, final int y);

	public Point getLocation();

	public ArrayList<Lato> getListaLati();

	public ArrayList<Lato> setMouseSuiLati(Point mouse);

}
