package main.java.com.molinari.utility.common;

/**
 * thanks to
 * http://blog.taragana.com/index.php/archive/core-java-how-to-get-java
 * -source-code-line-number-file-name-in-code/!
 * 
 * @author klm
 * 
 */
public class LineStackTracePrinter {

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
		String className = stackTraces[6].getClassName();
		String methodName = stackTraces[6].getMethodName();
		return className + " (" + methodName + ")";
	}

	public static String scriviLineaDellErrore(final int nStack) {
		final String msg = " Errore sul file: " + LineStackTracePrinter.getFileName(nStack) + " line " + LineStackTracePrinter.getLineNumber(nStack);
		return msg;
	}

	public static void scriviLineeErrore() {
		for (int i = 2; i < 6; i++) {
			final String msg = " Errore sul file: " + LineStackTracePrinter.getFileName(i) + " line " + LineStackTracePrinter.getLineNumber(i);
			System.err.println(msg);
		}
	}
}
