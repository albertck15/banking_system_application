package hu.csercsak_albert.banking_system.menu_options;

import java.sql.Connection;
import java.sql.SQLException;

import hu.csercsak_albert.banking_system.main.MenuOption;
import hu.csercsak_albert.banking_system.main.OptionTypes;
import hu.csercsak_albert.banking_system.main.User;
import hu.csercsak_albert.banking_system.main.UserInput;

class MONotImplemented implements MenuOption {

	private final String label;

	MONotImplemented(OptionTypes type) {
		this.label = type.getLabel() + " (Under development)";
	}

	@Override
	public void execute() throws SQLException {
		System.out.println("Currently not available.");
	}

	@Override
	public void setup(Connection connection, UserInput userInput, User user) {
	}

	@Override
	public String getLabel() {
		return label;
	}
}
