package ioutil;
import java.io.File;

public class IoUtil {

	public static String[] deleteFileDaDirectory2(String Dir) {
		File dir = new File(Dir);
		String[] files = null;
		if (dir != null && dir.isDirectory()) {
			files = dir.list();

			for (int i = 0; i < files.length; i++) {
				File f = new File(dir, files[i]);
				f.delete();
			}
		}
		return files;
	}

	public static String[] deleteFileDaDirectory(String Dir, String treCharIniziali) {
		File dir = new File(Dir);
		String[] files = dir.list();

		for (int i = 0; i < files.length; i++) {
			File f = new File(dir, files[i]);
			if (f.isDirectory() == false
			                && f.getName().substring(0, 3).equals(treCharIniziali)) {
				f.delete();
			}

		}
		return files;
	}
}
