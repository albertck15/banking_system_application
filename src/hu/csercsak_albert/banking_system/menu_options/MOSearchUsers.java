package hu.csercsak_albert.banking_system.menu_options;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hu.csercsak_albert.banking_system.general.FastQuitException;
import hu.csercsak_albert.banking_system.general.OperationException;
import hu.csercsak_albert.banking_system.main.OptionTypes;
import hu.csercsak_albert.banking_system.main.User;

class MOSearchUsers extends AbstractMenuOption {

	private static final Logger LOG = LogManager.getLogger(MOSearchUsers.class);

	MOSearchUsers(OptionTypes type) {
		super(type.getLabel());
		LOG.info("initialized");
	}

	@Override
	void doExecute() throws OperationException, SQLException, FastQuitException {
		System.out.printf("%n│ You can use * as a wildcard character%n%n");
		List<User> users = new ArrayList<>();
		String firstName = userInput.inputText("Enter the user's first name");
		LOG.info("Searching for : " + firstName);
		try (var ps = connection
				.prepareStatement("SELECT id, username, first_name, last_name, email, account_number FROM user WHERE first_name LIKE ?")) {
			ps.setString(1, firstName.replace('*', '%'));
			try (var rs = ps.executeQuery()) {
				while (rs.next()) {
					users.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6)));
				}
			}
		}
		LOG.info("Found %d user".formatted(users.size()));
		printResult(users);
	}

	private void printResult(List<User> users) {
		LOG.info("printing found users");
		System.out.println();
		try {
			users.forEach(u -> System.out.println("""
					│
					│ Name : %s %s
					│ Account number : %,d
					│
					""".formatted(u.firstName(), u.lastName(), u.accountNumber())));
		} catch (NullPointerException e) {
			LOG.info("User not found with this name");
			System.out.printf("│ User not found!");
		}
	}
}
