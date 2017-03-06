package command.javabeancommand;

import java.util.Map;

import command.AbstractCommand;
import db.dao.IDAO;

public abstract class AbstractCommandForJavaBean<T extends AbstractOggettoEntita> extends AbstractCommand {

	protected T entita;
	protected IDAO<T> wrap;
	protected Map<String, T> mappaCache;
}
