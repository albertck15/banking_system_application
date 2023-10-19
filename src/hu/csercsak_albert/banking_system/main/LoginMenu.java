package hu.csercsak_albert.banking_system.main;

import java.sql.SQLException;

public interface LoginMenu {

	User loginOrRegister() throws SQLException;
}
