- Copiare ed incollare i file config-style.xml e config-core.xml direttamente dentro il progetto che si sta creando

Il Controller

- Creare un Controllore che estende uno dei 'ControlloreBase'. 
  Il Controllore dovrà avere costruttore privato, singleton con relativo getter:
	
	private static Controllore singleton;

	public static Controllore getSingleton() {
		if (singleton == null) {
			singleton = new Controllore();
		}
		return singleton;
	}
	 
 - Sarà necessario crearsi un main da cui far partire l'applicazione. 
	Il main tramite il singleton dovrà chiamare il metodo mymain della classe ControlloreBase in questa maniera:

	public static void main(final String[] args) {
		Controllore.getSingleton().myMain(Controllore.getSingleton(), true, "myApplication");
	}
	
- Copiare il file messaggi all'interno della cartella "src" del progetto

- la grafica va inserita all'interno del metodo mainOverridato(FrameBase).
	Va considerato che il frame principale dell'applicazione è già istanziato ed è il parametro del metodo.
	E' quindi possibile prenderlo per eventuali settaggi.

Disegno
- Se si vorranno sfruttare le potenzialità del disegno il vostro Controller dovrà estendere ControlloreDisegno. 