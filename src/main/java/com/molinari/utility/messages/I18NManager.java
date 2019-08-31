package com.molinari.utility.messages;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.stream.Collectors;

import com.molinari.utility.controller.ControlloreBase;
import com.molinari.utility.xml.CoreXMLManager;

public class I18NManager {

	private static I18NManager singleton;
	private Locale currentLocale;
	private List<ResourceBundle> messages;
	CollectorBundle collectorBundle = new CollectorBundle();

	private I18NManager() {
		//do nothing
	}
	/**
	 * @return the singleton
	 */
	public static I18NManager getSingleton() {
		if (singleton == null) {
			singleton = new I18NManager();
		}
		return singleton;
	}


	public String getMessaggio(final String key) {
		try {
			if (this.getMessages() == null) {
				this.caricaMessaggi(CoreXMLManager.getSingleton().getLanguage(), null);
			}
			
			Optional<ResourceBundle> firstRB = getMessages().stream().filter(rb -> rb.containsKey(key)).findFirst();
			if(firstRB.isPresent()) {
				return firstRB.get().getString(key);
			}
			
		} catch (final Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(),e);
		}
		return key;
	}

	public String getMessaggio(final String key, final String... params) {
		try {
			String messaggio = getMessaggio(key);
			
			for (String par : params) {
				messaggio = messaggio.replaceFirst("@", par);
			}
			return messaggio;
		} catch (final Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(),e);
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
		List<String> fileMessaggiName = CoreXMLManager.getSingleton().getFileMessaggiName();
		messages = fileMessaggiName.parallelStream().map(f -> getBundle(f)).collect(Collectors.toList());
		
	}
	private ResourceBundle getBundle(String f) {
		return getCollectorBundle().getBundle(f, currentLocale);
	}

	public List<ResourceBundle> getMessages() {
		return messages;
	}
	public CollectorBundle getCollectorBundle() {
		return collectorBundle;
	}
}
