package hu.csercsak_albert.banking_system.login;

import java.sql.Connection;
import java.sql.SQLException;

import hu.csercsak_albert.banking_system.main.LoginMenu;
import hu.csercsak_albert.banking_system.main.LoginMenuContactPoint;
import hu.csercsak_albert.banking_system.main.UserInput;

public class LoginMenuContactPointImpl implements LoginMenuContactPoint {

	@Override
	public LoginMenu getLoginMenu(Connection connection, UserInput userInput) throws SQLException {
		return new LoginMenuImpl(connection, userInput);
	}

}
