package hu.csercsak_albert.banking_system.validator;

import hu.csercsak_albert.banking_system.menu.Validator;

public class PasswordValidator implements Validator {

	private static final PasswordValidator INSTANCE = new PasswordValidator();

	private PasswordValidator() {
	}

	public static PasswordValidator getInstance() {
		return INSTANCE;
	}

	private static final int MIN_PASSWORD_LENGTH = 7;

	private static final String SPECIAL_CHARS = "@#$&*_-+=<>";

	@Override
	public boolean validate(String password) {
		if (password.length() < MIN_PASSWORD_LENGTH) {
			System.out.println("Minimum password length = " + MIN_PASSWORD_LENGTH);
			return false;
		}
		boolean hasNumber = false;
		boolean hasUppercase = false;
		boolean hasSpecial = false;
		for (char ch : password.toCharArray()) {
			if (Character.isDigit(ch)) {
				hasNumber = true;
			} else if (Character.isUpperCase(ch)) {
				hasUppercase = true;
			} else if (SPECIAL_CHARS.indexOf(ch) >= 0) {
				hasSpecial = true;
			}
		}
		if(!hasNumber || !hasUppercase || !hasSpecial) {
			System.out.printf("%nPassword should contain a number, uppercase letter and a special character%n%n");
		}
		return hasNumber && hasUppercase && hasSpecial;
	}

}
