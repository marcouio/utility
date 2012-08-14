package command;


public abstract class AbstractCommand implements ICommand {

	@Override
	public boolean doCommand() throws Exception {
		if (execute()) {
			scriviLogExecute(true);
			return true;
		} else {
			scriviLogExecute(false);
			return false;
		}
	}

	@Override
	public boolean undoCommand() throws Exception {
		if (unExecute()) {
			scriviLogUnExecute(true);
			return true;
		} else {
			scriviLogUnExecute(false);
			return false;
		}
	}

	@Override
	public abstract boolean execute() throws Exception;

	@Override
	public abstract boolean unExecute() throws Exception;

	@Override
	public abstract void scriviLogExecute(boolean isComandoEseguito);

	@Override
	public abstract void scriviLogUnExecute(boolean isComandoEseguito);

}
