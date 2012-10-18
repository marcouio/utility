package messaggi;

import java.util.Locale;
import java.util.ResourceBundle;

import xml.CoreXMLManager;

public class I18NManager {

	public static void main(final String[] args) {
//		final String prova = Controllore.getSingleton().getMessaggio("titolo",
//				new String[] { "primoP", "secondoP", "terzoP" });
//		System.out.println(prova);
	}

	private static I18NManager singleton;
	private Locale currentLocale;
	private ResourceBundle messages;

	/**
	 * @return the singleton
	 */
	public static I18NManager getSingleton() {
		if (singleton == null) {
			singleton = new I18NManager();
		}
		return singleton;
	}

	private I18NManager() {

	}

	public String getMessaggio(final String key) {
		try {
			if (this.getMessages() == null) {
				this.caricaMessaggi(CoreXMLManager.getSingleton().getLanguage(), null);
			}
			return this.getMessages().getString(key);
		} catch (final Exception e) {
			return key;
		}
	}

	public String getMessaggio(final String key, final String[] params) {
		try {
			String msgTot = "";
			final String messaggio = getMessaggio(key);
			final String[] msgSplit = messaggio.split("@");
			if (msgSplit.length - 1 == params.length) {
				for (int i = 0; i < params.length; i++) {
					msgTot += msgSplit[i] + params[i];
					if (i == params.length - 1) {
						msgTot += msgSplit[msgSplit.length - 1];
					}
				}
				// lo split non funziona quando il regex e' alla fine
			} else if (msgSplit.length == params.length && messaggio.endsWith("@")) {
				for (int i = 0; i < params.length; i++) {
					msgTot += msgSplit[i] + params[i];
				}
			}
			return msgTot;
		} catch (final Exception e) {
			return key;
		}
	}

	private void creaLocale(final String language, final String country) {
		if (language != null && country != null) {
			setLocale(language, country);
		} else if (language != null) {
			setLocale(language);
		} else {
			currentLocale = Locale.ITALIAN;
		}

	}

	public void setLocale(final String language) {
		currentLocale = new Locale(language);
	}

	public void setLocale(final String language, final String country) {
		currentLocale = new Locale(language, country);
	}

	public void caricaMessaggi(final String language, final String country) {
		creaLocale(language, country);
		setMessages(ResourceBundle.getBundle(CoreXMLManager.getSingleton().getFileMessaggiName(), currentLocale));
	}

	public void setMessages(final ResourceBundle messages) {
		this.messages = messages;
	}

	public ResourceBundle getMessages() {
		return messages;
	}
}
