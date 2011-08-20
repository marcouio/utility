package command.javabeancommand;

import java.util.HashMap;

import command.AbstractCommand;

public abstract class AbstractCommandForJavaBean extends AbstractCommand {

	protected AbstractOggettoEntita entita;
	protected IDAO wrap;
	protected HashMap<String, AbstractOggettoEntita> mappaCache;
}
