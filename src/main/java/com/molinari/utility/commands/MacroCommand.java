package com.molinari.utility.commands;

import java.util.ArrayList;
import java.util.Iterator;

public class MacroCommand extends AbstractCommand {

	ArrayList<AbstractCommand> listaComandiInterna = new ArrayList<AbstractCommand>();
	private int indiceCorrente = 0;
	

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
	public boolean execute() throws Exception {
		boolean ok = true;
		for (Iterator<AbstractCommand> iterator = listaComandiInterna.iterator(); iterator.hasNext();) {
			AbstractCommand comando = (AbstractCommand) iterator.next();
			if(!comando.execute()){
				ok = false;
				break;
			}else{
				indiceCorrente++;
			}
		}
		return ok;
	}

	@Override
	public boolean unExecute() throws Exception {
		boolean ok = true;
		for (Iterator<AbstractCommand> iterator = listaComandiInterna.iterator(); iterator.hasNext();) {
			AbstractCommand comando = (AbstractCommand) iterator.next();
			if(!comando.unExecute()){
				ok = false;
				break;
			}else{
				indiceCorrente--;
			}
		}
		return ok;
	}

	@Override
	public void scriviLogExecute(boolean isComandoEseguito) {
		// TODO Auto-generated method stub

	}

	@Override
	public void scriviLogUnExecute(boolean isComandoEseguito) {
		// TODO Auto-generated method stub

	}

	public ArrayList<AbstractCommand> getListaComandiInterna() {
		return listaComandiInterna;
	}
	
	public int getIndiceCorrente() {
		return indiceCorrente;
	}
}
