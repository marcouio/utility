package aggiornatori;

public class AggiornatoreManager {

	public static final String AGGIORNA_ENTRATE = "entrate";
	public static final String AGGIORNA_USCITE = "uscite";
	public static final String AGGIORNA_ALL = "all";
	public static final String AGGIORNA_NULLA = "nulla";

	private static AggiornatoreManager singleton;

	public static AggiornatoreManager getSingleton() {
		if (singleton == null) {
			singleton = new AggiornatoreManager();
		}
		return singleton;
	}

	private AggiornatoreManager() {
	}

	public IAggiornatore creaAggiornatore(final String classe) {
		IAggiornatore aggiornatore = null;
		try {
			aggiornatore = classe != null ? (IAggiornatore) Class.forName(classe).newInstance() : new AggiornatoreBase();
		} catch (final Exception e) {
			aggiornatore = new AggiornatoreBase();
		}
		return aggiornatore;
	}
}
