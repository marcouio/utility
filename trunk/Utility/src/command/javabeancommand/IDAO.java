package command.javabeancommand;

import java.util.Iterator;
import java.util.Vector;

public interface IDAO {

	public AbstractOggettoEntita getEntitaPadre();

	public Object selectById(int id);

	public Iterator<Object> selectWhere(String where);

	public Vector<Object> selectAll();

	public boolean insert(Object oggettoEntita);

	public boolean delete(int id);

	public boolean update(Object oggettoEntita);

	public boolean deleteAll();
}
