package hu.csercsak_albert.banking_system.validator;

import hu.csercsak_albert.banking_system.login.Validator;

public class EmailValidator implements Validator {

	private static final EmailValidator INSTANCE = new EmailValidator();

	private EmailValidator() {
	}

	public static EmailValidator getInstance() {
		return INSTANCE;
	}

	@Override
	public boolean validate(String email) {
		return email.matches("^[A-Za-z0-9+_.-]+@(.+)\\.[A-Za-z]+$");
	}

}
