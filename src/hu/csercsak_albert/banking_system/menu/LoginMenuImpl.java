package hu.csercsak_albert.banking_system.menu;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Random;

import hu.csercsak_albert.banking_system.general.OperationException;
import hu.csercsak_albert.banking_system.main.User;
import hu.csercsak_albert.banking_system.main.UserInput;
import hu.csercsak_albert.banking_system.validator.EmailValidator;
import hu.csercsak_albert.banking_system.validator.PasswordValidator;
import hu.csercsak_albert.banking_system.validator.UsernameValidator;

class LoginMenuImpl {

	private final Connection connection;
	private final UserInput userInput;
	private final UsernameValidator usernameValidator = UsernameValidator.getInstance();
	private final PasswordValidator pwValidator = PasswordValidator.getInstance();
	private final EmailValidator emailValidator = EmailValidator.getInstance();

	private final String menuText = """
			 1. Login
			 2. Register
			""";

	LoginMenuImpl(Connection connection, UserInput userInput) {
		this.connection = connection;
		this.userInput = userInput;
	}

	User loginOrRegister() throws SQLException {
		User user = null;
		System.out.println(menuText);
		do {
			try {
				if (userInput.inputInt("Choose", 1, 2) == 1) {
					user = login();
				} else {
					user = register();
				}
			} catch (OperationException e) {
				System.out.printf("%s!%n%n", e.getMessage());
			}
		} while (user == null);
		return user;
	}

	private User login() throws SQLException, OperationException {
		String username = null;
		do {
			username = userInput.inputText("Username");
			if (!isUserExists(username) && userInput.inputBool("\n Username not found! Would you like to register with this username")) {
				return register(username);
			}
		} while (username == null);
		return login(username);
	}

	private User login(String username) throws SQLException {
		User user = null;
		do {
			String password = String.valueOf(userInput.inputText("Password").hashCode());
			try {
				user = loginUser(username, password);
			} catch (OperationException e) {
				System.out.println(e.getMessage() + "!");
			}
		} while (user == null);
		return user;
	}

	private User register() throws SQLException, OperationException {
		String username;
		do {
			username = userInput.inputText("Username");
		} while (!usernameValidator.validate(username));
		return register(username);
	}

	private User register(String username) throws SQLException, OperationException {
		if (isUserExists(username) && !userInput.inputBool("\n Username already exists! Would you like to login")) { // TODO Not working correctly.
			throw new OperationException("You've stopped the login process");
		} else if (isUserExists(username)) {
			return login(username);
		}
		String password;
		do {
			password = userInput.inputText("Password");
		} while (!pwValidator.validate(password));
		password = String.valueOf(password.hashCode()); // NOT SAFE! Just to imitate 'hashing'
		String firstName = userInput.inputText("First name");
		String lastName = userInput.inputText("Last name");
		String email;
		do {
			email = userInput.inputText("Email address");
		} while (!emailValidator.validate(email));
		int accountNumber = generateNumber();
		User user = new User(0, username, firstName, lastName, email, accountNumber); // dummy ID
		if (registerNewUser(password, user)) {
			setDefaultBalance(user);
			return user;
		}
		throw new UnsupportedOperationException("Registering new user has been failed");
	}

	private void setDefaultBalance(User user) throws SQLException {
		try (var ps = connection.prepareStatement("INSERT INTO balance(user_id,balance) VALUES(?,?)")) {
			ps.setInt(1, getUserId(user));
			ps.setLong(2, 0);
			ps.executeUpdate();
		}
	}

	private int getUserId(User user) throws SQLException {
		try (var ps = connection.prepareStatement("SELECT id FROM user WHERE account_number = ?")) {
			ps.setInt(1, user.accountNumber());
			try (var rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1);
				}
			}
		}
		throw new UnsupportedOperationException("Getting user ID has been failed");
	}

	private boolean registerNewUser(String hashedPassword, User user) throws SQLException {
		try (var ps = connection.prepareStatement("""
				INSERT INTO user(username, password, first_name, last_name, email, account_number)
				VALUES(?, ?, ?, ?, ?, ?);
								""")) {
			ps.setString(1, user.username());
			ps.setString(2, hashedPassword);
			ps.setString(3, user.firstName());
			ps.setString(4, user.lastName());
			ps.setString(5, user.email());
			ps.setInt(6, user.accountNumber());
			ps.executeUpdate();
		}
		return isUserExists(user.username()); // Checking if the registration was successful
	}

	private User loginUser(String username, String password) throws SQLException, OperationException {
		try (var ps = connection.prepareStatement("""
				SELECT id, username, first_name, last_name, email, account_number FROM user
				WHERE username = ? AND password = ?
				""")) {
			ps.setString(1, username);
			ps.setString(2, password);
			try (var rs = ps.executeQuery()) {
				if (rs.next()) {
					// User(ID, Username, First name, Last name, Email, Account number)
					return new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6));
				}
			}
		}
		throw new OperationException("Incorrect password");
	}

	private int generateNumber() throws SQLException {
		Random random = new Random();
		int number;
		do {
			number = random.nextInt(9_999_999);
		} while (isAccountNumberAvailable(number));
		return number;
	}

	private boolean isUserExists(String username) throws SQLException {
		try (var ps = connection.prepareStatement("SELECT COUNT(*) FROM user WHERE username = ?")) {
			ps.setString(1, username);
			try (var rs = ps.executeQuery()) {
				return rs.next() && rs.getInt(1) == 1;
			}
		}
	}

	private boolean isAccountNumberAvailable(int number) throws SQLException {
		try (var ps = connection.prepareStatement("SELECT COUNT(*) as Count FROM user WHERE account_number = ?")) {
			ps.setInt(1, number);
			try (var rs = ps.executeQuery()) {
				return rs.next() && rs.getInt(1) == 1;
			}
		}
	}
}
