package hu.csercsak_albert.banking_system.validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hu.csercsak_albert.banking_system.menu.Validator;

public class UsernameValidator implements Validator {

	private static final Logger LOG = LogManager.getLogger(UsernameValidator.class);

	private static final UsernameValidator INSTANCE = new UsernameValidator();

	private UsernameValidator() {
		LOG.info("initialized");
	}

	public static UsernameValidator getInstance() {
		LOG.info("returning singleton instace");
		return INSTANCE;
	}

	private static final int MIN_USERNAME_LENGTH = 3;

	@Override
	public boolean validate(String username) {
		LOG.info("validating : " + username);
		return username.length() >= MIN_USERNAME_LENGTH;
	}

}
