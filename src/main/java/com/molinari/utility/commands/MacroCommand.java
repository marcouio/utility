package com.molinari.utility.commands;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MacroCommand extends AbstractCommand {

	ArrayList<AbstractCommand> listaComandiInterna = new ArrayList<>();
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
	public boolean execute() {
		boolean ok = true;
		for (Iterator<AbstractCommand> iterator = listaComandiInterna.iterator(); iterator.hasNext();) {
			AbstractCommand comando = iterator.next();
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
	public boolean unExecute() {
		boolean ok = true;
		for (Iterator<AbstractCommand> iterator = listaComandiInterna.iterator(); iterator.hasNext();) {
			AbstractCommand comando = iterator.next();
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
		//do nothing		
	}

	@Override
	public void scriviLogUnExecute(boolean isComandoEseguito) {
		//do nothing
	}

	public List<AbstractCommand> getListaComandiInterna() {
		return listaComandiInterna;
	}
	
	public int getIndiceCorrente() {
		return indiceCorrente;
	}
}
