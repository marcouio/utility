package command;

import java.util.ArrayList;

public class MacroCommand extends AbstractCommand {

	ArrayList<AbstractCommand> listaComandiInterna = new ArrayList<AbstractCommand>();
	
	public void add(final AbstractCommand comando) {
		listaComandiInterna.add(comando);
	}
	
	@Override
	public boolean execute() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean unExecute() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void scriviLogExecute(boolean isComandoEseguito) {
		// TODO Auto-generated method stub

	}

	@Override
	public void scriviLogUnExecute(boolean isComandoEseguito) {
		// TODO Auto-generated method stub

	}

}
