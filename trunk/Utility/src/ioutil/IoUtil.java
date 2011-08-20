package ioutil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class IoUtil {

	public static String[] deleteFileDaDirectory2(final String Dir) {
		final File dir = new File(Dir);
		String[] files = null;
		if (dir != null && dir.isDirectory()) {
			files = dir.list();

			for (int i = 0; i < files.length; i++) {
				final File f = new File(dir, files[i]);
				f.delete();
			}
		}
		return files;
	}

	public static String[] deleteFileDaDirectory(final String Dir, final String treCharIniziali) {
		final File dir = new File(Dir);
		final String[] files = dir.list();

		for (int i = 0; i < files.length; i++) {
			final File f = new File(dir, files[i]);
			if (f.isDirectory() == false && f.getName().substring(0, 3).equals(treCharIniziali)) {
				f.delete();
			}

		}
		return files;
	}

	public static void scriviFileSuPiuRighe(final File file, final ArrayList<String> righe) {
		try {
			final BufferedWriter out = new BufferedWriter(new FileWriter(file));
			for (final Iterator<String> iterator = righe.iterator(); iterator.hasNext();) {
				final String type = iterator.next();
				out.write(type);
				out.newLine();
			}
			out.close();
		} catch (final IOException e) {
		}
	}
}
