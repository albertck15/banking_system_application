package hu.csercsak_albert.banking_system.validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hu.csercsak_albert.banking_system.menu.Validator;

public class PasswordValidator implements Validator {

	private static final Logger LOG = LogManager.getLogger(PasswordValidator.class);

	private static final PasswordValidator INSTANCE = new PasswordValidator();

	private PasswordValidator() {
		LOG.info("initialized");
	}

	public static PasswordValidator getInstance() {
		LOG.info("returning singleton instance");
		return INSTANCE;
	}

	private static final int MIN_PASSWORD_LENGTH = 7;

	private static final String SPECIAL_CHARS = "@#$&*_-+=<>";

	@Override
	public boolean validate(String password) {
		LOG.info("validating password");
		if (password.length() < MIN_PASSWORD_LENGTH) {
			LOG.info("password too short");
			System.out.printf("%n Minimum password length = %d%n%n",MIN_PASSWORD_LENGTH);
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
			LOG.info("password not strong enough");
			System.out.printf("%n Password should contain a number, uppercase letter and a special character%n%n");
		}
		return hasNumber && hasUppercase && hasSpecial;
	}

}
