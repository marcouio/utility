package com.molinari.utility.graphic.look;

import java.util.logging.Level;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import com.molinari.utility.controller.ControlloreBase;

public class LookManager {

	public static void setLook(final String lookString) {
		final LookAndFeelInfo[] looks = UIManager.getInstalledLookAndFeels();
		for (LookAndFeelInfo look : looks) {
			if (look.getName().equals(lookString)) {
				try {
					UIManager.setLookAndFeel(look.getClassName());
				} catch (final Exception e) {
					ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
				}
			}
		}
	}
}
