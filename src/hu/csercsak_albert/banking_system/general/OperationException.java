package hu.csercsak_albert.banking_system.general;

public class OperationException extends Exception {

	private static final long serialVersionUID = 1L;

	public OperationException() {
	}

	public OperationException(String message) {
		super(message);
	}

}
