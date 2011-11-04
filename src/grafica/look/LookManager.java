package grafica.look;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class LookManager {

	public static void setLook(final String lookString) {
		final LookAndFeelInfo[] looks = UIManager.getInstalledLookAndFeels();
		for (LookAndFeelInfo look : looks) {
			if (look.getName().equals(lookString)) {
				try {
					UIManager.setLookAndFeel(look.getClassName());
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
