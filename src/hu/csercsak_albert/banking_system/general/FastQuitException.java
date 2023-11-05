package hu.csercsak_albert.banking_system.general;

public class FastQuitException extends Exception {

	private static final long serialVersionUID = 1L;

	public FastQuitException() {
		super();
	}

	public FastQuitException(String message) {
		super(message);
	}

}
