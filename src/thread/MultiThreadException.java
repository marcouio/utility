package thread;

import java.util.concurrent.ExecutionException;

public class MultiThreadException extends ExecutionException {

	private static final long	serialVersionUID	= 1L;

	boolean irrecuperabile;

	public MultiThreadException(String message, Exception e, boolean irrecuperabile) {
		super(message, e);
		setIrrecuperabile(irrecuperabile);
	}
	
	public MultiThreadException(Exception e,  boolean irrecuperabile) {
		super(e);
		setIrrecuperabile(irrecuperabile);
	}

	/**
	 * @return the irrecuperabile
	 */
	public boolean isIrrecuperabile() {
		return irrecuperabile;
	}

	/**
	 * @param irrecuperabile the irrecuperabile to set
	 */
	public void setIrrecuperabile(boolean irrecuperabile) {
		this.irrecuperabile = irrecuperabile;
	}
	
}
