package hu.csercsak_albert.banking_system.validator;

import hu.csercsak_albert.banking_system.login.Validator;

public class UsernameValidator implements Validator {

	private static final UsernameValidator INSTANCE = new UsernameValidator();

	private UsernameValidator() {
	}

	public static UsernameValidator getInstance() {
		return INSTANCE;
	}

	private static final int MIN_USERNAME_LENGTH = 3;

	@Override
	public boolean validate(String username) {
		return username.length() >= MIN_USERNAME_LENGTH;
	}

}
