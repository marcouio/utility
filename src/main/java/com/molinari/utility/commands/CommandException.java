package main.java.com.molinari.utility.commands;

public class CommandException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CommandException(Exception e) {
		super(e);
	}

}
