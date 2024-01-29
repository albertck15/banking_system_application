package hu.csercsak_albert.banking_system.main;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hu.csercsak_albert.banking_system.general.FastQuitException;
import hu.csercsak_albert.banking_system.general.OperationException;
import hu.csercsak_albert.banking_system.menu.MenuImpl;

public class Main {

	private static final Logger LOG = LogManager.getLogger(Main.class);

	public static void main(String[] args) {
		new Main().run();
	}

	private void run() {
		LOG.info("Application starts");
		var th = new TextHolder();
		th.welcome();
		try (var connection = getConnection(); var userInput = new UserInput()) {
			LOG.info("Connection, UserInput objects are initialized");
			Menu menu = new MenuImpl.Builder().setConnection(connection) //
					.setUserInput(userInput) //
					.setPrompt("-->") //
					.addOptions(OptionTypes.values()) //
					.build();
			LOG.info("Main menu builded up");
			chooseAndExecute(menu);
		} catch (SQLException e) {
			LOG.error("SQL Exception (%s)".formatted(e.getMessage()));
			throw new RuntimeException(e.getMessage() + "!");
		} catch (FastQuitException e) {
			LOG.info("User quit with FastQuitException or Quit menu option");
		}
		th.goodbye();
		LOG.info("Application stops");
	}

	private void chooseAndExecute(Menu mainMenu) throws FastQuitException, SQLException {
		try {
			for (MenuOption option; (option = mainMenu.choose()) != null;) {
				LOG.info("Menu option (%s) choosed".formatted(option.getLabel()));
				option.execute();
				System.out.println();
			}
		} catch (OperationException e) {
			LOG.error("Operation Exception (%s)".formatted(e.getMessage()));
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
