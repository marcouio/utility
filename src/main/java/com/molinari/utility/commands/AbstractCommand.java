package com.molinari.utility.commands;


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
	public boolean execute() {
		return true;
	}

	@Override
	public boolean unExecute() {
		return true;
	}

	@Override
	public void scriviLogExecute(boolean isComandoEseguito){
		// do nothing here
	}

	@Override
	public void scriviLogUnExecute(boolean isComandoEseguito){
		// do nothing here
	}

}
