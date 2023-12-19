package hu.csercsak_albert.banking_system.main;

import java.sql.Connection;

public interface TransactionHandler {

	void makeTransaction(Connection connection, Transaction transaction);
}
