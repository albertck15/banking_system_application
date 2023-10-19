package hu.csercsak_albert.banking_system.main;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import hu.csercsak_albert.banking_system.login.LoginMenuContactPointImpl;

public class Main {

	public static void main(String[] args) {
		new Main().run();
	}

	private void run() {
		var th = new TextHolder();
		th.welcome();
		try (var connection = getConnection(); var userInput = new UserInput()) {
			User user = loginOrRegister(connection, userInput);
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage() + "!");
		}
	}

	private User loginOrRegister(Connection connection, UserInput userInput) throws SQLException {
		LoginMenu menu = new LoginMenuContactPointImpl().getLoginMenu(connection, userInput);
		return menu.loginOrRegister();
	}

	private Connection getConnection() throws SQLException {
		Properties properties = new Properties();
		try (var fis = new FileInputStream("./resources/connection.properties")) {
			properties.load(fis);
		} catch (IOException e) {
			System.out.println(e.getMessage() + "!");
			throw new RuntimeException();
		}
		return DriverManager.getConnection("jdbc:mysql://localhost:3306/albert_banking_system", properties);
	}
}
