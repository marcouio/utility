package thread;

import java.util.ArrayList;

public class TaskProva extends Task {

	@Override
	public Class<? extends RunnerBase> getRunnbleClass() {
		return RunnerCiao.class;
	}
	
	@Override
	public ArrayList<?> getRichieste() {
		ArrayList<Object> lista = new ArrayList<>();
		for (int i = 0; i < 30; i++) {
			lista.add("thr" + i);
		}
		
		
		return lista;
	}

}
