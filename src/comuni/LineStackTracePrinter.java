package comuni;

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
	public static int getLineNumber() {
		return Thread.currentThread().getStackTrace()[4].getLineNumber();
	}

	public static String getFileName() {
		StackTraceElement[] stackTraces = Thread.currentThread().getStackTrace();
		String className = stackTraces[4].getClassName();
		String methodName = stackTraces[4].getMethodName();
		return className + " (" + methodName + ")";
	}

	public static String scriviLineaDellErrore() {
		final String msg = " Errore sul file: " + LineStackTracePrinter.getFileName() + " line "
				+ LineStackTracePrinter.getLineNumber();
		return msg;
	}
}
