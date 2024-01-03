package hu.csercsak_albert.banking_system.transaction;

import java.sql.Connection;
import java.sql.SQLException;

import hu.csercsak_albert.banking_system.general.OperationException;
import hu.csercsak_albert.banking_system.general.Transaction;
import hu.csercsak_albert.banking_system.main.TransactionHandler;

public class TransactionHandlerImpl implements TransactionHandler {

	private static final TransactionHandlerImpl INSTANCE = new TransactionHandlerImpl();

	private TransactionHandlerImpl() {
	}

	public static TransactionHandlerImpl getInstance() {
		return INSTANCE;
	}

	// *********************************
	// Transaction from user to user
	//

	@Override
	public void makeTransaction(Connection connection, Transaction transaction) throws OperationException, SQLException {
		int fromId = transaction.from().id();
		int toId = transaction.to().id();
		if (isAmountAvailable(connection, fromId, transaction.amount())) {
			updateBalance(connection, fromId//
					, getBalance(connection, fromId) - transaction.amount());
			updateBalance(connection, toId//
					, getBalance(connection, toId) + transaction.amount());
			System.out.printf(" Your new balance : $%,d%n%n", getBalance(connection, fromId));
		} else {
			throw new OperationException("Your balance doesn't have enough amount!%n%n");
		}

	}

	// *********************************
	// Withdraw
	//

	@Override
	public void makeWithdraw(Connection connection, int userId, int amount) throws OperationException, SQLException {
		long balance = getBalance(connection, userId);
		if (balance < amount) {
			System.out.printf("Your balance doesn't have enough amount!%n%n");
		} else {
			long newBalance = balance - amount;
			updateBalance(connection, userId, newBalance);
		}
		System.out.printf("%n Your new balance : $%,d%n%n", getBalance(connection, userId));
	}

	// *********************************
	// Deposit
	//

	@Override
	public void makeDeposit(Connection connection, int userId, int amount) throws OperationException, SQLException {
		updateBalance(connection, userId, amount + getBalance(connection, userId));
		System.out.printf("%n Your new balance : $%,d%n", getBalance(connection, userId));
	}

	// *********************************
	// Helper methods
	//

	private boolean isAmountAvailable(Connection connection, int userId, int amount) throws SQLException {
		return getBalance(connection, userId) >= amount;
	}

	private int getBalance(Connection connection, int userId) throws SQLException {
		try (var ps = connection.prepareStatement("SELECT balance FROM balance WHERE user_id = ?")) {
			ps.setInt(1, userId);
			try (var rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1);
				}
			}
		}
		throw new UnsupportedOperationException("Getting balance has been failed");
	}

	private void updateBalance(Connection connection, int userId, long newBalance) throws SQLException {
		try (var ps = connection.prepareStatement("UPDATE balance SET balance = ? WHERE user_id = ?")) {
			ps.setLong(1, newBalance);
			ps.setInt(2, userId);
			ps.executeUpdate();
		}
	}
}
