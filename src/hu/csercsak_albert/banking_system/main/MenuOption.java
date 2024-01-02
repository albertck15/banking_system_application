package hu.csercsak_albert.banking_system.main;

import java.sql.Connection;
import java.sql.SQLException;

import hu.csercsak_albert.banking_system.general.FastQuitException;

public interface MenuOption {

	String getLabel();

	void execute() throws SQLException, FastQuitException;

	void setup(Connection connection, UserInput userInput, User user);
}
