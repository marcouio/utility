package com.molinari.utility.io;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.molinari.utility.controller.ControlloreBase;

/**
 * Questa classe serve per eseguire la stessa operazione su piu file. Scorre
 * tutti i file, anche andando in profondita ed esegue l'operazione implementata
 * nelle classi figlie.
 * 
 * @author marco.molinari
 * 
 */
public abstract class EsecutoreBasePiuFile {

	private final ArrayList<String> cartelleDaScorrere = new ArrayList<>();

	/**
	 * Questo metodo e' solo una parte di codice ricorrente che scorre tutte le
	 * cartelle in profondita'� per tutti i file che raggiunge eseguo un metodo
	 * che va implementato a secondo dell'uso che se ne vuole fare
	 * 
	 * @param pathFile
	 * @return
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	public boolean scorriEdEseguiSuTuttiIFile(String pathFilePar) throws ParserConfigurationException, SAXException {
		String pathFile = pathFilePar;
		final boolean ok = true;
		if (!pathFile.substring(pathFile.length() - 1, pathFile.length()).equals(UtilIo.slash())) {
			pathFile += UtilIo.slash();
		}
		final File dir = new File(pathFile);
		final String[] files = dir.list();

		for (final String file : files) {
			final File f = new File(pathFile + file);

			if (checkFile(f)) {
				if (f.isFile() && f.canRead()) {
					try {
						operazioneDaEseguireSulFile(pathFile, f);
					} catch (final Exception e) {
						// tag non presente
						ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
					}
				} else if (f.isDirectory()) {
					cartelleDaScorrere.add(f.getAbsolutePath());
				}
			}
		}
		
		if (cartelleDaScorrere != null && !cartelleDaScorrere.isEmpty()) {
			final String path = cartelleDaScorrere.get(0);
			cartelleDaScorrere.remove(0);
			scorriEdEseguiSuTuttiIFile(path);
		}
		operazioneFinale();
		return ok;
	}
	
	public static void main(String[] args) throws Exception {
		EsecutoreBasePiuFile esecutoreBasePiuFile = new EsecutoreBasePiuFile() {
			
			@Override
			public void operazioneDaEseguireSulFile(String pathFile, File f) {
				ControlloreBase.getLog().log(Level.INFO, "Sto scorrendo il file: " +f.getName());
				
			}
		};
		
		esecutoreBasePiuFile.scorriEdEseguiSuTuttiIFile("/home/kiwi/Immagini");
	}

	/**
	 * se necessario un controllo sulla validita' del file eseguire l'override
	 * del metodo.
	 * 
	 * @param f
	 * @return
	 */
	public boolean checkFile(final File f) {
		ControlloreBase.getLog().log(Level.INFO, "Sto eseguendo il check per il file: " +f.getName());
		return true;
	}

	/**
	 * Se alla fine dello scorrimento dei file � necessario eseguire
	 * un'operazione finale, overridare questo metodo.
	 */
	public void operazioneFinale() {

	}

	/**
	 * Operazione centrale della classe, deve essere implementato con
	 * l'operazione da eseguire su ogni singolo file.
	 * 
	 * @param pathFile
	 * @param f
	 */
	public abstract void operazioneDaEseguireSulFile(String pathFile, File f);

}
