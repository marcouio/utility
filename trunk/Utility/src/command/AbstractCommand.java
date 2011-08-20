package command;


public abstract class AbstractCommand implements ICommand {

	@Override
	public boolean doCommand() {
		if (execute()) {
			scriviLogExecute(true);
			return true;
		} else {
			scriviLogExecute(false);
			return false;
		}
	}

	@Override
	public boolean undoCommand() {
		if (unExecute()) {
			scriviLogUnExecute(true);
			return true;
		} else {
			scriviLogUnExecute(false);
			return false;
		}
	}

	@Override
	public abstract boolean execute();

	@Override
	public abstract boolean unExecute();

	@Override
	public abstract void scriviLogExecute(boolean isComandoEseguito);

	@Override
	public abstract void scriviLogUnExecute(boolean isComandoEseguito);

}
