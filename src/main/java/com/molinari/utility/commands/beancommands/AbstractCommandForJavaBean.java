package com.molinari.utility.commands.beancommands;

import java.util.Map;

import com.molinari.utility.commands.AbstractCommand;
import com.molinari.utility.database.dao.IDAO;

public abstract class AbstractCommandForJavaBean<T extends AbstractOggettoEntita> extends AbstractCommand {

	protected T entita;
	protected IDAO<T> wrap;
	protected Map<String, T> mappaCache;
}
