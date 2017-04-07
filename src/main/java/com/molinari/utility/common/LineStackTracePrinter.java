package com.molinari.utility.common;

import java.util.logging.Level;

import com.molinari.utility.controller.ControlloreBase;

/**
 * thanks to
 * http://blog.taragana.com/index.php/archive/core-java-how-to-get-java
 * -source-code-line-number-file-name-in-code/!
 * 
 * @author klm
 * 
 */
public class LineStackTracePrinter {
	
	private LineStackTracePrinter(){
		//do nothing
	}

	// original tutorial suggests items [2]
	// from arrays, but [3] works for android.
	// there you go..

	/**
	 * Get the current line number.
	 * 
	 * @return int - Current line number.
	 */
	public static int getLineNumber(final int nStack) {
		return Thread.currentThread().getStackTrace()[nStack].getLineNumber();
	}

	public static String getFileName(final int nStack) {
		StackTraceElement[] stackTraces = Thread.currentThread().getStackTrace();
		String className = stackTraces[nStack].getClassName();
		String methodName = stackTraces[nStack].getMethodName();
		return className + " (" + methodName + ")";
	}

	public static String scriviLineaDellErrore(final int nStack) {
		StringBuilder sb = new StringBuilder();
		sb.append(" Errore sul file: ");
		sb.append(LineStackTracePrinter.getFileName(nStack));
		sb.append(" line ");
		sb.append(LineStackTracePrinter.getLineNumber(nStack));
		return sb.toString();
	}

	public static void scriviLineeErrore() {
		for (int i = 2; i < 6; i++) {
			final String msg = " Errore sul file: " + LineStackTracePrinter.getFileName(i) + " line " + LineStackTracePrinter.getLineNumber(i);
			ControlloreBase.getLog().log(Level.SEVERE, msg);
		}
	}
}
