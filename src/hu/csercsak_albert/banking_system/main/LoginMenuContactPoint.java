package hu.csercsak_albert.banking_system.main;

import java.sql.Connection;
import java.sql.SQLException;

public interface LoginMenuContactPoint {

	LoginMenu getLoginMenu(Connection connection, UserInput userInput) throws SQLException;
}
