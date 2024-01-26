package hu.csercsak_albert.banking_system.main;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import hu.csercsak_albert.banking_system.general.FastQuitException;
import hu.csercsak_albert.banking_system.general.OperationException;
import hu.csercsak_albert.banking_system.menu.MenuImpl;

public class Main {

	public static void main(String[] args) {
		new Main().run();
	}

	private void run() {
		var th = new TextHolder();
		th.welcome();
		try (var connection = getConnection(); var userInput = new UserInput()) {
			Menu menu = new MenuImpl.Builder().setConnection(connection) //
					.setUserInput(userInput) //
					.setPrompt("-->") //
					.addOptions(OptionTypes.values()) //
					.build();
			chooseAndExecute(menu);
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage() + "!");
		} catch (FastQuitException e) {
		}
		th.goodbye();
	}

	private void chooseAndExecute(Menu mainMenu) throws FastQuitException, SQLException {
		try {
			for (MenuOption option; (option = mainMenu.choose()) != null;) {
				option.execute();
				System.out.println();
			}
		} catch (OperationException e) {
			System.out.println(e.getMessage() + "!");
		}
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
