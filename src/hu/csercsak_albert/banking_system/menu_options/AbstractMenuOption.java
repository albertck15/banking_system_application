package hu.csercsak_albert.banking_system.menu_options;

import java.sql.Connection;
import java.sql.SQLException;

import hu.csercsak_albert.banking_system.main.MenuOption;
import hu.csercsak_albert.banking_system.main.UserInput;

abstract class AbstractMenuOption implements MenuOption {

	protected Connection connection;
	protected UserInput userInput;

	AbstractMenuOption() {

	}

	@Override
	public void setup(Connection connection, UserInput userInput) {
		this.connection = connection;
		this.userInput = userInput;
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
