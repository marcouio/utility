package command;

import java.util.ArrayList;
import java.util.Iterator;

public class MacroCommand extends AbstractCommand {

	ArrayList<AbstractCommand> listaComandiInterna = new ArrayList<AbstractCommand>();
	
	public void add(final AbstractCommand comando) {
		listaComandiInterna.add(comando);
	}
	
	public void add(final int index, final AbstractCommand comando) {
		listaComandiInterna.add(index, comando);
	}
	
	public void remove(final int index) {
		listaComandiInterna.remove(index);
	}
	
	public void remove(final AbstractCommand comando){
		listaComandiInterna.remove(comando);
	}
	
	@Override
	public boolean execute() {
		for (Iterator<AbstractCommand> iterator = listaComandiInterna.iterator(); iterator.hasNext();) {
			AbstractCommand comando = (AbstractCommand) iterator.next();
			comando.execute();
			return true;
		}
		return false;
	}

	@Override
	public boolean unExecute() {
		for (Iterator<AbstractCommand> iterator = listaComandiInterna.iterator(); iterator.hasNext();) {
			AbstractCommand comando = (AbstractCommand) iterator.next();
			comando.unExecute();
			return true;
		}
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
