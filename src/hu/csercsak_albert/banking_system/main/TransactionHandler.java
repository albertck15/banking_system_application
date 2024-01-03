package hu.csercsak_albert.banking_system.main;

import java.sql.Connection;
import java.sql.SQLException;

import hu.csercsak_albert.banking_system.general.OperationException;
import hu.csercsak_albert.banking_system.general.Transaction;

public interface TransactionHandler {

	void makeTransaction(Connection connection, Transaction transaction) throws OperationException, SQLException;

	void makeWithdraw(Connection connection, int userId, int amount) throws OperationException, SQLException;

	void makeDeposit(Connection connection, int userId, int amount) throws OperationException, SQLException;
}
