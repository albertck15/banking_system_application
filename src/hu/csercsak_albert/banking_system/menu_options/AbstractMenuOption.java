package hu.csercsak_albert.banking_system.menu_options;

import java.sql.Connection;
import java.sql.SQLException;

import hu.csercsak_albert.banking_system.general.OperationException;
import hu.csercsak_albert.banking_system.main.MenuOption;
import hu.csercsak_albert.banking_system.main.User;
import hu.csercsak_albert.banking_system.main.UserInput;

abstract class AbstractMenuOption implements MenuOption {

	protected Connection connection;
	protected UserInput userInput;
	protected User user;
	protected final String label;

	AbstractMenuOption(String label) {
		this.label = label;
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

	@Override
	public String getLabel() {
		return label;
	}

	abstract void doExecute() throws OperationException, SQLException;

}
