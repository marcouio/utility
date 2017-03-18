package main.java.com.molinari.utility.thread;

public class RunnerCiao extends RunnerBase{

	public RunnerCiao(ManagerThread manager, Object parametro) {
		super(manager, parametro);
	}

	@Override
	public void runEsteso() {
		String nome = (String) getParametro();
		System.out.println("Ciao, sono " + nome);
	}

}
