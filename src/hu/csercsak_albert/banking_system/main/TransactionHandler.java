package hu.csercsak_albert.banking_system.main;

import java.sql.Connection;
import java.sql.SQLException;

import hu.csercsak_albert.banking_system.general.OperationException;
import hu.csercsak_albert.banking_system.transaction.Transaction;

public interface TransactionHandler {

	void makeTransaction(Connection connection, Transaction transaction) throws OperationException, SQLException;

	void makeDeposit(Connection connection, User user, int amount) throws OperationException, SQLException;

	void makeWithdraw(Connection connection, User user, int amount) throws OperationException, SQLException;
}
