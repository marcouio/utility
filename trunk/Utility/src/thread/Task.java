package thread;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import thread.richieste.ManagerRichieste;
import thread.richieste.RichiestaThread;

public abstract class Task {
	
	private ManagerThread managerThread;
	private List<RichiestaThread> richieste;
	private Date dataInizioTask;
	private Date dataFineTask;
	
    public List<RichiestaThread> getRichieste() throws Exception{
    	if(richieste == null){
    		
    		richieste = creaListaRichiestaThread(creaRichieste()); 
    	}
    	return richieste;
    }
    
    public ArrayList<RichiestaThread> creaListaRichiestaThread(List<?> lista){
    	ArrayList<RichiestaThread> listaRic = new ArrayList<RichiestaThread>();
    	if(lista != null){
	    	for (Object object : lista) {
				RichiestaThread richThread = new RichiestaThread();
				richThread.setRichiesta(object);
				listaRic.add(richThread);
			}
    	}
    	return listaRic;
    }
    
    protected abstract List<?> creaRichieste() throws Exception;

	public abstract Class<?> getCallableClass();
    
    protected void unsafeRunTask() throws Exception {
    	dataInizioTask = new Date(System.currentTimeMillis());
    	if(canExecute()){
    		eseguiTask();
    	}
    }
    
    public boolean isTaskArrestabile() {
    	return true;
    }
    
    public ManagerThread getManagerThread() throws Exception{
    	if(managerThread == null){
    		managerThread = new ManagerThread(getRichieste(), getCallableClass());
    	}
    	return managerThread;
    }
    
    protected boolean canExecute() {
		return true;
	}

	public void eseguiTask() throws Exception{
            
			ManagerThread manager = getManagerThread();
			
            manager.setTask(this);
            manager.eseguiProcesso();
    }
    
    
	public Date getDataInizioTask() {
		return dataInizioTask;
	}

	public void setDataInizioTask(Date dataInizioTask) {
		this.dataInizioTask = dataInizioTask;
	}

	public static void main(String[] args) throws Exception {
         
    }
    
	public void postExecute(ManagerRichieste managerRichieste) throws Exception{
		dataFineTask = new Date(System.currentTimeMillis());
		
		int totaleRichieste = managerRichieste.getTotaleRichieste();
		System.out.println("Totale richieste da eseguire: " + totaleRichieste);
		
		int richiesteEseguite = managerRichieste.getNumRichiesteEseguite();
		System.out.println("Totale richieste eseguite: " + richiesteEseguite);
		
		int richiesteScartate = managerRichieste.getNumRichiesteScartate();
		System.out.println("Totale richieste scartate: " + richiesteScartate);
		
		//TODO
//		String time = UtilityCalendario.getStringDifferenzaTimeStamp(new TimeStamp(dataFineTask), new TimeStamp(dataInizioTask));
		String time = "";
		String msg = "Tempo di elaborazione del task: " + time;
		System.out.println(msg);
	}

	public void preExecute(CopyOnWriteArrayList<?> listaRichieste) throws Exception {
		
	}

    public Date getDataFineTask() {
        return dataFineTask;
    }

    public void setDataFineTask(Date dataFineTask) {
        this.dataFineTask = dataFineTask;
    }
}