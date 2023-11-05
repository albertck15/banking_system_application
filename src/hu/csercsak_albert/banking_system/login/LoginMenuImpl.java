package hu.csercsak_albert.banking_system.login;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Random;

import hu.csercsak_albert.banking_system.general.OperationException;
import hu.csercsak_albert.banking_system.main.LoginMenu;
import hu.csercsak_albert.banking_system.main.Menu;
import hu.csercsak_albert.banking_system.main.OptionTypes;
import hu.csercsak_albert.banking_system.main.User;
import hu.csercsak_albert.banking_system.main.UserInput;

class LoginMenuImpl implements LoginMenu {

	private static final int MIN_USERNAME_LENGTH = 3;

	private static final int MIN_PASSWORD_LENGTH = 7;

	private static final String SPECIAL_CHARS = "@#$&*_-+=<>";

	private final Connection connection;
	private final UserInput userInput;
	private final OptionTypes[] options;

	private final String menuText = """
			 1. Login
			 2. Register
			""";

	public LoginMenuImpl(Connection connection, UserInput userInput, OptionTypes... options) {
		this.connection = connection;
		this.userInput = userInput;
		this.options = options;
	}

	@Override
	public Menu loginOrRegister() throws SQLException {
		User user;
		System.out.println(menuText);
		if (userInput.inputInt("Choose one", 1, 2) == 1) {
			user = login();
		} else {
			user = register();
		}
		return MenuImpl.get(user, userInput, options);
	}

	private User login() throws SQLException {
		String username = null;
		do {
			username = userInput.inputText("Username");
			if (!isUserExists(username) && userInput.inputBool("\n Username not found! Would you like to register with this username")) {
				return register(username);
			}
		} while (username == null);
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

	private User register() throws SQLException {
		String username;
		do {
			username = userInput.inputText("Username");
		} while (username.length() <= MIN_USERNAME_LENGTH);
		return register(username);
	}

	private User register(String username) throws SQLException {
		String password;
		do {
			password = userInput.inputText("Password");
		} while (!checkPw(password));
		password = String.valueOf(password.hashCode()); // NOT SAFE! Just to imitate 'hashing'
		String firstName = userInput.inputText("First name");
		String lastName = userInput.inputText("Last name");
		String email;
		do {
			email = userInput.inputText("Email address");
		} while (!checkEmail(email));
		int accountNumber = generateNumber();
		User user = new User(0, username, firstName, lastName, email, accountNumber); // dummy ID
		if (registerNewUser(password, user)) {
			setDefaultBalance(user);
			return user;
		}
		throw new UnsupportedOperationException("Registering new user has been failed");
	}

	private void setDefaultBalance(User user) throws SQLException {
		try (var ps = connection.prepareStatement("INSERT INTO balance(userId,balance) VALUES(?,?)")) {
			ps.setInt(1, getUserId(user));
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

	// *******************************************
	// Validating
	// //TODO Make validator for this

	private boolean checkPw(String password) {
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
		return hasNumber && hasUppercase && hasSpecial;
	}

	private boolean checkEmail(String email) {
		return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
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
