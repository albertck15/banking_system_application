package hu.csercsak_albert.banking_system.menu_options;

import java.sql.Connection;
import java.sql.SQLException;

import hu.csercsak_albert.banking_system.main.MenuOption;
import hu.csercsak_albert.banking_system.main.User;
import hu.csercsak_albert.banking_system.main.UserInput;

abstract class AbstractMenuOption implements MenuOption {

	protected Connection connection;
	protected UserInput userInput;
	protected User user;

	AbstractMenuOption() {
	}

	@Override
	public void setup(Connection connection, UserInput userInput, User user) {
		this.connection = connection;
		this.userInput = userInput;
		this.user = user;
	}

	@Override
	public void execute() throws SQLException {
		try {
			doExecute();
		} catch (OperationException e) {
			System.out.println(e.getMessage() + "!");
		}
	}

	abstract void doExecute() throws OperationException, SQLException;

}
