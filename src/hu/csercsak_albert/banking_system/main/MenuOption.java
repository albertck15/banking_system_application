package hu.csercsak_albert.banking_system.main;

import java.sql.Connection;
import java.sql.SQLException;

public interface MenuOption {

	String getLabel();

	void execute() throws SQLException;

	void setup(Connection connection, UserInput userInput, User user);
}
