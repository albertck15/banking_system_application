package hu.csercsak_albert.banking_system.validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hu.csercsak_albert.banking_system.menu.Validator;

public class EmailValidator implements Validator {

	private static final Logger LOG = LogManager.getLogger(EmailValidator.class);

	private static final EmailValidator INSTANCE = new EmailValidator();

	private EmailValidator() {
		LOG.info("initialized");
	}

	public static EmailValidator getInstance() {
		LOG.info("returning singleton instance");
		return INSTANCE;
	}

	@Override
	public boolean validate(String email) {
		LOG.info("validating : " + email);
		return email.matches("^[A-Za-z0-9+_.-]+@(.+)\\.[A-Za-z]+$");
	}

}
