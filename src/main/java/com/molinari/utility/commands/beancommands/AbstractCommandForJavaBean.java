package main.java.com.molinari.utility.commands.beancommands;

import java.util.Map;

import main.java.com.molinari.utility.commands.AbstractCommand;
import main.java.com.molinari.utility.database.dao.IDAO;

public abstract class AbstractCommandForJavaBean<T extends AbstractOggettoEntita> extends AbstractCommand {

	protected T entita;
	protected IDAO<T> wrap;
	protected Map<String, T> mappaCache;
}
